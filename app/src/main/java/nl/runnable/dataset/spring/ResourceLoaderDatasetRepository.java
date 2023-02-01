package nl.runnable.dataset.spring;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.runnable.dataset.Dataset;
import nl.runnable.dataset.DatasetRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;
import java.util.stream.Stream;

@Component
@Slf4j
@RequiredArgsConstructor
class ResourceLoaderDatasetRepository implements DatasetRepository {

    private final ResourcePatternResolver resourcePatternResolver;

    private String directory;

    @Value("${xslt-datasets.directory}")
    public void setDirectory(String directory) {
        while (directory.endsWith("/")) {
            directory = directory.substring(0, directory.length() - 1);
        }
        this.directory = directory;
    }

    @Override
    public Optional<Dataset> find(String name) {
        final var csv = resourcePatternResolver.getResource("%s/%s.csv".formatted(directory, name));
        if (csv.exists()) {
            return Optional.of(Dataset.forCsv(resolveName(csv), csv));
        }

        final var xml = resourcePatternResolver.getResource("%s/%s.xml".formatted(directory, name));
        if (xml.exists()) {
            final var hasHtmlFormat = stylesheetExists("%s.html".formatted(name));
            final var hasPdfFormat = stylesheetExists("%s.fo".formatted(name));
            return Optional.of(Dataset.forXml(name, xml, hasHtmlFormat, hasPdfFormat));
        }

        return Optional.empty();
    }

    @Override
    public Stream<Dataset> findAll() {
        try {
            final var csvs = Stream.of(resourcePatternResolver.getResources("%s/*.csv".formatted(directory)))
                    .map(csv -> Dataset.forCsv(resolveName(csv), csv));
            final var xmls = Stream.of(resourcePatternResolver.getResources("%s/*.xml".formatted(directory)))
                    .map(xml -> {
                        final var name = resolveName(xml);
                        final var hasHtmlFormat = stylesheetExists("%s.html".formatted(name));
                        final var hasPdfFormat = stylesheetExists("%s.fo".formatted(name));
                        return Dataset.forXml(name, xml, hasHtmlFormat, hasPdfFormat);
                    });
            return Stream.concat(csvs, xmls).sorted((a, b) -> a.name().compareToIgnoreCase(b.name()));
        } catch (IOException e) {
            log.warn("Error resolving resources: {}", e.getMessage());
            return Stream.empty();
        }
    }

    @Override
    public boolean stylesheetExists(@NonNull String name) {
        final var xslt = resourcePatternResolver.getResource("%s/%s.xslt".formatted(directory, name));
        return xslt.exists();
    }


    @NonNull
    private static String resolveName(@NonNull Resource resource) {
        var name = resource.getFilename();
        if (name == null || name.lastIndexOf('.') == -1) {
            return "";
        }
        name = name.substring(0, name.lastIndexOf('.'));
        return name;
    }

}

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
@RequiredArgsConstructor
@Slf4j
class ResourceLoaderDatasetRepository implements DatasetRepository {

    private final ResourcePatternResolver resourcePatternResolver;

    @Value("${xslt-datasets.directory}")
    private String directory;

    @Override
    public Optional<Dataset> find(String name) {
        final var filename = "%s/%s.csv".formatted(directory, name);
        final var resource = resourcePatternResolver.getResource(filename);
        if (resource.exists()) {
            return Optional.of(new Dataset(resolveName(resource), resource));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Stream<Dataset> findAll() {
        try {
            final var pattern = "%s/*.csv".formatted(directory);
            return Stream.of(resourcePatternResolver.getResources(pattern))
                    .map(resource -> {
                        String name = resolveName(resource);
                        return new Dataset(name, resource);
                    });
        } catch (IOException e) {
            log.warn("Error resolving resources: {}", e.getMessage());
            return Stream.empty();
        }
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

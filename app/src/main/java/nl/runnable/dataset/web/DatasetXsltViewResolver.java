package nl.runnable.dataset.web;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.view.AbstractUrlBasedView;
import org.springframework.web.servlet.view.xslt.XsltView;
import org.springframework.web.servlet.view.xslt.XsltViewResolver;

import javax.xml.transform.OutputKeys;
import java.util.*;

class DatasetXsltViewResolver extends XsltViewResolver {

    private static final String DATASET_PREFIX = "dataset:";

    static String datasetViewName(String viewName) {
        return "%s%s".formatted(DATASET_PREFIX, viewName);
    }

    private final Map<String, String> mimeTypesBySuffix = new HashMap<>();

    @Value("${xslt-datasets.directory}")
    private String datasetDirectory;

    public DatasetXsltViewResolver() {
        mimeTypesBySuffix.put("xml", "text/xml");
    }

    @Override
    protected boolean canHandle(String viewName, Locale locale) {
        return !viewName.matches("^.+\\.fo$");
    }

    @Override
    protected AbstractUrlBasedView buildView(String viewName) throws Exception {
        final var isDatasetViewName = viewName.startsWith(DATASET_PREFIX);
        if (isDatasetViewName) {
            viewName = viewName.substring(DATASET_PREFIX.length());
        }

        final var view = (XsltView) super.buildView(viewName);
        if (isDatasetViewName) {
            view.setUrl("%s%s%s".formatted(datasetDirectory, viewName, getSuffix()));
        }

        resolveContentType(viewName).ifPresent(contentType -> {
            final var properties = new Properties();
            properties.setProperty(OutputKeys.MEDIA_TYPE, contentType);
            view.setOutputProperties(properties);
        });

        return view;
    }

    private Optional<String> resolveContentType(@NonNull String viewName) {
        final var suffix = viewName.replaceFirst("^.+\\.(\\w+)$", "$1");
        return mimeTypesBySuffix.containsKey(suffix) ?
                Optional.of(mimeTypesBySuffix.get(suffix)) : Optional.empty();
    }

}

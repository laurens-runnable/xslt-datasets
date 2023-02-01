package nl.runnable.dataset.web;

import lombok.NonNull;
import org.springframework.web.servlet.view.AbstractUrlBasedView;
import org.springframework.web.servlet.view.xslt.XsltView;
import org.springframework.web.servlet.view.xslt.XsltViewResolver;

import javax.xml.transform.OutputKeys;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;

/**
 * {@link XsltViewResolver} that configures the stylesheet output's output content type based on the
 * view name suffix.
 */
class ContentTypeXsltViewResolver extends XsltViewResolver {

    private final Map<String, String> mimeTypesBySuffix = new HashMap<>();

    public ContentTypeXsltViewResolver() {
        mimeTypesBySuffix.put("xml", "text/xml");
        mimeTypesBySuffix.put("fo", "application/pdf");
    }

    @Override
    protected AbstractUrlBasedView buildView(String viewName) throws Exception {
        final var view = (XsltView) super.buildView(viewName);
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

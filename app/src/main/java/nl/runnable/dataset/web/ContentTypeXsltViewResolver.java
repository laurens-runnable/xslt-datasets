package nl.runnable.dataset.web;

import lombok.NonNull;
import org.springframework.web.servlet.view.AbstractUrlBasedView;
import org.springframework.web.servlet.view.xslt.XsltView;
import org.springframework.web.servlet.view.xslt.XsltViewResolver;

import javax.xml.transform.OutputKeys;
import java.util.Optional;
import java.util.Properties;

class ContentTypeXsltViewResolver extends XsltViewResolver {

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

    private static Optional<String> resolveContentType(@NonNull String viewName) {
        final var format = viewName.replaceFirst("^.+\\.(\\w+)$", "$1");
        if (format.equals("xml")) {
            return Optional.of("text/xml");
        } else if (format.equals("fo")) {
            return Optional.of("application/pdf");
        }
        return Optional.empty();
    }

}

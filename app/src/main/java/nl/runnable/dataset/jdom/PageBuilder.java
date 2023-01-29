package nl.runnable.dataset.jdom;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
class PageBuilder {

    @Value("classpath:/fragments/navigation.xml")
    private Resource navigationXml;

    Builder createPage(@NonNull String title) {
        return new Builder(title);
    }

    private Element resolveNavigationElement() {
        try {
            var builder = new SAXBuilder();
            final var navigationDocument = builder.build(navigationXml.getInputStream());
            return navigationDocument.getRootElement().detach();
        } catch (JDOMException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public class Builder {

        private final String title;

        private final List<Element> contentElements = new ArrayList<>();

        private Builder(@NonNull String title) {
            this.title = title;
        }

        public Builder withContent(@NonNull Element element) {
            contentElements.add(element);
            return this;
        }

        public Element build() {
            final var pageElement = new Element("page");
            pageElement.addContent(new Element("title").addContent(title));
            pageElement.addContent(resolveNavigationElement());

            final var contentElement = new Element("content");
            pageElement.addContent(contentElement);
            contentElements.forEach(contentElement::addContent);
            return pageElement;
        }

    }
}

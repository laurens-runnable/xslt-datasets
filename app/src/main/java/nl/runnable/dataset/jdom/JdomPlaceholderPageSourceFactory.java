package nl.runnable.dataset.jdom;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import nl.runnable.dataset.PlaceholderPageSourceFactory;
import org.jdom2.transform.JDOMSource;
import org.springframework.stereotype.Component;

import javax.xml.transform.Source;

@Component
@RequiredArgsConstructor
class JdomPlaceholderPageSourceFactory implements PlaceholderPageSourceFactory {

    private final PageBuilder pageBuilder;

    @Override
    public Source createPageSource(@NonNull String title) {
        return new JDOMSource(pageBuilder.createPage(title).build());
    }

}

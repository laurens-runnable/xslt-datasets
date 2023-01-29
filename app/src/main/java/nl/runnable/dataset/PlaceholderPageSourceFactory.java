package nl.runnable.dataset;

import javax.xml.transform.Source;

public interface PlaceholderPageSourceFactory {

    Source createPageSource(String pageId);

}

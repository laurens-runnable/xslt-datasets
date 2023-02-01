package nl.runnable.dataset.fop;

import lombok.RequiredArgsConstructor;
import org.apache.xmlgraphics.io.Resource;
import org.apache.xmlgraphics.io.ResourceResolver;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;

@Component
@RequiredArgsConstructor
class ResourceLoaderResourceResolver implements ResourceResolver {

    private final ResourceLoader resourceLoader;

    @Override
    public Resource getResource(URI uri) throws IOException {
        return new Resource(resourceLoader.getResource(uri.toString()).getInputStream());
    }

    @Override
    public OutputStream getOutputStream(URI uri) throws IOException {
        throw new UnsupportedEncodingException();
    }
}

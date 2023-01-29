package nl.runnable.dataset.xml;

import org.xml.sax.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Abstract base class for XMLReader implementations.
 */
public abstract class AbstractXMLReader implements XMLReader {

    protected ContentHandler contentHandler;
    protected DTDHandler dtdHandler;
    protected EntityResolver entityResolver;
    protected ErrorHandler errorHandler;
    protected Map<String, Boolean> featureMap = new HashMap<>();
    protected Map<String, Object> propertyMap = new HashMap<>();

    public void setContentHandler(ContentHandler newContentHandler) {
        contentHandler = newContentHandler;
    }

    public void setDTDHandler(DTDHandler newDTDHandler) {
        dtdHandler = newDTDHandler;
    }

    public void setEntityResolver(EntityResolver newEntityResolver) {
        entityResolver = newEntityResolver;
    }

    public void setErrorHandler(ErrorHandler newErrorHandler) {
        errorHandler = newErrorHandler;
    }

    public void setFeature(String name, boolean value) {
        featureMap.put(name, value);
    }

    public void setProperty(String name, Object value) {
        propertyMap.put(name, value);
    }

    public ContentHandler getContentHandler() {
        return contentHandler;
    }

    public DTDHandler getDTDHandler() {
        return dtdHandler;
    }

    public EntityResolver getEntityResolver() {
        return entityResolver;
    }

    public ErrorHandler getErrorHandler() {
        return errorHandler;
    }

    public boolean getFeature(String name) {
        var b = featureMap.get(name);
        return Objects.requireNonNullElse(b, false);
    }

    public Object getProperty(String name) {
        return propertyMap.get(name);
    }

}

package nl.runnable.dataset;

import org.springframework.core.io.Resource;

public record Dataset(String name, Resource resource, Type type,
                      boolean hasHtmlFormat, boolean hasPdfFormat, boolean hasCsvFormat) {

    public enum Type {
        CSV,
        XML,
    }

    public static Dataset forCsv(String name, Resource resource) {
        return new Dataset(name, resource, Type.CSV, true, true, true);
    }

    public static Dataset forXml(String name, Resource resource, boolean hasHtmlFormat, boolean hasPdfFormat) {
        return new Dataset(name, resource, Type.XML, hasHtmlFormat, hasPdfFormat, false);
    }

}

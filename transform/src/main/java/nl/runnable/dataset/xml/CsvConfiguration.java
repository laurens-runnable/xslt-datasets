package nl.runnable.dataset.xml;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.function.Function;

@Getter
@Setter
public class CsvConfiguration {

    private char separator = ',';

    private char quoteChar = '"';

    private boolean ignoreQuotations = false;

    private boolean ignoreLeadingWhitespace = true;

    private String csvNamespaceUri = "http://runnable.nl/dataset/csv";

    private String csvNamespacePrefix = "csv";

    private String csvRootElement = "data";

    private String csvRowElement = "row";

    private String contentNamespaceUri = "http://runnable.nl/dataset/content";

    private String contentNamespacePrefix = "content";

    private Function<String, String> headerElementName = CsvConfiguration::kebabCase;

    public static String kebabCase(@NonNull String header) {
        return header.trim().toLowerCase().replaceAll("\\s+", "-");
    }

}

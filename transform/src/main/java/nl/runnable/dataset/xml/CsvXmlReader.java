package nl.runnable.dataset.xml;

import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * XMLReader implementation that converts CSV data to SAX events.
 */
@Slf4j
public class CsvXmlReader extends AbstractXMLReader {

    private final CsvConfiguration configuration;

    public CsvXmlReader(CsvConfiguration configuration) {
        this.configuration = configuration;
    }

    public CsvXmlReader() {
        this(new CsvConfiguration());
    }

    @Override
    public void parse(InputSource input) throws IOException, SAXException {
        if (contentHandler == null) {
            throw new IllegalStateException("ContentHandler is not configured");
        }

        readCsv(input);
    }

    private void readCsv(@NonNull InputSource input) throws IOException, SAXException {
        var csvParser = new CSVParserBuilder()
                .withSeparator(configuration.getSeparator())
                .withQuoteChar(configuration.getQuoteChar())
                .withIgnoreQuotations(configuration.isIgnoreQuotations())
                .withIgnoreLeadingWhiteSpace(configuration.isIgnoreLeadingWhitespace())
                .build();

        try (var csvReader = new CSVReaderBuilder(input.getCharacterStream())
                .withCSVParser(csvParser)
                .build()) {

            String[] headers;
            try {
                headers = csvReader.readNext();
                for (int i = 0; i < headers.length; i++) {
                    headers[i] = configuration.getHeaderElementName().apply(headers[i]);
                }
            } catch (CsvValidationException e) {
                throw new SAXException("CSV does not contain headers");
            }

            final var attributes = new AttributesImpl();

            contentHandler.startDocument();

            final var csvNamespaceUri = configuration.getCsvNamespaceUri();
            final var csvRootElement = configuration.getCsvRootElement();
            final var csvRootQName = "%s:%s".formatted(configuration.getCsvNamespacePrefix(), csvRootElement);
            contentHandler.startElement(csvNamespaceUri, csvRootElement, csvRootQName, attributes);

            String[] fields;
            do {
                try {
                    fields = csvReader.readNext();
                    if (fields != null && headers.length == fields.length) {
                        final var csvRowQName = "%s:%s".formatted(configuration.getCsvNamespacePrefix(), configuration.getCsvRowElement());
                        contentHandler.startElement(csvNamespaceUri, configuration.getCsvRowElement(), csvRowQName, attributes);
                        for (int i = 0; i < fields.length; i++) {
                            var header = headers[i];
                            var field = fields[i];

                            final var contentNamespaceUri = configuration.getContentNamespaceUri();
                            final var headerQName = "%s:%s".formatted(configuration.getContentNamespacePrefix(), header);
                            contentHandler.startElement(contentNamespaceUri, header, headerQName, attributes);
                            contentHandler.characters(field.toCharArray(), 0, field.length());
                            contentHandler.endElement(contentNamespaceUri, header, headerQName);
                        }
                        contentHandler.endElement(csvNamespaceUri, configuration.getCsvRowElement(), csvRowQName);
                    }
                } catch (CsvValidationException e) {
                    throw new SAXException(e.getMessage());
                }
            } while (fields != null);

            contentHandler.endElement(csvNamespaceUri, csvRootElement, csvRootQName);

            contentHandler.endDocument();
        }
    }

    @Override
    public void parse(String systemId) throws IOException, SAXException {
        throw new UnsupportedEncodingException("Not supported");
    }
}

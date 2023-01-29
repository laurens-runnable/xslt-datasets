package nl.runnable.dataset.xml;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xmlunit.builder.DiffBuilder;
import org.xmlunit.builder.Input;

import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.sax.SAXSource;
import java.io.InputStream;
import java.io.InputStreamReader;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
public class CsvXmlReaderTests {

    @Test
    public void convertsCsvToXml() {
        assertXmlHasNoDifferences("skills.csv", "skills.xml");
    }

    @Test
    public void detectsInvalidHeaders() {
        final var exception = assertThrows(TransformerException.class, () -> {
                    final var csv = getClass().getResourceAsStream("invalid-headers.csv");
                    parseCsv(csv);
                }
        );
        assertInstanceOf(SAXException.class, exception.getCause());
        assertTrue(exception.getCause().getMessage().contains("2022"));
    }

    private void assertXmlHasNoDifferences(String csvPath, String xmlPath) {
        try {
            final var csv = getClass().getResourceAsStream(csvPath);
            final var document = parseCsv(csv);

            final var xml = getClass().getResourceAsStream(xmlPath);
            final var diff = DiffBuilder
                    .compare(Input.fromDocument(document))
                    .withTest(Input.fromStream(xml))
                    .ignoreWhitespace()
                    .build();

            for (var difference : diff.getDifferences()) {
                log.error(difference.toString());
            }
            assertFalse(diff.hasDifferences());
        } catch (TransformerException e) {
            throw new RuntimeException(e);
        }
    }

    private static Document parseCsv(InputStream inputStream) throws TransformerException {
        final var inputSource = new InputSource(new InputStreamReader(inputStream));
        final var saxSource = new SAXSource(inputSource);
        saxSource.setXMLReader(new CsvXmlReader());

        final var result = new DOMResult();

        final var transformer = TransformerFactory.newInstance().newTransformer();
        transformer.transform(saxSource, result);

        return (Document) result.getNode();
    }
}

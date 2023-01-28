package nl.runnable.dataset.xml;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xmlunit.builder.DiffBuilder;
import org.xmlunit.builder.Input;

import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.sax.SAXSource;
import java.io.InputStream;
import java.io.InputStreamReader;

import static org.junit.jupiter.api.Assertions.assertFalse;

@Slf4j
public class CsvXmlReaderTests {

    @Test
    public void skillsExample() {
        assertXmlHasNoDifferences("skills.csv", "skills.xml");
    }

    private void assertXmlHasNoDifferences(String sourcePath, String testPath) {
        final var source = getClass().getResourceAsStream(sourcePath);
        final var test = getClass().getResourceAsStream(testPath);
        final var document = parseCsv(source);

        final var diff = DiffBuilder
                .compare(Input.fromDocument(document))
                .withTest(Input.fromStream(test))
                .ignoreWhitespace()
                .build();

        for (var difference : diff.getDifferences()) {
            log.error(difference.toString());
        }
        assertFalse(diff.hasDifferences());
    }

    private static Document parseCsv(InputStream inputStream) {
        try {
            final var inputSource = new InputSource(new InputStreamReader(inputStream));
            final var saxSource = new SAXSource(inputSource);
            saxSource.setXMLReader(new CsvXmlReader());

            final var result = new DOMResult();

            final var transformer = TransformerFactory.newInstance().newTransformer();
            transformer.transform(saxSource, result);

            return (Document) result.getNode();
        } catch (TransformerException e) {
            throw new RuntimeException(e);
        }
    }
}

package nl.runnable.dataset.jdom;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.runnable.dataset.Dataset;
import nl.runnable.dataset.DatasetSourceFactory;
import nl.runnable.dataset.xml.CsvXmlReader;
import org.jdom2.Element;
import org.jdom2.transform.JDOMResult;
import org.jdom2.transform.JDOMSource;
import org.springframework.stereotype.Component;
import org.xml.sax.InputSource;

import javax.xml.transform.Source;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamSource;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
@Slf4j
class JdomDatasetSourceFactory implements DatasetSourceFactory {

    private final PageBuilder pageBuilder;

    @Override
    public Source createIndexPageSource(Stream<Dataset> datasets) {
        final var datasetsElement = new Element("datasets");
        datasets.map(dataset -> {
                    final var element = new Element("dataset");
                    element.setAttribute("name", dataset.name());
                    element.setAttribute("type", dataset.type().name());
                    element.setAttribute("hasHtmlFormat", Boolean.toString(dataset.hasHtmlFormat()));
                    element.setAttribute("hasPdfFormat", Boolean.toString(dataset.hasPdfFormat()));
                    element.setAttribute("hasCsvFormat", Boolean.toString(dataset.hasCsvFormat()));
                    return element;
                })
                .forEach(datasetsElement::addContent);

        final var pageElement = pageBuilder
                .createPage("Datasets")
                .withContent(datasetsElement)
                .build();
        return new JDOMSource(pageElement);
    }

    @Override
    public Source createDatasetPageSource(@NonNull Dataset dataset) {
        try {
            final var source = createDatasetXmlSource(dataset);
            final var transformer = TransformerFactory.newInstance().newTransformer();
            final var result = new JDOMResult();
            transformer.transform(source, result);
            final var element = result.getDocument().getRootElement().detach();

            final var pageElement = pageBuilder
                    .createPage(dataset.name())
                    .withContent(element)
                    .build();
            return new JDOMSource(pageElement);
        } catch (TransformerException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Source createDatasetXmlSource(Dataset dataset) {
        try {
            return switch (dataset.type()) {
                case CSV -> {
                    final InputSource inputSource = new InputSource(new InputStreamReader(dataset.resource().getInputStream()));
                    final var saxSource = new SAXSource(inputSource);
                    saxSource.setXMLReader(new CsvXmlReader());
                    yield saxSource;
                }
                case XML -> new StreamSource(dataset.resource().getInputStream());
            };
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}

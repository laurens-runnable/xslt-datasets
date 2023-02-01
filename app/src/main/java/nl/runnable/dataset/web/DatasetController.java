package nl.runnable.dataset.web;

import lombok.RequiredArgsConstructor;
import nl.runnable.dataset.Dataset;
import nl.runnable.dataset.DatasetRepository;
import nl.runnable.dataset.DatasetSourceFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.xml.transform.Source;
import java.util.Map;
import java.util.NoSuchElementException;

import static nl.runnable.dataset.web.DatasetXsltViewResolver.datasetViewName;

@Controller
@RequestMapping("/datasets")
@RequiredArgsConstructor
public class DatasetController {

    private final DatasetRepository datasetRepository;

    private final DatasetSourceFactory datasetSourceFactory;

    @GetMapping
    public ModelAndView index() {
        final var modelAndView = new ModelAndView("dataset/index.html");
        final var datasets = datasetRepository.findAll();
        modelAndView.addObject(datasetSourceFactory.createIndexPageSource(datasets));
        return modelAndView;
    }

    @GetMapping("/{name}.html")
    public ModelAndView datasetHtml(@PathVariable(value = "name") String name) {
        final var dataset = datasetRepository.find(name).orElseThrow();

        var viewName = "%s.html".formatted(name);
        final Source source;
        if (datasetRepository.stylesheetExists(viewName)) {
            viewName = datasetViewName(viewName);
            source = datasetSourceFactory.createDatasetXmlSource(dataset);
        } else {
            viewName = "dataset/table.html";
            source = datasetSourceFactory.createDatasetPageSource(dataset);
        }

        return new ModelAndView(viewName, Map.of("source", source));
    }

    @GetMapping("/{name}.pdf")
    public ModelAndView datasetPdf(@PathVariable(value = "name") String name) {
        final var dataset = datasetRepository.find(name).orElseThrow();
        final var source = datasetSourceFactory.createDatasetXmlSource(dataset);
        return new ModelAndView("dataset/table.fo", Map.of("source", source));
    }

    @GetMapping("/{name}.xml")
    public ModelAndView datasetXml(@PathVariable(value = "name") String name) {
        final var dataset = datasetRepository.find(name).orElseThrow();
        final var source = datasetSourceFactory.createDatasetXmlSource(dataset);
        return new ModelAndView("dataset/identity.xml", Map.of("source", source));
    }

    @GetMapping("/{name}.csv")
    public ModelAndView datasetCsv(@PathVariable(value = "name") String name) {
        final var dataset = datasetRepository.find(name).orElseThrow();
        if (dataset.type() != Dataset.Type.CSV) {
            throw new NoSuchElementException();
        }

        final var modelAndView = new ModelAndView();
        modelAndView.setView(new ResourceView(dataset.resource(), "text/csv"));
        return modelAndView;
    }

    @ModelAttribute("sortColumn")
    public int sortColumn(@RequestParam(value = "sort", defaultValue = "1") int sort) {
        return sort;
    }

    @ModelAttribute("sortOrder")
    public String sortOrder(@RequestParam(value = "asc", defaultValue = "true") boolean asc) {
        return asc ? "ascending" : "descending";
    }

    @ModelAttribute("pageOrientation")
    public String pageOrientation(@RequestParam(value = "portrait", defaultValue = "true") boolean portrait) {
        return portrait ? "portrait" : "landscape";
    }

}

package nl.runnable.dataset.web;

import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class DatasetControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void indexShouldYieldStatusOk() throws Exception {
        final var html = mockMvc.perform(get("/datasets"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andReturn().getResponse().getContentAsString();

        final var htmlCleaner = new HtmlCleaner();
        final var document = htmlCleaner.clean(new StringReader(html));
        final var anchors = document.evaluateXPath("//table/tbody/tr/td[1]/a");
        assertEquals(2, anchors.length);
        assertEquals("/datasets/items.html", ((TagNode) anchors[0]).getAttributeByName("href"));
        assertEquals("/datasets/skills.html", ((TagNode) anchors[1]).getAttributeByName("href"));
    }

    @Test
    public void htmlResourceForCsvDatasetShouldYieldStatusOk() throws Exception {
        mockMvc.perform(get("/datasets/skills.html"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML));
    }

    @Test
    public void htmlResourceForXmlDatasetShouldYieldStatusOk() throws Exception {
        final var html = mockMvc.perform(get("/datasets/items.html"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andReturn().getResponse().getContentAsString();

        final var htmlCleaner = new HtmlCleaner();
        final var document = htmlCleaner.clean(new StringReader(html));
        final var listItems = document.evaluateXPath("//ul/li");
        assertEquals(3, listItems.length);
    }

    @Test
    public void xmlResourceForCsvDatasetShouldYieldStatusOk() throws Exception {
        mockMvc.perform(get("/datasets/skills.xml"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_XML));
    }

    @Test
    public void xmlResourceForXmlDatasetShouldYieldStatusOk() throws Exception {
        mockMvc.perform(get("/datasets/skills.xml"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_XML));
    }

    @Test
    public void pdfResourceForCsvDatasetShouldYieldStatusOk() throws Exception {
        mockMvc.perform(get("/datasets/skills.pdf"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/pdf"));
    }

    @Test
    public void csvResourceForCsvDatasetShouldYieldStatusOk() throws Exception {
        mockMvc.perform(get("/datasets/skills.csv"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/csv"));
    }

    @Test
    public void csvResourceForXmlDatasetShouldYieldStatusNotFound() throws Exception {
        mockMvc.perform(get("/datasets/items.csv"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void nonExistingResourceShouldYieldStatusNotFound() throws Exception {
        mockMvc.perform(get("/datasets/nonexisting.html"))
                .andExpect(status().isNotFound());
    }

}

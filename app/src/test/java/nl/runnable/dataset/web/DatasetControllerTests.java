package nl.runnable.dataset.web;

import org.jsoup.Jsoup;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
                .andReturn().getResponse().getContentAsString();

        final var doc = Jsoup.parse(html);
        final var tr = doc.select("table > tbody > tr");
        assertEquals(1, tr.size());
        final var td = tr.first().select("td");
        assertEquals(5, td.size());
        assertTrue(td.get(0).select("a[href]").attr("href").contains("skills.html"));
        assertTrue(td.get(1).select("a[href]").attr("href").contains("skills.xml"));
        assertTrue(td.get(2).select("a[href]").attr("href").contains("skills.pdf"));
        assertTrue(td.get(3).select("a[href]").attr("href").contains("skills.pdf?portrait=false"));
        assertTrue(td.get(4).select("a[href]").attr("href").contains("skills.csv"));
    }

    @Test
    public void htmlResourceShouldYieldStatusOk() throws Exception {
        mockMvc.perform(get("/datasets/skills.html"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML));
    }

    @Test
    public void xmlResourceShouldYieldStatusOk() throws Exception {
        mockMvc.perform(get("/datasets/skills.xml"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_XML));
    }

    @Test
    public void pdfResourceShouldYieldStatusOk() throws Exception {
        mockMvc.perform(get("/datasets/skills.pdf"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/pdf"));
    }

    @Test
    public void csvResourceShouldYieldStatusOk() throws Exception {
        mockMvc.perform(get("/datasets/skills.csv"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/csv"));
    }

    @Test
    public void nonExistingResourceShouldYieldStatusNotFound() throws Exception {
        mockMvc.perform(get("/datasets/nonexisting.html"))
                .andExpect(status().isNotFound());
    }

}

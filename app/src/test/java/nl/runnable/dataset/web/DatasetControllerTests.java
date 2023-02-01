package nl.runnable.dataset.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

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
        mockMvc.perform(get("/datasets"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML));
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

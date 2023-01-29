package nl.runnable.dataset.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MvcConfig {

    @Bean
    public ContentTypeXsltViewResolver xsltViewResolver() {
        var viewResolver = new ContentTypeXsltViewResolver();
        viewResolver.setPrefix("classpath:/views/");
        viewResolver.setSuffix(".xslt");
        return viewResolver;
    }

}

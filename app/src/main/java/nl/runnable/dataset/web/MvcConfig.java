package nl.runnable.dataset.web;

import nl.runnable.dataset.fop.FopXsltViewResolver;
import org.apache.fop.apps.FopFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

@Configuration
public class MvcConfig {

    @Bean
    public FopXsltViewResolver fopXsltViewResolver(FopFactory fopFactory) {
        var viewResolver = new FopXsltViewResolver(fopFactory);
        viewResolver.setPrefix("classpath:/views/");
        viewResolver.setSuffix(".xslt");
        viewResolver.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return viewResolver;
    }

    @Bean
    public ContentTypeXsltViewResolver xsltViewResolver() {
        var viewResolver = new ContentTypeXsltViewResolver();
        viewResolver.setPrefix("classpath:/views/");
        viewResolver.setSuffix(".xslt");
        return viewResolver;
    }

}

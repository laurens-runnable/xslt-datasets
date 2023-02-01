package nl.runnable.dataset.web;

import nl.runnable.dataset.fop.FopXsltViewResolver;
import org.apache.fop.apps.FopFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

@Configuration
class ViewResolverConfig {

    @Bean
    public FopXsltViewResolver fopXsltViewResolver(FopFactory fopFactory) {
        var viewResolver = new FopXsltViewResolver(fopFactory);
        viewResolver.setPrefix("classpath:/views/");
        viewResolver.setSuffix(".xslt");
        // Configure the FOP ViewResolver with the highest precedence, so that it overrides
        // Spring's default ContentNegotiatingViewResolver behaviour.
        viewResolver.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return viewResolver;
    }

    @Bean
    public DatasetXsltViewResolver datasetXsltViewResolver() {
        var viewResolver = new DatasetXsltViewResolver();
        viewResolver.setPrefix("classpath:/views/");
        viewResolver.setSuffix(".xslt");
        return viewResolver;
    }

}

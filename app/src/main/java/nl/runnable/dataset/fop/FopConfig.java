package nl.runnable.dataset.fop;

import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.FopFactoryBuilder;
import org.apache.fop.configuration.ConfigurationException;
import org.apache.fop.configuration.DefaultConfigurationBuilder;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.net.URI;

@Configuration
public class FopConfig {

    @Value("classpath:/fop/fop-config.xml")
    private Resource fopConfig;

    @Bean
    FopFactory fopFactory(SpringResourceResolver resourceResolver) {
        try {
            final var configurationBuilder = new DefaultConfigurationBuilder();
            final var configuration = configurationBuilder.build(fopConfig.getInputStream());
            final var factoryBuilder = new FopFactoryBuilder(URI.create("/"), resourceResolver);
            factoryBuilder.setConfiguration(configuration);
            return factoryBuilder.build();
        } catch (ConfigurationException | IOException e) {
            throw new BeanCreationException("Error initializing FOP: %s".formatted(e.getMessage()), e);
        }
    }
}

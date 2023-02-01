package nl.runnable.dataset;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "xslt-datasets")
public class XsltDatasetsProperties {

    @Getter
    @Setter
    private String directory;

}

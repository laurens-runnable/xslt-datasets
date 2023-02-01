package nl.runnable.dataset;

import lombok.Getter;
import lombok.NonNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "xslt-datasets")
public class XsltDatasetsProperties {

    @Getter
    private String directory;

    public void setDirectory(@NonNull String directory) {
        while (directory.endsWith("/")) {
            directory = directory.substring(0, directory.length() - 1);
        }
        this.directory = directory;
    }
}

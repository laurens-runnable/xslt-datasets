# Dataset

* Convert CSV data to XML
* Transform XML using XSLT stylesheets

## Local development

* OpenJDK 17
* Docker

### Docker containers

For convenience, you can run in-memory instances for the infrastructure.

```bash
# Start Docker containers
./local-dev/bin/up.sh

# Follow logs
./local-dev/bin/follow-logs.sh

# Stop containers
./local-dev/bin/down.sh
```

### Run

```bash
./mvnw spring-boot:run
```

[Spring Boot Developer Tools](https://docs.spring.io/spring-boot/docs/3.0.2/reference/html/using.html#using.devtools)
are enabled:

* The application restarts automatically after a resource on the classpath is updated.
* The browser reloads the current page when the [LiveReload](https://github.com/livereload/livereload-js) extension is
  installed.

## Example datasets

* [Kaggle datasets](https://www.kaggle.com/datasets)  
  Dataset community, registration required

## Stack

* [Spring Boot](https://github.com/spring-projects/spring-boot)
* [Saxon HE](https://github.com/Saxonica/Saxon-HE)
* [JDOM](https://github.com/hunterhacker/jdom)
* [Apache Solr](https://github.com/apache/solr)

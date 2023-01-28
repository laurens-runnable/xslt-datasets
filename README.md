# dataset-visualizer

Visualize datasets using XSLT.

* [Spring Boot](https://github.com/spring-projects/spring-boot)
* [Saxon HE](https://github.com/Saxonica/Saxon-HE)
* [JDOM](https://github.com/hunterhacker/jdom)
* [Apache Solr](https://github.com/apache/solr)

# Local development

* OpenJDK 17
* Docker

## Docker containers

For convenience, you can in-memory instances for the infrastructure. In this case, Solr.

```bash
# Start Docker containers
./local-dev/bin/up.sh

# Follow logs
./local-dev/bin/follow-logs.sh

# Stop containers
./local-dev/bin/down.sh
```

## Run

```bash
./mvnw spring-boot:run
```

[Spring Boot Developer Tools](https://docs.spring.io/spring-boot/docs/3.0.2/reference/html/using.html#using.devtools)
are enabled.

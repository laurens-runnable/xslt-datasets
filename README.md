# XSLT datasets

Example application that demonstrates the use of XSLT stylesheets to transform CSV data to HTML and PDF.

The current implementation produces simple table output. More sophisticated examples may follow.

# Stack

* OpenJDK 17
* [Spring Boot](https://github.com/spring-projects/spring-boot)
  and [Spring MVC](https://github.com/spring-projects/spring-framework)
* [Saxon HE](https://github.com/Saxonica/Saxon-HE)
* [Apache FOP](https://github.com/apache/xmlgraphics-fop)

## Run

```bash
./mvnw spring-boot:run
```

* [http://localhost:8080/](http://localhost:8080/)
* Save `.csv` files in the [`datasets`](datasets) directory
* Reload the main page to browse the available datasets

### Spring Developer tools

[Spring Boot Developer Tools](https://docs.spring.io/spring-boot/docs/3.0.2/reference/html/using.html#using.devtools)
are enabled, meaning that:

* the application restarts automatically after a resource on the classpath is updated
* the browser reloads the current page when the [LiveReload](https://github.com/livereload/livereload-js) extension is
  installed

## Resources

* [Kaggle datasets](https://www.kaggle.com/datasets)  
  Dataset community, registration required

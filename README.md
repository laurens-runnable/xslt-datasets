# XSLT datasets

Example application that demonstrates the use of XSLT stylesheets to transform CSV data to HTML and PDF.

The current implementation produces simple table output. More sophisticated examples may follow.

# Stack

* [OpenJDK 17](https://openjdk.org/projects/jdk/17/)
* [Spring Boot](https://github.com/spring-projects/spring-boot)
  and [Spring MVC](https://github.com/spring-projects/spring-framework)
* [Saxon HE](https://github.com/Saxonica/Saxon-HE)
* [Apache FOP](https://github.com/apache/xmlgraphics-fop)

## Run

```bash
./mvnw spring-boot:run
```

* [http://localhost:8080/](http://localhost:8080/) to open the web interface
* Save `.csv` and `.xml` files in the [`datasets`](datasets) directory  
  Note that the application does not support duplicate base names. E.g. it cannot properly distinguish between
  datasets `items.csv` and `items.xml`.
* Reload the web page to refresh the datasets

### Spring Developer tools

[Spring Boot Developer Tools](https://docs.spring.io/spring-boot/docs/3.0.2/reference/html/using.html#using.devtools)
are enabled, meaning that:

* the application restarts automatically after a resource on the classpath is updated
* the browser reloads the current page when the [LiveReload](https://github.com/livereload/livereload-js) extension is
  installed

## Resources

* [Kaggle datasets](https://www.kaggle.com/datasets)  
  Dataset community, registration required

## License

[Public Domain](LICENSE)

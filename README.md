# springdaogen-example-sakila

An example project showing how a REST API can be created 
using [spring-dao-codegen](https://github.com/plilja/spring-dao-codegen) from
the MySQL [Sakila database](https://dev.mysql.com/doc/sakila/en/).

The code in the packages 'model' and 'dbframework' has been generated.

To run the project you need to have Java 11, Maven and Docker installed.
```bash
> cd springdaogen-example-sakila
> cd sakila
> mvn clean package
> cd ..
> docker-compose up
```
After that you should be able to navigate to http://localhost:8080 with you browser.

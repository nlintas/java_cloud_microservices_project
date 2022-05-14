# Microservices Based File Converter
In this project several technologies will enable the creation of an application that will allow for independent microservices to handle file conversion. These will coordinate initially with a Netflix Eureka server and then be Dockerized and managed by Kubernetes.

## Run Instructions
1. Initialize the Eureka server.
2. Initialize the web server.
3. Initialize the microservices (if at this point the server fails to serve results, HTTP Error 500 may occur and exception of type: no instance of 'x' microservice running will be thrown).
4. Open all web pages through the ports provided from Eureka (Eureka is at port 8761).

## Software Architecture

![](diagrams/CC CW Software Structure Diagram.jpg)

## Technologies
- [Java 18](https://howtodoinjava.com/java-version-wise-features-history/) Alt Docs: [Original](https://docs.oracle.com/en/java/javase/18/)
- [Netflix Eureka](https://cloud.spring.io/spring-cloud-netflix/reference/html/)
- [SpringBoot Framework](https://docs.spring.io/spring-boot/docs/current/reference/html/)
- [Thymeleaf Templating Language](https://www.thymeleaf.org/doc/tutorials/3.0/usingthymeleaf.html)
- [Docker](https://spring.io/guides/gs/spring-boot-docker/) Alt Docs: [Similar 1](https://spring.io/guides/topicals/spring-boot-docker/), [Docker with Java](https://docs.docker.com/language/java/)
- [Kubernetes](https://docs.spring.io/spring-cloud-kubernetes/docs/current/reference/html/) Alt Docs: [1 (Original)](https://kubernetes.io/docs/home/)
- [Intellij IDEA](https://www.jetbrains.com/help/idea/2022.1/spring-support.html)

## LOC??
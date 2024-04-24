# Microservices Based File Converter
In this project several technologies will enable the creation of an application that will allow for independent microservices to handle file conversion. These will coordinate initially with a Netflix Eureka server and then be Dockerized and managed by Kubernetes and Google Cloud.

## Run Local Build Instructions
1. Initialize the Eureka server.
2. Initialize the conversion microservice.
3. Initialize the web server. 
4. Open all web pages through the ports provided from Eureka (Eureka is at port 8761).
   1. If an error occurs where no conversion/text to pdf microservice is avaliable wait for a minute so that it registers with Eureka and try again.

## Run Locally Docker Compose Instructions
Run from the root directory the `test_local_docker_compose.sh` file.
**Warning!**
Be aware that the script contains a `docker rmi` command that removes all images with names `eureka`, `conversion_service` and `client`.

## Deployment Instructions
1. Make sure existing project is cleaned up from Google Cloud using the UI
   1. Kubernetes Engine -> Services & Ingress -> Delete All
   2. Kubernetes Engine -> Workloads -> Delete All
   3. Kubernetes Engine -> Clusters -> Delete All
2. Run from the root directory the `deploy.sh` file

**Warning!**
Be aware that the script contains a `docker rmi` command that removes all images with names `eureka`, `conversion_service` and `client`.

## Remove Deployments & Billing on Google Cloud
Follow the steps:
   1. [Google Official Docs](https://cloud.google.com/appengine/docs/standard/python3/building-app/cleaning-up)
   2. Disable all services on Google Cloud Platform.
      1. Open the hamburger menu
      2. Select APIs & Services
      3. Select Enabled APIs & Services
      4. Sort by most Requests (arrow points down)
      5. Click on each service with any entries
      6. Click the disable button.
## Technologies
- Java 18
- Netflix Eureka
- SpringBoot Framework
- Thymeleaf Templating Language
- Docker
- Kubernetes
- Google Cloud
- Intellij IDEA

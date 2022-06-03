FROM openjdk:18.0.1-jdk-oraclelinux7
COPY target/text-to-pdf-microservice-1.0.0.jar ./
EXPOSE 8761
ENTRYPOINT ["java", "-jar", "text-to-pdf-microservice-1.0.0.jar"]
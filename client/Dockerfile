FROM openjdk:18.0.1-jdk-oraclelinux7
COPY target/client-server-0.0.1-SNAPSHOT.jar ./
EXPOSE 8761
ENTRYPOINT ["java", "-jar", "text-to-pdf-microservice-1.0.0.jar"]
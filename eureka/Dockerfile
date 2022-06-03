FROM openjdk:18.0.1-jdk-oraclelinux7
COPY target/eureka-server-1.0.0.jar ./
EXPOSE 8761
ENTRYPOINT ["java", "-jar", "eureka-server-1.0.0.jar"]
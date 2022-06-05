# Use a linux distribution with open jdk 18 installed. Required to run java files.
FROM openjdk:18.0.1-jdk-oraclelinux7
# Copy inside the jar file produced by 'mvn package'
COPY target/client-server-1.0.0.jar ./
# Expose a port
EXPOSE 8761
# Run copied jar file. Non-overidable Parameters
ENTRYPOINT ["java", "-jar", "client-server-1.0.0.jar"]
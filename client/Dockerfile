# Use a linux distribution with open jdk 18 installed. Required to run java files.
FROM openjdk:18.0.1-jdk-oraclelinux7
# Copy inside the jar file produced by 'mvn package'
COPY target/client-server-1.0.0.jar ./
# Expose a port
EXPOSE 4444
# Points to the java installation inside the image specifies type of runnable and points to its locations. Additional parameters such as the server port and the name of the container are provided.
ENTRYPOINT ["/usr/java/openjdk-18/bin/java", "-jar", "client-server-1.0.0.jar", "--server.port=4444", "client"]
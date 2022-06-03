# Use a light Linux Distribution
FROM alpine:3.16.0
# Copy inside the jar file
COPY /app/app.jar ./app.jar
# Run the jar file
ENTRYPOINT ["java","-jar","/app.jar"]
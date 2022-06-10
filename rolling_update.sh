#!/bin/bash

# Functions
build_eureka() {
    echo "Building Eureka Docker Image"
    cd "eureka"
    mvn clean package
    docker buildx build --platform=linux/amd64 -t eureka:v2 .
    echo "Tagging Eureka Docker Image"
    docker tag eureka:v2 europe-west1-docker.pkg.dev/cloud-computing-cw-352806/cloud-repo/eureka:v2
    echo "Pushing Eureka Docker Image to Google Cloud"
    docker push europe-west1-docker.pkg.dev/cloud-computing-cw-352806/cloud-repo/eureka:v2
}

build_conversion_service() {
    echo "Building Conversion Service Docker Image"
    cd "text_to_pdf"
    mvn clean package
    docker buildx build --platform=linux/amd64 -t conversion_service:v2 .
    echo "Tagging Conversion Service Docker Image"
    docker tag conversion_service:v2 europe-west1-docker.pkg.dev/cloud-computing-cw-352806/cloud-repo/conversion_service:v2
    echo "Pushing Conversion Service Docker Image to Google Cloud"
    docker push europe-west1-docker.pkg.dev/cloud-computing-cw-352806/cloud-repo/conversion_service:v2
}

build_client() {
    echo "Building Client Docker Image"
    cd "client"
    mvn clean package
    docker buildx build --platform=linux/amd64 -t client:v2 .
    echo "Tagging Client Docker Image"
    docker tag client europe-west1-docker.pkg.dev/cloud-computing-cw-352806/cloud-repo/client:v2
    echo "Pushing Client Docker Image to Google Cloud"
    docker push europe-west1-docker.pkg.dev/cloud-computing-cw-352806/cloud-repo/client:v2
}

roll_update() {
    echo "*** Roll Update ***"
    kubectl set image deployment/eureka eureka=europe-west1-docker.pkg.dev/cloud-computing-cw-352806/cloud-repo/eureka:v2
    ## Deployment name cant have underscores on conversion service
    kubectl set image deployment/conversion-service conversion-service=europe-west1-docker.pkg.dev/cloud-computing-cw-352806/cloud-repo/conversion_service:v2
    kubectl set image deployment/client client=europe-west1-docker.pkg.dev/cloud-computing-cw-352806/cloud-repo/client:v2
}

# Terminate all processes on exit
trap "trap - SIGTERM && kill -- -$$" SIGINT SIGTERM EXIT
# Running Area (if & present then concurent threads are used)
build_eureka & build_client & build_conversion_service
roll_update
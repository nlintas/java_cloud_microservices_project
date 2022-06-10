#!/bin/bash

# Functions
build_eureka() {
    echo "Building Eureka Docker Image"
    cd "eureka"
    mvn clean package
    docker buildx build --platform=linux/amd64 -t eureka .
    echo "Tagging Eureka Docker Image"
    docker tag eureka europe-west1-docker.pkg.dev/cloud-computing-cw-352806/cloud-repo/eureka
    echo "Pushing Eureka Docker Image to Google Cloud"
    docker push europe-west1-docker.pkg.dev/cloud-computing-cw-352806/cloud-repo/eureka
}

build_conversion_service() {
    echo "Building Conversion Service Docker Image"
    cd "text_to_pdf"
    mvn clean package
    docker buildx build --platform=linux/amd64 -t conversion_service .
    echo "Tagging Conversion Service Docker Image"
    docker tag conversion_service europe-west1-docker.pkg.dev/cloud-computing-cw-352806/cloud-repo/conversion_service
    echo "Pushing Conversion Service Docker Image to Google Cloud"
    docker push europe-west1-docker.pkg.dev/cloud-computing-cw-352806/cloud-repo/conversion_service
}

build_client() {
    echo "Building Client Docker Image"
    cd "client"
    mvn clean package
    docker buildx build --platform=linux/amd64 -t client .
    echo "Tagging Client Docker Image"
    docker tag client europe-west1-docker.pkg.dev/cloud-computing-cw-352806/cloud-repo/client
    echo "Pushing Client Docker Image to Google Cloud"
    docker push europe-west1-docker.pkg.dev/cloud-computing-cw-352806/cloud-repo/client
}

prepare_kubernetes() {
    cd "../"
    echo "Update Eureka Kubernetes Descriptors"
    kubectl apply -f eureka-deployment.yaml
    kubectl apply -f eureka-service.yaml
    echo "Update Client Kubernetes Descriptors"
    kubectl apply -f client-deployment.yaml
    kubectl apply -f client-service.yaml
    echo "Update Conversion Service Kubernetes Descriptors"
    kubectl apply -f conversion-service-deployment.yaml
    kubectl apply -f conversion-service-service.yaml
}

# Terminate all processes on exit
trap "trap - SIGTERM && kill -- -$$" SIGINT SIGTERM EXIT
# Running Area (if & present then concurent threads are used)
build_eureka & build_client & build_conversion_service
prepare_kubernetes
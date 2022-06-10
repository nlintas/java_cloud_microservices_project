#!/bin/bash

# Functions
cleanup_docker(){
    echo "Removing images with conflicting name by force"
    docker rmi -f `docker images | awk '$1 ~ /eureka/ {print $3}'`
    docker rmi -f `docker images | awk '$1 ~ /client/ {print $3}'`
    docker rmi -f `docker images | awk '$1 ~ /conversion_service/ {print $3}'`
}

build_eureka() {
    echo "Building Eureka Docker Image"
    cd "eureka"
    mvn clean package
    docker build -t eureka .
}

build_conversion_service() {
    echo "Building Conversion Service Docker Image"
    cd "text_to_pdf"
    mvn clean package
    docker build -t conversion_service .
}

build_client() {
    echo "Building Client Docker Image"
    cd "client"
    mvn clean package
    docker build -t client .
}

docker_compose() {
    cd "../"
    docker compose up
}

# Terminate all processes on exit
trap "trap - SIGTERM && kill -- -$$" SIGINT SIGTERM EXIT
# Running Area (if & present then concurent threads are used)
cleanup_docker # COMMENT TO PREVENT DELETION OF DOCKER CONTAINERS BY NAME
build_eureka & build_client & build_conversion_service
docker_compose
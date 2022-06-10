#!/bin/bash

# Functions
cleanup_docker() {
    echo "Removing images with conflicting name by force"
    docker rmi -f $(docker images | awk '$1 ~ /eureka2/ {print $3}')
    docker rmi -f $(docker images | awk '$1 ~ /client2/ {print $3}')
    docker rmi -f $(docker images | awk '$1 ~ /conversion_service2/ {print $3}')
}

build_eureka() {
    echo "Building Eureka 2 Docker Image"
    cd "eureka"
    mvn clean package -Dmaven.test.skip
    docker build -t eureka2 .
}

build_conversion_service() {
    echo "Building Conversion Service 2 Docker Image"
    cd "text_to_pdf"
    mvn clean package -Dmaven.test.skip
    docker build -t conversion_service2 .
}

build_client() {
    echo "Building Client 2 Docker Image"
    cd "client"
    mvn clean package -Dmaven.test.skip
    docker build -t client2 .
}

docker_compose() {
    cd "../"
    docker compose up
}

# Terminate all processes on exit
trap "trap - SIGTERM && kill -- -$$" SIGINT SIGTERM EXIT
# Running Area (if & present then concurent threads are used)
cleanup_docker # COMMENT TO PREVENT DELETION OF DOCKER CONTAINERS BY NAME
build_eureka &
build_client &
build_conversion_service
docker_compose

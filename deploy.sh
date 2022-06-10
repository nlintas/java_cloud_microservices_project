#!/bin/bash

# Variables
GCLOUD_REPOSITORY=cloud-repo
GCLOUD_CLUSTER=cloud-cluster
GCLOUD_REGION=europe-west1
GCLOUD_ZONE=europe-west1-b

# Functions
cleanup_docker() {
    echo "Removing images with conflicting name by force"
    docker rmi -f $(docker images | awk '$1 ~ /eureka/ {print $3}')
    docker rmi -f $(docker images | awk '$1 ~ /client/ {print $3}')
    docker rmi -f $(docker images | awk '$1 ~ /conversion_service/ {print $3}')
}

setup_artifact_repository() {
    echo "Creating Artifact Repository for Docker Images"
    gcloud artifacts repositories create $GCLOUD_REPOSITORY --repository-format=docker --location=$GCLOUD_REGION --description="Docker repository"
    echo "Authenticating Local Docker Repository with Google Cloud Artifact Repository"
    gcloud auth configure-docker $GCLOUD_REGION-docker.pkg.dev
}

setup_cluster() {
    echo "Creating Regional Kubernetes Cluster"
    gcloud container clusters create $GCLOUD_CLUSTER --region $GCLOUD_REGION --node-locations $GCLOUD_ZONE
    echo "Authenticating Local Google Cloud CLI with Kubernetes Cluster"
    gcloud container clusters get-credentials $GCLOUD_CLUSTER --region=$GCLOUD_REGION
}

build_eureka() {
    echo "Building Eureka Docker Image"
    cd "eureka"
    mvn clean package -Dmaven.test.skip
    docker buildx build --platform=linux/amd64 -t eureka .
    echo "Tagging Eureka Docker Image"
    docker tag eureka $GCLOUD_REGION-docker.pkg.dev/cloud-computing-cw-352806/$GCLOUD_REPOSITORY/eureka
    echo "Pushing Eureka Docker Image to Google Cloud"
    docker push $GCLOUD_REGION-docker.pkg.dev/cloud-computing-cw-352806/$GCLOUD_REPOSITORY/eureka
}

build_conversion_service() {
    echo "Building Conversion Service Docker Image"
    cd "text_to_pdf"
    mvn clean package -Dmaven.test.skip
    docker buildx build --platform=linux/amd64 -t conversion_service .
    echo "Tagging Conversion Service Docker Image"
    docker tag conversion_service $GCLOUD_REGION-docker.pkg.dev/cloud-computing-cw-352806/$GCLOUD_REPOSITORY/conversion_service
    echo "Pushing Conversion Service Docker Image to Google Cloud"
    docker push $GCLOUD_REGION-docker.pkg.dev/cloud-computing-cw-352806/$GCLOUD_REPOSITORY/conversion_service
}

build_client() {
    echo "Building Client Docker Image"
    cd "client"
    mvn clean package -Dmaven.test.skip
    docker buildx build --platform=linux/amd64 -t client .
    echo "Tagging Client Docker Image"
    docker tag client $GCLOUD_REGION-docker.pkg.dev/cloud-computing-cw-352806/$GCLOUD_REPOSITORY/client
    echo "Pushing Client Docker Image to Google Cloud"
    docker push $GCLOUD_REGION-docker.pkg.dev/cloud-computing-cw-352806/$GCLOUD_REPOSITORY/client
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
cleanup_docker # COMMENT TO PREVENT DELETION OF DOCKER CONTAINERS BY NAME
setup_artifact_repository
setup_cluster
build_eureka &
build_client &
build_conversion_service
prepare_kubernetes

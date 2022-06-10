#!/bin/bash

# Variables
GCLOUD_REPOSITORY=cloud-repo
GCLOUD_REGION=europe-west1
GCLOUD_PROJECT_ID=cloud-computing-cw-352806
UPDATE_VERSION=:v2

# Functions
cleanup_docker() {
    echo "Removing images with conflicting name by force"
    docker rmi -f $(docker images | awk '$1 ~ /eureka/ {print $3}')
    docker rmi -f $(docker images | awk '$1 ~ /client/ {print $3}')
    docker rmi -f $(docker images | awk '$1 ~ /conversion_service/ {print $3}')
}

build_eureka() {
    echo "Building Eureka Docker Image"
    cd "eureka"
    mvn clean package -Dmaven.test.skip
    docker buildx build --platform=linux/amd64 -t eureka .
    echo "Tagging Eureka Docker Image"
    docker tag eureka$UPDATE_VERSION $GCLOUD_REGION-docker.pkg.dev/$GCLOUD_PROJECT_ID/$GCLOUD_REPOSITORY/eureka$UPDATE_VERSION
    echo "Pushing Eureka Docker Image to Google Cloud"
    docker push $GCLOUD_REGION-docker.pkg.dev/$GCLOUD_PROJECT_ID/$GCLOUD_REPOSITORY/eureka$UPDATE_VERSION
}

build_conversion_service() {
    echo "Building Conversion Service Docker Image"
    cd "text_to_pdf"
    mvn clean package -Dmaven.test.skip
    docker buildx build --platform=linux/amd64 -t conversion_service$UPDATE_VERSION .
    echo "Tagging Conversion Service Docker Image"
    docker tag conversion_service$UPDATE_VERSION $GCLOUD_REGION-docker.pkg.dev/$GCLOUD_PROJECT_ID/$GCLOUD_REPOSITORY/conversion_service$UPDATE_VERSION
    echo "Pushing Conversion Service Docker Image to Google Cloud"
    docker push $GCLOUD_REGION-docker.pkg.dev/$GCLOUD_PROJECT_ID/$GCLOUD_REPOSITORY/conversion_service$UPDATE_VERSION
}

build_client() {
    echo "Building Client Docker Image"
    cd "client"
    mvn clean package -Dmaven.test.skip
    docker buildx build --platform=linux/amd64 -t client$UPDATE_VERSION .
    echo "Tagging Client Docker Image"
    docker tag client$UPDATE_VERSION $GCLOUD_REGION-docker.pkg.dev/$GCLOUD_PROJECT_ID/$GCLOUD_REPOSITORY/client$UPDATE_VERSION
    echo "Pushing Client Docker Image to Google Cloud"
    docker push $GCLOUD_REGION-docker.pkg.dev/$GCLOUD_PROJECT_ID/$GCLOUD_REPOSITORY/client$UPDATE_VERSION
}

roll_update() {
    echo "*** Roll Update ***"
    kubectl set image deployment/eureka eureka=$GCLOUD_REGION-docker.pkg.dev/$GCLOUD_PROJECT_ID/$GCLOUD_REPOSITORY/eureka$UPDATE_VERSION
    ## Deployment name cant have underscores on conversion service
    kubectl set image deployment/conversion-service conversion-service=$GCLOUD_REGION-docker.pkg.dev/$GCLOUD_PROJECT_ID/$GCLOUD_REPOSITORY/conversion_service$UPDATE_VERSION
    kubectl set image deployment/client client=$GCLOUD_REGION-docker.pkg.dev/$GCLOUD_PROJECT_ID/$GCLOUD_REPOSITORY/client$UPDATE_VERSION
}

# Terminate all processes on exit
trap "trap - SIGTERM && kill -- -$$" SIGINT SIGTERM EXIT
# Running Area (if & present then concurent threads are used)
cleanup_docker
build_eureka & build_client & build_conversion_service
roll_update
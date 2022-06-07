#!/bin/bash

# Functions
run_eureka() {
    echo "Running Eureka Server"
    cd "eureka"
    mvn spring-boot:run
}

run_txt_2_pdf() {
    echo "Running Text to PDF Microservice"
    cd "text_to_pdf"
    mvn spring-boot:run
}

run_client() {
    echo "Running Client Microservice"
    cd "client"
    mvn spring-boot:run
}

# Terminate all processes on exit
trap "trap - SIGTERM && kill -- -$$" SIGINT SIGTERM EXIT
# Running Area (Concurent Threads)
run_eureka &
run_txt_2_pdf &
run_client

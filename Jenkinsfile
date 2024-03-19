pipeline {
    agent any
    environment{
        registry = "rpdharanidhar/devops-task02:latest"
        DOCKER_IMAGE = "rpdharanidhar/devops-task02:latest"
        KUBE_NAMESPACE = "jenkinsdemo-kube"
        DOCKER_PASSWORD = credentials('docker-password')
        DOCKER_USERNAME = credentials('docker-username')
        DOCKER_IMAGE_NAME = "rpdharanidhar/devops-task02"
        DOCKER_HUB_REPO = "rpdharanidhar"
        DOCKER_REGISTRY = "rpdharanidhar/devops-task01"
        MONGODB_SERVER = 'mongodb://localhost:27017'
        MONGODB_DATABASE = 'test'
        MONGODB_COLLECTION = 'people'
        MONGODB_URL = 'mongodb://dharani:dharani@localhost:27017/admin'
        CONTAINER1_NAME = 'mongodb'
        CONTAINER2_NAME = 'nodejs'
        
    }
    
    stages {
        stage('Checkout') {
            steps {
                git url: 'https://github.com/rpdharanidhar/devops-task02.git', branch: 'main', credentialsId: 'git-credentials'
            }
        }
        stage('Build Docker Image') {
            steps {
                bat "docker-compose build"
            }
        }
        stage('Run Docker Container') {
            steps {
                bat "docker-compose up -d"
            }
        }
        stage('Push Docker Image to Hub') {
            steps {
                bat "docker login -u ${DOCKER_USERNAME} -p ${DOCKER_PASSWORD} && docker-compose push nodejs"
            }
        }
        // stage('Cleaning up') {
        //     steps{
        //         bat "docker-compose down --rmi ${CONTAINER1_NAME} --rmi ${CONTAINER2_NAME}"
        //     }
        // }
    }
}

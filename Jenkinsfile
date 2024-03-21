pipeline {
    agent any
    environment{
        registry = "rpdharanidhar/devops-task03:latest"
        DOCKER_IMAGE = "rpdharanidhar/devops-task03:latest"
        KUBE_NAMESPACE = "jenkinsdemo-kube"
        DOCKER_PASSWORD = credentials('docker-password')
        DOCKER_USERNAME = credentials('docker-username')
        DOCKER_IMAGE_NAME = "rpdharanidhar/devops-task03"
        DOCKER_HUB_REPO = "rpdharanidhar"
        DOCKER_REGISTRY = "rpdharanidhar/devops-task01"
        MONGODB_SERVER = 'mongodb://localhost:27017'
        MONGODB_DATABASE = 'test'
        MONGODB_COLLECTION = 'people'
        MONGODB_URL = 'mongodb://dharani:dharani@localhost:27017/admin'
        CONTAINER1_NAME = 'mongodb'
        CONTAINER2_NAME = 'nodejs'
        SCANNER_HOME = tool 'sonarqube-scanner'
        SONAR_PASSWORD = credentials('sonar-password')
        SONAR_LOGIN = credentials('sonar-login')
        
    }
    stages {
        stage('Checkout') {
            steps {
                git url: 'https://github.com/rpdharanidhar/devops-task03.git', branch: 'main', credentialsId: 'git-credentials'
            }
        }
        stage('SonarQube Analysis') {
            steps {
                script {
                    try {
                        withCredentials([usernamePassword(credentialsId: 'sonar-login', usernameVariable: 'SONAR_LOGIN', passwordVariable: 'SONAR_PASSWORD')]){
                            withSonarQubeEnv() {
                                bat "${scannerHome}/bin/sonar-scanner"
                            }
                            waitForQualityGate()
                        }
                    } catch (Exception e) {
                        echo "SonarQube stage has been failed...!!! better luck next time !!!."
                }
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
        stage('Deploy to Kubernetes') {
            steps {
                script {
                    try {
                        bat "start /B kubectl apply -f app-deployment.yaml --validate=false"
                    } catch (Exception e) {
                        echo "Deployment failed! Rolling back..."
                        bat "start /B kubectl delete service my-mongodb-task03, my-nodejs-app-task03"
                        bat "start /B kubectl delete deployments my-nodejs-app-task03"
                        echo "Rollback completed. Deployment failed."
                    }
                }
            }
        }
        stage('Cleaning up') {
            steps{
                bat "start /B kubectl delete service my-mongodb-task03, my-nodejs-app-task03"
                bat "start /B kubectl delete deployments my-nodejs-app-task03"
            }
        }
    }
}

pipeline {
    agent any
   
    // environment {
    //     DOCKER_CREDENTIALS = credentials('dockerhub')
    //     DOCKER_IMAGE_NAME = "rpdharanidhar/devops-task01"
    //     DOCKER_HUB_REPO = "rpdharanidhar"
    // }
    // environment {
    //     // Define the Azure VM details
    //     AZURE_VM_IP = '172.208.57.242'
    //     AZURE_VM_USERNAME = 'admin'
    //     AZURE_VM_PRIVATE_KEY = credentialsId('azure-vm')
    // }
    environment {
        DOCKER_IMAGE = 'rpdharanidhar/devops-task01:latest'
        KUBE_NAMESPACE = 'jenkinsdemo-kube'
        // DOCKER_PASSWORD = credentials("docker-pass-txt")
        DOCKER_PASSWORD = 'dharanirp1482'
        // DOCKER_USERNAME = credentials("docker-username-txt")
        DOCKER_USERNAME = 'rpdharanidhar'
        // DOCKER_CREDENTIALS = credentialsId('dockerhub')
        DOCKER_IMAGE_NAME = "rpdharanidhar/devops-task01"
        DOCKER_HUB_REPO = "rpdharanidhar"
        // DOCKER_REGISTRY = 'docker.io/rpdharanidhar/devops-task01/'
        DOCKER_REGISTRY = 'http://localhost:54686/'
    }
    
    stages {
        // stage('Connect to Azure VM') {
        //     steps {
        //         script {
        //             // Start the SSH agent and add the private key
        //             // sshagent(['azure-vm']) {
        //                 // Use SSH to connect to the Azure VM and execute a command
        //                 // sshCommand remote: "ubuntu@${AZURE_VM_IP}", command: 'echo Hello from Jenkins'
        //             sh 'scp -r *  azureadmin@172.208.57.242:/var/www/html/', credentialsId: 'azure-vm-pass'
                    
        //         }
        //     }
        // }
        // stage('azure vm connect') {
        //     steps {
        //         sh 'az login --service-principal -u $AZURE_VM_USERNAME -p $MY_CRED_CLIENT_SECRET -t $MY_CRED_TENANT_ID'
        //     }
        // }

        

        stage('Checkout') {
            steps {
                git url: 'https://github.com/rpdharanidhar/devops-task01.git', branch: 'main', credentialsId: 'git-credentials'
            }
        }
        // stage('Docker Login') {
        //     steps {
        //         script {
        //             docker.withRegistry('https://docker.io/rpdharanidhar/devops-task01/', credentials: 'dockerhub-registry') {
        //                 docker.image('rpdharanidhar/devops-task01').push('latest')
        //                 // This block is authenticated with Docker Hub.
        //                 // You can now pull/push images to Docker Hub.
        //                 // withCredentials([usernamePassword(credentialsId: env.DOCKER_CREDENTIALS, usernameVariable: 'DOCKER_USERNAME', passwordVariable: 'DOCKER_PASSWORD')]) {
        //                     // docker.withRegistry(env.DOCKER_REGISTRY, env.DOCKER_USERNAME, env.DOCKER_PASSWORD)
        //                 // withCredentials([usernamePassword(credentialsId: "${DOCKER_CREDENTIALS}", usernameVariable: 'DOCKER_USERNAME', passwordVariable: 'DOCKER_PASSWORD')]) {
        //                     // sh "docker login -u ${DOCKER_USERNAME} -p ${DOCKER_PASSWORD}"
        //                 // sh "docker login"
        //                 // }
        //             }
        //         }
        //     }
        // }
        stage('Build Docker Image') {
            steps {
                script {
                     //sh 'docker build -t testdemo-jenkins https://github.com/rpdharanidhar/devops-task01.git#rpdharanidhar/devops-task01', branch: 'main', credentialsId: 'polar-git-credentials'
                    docker.build("rpdharanidhar/devops-task01")
                }
            }
        }
        // stage('Push image to Hub'){
        //     steps{
        //         script{
        //             withCredentials([string(credentials: 'dockerhub', variable: 'dockerhub')]) {
        //             sh 'docker tag testdemo-jenkins rpdharanidhar/devops-task01:latest'
        //             sh 'docker login -u rpdharanidhar -p ${docker-pass-txt}'
        //             sh 'docker push rpdharanidhar/devops-task01:latest'
        //             }
        //         }
        //     }
        // }
        // stage('Login to Docker Registry') {
        //     steps {
        //         bat 'docker login -u rpdharanidhar -p ${docker-pass-txt} ${env.DOCKER_REGISTRY}'
        //     }
        // }
        stage('Login to Docker Registry') {
            steps{
                // withCredentials([usernamePassword(credentialsId: 'docker-registry-username-id', usernameVariable: 'DOCKER_USERNAME', passwordVariable: 'DOCKER_PASSWORD')]) {
                //     bat 'docker login -u rpdharanidhar -p dharanirp1482 docker.io/rpdharanidhar/devops-task01'
                // }
                bat 'docker login -u rpdharanidhar -p dharanirp1482 docker.io/rpdharanidhar/devops-task01'
            }
        }
        stage('Push Docker Image to Hub') {
            steps {
                // script {
                //     docker.withRegistry('https://docker.io/rpdharanidhar/devops-task01/', dockerhub){
                // //     // docker.withRegistry('https://index.docker.io/v1/', dockerhub) 
                // //        // docker.image("${DOCKER_IMAGE_NAME}").push("${env.DOCKER_HUB_REPO}:${env.BUILD_NUMBER}")
                //         docker.image('rpdharanidhar/devops-task01:latest').push()
                // //     }
                //     }
                // }
                // // bat 'docker login -u rpdharanidhar -p dharanirp1482 docker.io'
                // // bat 'docker push rpdharanidhar/devops-task01'
                // }
                // bat 'docker login -u rpdharanidhar -p dharanirp1482 docker.io/rpdharanidhar/devops-task01'
                // bat 'docker build -t latest .'
                // bat 'docker tag rpdharanidhar/devops-task01 devops-task01:latest'
                // // bat 'docker push devops-task01:latest'
                bat "docker login -u rpdharanidhar -p dharanirp1482 && docker push rpdharanidhar/devops-task01"
                // bat 'docker push rpdharanidhar/devops-task01:latest'
                
            }
        }
        
        stage('Run Docker Container') {
            steps {
                // script {
                //     //docker.image("testdemo-jenkins").run("-p 8080:8080")
                //     sh 'docker run -d --name rpdharanidhar/devops-task01:latest -p 8080:80'
                //     // rpdharanidhar/devops-task01:latest
                //     // sh "docker run -d --name ${containerName} ${imageName}"
                // }
                bat 'docker run -d -p 8080:80 rpdharanidhar/devops-task01:latest'
            }
        }
        // // stage('Run Docker Container') {
        // //     steps {
        // //         script {
        // //             // Run a Docker container from the built image
        // //             def dockerContainer = docker.image("${DOCKER_IMAGE_NAME}").run('-d')
        // //             // Get the container ID
        // //             def containerId = dockerContainer.id
        // //             // Print the container ID
        // //             println "Docker container ID: ${containerId}"
        // //         }
        // //     }
        // // }
        stage('Deploy to Kubernetes') {
            steps {
                script {
                    // Deploy your Docker image to Kubernetes
                    kubernetesDeploy(
                        kubeconfigId: 'your-kubeconfig-credentials-id',
                        configs: 'web-deployment.yaml',
                        enableConfigSubstitution: true,
                        showInline: true,
                        namespace: jenkinsdemo-kube
                    )
                }
            }
        }
    }

}

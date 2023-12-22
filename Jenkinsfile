pipeline {
    
    agent any
    
    tools {
        maven '3.9.4'
    }

    environment {
        APP_NAME = "Demo Spring Boot App"
        GIT_REPO = "https://github.com/ad-munir/jenkins-pipeline-demo"
        DOCKER_IMAGE = 'mounirad/demo-app'
    }

    stages {
        stage('Cleanup Workspace') {
            steps {
                cleanWs()
                echo "Cleaned Up Workspace for ${APP_NAME}"
            }
        }

        stage('Stop and Remove Existing Container') {
            steps {
                script {
                    // Stop and remove the existing container if it exists
                    catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
                        bat 'docker stop demo'
                        bat 'docker rm demo'
                    }
                }
            }
        }

        stage('Code Checkout') {
            steps {
                checkout scmGit([
                    branches: [[name: '*/master']],
                    userRemoteConfigs: [[url: GIT_REPO]]
                ])
            }
        }

        stage('Code Build') {
            steps {
                bat 'mvn install -Dmaven.test.skip=true'
            }
        }

        stage('Build Docker Image') {
            steps {
                // Build a Docker image the Spring Boot application
                bat "docker build -t $DOCKER_IMAGE ."
            }
        }

         stage('Run the app as container') {
            steps {
                bat " docker run -d -p 8080:8080 --name demo $DOCKER_IMAGE"
            }
        }

        stage('Push Docker Image') {
            steps {
                    // Utilisez withCredentials pour masquer les informations d'identification
                    withCredentials([usernamePassword(credentialsId: 'DOCKER_HUB', passwordVariable: 'DOCKER_PASSWORD', usernameVariable: 'DOCKER_USERNAME')]) {
                        // Authenticate to Docker Hub registry
                        bat "docker login -u $DOCKER_USERNAME -p $DOCKER_PASSWORD"
                    }
                    
                    // Tag the Docker image for Docker Hub
                    bat "docker tag $DOCKER_IMAGE $DOCKER_IMAGE:latest"


                    // Push the Docker image to Docker Hub
                    bat "docker push $DOCKER_IMAGE:latest"
                }
            }
        }
        
    }
}

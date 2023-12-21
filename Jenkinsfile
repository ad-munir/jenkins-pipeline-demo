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
                bat " docker run -d -p 8080:8081 --name demo $DOCKER_IMAGE"
            }
        }
        
    }
}

pipeline {
    agent any
    tools {
        maven "maven"
    }

    environment {
        GIT_REPO_URL = 'https://github.com/ahurein-amalitech/jenkins-rest-example'
        BRANCH = 'lab_2'
        DOCKER_IMAGE = 'ahurein/lab_2_i'
        DOCKER_CREDENTIALS_ID = 'dockerhub-pwd'
        CONTAINER_TAG = 'lab_2_c'
    }
    
    stages {
        stage("Checkout") {
            steps {
                checkout scmGit(branches: [[name: "*/${BRANCH}"]], extensions: [], userRemoteConfigs: [[url: "${GIT_REPO_URL}"]])
            }
        }

       stage("Unit Tests") {
            steps {
                sh 'mvn test'
            }
            post {
                success {
                    junit '**/target/surefire-reports/*.xml'
                    echo "Unit tests completed successfully"
                }
                failure {
                    echo "Unit tests failed"
                }
            }
        }
        
        stage("Build") {
            steps {
                sh 'mvn clean install'
            }
            post {
                success {
                    echo "Build completed successfully"
                }
                failure {
                    echo "Build failed"
                }
            }
        }
        
        stage("Build Docker Image") {
            steps {
                script {
                    sh "docker stop ${CONTAINER_TAG} || true"
                    sh "docker rm ${CONTAINER_TAG} || true"
                    sh "docker rmi ${DOCKER_IMAGE} || true"
                    sh "docker build --no-cache -t ${DOCKER_IMAGE} ."
                }
            }
            post {
                success {
                    echo "Docker image built successfully"
                }
                failure {
                    echo "Docker image build failed"
                }
            }
        }

        stage("Run Docker Image") {
            steps {
                script {
                    sh "docker run -p 8082:8080 -d --name ${CONTAINER_TAG} ${DOCKER_IMAGE}"
                }
            }
            post {
                success {
                    echo "Docker image run successfully"
                }
                failure {
                    echo "Docker image run failed"
                }
            }
        }
    }

    post {
        always {
            cleanWs()
        }
    }
}
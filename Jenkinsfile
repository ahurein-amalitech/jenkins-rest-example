pipeline {
    agent any
    tools {
        maven "maven"
    }

    environment {
        GIT_REPO_URL = 'https://github.com/ahurein-amalitech/jenkins-rest-example'
        BRANCH = 'lab_3'
        DOCKER_IMAGE = 'ahurein/devops_reg'
        DOCKER_CREDENTIALS_ID = 'dockerhub-pwd'
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
                    sh "docker rmi ${DOCKER_IMAGE} || true"
                    sh "docker build -t ${DOCKER_IMAGE} ."
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
        
        stage("Push Docker Image to Docker Hub") {
            steps {
                script {
                    withCredentials([string(credentialsId: "${DOCKER_CREDENTIALS_ID}", variable: 'dockerhubpwd')]) {
                        sh "echo ${dockerhubpwd} | docker login -u ahurein --password-stdin"
                    }
                    sh "docker push ${DOCKER_IMAGE}"
                }
            }
            post {
                success {
                    echo "Docker image pushed to Docker Hub successfully"
                }
                failure {
                    echo "Docker image push failed"
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

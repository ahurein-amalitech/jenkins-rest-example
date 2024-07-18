pipeline {

    agent any

    tools {
        maven "maven"
    }
    
    environment {
        GIT_REPO_URL = 'https://github.com/ahurein-amalitech/jenkins-rest-example'
        BRANCH = 'lab_2'
        TOMCAT_URL = 'http://localhost:8081'
        CREDENTIALS_ID = 'tomcat_admin'
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
                sh 'mvn clean package'
            }
            post {
                success {
                    echo "Archiving the artifact"
                    archiveArtifacts artifacts: "**/target/*.war"
                }
                failure {
                    echo "Build failed"
                }
            }
        }

        stage("Deploy to Tomcat") {
            steps {
                deploy adapters: [tomcat9(credentialsId: "${CREDENTIALS_ID}", path: '', url: "${TOMCAT_URL}")], contextPath: null, war: '**/*.war'
            }
            post {
                success {
                    echo "Deployment to Tomcat successful"
                }
                failure {
                    echo "Deployment to Tomcat failed"
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

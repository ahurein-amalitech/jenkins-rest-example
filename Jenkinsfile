pipeline {
    agent any
    tools {
        maven "maven"
    }
    
    stages {
        stage("Build") {
            steps {
                checkout scmGit(branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/ahurein-amalitech/jenkins-rest-example']])
                sh 'mvn clean install'
            }
        }
        
        stage("Build docker image"){
            steps {
                script {
                    sh 'docker rmi ahurein/devops_doc'
                    sh "docker build -t ahurein/devops_doc ."
                }
            }
        }
        
        stage("Push image to docker hub"){
            steps {
                script {
                    withCredentials([string(credentialsId: 'dockerhub-pwd', variable: 'dockerhubpwd')]) {
                        sh "docker login -u ahurein -p ${dockerhubpwd}"
                    }
                    sh 'docker push ahurein/devops_doc'
                }
            }
        }
    }
    
}
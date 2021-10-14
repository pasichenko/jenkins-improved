pipeline {
    agent any
    tools {
        maven 'maven'
        jdk 'jdk'
    }
    stages {
        stage('Build') {
            steps {
                sh 'mvn clean verify'
            }
        }
        stage('Deploy') {
            steps {
                deploy adapters: [tomcat8(
                        credentialsId: 'tomcat-test-hp',
                        path: '',
                        url: 'http://192.168.0.4:8083/')],
                        contextPath: '/jenkins',
                        war: 'target/jenkins.war'
            }
        }
    }

}
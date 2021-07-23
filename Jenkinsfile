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
            sh 'mv target/jenkins-0.0.1-SNAPSHOT.jar jenkins-app.jar'
            sshPublisher(
                    continueOnError: false,
                    failOnError: true,
                    publishers: [
                            sshPublisherDesc(
                                    configName: "remote_deploy_server",
                                    transfers: [sshTransfer(
                                            sourceFiles: 'jenkins-app.jar',
                                            execCommand: "./run.sh"
                                    )
                                    ],
                                    verbose: true,
                            )
                    ]
            )
        }
    }

}
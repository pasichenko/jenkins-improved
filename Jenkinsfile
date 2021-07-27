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
        stage('Commit Database Changeset') {
            when {
                branch 'master'
            }
            steps {
                sh "cd src"
                sh "ls -l"
            }
        }
        stage('Deploy') {
            stages {
                stage('Pre deploy preparation') {
                    steps {
                        catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
                            script {
                                try {
                                    timeout(time: 20, unit: 'SECONDS') {
                                        RELEASE_SCOPE = input(
                                                id: "IDAPP",
                                                message: "You want deploy artifact?",
                                                ok: "Continue",
                                                parameters: [
                                                        choice(name: "CHOICES", choices: ['Not deploy', 'Deploy'])

                                                ]
                                        )
                                    }
                                } catch (ignored) {
                                    RELEASE_SCOPE = 'fail'
                                }
                            }
                        }
                    }
                }
                stage('Deploy') {
                    when {
                        anyOf {
                            expression { RELEASE_SCOPE == 'Deploy' }
                            branch 'master'
                        }

                    }
                    steps {
                        sh 'mv target/jenkins-default-0.0.1-SNAPSHOT.jar jenkins-app.jar'
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
        }
    }

}
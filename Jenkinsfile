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
                sh "echo Step DB run in master"
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
                                } catch (err) {
                                    RELEASE_SCOPE = 'fail'
                                }
                            }
                        }
                    }
                }
                stage('Deploy') {
                    when {
                        anyOf {
                            expression { RELEASE_SCOPE == 'Deploy' };
                            branch 'master'
                        }

                    }
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
    }

}
pipelineJob('SonarQube_Analysis_Job') {
    definition {
        cps {
            script('''
pipeline {
    agent any
    environment {
        SONARQUBE_URL = 'https://sonarqube.trainingnotes.fit/'
        SONARQUBE_CREDENTIALS = credentials('sonarqube-token-id')  // tutaj podajesz ID kredencji Jenkins dla SonarQube
    }
    stages {
        stage('Checkout') {
            steps {
                git url: 'https://github.com/fanfanafankianki/Django_Website.git', branch: 'main'
            }
        }
        stage('SonarQube Analysis') {
            environment {
                scannerHome = tool 'SonarScanner'
            }
            steps {
                withSonarQubeEnv('sonar') {
                    sh """
                    ${scannerHome}/bin/sonar-scanner \\
                    -Dsonar.projectKey=PwrTrckrSite \\
                    -Dsonar.projectName=PwrTrckr \\
                    -Dsonar.projectVersion=1.0 \\
                    -Dsonar.sources=. \\
                    -Dsonar.java.binaries=target/test-classes/com/visualpathit/account/controllerTest/ \\
                    -Dsonar.junit.reportsPath=target/surefire-reports/ \\
                    -Dsonar.jacoco.reportsPath=target/jacoco.exec \\
                    -Dsonar.java.checkstyle.reportPaths=target/checkstyle-result.xml \\
                    -Dsonar.login=${SONARQUBE_CREDENTIALS}"""
                }
            }
        }
        stage("Quality Gate") {
            steps {
                script {
                    timeout(time: 1, unit: 'HOURS') {
                        waitForQualityGate abortPipeline: true
                    }
                }
            }
        }
    }
}
            '''.stripIndent())
            sandbox()
        }
    }
}

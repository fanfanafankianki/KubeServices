pipeline {
    agent any // lub wybierz odpowiedni agent

    triggers {
        cron('H/15 * * * *')
    }

    options {
        buildDiscarder(logRotator(numToKeepStr: '10', artifactNumToKeepStr: '5'))
    }

    stages {
        stage('Przykładowy etap') {
            steps {
                sh 'echo "Hello, World!"'
            }
        }
    }

    description 'To jest przykładowe zadanie Jenkins'
}

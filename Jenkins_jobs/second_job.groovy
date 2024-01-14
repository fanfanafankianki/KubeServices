pipeline {
    agent any

    stages {
        stage('Clone Repository') {
            steps {
                // Klonowanie repozytorium Git - zmień URL na właściwy
                git url: 'https://github.com/fanfanafankianki/KubeServices.git', branch: 'main'
            }
        }

        stage('Print Current Directory') {
            steps {
                script {
                    // Wyświetlenie aktualnej ścieżki do folderu
                    def currentDirectory = pwd()
                    echo "Aktualny folder: ${currentDirectory}"
                }
            }
        }

        stage('List Files in Current Directory') {
            steps {
                script {
                    // Wyświetlenie listy plików w bieżącym folderze
                    sh 'ls -l'
                }
            }
        }

        stage('Create Jenkins Jobs from DSL Scripts') {
            steps {
                script {
                    // Znajdowanie plików DSL w bieżącym folderze
                    def dslScripts = sh(script: "find . -name '*.groovy'", returnStdout: true).trim().split('\n')

                    // Wypisanie ścieżek do znalezionych skryptów DSL
                    echo "Znalezione skrypty DSL: ${dslScripts.join(', ')}"
                    
                    // Iterowanie po znalezionych skryptach i tworzenie jobów
                    dslScripts.each { dslScript ->
                        echo "Adding DSL script as job: ${dslScript}"

                        // Wczytywanie skryptu DSL jako nowy job
                        jobDsl targets: "${dslScript}", ignoreExisting: false
                    }
                }
            }
        }
    }
}

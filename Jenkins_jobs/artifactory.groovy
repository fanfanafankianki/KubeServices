pipelineJob('Artifactory') {
  definition {
    cps {
      script("""
pipeline {
    agent {
        kubernetes {
            label 'docker'
            defaultContainer 'docker'
            yaml \"""
apiVersion: v1
kind: Pod
spec:
  containers:
  - name: docker
    image: docker:latest
    command:
    - cat
    tty: true
    volumeMounts:
    - name: docker-sock
      mountPath: /var/run/docker.sock
  volumes:
  - name: docker-sock
    hostPath:
      path: /var/run/docker.sock
\"""
        }
    }
    environment {
        ARTIFACTORY_URL = 'artif'
        ARTIFACTORY_REPO = 'kluczyk'
        ARTIFACTORY_CREDENTIALS = credentials('your-artifactory-credentials-id')
        IMAGE_NAME = "your-dockerhub-username/your-image-name"
        IMAGE_TAG = "latest"
    }
    stages {
        stage('Checkout') {
            steps {
                // Sprawdzenie kodu źródłowego z repozytorium
                git url: 'https://github.com/fanfanafankianki/Django_Website.git', branch: 'main'
            }
        }
        stage('Build Docker Image') {
            steps {
                script {
                    sh 'docker build -t artifactory.trainingnotes.fit/docker/fanfan:jdwa app/.'
                }
            }
        }
        stage('Login to Artifactory') {
            steps {
                script {
                    sh "docker login -u jenkins -p Password123 https://artifactory.trainingnotes.fit"
                }
            }
        }
        stage('Push Docker Image') {
            steps {
                script {
                    sh 'docker push artifactory.trainingnotes.fit/docker/fanfan:jdwa'
                }
            }
        }
    }
}
""")
      sandbox()
    }
  }
}
pipelineJob('First_job') {
  definition {
    cps {
      script("""
pipeline {
    agent {
        kubernetes {
            label 'docker'
            defaultContainer 'docker'
            yaml \"""
apiVersion: v1
kind: Pod
spec:
  containers:
  - name: docker
    image: docker:latest
    command:
    - cat
    tty: true
    volumeMounts:
    - name: docker-sock
      mountPath: /var/run/docker.sock
  volumes:
  - name: docker-sock
    hostPath:
      path: /var/run/docker.sock
\"""
        }
    }
    environment {
        ARTIFACTORY_URL = 'artif'
        ARTIFACTORY_REPO = 'kluczyk'
        ARTIFACTORY_CREDENTIALS = credentials('your-artifactory-credentials-id')
        IMAGE_NAME = "your-dockerhub-username/your-image-name"
        IMAGE_TAG = "latest"
    }
    stages {
        stage('Checkout') {
            steps {
                // Sprawdzenie kodu źródłowego z repozytorium
                git url: 'https://github.com/fanfanafankianki/Django_Website.git', branch: 'main'
            }
        }
        stage('Build Docker Image') {
            steps {
                script {
                    sh 'docker build -t artifactory.trainingnotes.fit/docker/fanfan:jdwa app/.'
                }
            }
        }
        stage('Login to Artifactory') {
            steps {
                script {
                    sh "docker login -u jenkins -p Password123 https://artifactory.trainingnotes.fit"
                }
            }
        }
        stage('Push Docker Image') {
            steps {
                script {
                    sh 'docker push artifactory.trainingnotes.fit/docker/fanfan:jdwa'
                }
            }
        }
    }
}
""")
      sandbox()
    }
  }
}

pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                echo 'Building...'
                sh '''
                chmod +x gradlew
                ./gradlew build
                '''
            }
        }
        stage('Test') {
            steps {
                echo 'Testing...'
                // Run tests using Gradle wrapper
                sh './gradlew test'
            }
            post {
                always {
                    // Archive failed_log.txt if it exists
                    archiveArtifacts artifacts: 'failed_log.txt', allowEmptyArchive: true
                }
            }
        }
        stage('Deploy') {
            steps {
                echo 'Deploying...'
            }
        }
    }

    post {
        success {
            echo 'Build succeeded!'
        }
        failure {
            echo 'Build failed! Sending notification...'
            // Add notification logic here (e.g., email, Slack)
        }
    }
}
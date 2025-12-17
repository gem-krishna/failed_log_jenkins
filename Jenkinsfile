pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                echo 'Building project...'
                sh '''
                    chmod +x gradlew
                    ./gradlew assemble
                '''
            }
        }
        stage('Check zip tools') {
            steps {
                sh '''
                    echo "Checking zip:"
                    command -v zip || echo "zip NOT found"

                    echo "Checking tar:"
                    command -v tar || echo "tar NOT found"
                '''
            }
        }


        stage('Test') {
            steps {
                echo 'Running tests (expected to fail)...'
                catchError(buildResult: 'FAILURE', stageResult: 'FAILURE') {
                    sh './gradlew test'
                }
            }
        }
    }

    post {
        always {
            echo 'Post stage: preparing artifacts'
            sh 'ls -la'

            script {
                if (fileExists('failed_log.txt')) {
                    echo 'failed_log.txt found – creating downloadable zip artifact'
                    sh 'tar -czf failed_log.tar.gz failed_log.txt'

                    archiveArtifacts artifacts: 'failed_log.tar.gz', fingerprint: true
                    currentBuild.description = '❌ Tests failed – failed_log.zip available'
                } else {
                    echo 'failed_log.txt NOT found'
                }
            }
        }

        failure {
            echo 'Build failed (EXPECTED for demo)'
        }
    }
}

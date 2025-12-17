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

        stage('Test') {
            steps {
                echo 'Running tests...'
                catchError(buildResult: 'FAILURE', stageResult: 'FAILURE') {
                    sh './gradlew test'
                }
            }
        }
    }

    post {
        always {
            echo 'Packaging failed_log.txt if present'

            script {
                if (fileExists('failed_log.txt')) {
                    // Jenkins-native zip (NO system zip required)
                    zip zipFile: 'failed_log.zip', archive: false, dir: '.', glob: 'failed_log.txt'

                    // Archive the zip so it is downloadable
                    archiveArtifacts artifacts: 'failed_log.zip', allowEmptyArchive: true

                    // Optional: show content inline
                    def logContent = readFile('failed_log.txt')
                    summary {
                        text("""
### Failed Log
<pre>${logContent}</pre>
""")
                    }
                } else {
                    echo 'failed_log.txt not found'
                }
            }
        }

        success {
            echo 'Build succeeded!'
        }

        failure {
            echo 'Build failed (EXPECTED for demo)'
        }
    }
}

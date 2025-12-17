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
                // Allow tests to fail but continue pipeline
                catchError(buildResult: 'FAILURE', stageResult: 'FAILURE') {
                    sh './gradlew test'
                }
            }
            post {
                always {
                    echo 'Zipping failed_log.txt if present'

                    script {
                        if (fileExists('failed_log.txt')) {
                            sh 'zip -r failed_log.zip failed_log.txt'
                        }
                    }

                    // Archive the zip file
                    archiveArtifacts artifacts: 'failed_log.zip', allowEmptyArchive: true

                    // Optional: show contents inline in build summary
                    script {
                        if (fileExists('failed_log.txt')) {
                            def logContent = readFile('failed_log.txt')
                            summary {
                                text("""
### Failed Log
<pre>${logContent}</pre>
""")
                            }
                        }
                    }
                }
            }
        }

        stage('Deploy') {
            when {
                expression { currentBuild.result == null || currentBuild.result == 'SUCCESS' }
            }
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
            echo 'Build failed! (Expected for demo purposes)'
        }
    }
}

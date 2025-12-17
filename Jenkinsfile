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
                echo 'Running tests (expected to fail)...'
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

                    // Jenkins-native zip (NO system zip needed)
                    zip zipFile: 'failed_log.zip',
                        archive: false,
                        dir: '.',
                        glob: 'failed_log.txt'

                    // Archive so it is downloadable
                    archiveArtifacts artifacts: 'failed_log.zip', allowEmptyArchive: true

                    // Set build description (visible on build page)
                    currentBuild.description = '❌ Tests failed – failed_log.zip available'
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

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
            echo 'Post stage: preparing artifacts'
            sh 'ls -la'
            sh 'ls -la target || true'

            script {
                if (fileExists('target/failed_log.txt')) {
                    echo 'failed_log.txt found in target/ – creating archive'

                    sh '''
                        tar -czf failed_log.tar.gz -C target failed_log.txt
                    '''

                    archiveArtifacts artifacts: 'failed_log.tar.gz', fingerprint: true

                    currentBuild.description =
                        '❌ Tests failed – failed_log.tar.gz available'
                } else {
                    echo 'failed_log.txt NOT found in target/'
                }
            }
        }

        failure {
            echo 'Build failed (EXPECTED for demo)'
        }
    }
}

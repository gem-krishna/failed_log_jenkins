// pipeline {
//     agent any
//
//     stages {
//         stage('Build') {
//             steps {
//                 echo 'Building...'
//                 sh '''
//                 chmod +x gradlew
//                 ./gradlew build
//                 '''
//             }
//         }
//         stage('Test') {
//             steps {
//                 echo 'Testing...'
//                 // Run tests using Gradle wrapper
//                 sh './gradlew test'
//             }
//             post {
//                 always {
//                     // Archive failed_log.txt if it exists
//                     archiveArtifacts artifacts: 'failed_log.txt', allowEmptyArchive: true
//                     // Display failed_log.txt content in build summary if it exists
//                     script {
//                         if (fileExists('failed_log.txt')) {
//                             def logContent = readFile('failed_log.txt')
//                             summary {
//                                 text("""
// ### Failed Log
// <pre>${logContent}</pre>
// """)
//                             }
//                         }
//                     }
//                 }
//             }
//         }
//         stage('Deploy') {
//             steps {
//                 echo 'Deploying...'
//             }
//         }
//     }
//
//     post {
//         success {
//             echo 'Build succeeded!'
//         }
//         failure {
//             echo 'Build failed! Sending notification...'
//             // Add notification logic here (e.g., email, Slack)
//         }
//     }
// }

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
                    echo 'Archiving failed_log.txt if present'
                    archiveArtifacts artifacts: 'failed_log.txt', allowEmptyArchive: true

                    script {
                        if (fileExists('failed_log.txt')) {
                            def logContent = readFile('failed_log.txt')
                            // Add content to build summary
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
            echo 'Build failed! (This is expected for demo purposes)'
        }
    }
}

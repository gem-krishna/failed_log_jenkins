// pipeline {
//     agent any
//
//     stages {
//         stage('Build') {
//             steps {
//                 echo 'Building project...'
//                 sh '''
//                     chmod +x gradlew
//                     ./gradlew assemble
//                 '''
//             }
//         }
//
//         stage('Test') {
//             steps {
//                 echo 'Running tests (expected to fail)...'
//                 catchError(buildResult: 'FAILURE', stageResult: 'FAILURE') {
//                     sh './gradlew test'
//                 }
//             }
//         }
//     }
//
//     post {
//         always {
//             echo 'Packaging failed_log.txt if present'
//
//             script {
//                 if (fileExists('failed_log.txt')) {
//
//                     // Jenkins-native zip (NO system zip needed)
//                     zip zipFile: 'failed_log.zip',
//                         archive: false,
//                         dir: '.',
//                         glob: 'failed_log.txt'
//
//                     // Archive so it is downloadable
//                     archiveArtifacts artifacts: 'failed_log.zip', allowEmptyArchive: true
//
//                     // Set build description (visible on build page)
//                     currentBuild.description = '❌ Tests failed – failed_log.zip available'
//                 } else {
//                     echo 'failed_log.txt not found'
//                 }
//             }
//         }
//
//         success {
//             echo 'Build succeeded!'
//         }
//
//         failure {
//             echo 'Build failed (EXPECTED for demo)'
//         }
//     }
// }
// pipeline {
//     agent any
//
//     stages {
//         stage('Build') {
//             steps {
//                 echo 'Building project...'
//                 sh '''
//                     chmod +x gradlew
//                     ./gradlew assemble
//                 '''
//             }
//         }
//
//         stage('Test') {
//             steps {
//                 echo 'Running tests (expected to fail)...'
//                 catchError(buildResult: 'FAILURE', stageResult: 'FAILURE') {
//                     sh './gradlew test'
//                 }
//             }
//         }
//     }
//
//     post {
//         always {
//             echo 'Post stage: preparing artifacts'
//
//             sh 'pwd'
//             sh 'ls -la'
//
//             script {
//                 if (fileExists('failed_log.txt')) {
//                     echo 'failed_log.txt found, zipping it'
//
//                     zip zipFile: 'failed_log.zip',
//                         archive: false,
//                         dir: '.',
//                         glob: 'failed_log.txt'
//
//                 } else {
//                     echo 'failed_log.txt NOT found'
//                 }
//             }
//
//             // 🔑 ALWAYS archive (this controls the UI section)
//             archiveArtifacts artifacts: 'failed_log.zip', allowEmptyArchive: false
//
//             script {
//                 if (fileExists('failed_log.zip')) {
//                     currentBuild.description = '❌ Tests failed – failed_log.zip available'
//                 }
//             }
//         }
//
//         failure {
//             echo 'Build failed (EXPECTED for demo)'
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

                    // ✅ THIS BOTH ZIPS *AND* ARCHIVES
                    zip zipFile: 'failed_log.zip',
                        archive: true,
                        dir: '.',
                        glob: 'failed_log.txt'

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

def call() {
    // In shared libs, use `script {}` to safely access env vars and run Groovy logic
    script {
        echo "${env.APP_GREETING} - Environment: ${env.ENV}"
        echo "Node name: ${env.NODE_NAME}"
        echo "Workspace: ${env.WORKSPACE}"
        echo "BRANCH_NAME=${env.BRANCH_NAME}"
        echo "GIT_BRANCH=${env.GIT_BRANCH}"
        echo "Building ${env.JOB_NAME} #${env.BUILD_NUMBER}"
    }

    sh 'java -version'
    sh 'mvn -version'
}
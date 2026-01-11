#!/usr/bin/env groovy

/**
 * Generic build step for applications
 * 
 * @param config Map containing build configuration
 *   - buildTool: The build tool to use (maven, gradle, npm, etc.)
 *   - buildCommand: Optional custom build command
 *   - skipTests: Whether to skip tests (default: false)
 * @return void
 */
def call(Map config = [:]) {
    def buildTool = config.buildTool ?: 'maven'
    def skipTests = config.skipTests ?: false
    def buildCommand = config.buildCommand
    
    echo "Building application with ${buildTool}..."
    
    if (buildCommand) {
        sh buildCommand
    } else {
        switch(buildTool) {
            case 'maven':
                def mvnCmd = skipTests ? 'mvn clean install -DskipTests' : 'mvn clean install'
                sh mvnCmd
                break
            case 'gradle':
                def gradleCmd = skipTests ? './gradlew build -x test' : './gradlew build'
                sh gradleCmd
                break
            case 'npm':
                sh 'npm install'
                if (!skipTests) {
                    sh 'npm test'
                }
                sh 'npm run build'
                break
            default:
                error "Unsupported build tool: ${buildTool}"
        }
    }
    
    echo "Build completed successfully!"
}

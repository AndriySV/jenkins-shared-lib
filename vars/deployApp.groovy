#!/usr/bin/env groovy

/**
 * Generic deployment step for applications
 * 
 * @param config Map containing deployment configuration
 *   - environment: Target environment (dev, staging, prod)
 *   - appName: Application name
 *   - version: Application version (default: 'latest')
 *   - deployCommand: Optional custom deployment command
 * @return void
 */
def call(Map config = [:]) {
    def environment = config.environment ?: 'dev'
    def appName = config.appName ?: error('appName is required')
    def version = config.version ?: 'latest'
    def deployCommand = config.deployCommand
    
    echo "Deploying ${appName} version ${version} to ${environment}..."
    
    if (deployCommand) {
        sh deployCommand
    } else {
        echo "Using default deployment logic"
        echo "Application: ${appName}"
        echo "Version: ${version}"
        echo "Environment: ${environment}"
        // Add your deployment logic here
    }
    
    echo "Deployment to ${environment} completed successfully!"
}

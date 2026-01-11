# Jenkins Shared Library

Jenkins Shared Library containing reusable pipeline steps and CI/CD logic for Jenkins pipelines.

## Overview

This library provides a collection of reusable pipeline steps, utility classes, and resources to simplify and standardize Jenkins pipeline development across your organization.

## Structure

```
jenkins-shared-lib/
├── vars/                    # Global variables and pipeline steps
│   ├── sayHello.groovy     # Simple greeting step
│   ├── buildApp.groovy     # Generic build step
│   ├── deployApp.groovy    # Generic deployment step
│   └── notifySlack.groovy  # Slack notification step
├── src/                     # Groovy source files
│   └── com/example/jenkins/
│       ├── Logger.groovy   # Logger utility class
│       └── Config.groovy   # Configuration utility class
├── resources/               # External resources
│   └── pipeline-config.yaml # Example configuration file
└── Jenkinsfile.example      # Example usage
```

## Installation

### 1. Configure in Jenkins

1. Go to **Manage Jenkins** → **Configure System**
2. Scroll to **Global Pipeline Libraries**
3. Click **Add**
4. Configure:
   - **Name**: `jenkins-shared-lib` (or your preferred name)
   - **Default version**: `main` (or your default branch)
   - **Retrieval method**: Modern SCM
   - **Source Code Management**: Git
   - **Project Repository**: `https://github.com/AndriySV/jenkins-shared-lib.git`

### 2. Use in Jenkinsfile

Add this line at the top of your Jenkinsfile:

```groovy
@Library('jenkins-shared-lib') _
```

## Usage

### Pipeline Steps

#### sayHello

Simple greeting step for testing and demonstration.

```groovy
sayHello('World')  // Outputs: Hello, World!
```

#### buildApp

Generic build step supporting multiple build tools.

```groovy
// Maven build
buildApp(
    buildTool: 'maven',
    skipTests: false
)

// Gradle build
buildApp(
    buildTool: 'gradle',
    skipTests: true
)

// NPM build
buildApp(
    buildTool: 'npm'
)

// Custom build command
buildApp(
    buildCommand: 'make build'
)
```

**Parameters:**
- `buildTool`: Build tool to use (maven, gradle, npm) - default: 'maven'
- `skipTests`: Whether to skip tests - default: false
- `buildCommand`: Custom build command (overrides buildTool)

#### deployApp

Generic deployment step for various environments.

```groovy
deployApp(
    environment: 'dev',
    appName: 'my-app',
    version: '1.0.0'
)

// Custom deployment command
deployApp(
    environment: 'prod',
    appName: 'my-app',
    deployCommand: 'kubectl apply -f k8s/'
)
```

**Parameters:**
- `environment`: Target environment (dev, staging, prod) - default: 'dev'
- `appName`: Application name (required)
- `version`: Application version - default: 'latest'
- `deployCommand`: Custom deployment command

#### notifySlack

Send notifications to Slack.

```groovy
notifySlack(
    channel: '#ci-cd',
    message: 'Build completed successfully',
    color: 'good',
    credentialId: 'slack-token'
)
```

**Parameters:**
- `channel`: Slack channel - default: '#general'
- `message`: Message to send - default: 'Build notification'
- `color`: Message color (good, warning, danger) - default: 'good'
- `credentialId`: Jenkins credential ID for Slack token

### Utility Classes

#### Logger

Structured logging utility for pipelines.

```groovy
import com.example.jenkins.Logger

def logger = new Logger(this)
logger.info('Information message')
logger.warn('Warning message')
logger.error('Error message')
logger.debug('Debug message')

logger.section('Build Phase') {
    logger.info('Starting build...')
}
```

#### Config

Configuration management utility.

```groovy
import com.example.jenkins.Config

def config = new Config([
    environment: 'dev',
    version: '1.0.0'
])

config.set('buildTool', 'maven')
def env = config.get('environment', 'default')
config.validate(['environment', 'version'])
```

## Example Pipeline

See [Jenkinsfile.example](Jenkinsfile.example) for a complete example demonstrating all features.

```groovy
@Library('jenkins-shared-lib') _

pipeline {
    agent any
    
    stages {
        stage('Build') {
            steps {
                script {
                    sayHello('Jenkins')
                    buildApp(buildTool: 'maven')
                }
            }
        }
        
        stage('Deploy') {
            steps {
                script {
                    deployApp(
                        environment: 'dev',
                        appName: 'my-app',
                        version: '1.0.0'
                    )
                }
            }
        }
    }
    
    post {
        success {
            script {
                notifySlack(
                    message: "✅ Build succeeded",
                    color: 'good'
                )
            }
        }
    }
}
```

## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/my-feature`)
3. Make your changes
4. Test your changes in a Jenkins instance
5. Commit your changes (`git commit -am 'Add new feature'`)
6. Push to the branch (`git push origin feature/my-feature`)
7. Create a Pull Request

## Best Practices

- Keep pipeline steps simple and focused on a single responsibility
- Use meaningful parameter names and provide defaults
- Document all parameters and return values
- Handle errors gracefully
- Use the Logger utility for consistent logging
- Version your shared library using Git tags
- Test changes in a non-production Jenkins instance first

## License

This project is provided as-is for use in Jenkins pipeline automation.

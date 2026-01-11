#!/usr/bin/env groovy

/**
 * Send notification to Slack
 * 
 * @param config Map containing notification configuration
 *   - channel: Slack channel (default: '#general')
 *   - message: Message to send
 *   - color: Message color (good, warning, danger)
 *   - credentialId: Jenkins credential ID for Slack token
 * @return void
 */
def call(Map config = [:]) {
    def channel = config.channel ?: '#general'
    def message = config.message ?: 'Build notification'
    def color = config.color ?: 'good'
    def credentialId = config.credentialId
    
    echo "Sending notification to Slack channel: ${channel}"
    echo "Message: ${message}"
    echo "Color: ${color}"
    
    // If Slack plugin is installed and configured
    if (credentialId) {
        try {
            slackSend(
                channel: channel,
                color: color,
                message: message,
                tokenCredentialId: credentialId
            )
        } catch (Exception e) {
            echo "Warning: Failed to send Slack notification: ${e.message}"
        }
    } else {
        echo "Slack credential not configured. Skipping actual notification."
    }
}

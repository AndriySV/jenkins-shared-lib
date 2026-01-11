package com.example.jenkins

/**
 * Logger utility for Jenkins pipelines
 */
class Logger implements Serializable {
    private def script
    
    Logger(script) {
        this.script = script
    }
    
    void info(String message) {
        script.echo "[INFO] ${message}"
    }
    
    void warn(String message) {
        script.echo "[WARN] ${message}"
    }
    
    void error(String message) {
        script.echo "[ERROR] ${message}"
    }
    
    void debug(String message) {
        script.echo "[DEBUG] ${message}"
    }
    
    void section(String title, Closure body) {
        script.echo "========================================="
        script.echo "  ${title}"
        script.echo "========================================="
        body()
        script.echo "========================================="
    }
}

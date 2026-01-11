package com.example.jenkins

/**
 * Configuration utility for Jenkins pipelines
 */
class Config implements Serializable {
    private Map config = [:]
    
    Config(Map initialConfig = [:]) {
        this.config = initialConfig
    }
    
    /**
     * Get a configuration value
     */
    def get(String key, def defaultValue = null) {
        return config.get(key, defaultValue)
    }
    
    /**
     * Set a configuration value
     */
    void set(String key, def value) {
        config[key] = value
    }
    
    /**
     * Check if a configuration key exists
     */
    boolean has(String key) {
        return config.containsKey(key)
    }
    
    /**
     * Merge another configuration map
     */
    void merge(Map otherConfig) {
        config = config + otherConfig
    }
    
    /**
     * Get all configuration as a map
     */
    Map getAll() {
        return config.clone()
    }
    
    /**
     * Validate required configuration keys
     */
    void validate(List<String> requiredKeys) {
        def missing = requiredKeys.findAll { !has(it) }
        if (missing) {
            throw new IllegalArgumentException("Missing required configuration keys: ${missing.join(', ')}")
        }
    }
}

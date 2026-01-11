#!/usr/bin/env groovy

/**
 * Simple hello world step
 * 
 * @param name The name to greet (default: 'World')
 * @return void
 */
def call(String name = 'World') {
    echo "Hello, ${name}!"
}

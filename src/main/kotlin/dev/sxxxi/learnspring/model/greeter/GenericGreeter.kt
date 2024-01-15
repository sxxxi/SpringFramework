package dev.sxxxi.learnspring.model.greeter

import org.springframework.stereotype.Component

class GenericGreeter : Greeter {
    override fun greet(name: String): String {
        return "Hello, $name"
    }
}
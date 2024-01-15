package dev.sxxxi.learnspring.model.greeter

import org.springframework.stereotype.Component

class FriendlyGreeter : Greeter {
    override fun greet(name: String): String {
        return "Hi $name! Hope you're having a nice day!"
    }
}
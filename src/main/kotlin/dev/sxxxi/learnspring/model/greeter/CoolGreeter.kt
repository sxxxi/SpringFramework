package dev.sxxxi.learnspring.model.greeter

class CoolGreeter : Greeter {
    override fun greet(name: String): String {
        return "Wassup, $name!"
    }
}
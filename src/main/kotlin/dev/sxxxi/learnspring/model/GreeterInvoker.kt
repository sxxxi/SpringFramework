package dev.sxxxi.learnspring.model

import dev.sxxxi.learnspring.model.greeter.Greeter
import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.InitializingBean
import org.springframework.stereotype.Component

@Component
class GreeterInvoker {
    lateinit var greeter: Greeter

    init {
        println("Invoker created!")
    }

    fun invoke() {
        greeter.greet("Bob")
    }

    @PostConstruct
    fun init() {
        println("In afterPropertiesSet()")
    }
}
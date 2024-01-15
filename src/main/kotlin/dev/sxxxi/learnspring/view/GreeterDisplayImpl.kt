package dev.sxxxi.learnspring.view

import org.springframework.stereotype.Component

class GreeterDisplayImpl : GreeterDisplay {
    override fun display(greeting: String) {
        println(greeting)
    }
}
package dev.sxxxi.learnspring.model

import dev.sxxxi.learnspring.model.greeter.CoolGreeter
import dev.sxxxi.learnspring.model.greeter.FriendlyGreeter
import dev.sxxxi.learnspring.model.greeter.GenericGreeter

interface GreeterFactory {
    fun getCoolGreeter(): CoolGreeter
    fun getFriendlyGreeter(): FriendlyGreeter
    fun getDefaultGreeter(): GenericGreeter
}
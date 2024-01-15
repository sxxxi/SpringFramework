package dev.sxxxi.learnspring.model

import dev.sxxxi.learnspring.model.greeter.CoolGreeter
import dev.sxxxi.learnspring.model.greeter.FriendlyGreeter
import dev.sxxxi.learnspring.model.greeter.GenericGreeter
import dev.sxxxi.learnspring.model.greeter.Greeter

class GreeterPicker(
    private val coolGreeter: CoolGreeter,
    private val friendlyGreeter: FriendlyGreeter,
    private val defaultGreeter: GenericGreeter
) {
    fun pickGreeter(type: String): Greeter {
        return when (type) {
            "cool" -> coolGreeter
            "friendly" -> friendlyGreeter
            else -> defaultGreeter
        }
    }
}
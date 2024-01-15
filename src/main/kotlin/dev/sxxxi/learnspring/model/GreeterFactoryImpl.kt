package dev.sxxxi.learnspring.model

import dev.sxxxi.learnspring.model.greeter.CoolGreeter
import dev.sxxxi.learnspring.model.greeter.FriendlyGreeter
import dev.sxxxi.learnspring.model.greeter.GenericGreeter
import dev.sxxxi.learnspring.model.greeter.Greeter
import org.springframework.beans.factory.getBean
import org.springframework.context.ApplicationContext

class GreeterFactoryImpl : GreeterFactory {
    companion object {
        private val coolGreeter = CoolGreeter()
        private val friendlyGreeter = FriendlyGreeter()
        private val defaultGreeter = GenericGreeter()
    }

    override fun getCoolGreeter(): CoolGreeter = coolGreeter
    override fun getFriendlyGreeter(): FriendlyGreeter = friendlyGreeter
    override fun getDefaultGreeter(): GenericGreeter = defaultGreeter
}
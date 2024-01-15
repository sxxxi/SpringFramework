package dev.sxxxi.learnspring

import dev.sxxxi.learnspring.model.GreeterFactory
import dev.sxxxi.learnspring.model.GreeterPicker
import dev.sxxxi.learnspring.view.GreeterDisplay
import org.springframework.beans.factory.getBean
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.ApplicationContext
import org.springframework.context.support.ClassPathXmlApplicationContext
import java.util.*

@SpringBootApplication
class LearnSpringApplication : CommandLineRunner {
	companion object {
		@JvmStatic
		fun main(args: Array<String>) {
			runApplication<LearnSpringApplication>(*args)
		}
	}

	override fun run(args: Array<String>) {


		val container = ClassPathXmlApplicationContext("greeters.xml")

		val s = Scanner(System.`in`)
		println("What is your name?")
		val name = s.nextLine()
		println("How do you want to be greeted?")
		val response = s.nextLine()

		val greeterPicker = container.getBean<GreeterPicker>()
		val greeter = greeterPicker.pickGreeter(response)
		val display = container.getBean<GreeterDisplay>()
		display.display(greeter.greet(name))




	}
}

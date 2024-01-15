# SPRING FRAMEWORK NOTES
___
## TODO
- Method Injection
- Test
___
## IoC and Dependency Injection
### Containers
ApplicationContext interface represents the IoC container and lets you read and access bean definitions.
#### Reading Bean definitions from different file types

```kotlin

import org.springframework.beans.factory.groovy.GroovyBeanDefinitionReader
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader
import org.springframework.context.support.ClassPathXmlApplicationContext
import org.springframework.context.support.GenericApplicationContext
import org.springframework.context.support.GenericGroovyApplicationContext

// These files must be defined in the resources directory
val pathToBeanDefinitionXML = "bar.xml"
val pathToBeanDefinitionGroovy = "foo.groovy"

/*
 * ApplicationContext implementation for reading bean definitions in:
 *   - XML
 *   - Groovy
 */
val xmlContext = ClassPathXmlApplicationContext(pathToBeanDefinitionXML)
val groovyContext = GenericGroovyApplicationContext(pathToBeanDefinitionGroovy)

/* 
 *  This is way more flexible but I would rather stick to a single 
 *  file type when defining my beans externally
 */
val flexibleContext = GenericApplicationContext().apply {
    XmlBeanDefinitionReader(pathToBeanDefinitionXML)
    GroovyBeanDefinitionReader(pathToBeanDefinitionGroovy)
    refresh()
}
```
### Dependency Injection
```kotlin
class Foo
class Bar

class Constructor(private val foo: Foo, private val bar: Bar)

class Setter {
    lateinit var foo: Foo
    lateinit var bar: Bar
}

class StaticFactory private constructor(
    private val foo: Foo,
    private val bar: Bar
) {
    companion object {
        @JvmStatic
        fun createInstance(foo: Foo, bar: Bar) {
            return StaticFactory(foo, bar)
        }
    }
}
```

```xml
<beans>
    <bean id="constructor" class="...">
        <constructor-arg ref="foo" />
        <constructor-arg ref="bar" />
    </bean>
    
    <bean id="setter" class="..." >
        <!-- name is the class property/variable name -->
        <property name="foo" ref="foo" /> 
        <property name="bar" ref="bar" />
    </bean>
    
    <bean id="staticFactory" class="..." factory-method="createInstance">
        <constructor-arg ref="foo" />
        <constructor-arg ref="bar" />
    </bean>
    
    <bean id="foo" class="..." />
    <bean id="bar" class="..." />
</beans>
```

### Collaborators and the `idref` tag 
`ref` gets the value associated to the passed ID string, `idref` injects the ID itself (String) and invokes an error when passed ID does not exist.

### The p-namespace and c-namespace
The `p-namespace` allows for concise instantiation of bean properties with simple types like strings.\
Similarly, `c-namespace` enables developers to define bean constructor arguments inline instead of using `<constructor-arg>` tags.
```xml
<beans xmlns:p="http://www.springframework.org/schema/p"
       xmlns:c="http://www.springframework.org/schema/c">
    <bean id="father"
          class="com.example.Person" 
          p:name="Marco" 
          p:parent="" />

    <!-- Let's talk [depends-on] in the next section -->
    <bean id="baby" 
          class="com.example.Person" 
          depends-on="father"
          p:name="George" 
          p:parent-ref="father" />
    
    <bean id="fathersCouncil"
          class="com.example.ChichiBuKai"
          c:leader-ref="father,fathersCouncil" />
    
    <!-- If constructor names are not accessible -->
    <bean id="fathersCouncil"
          class="com.example.ChichiBuKai"
          c:_0-ref="father" />
</beans>
```

### The `depends-on` attribute
The bean that is depending on one or more beans will not be initialized until all the beans 
listed in the depends-on attribute are initialized.

### Lazy initialization with `lazy-init` and `default-lazy-init`
Lazy beans are forcibly initialized on start-up when a non-lazy singleton bean depends on it to satisfy it's dependencies. 
```xml
<beans default-lazy-init="true">
    <!-- no beans will be pre-instantiated... -->
    <bean lazy-init="true" />
</beans>
```

### Autowiring
| Modes      | Description                                                                               |
|------------|-------------------------------------------------------------------------------------------|
| `no`        | (Default) No autowiring. Use `ref` to reference collaborators instead.                    |
| `byName`     | Injects a bean with the same name as the property name.                                   |
| `byType`    | Injects a bean of a specific type. There must not be beans of same type in the container. |
| `constructor` | Analogous to `byType` but for constructor arguments.                                      |

### Method Injection
TODO

### Bean Scopes
| Scopes        | Description                                                                    |
|---------------|--------------------------------------------------------------------------------|
| `singleton`   | Scopes a single bean definition to a single object instance for each container |
| `prototype`   | Scopes a single bean definition to any number of object instances.             |
**NOTE**:`request`, `session`, `application`, and `websocket` create a single instance in each scope. Read the names and figure out what the scope is, future me.

### Lifecycle
The `InitializingBean` and `DisposableBean` interfaces from the `org.springframework.beans.factory` package provide beans to have their own initialization and destruction callback. 
However, this method couples the business logic to the framework. For this reason, it is recommended to use `@PostConstruct` or `init-method`, and `@PreDestroy` or `destroy-method` in place of 
`InitializingBean` and `DisposableBean` respectively. These methods are guaranteed to be called as soon as the class properties have all been supplied.\
\
The annotated methods work when the bean is managed by Spring.\
`default-init-method` and `default-init-method` can be used in the beans tag to ensure that the default initializer and destroy methods are uniformed.
```kotlin
package example

import jakarta.annotation.PostConstruct

@Component
class Foo {
    @PostConstruct
    fun onInit() { ... }
}
```
```xml
<beans default-init-method="init">
    <bean class="example.Foo"
          init-method="onInit" />
</beans>
```

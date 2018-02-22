package org.sample.myride

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class MyRideApplication

fun main(args: Array<String>) {
    SpringApplication.run(MyRideApplication::class.java, *args)
}

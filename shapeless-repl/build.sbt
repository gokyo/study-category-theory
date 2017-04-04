scalaVersion := "2.12.1"
 
libraryDependencies += "com.chuusai" %% "shapeless" % "2.3.2"

initialize ~= { _ =>
  val ansi = System.getProperty("sbt.log.noformat", "false") != "true"
  if (ansi) System.setProperty("scala.color", "true")
}

initialCommands in console := """
import shapeless._
import scala.reflect.runtime.universe._
import scala.concurrent.ExecutionContext.Implicits.global
final case class Person(name: String, age: Int)
final case class Cat(name: String, age: Int)
val dennis = Person("Dennis", 42)
val elsa = Cat("Elsa", 18)
val tijger = Cat("Tijger", 13)
val guys = List(elsa, tijger)
"""

scalaVersion := "2.12.4"

scalaOrganization in ThisBuild := "org.typelevel"
 
libraryDependencies += "com.chuusai" %% "shapeless" % "2.3.2"

// improves type constructor inference with support for partial unification,
// fixing the notorious SI-2712.
scalacOptions += "-Ypartial-unification"

// https://github.com/typelevel/scala/blob/typelevel-readme/notes/2.12.1.md#faster-compilation-of-inductive-implicits-pull5649-milessabin
// enable speed up inductive implicit resolution of type class instances
scalacOptions += "-Yinduction-heuristics"

// https://github.com/typelevel/scala/blob/typelevel-readme/notes/2.12.1.md#literal-types-pull5310-milesabin
// enable SIP-23 Singleton Literal Type Implementation
scalacOptions += "-Yliteral-types"

// enable exhaustivity of extractors, guards, and unsealed traits
scalacOptions += "-Xstrict-patmat-analysis"

// https://github.com/typelevel/scala/blob/typelevel-readme/notes/2.12.1.md#minimal-kind-polymorphism-pull5538-mandubian
// enable type and method difitions with type parameters of AnyKind
scalacOptions += "-Ykind-polymorphism"

//scalacOptions += "-Ydelambdafy:method"
scalacOptions += "-Ydelambdafy:inline"

initialize ~= { _ =>
  val ansi = System.getProperty("sbt.log.noformat", "false") != "true"
  if (ansi) System.setProperty("scala.color", "true")
}

initialCommands in console := """
import shapeless._
import shapeless.record._
import scala.reflect.runtime.universe._
import scala.concurrent.ExecutionContext.Implicits.global
final case class Person(name: String, age: Int)
final case class Cat(name: String, age: Int)
val dennis = Person("Dennis", 42)
val elsa = Cat("Elsa", 18)
val tijger = Cat("Tijger", 13)
val guys = List(elsa, tijger)
final case class IceCream(name: String, numCherries: Int, inCone: Boolean)
val sundae = LabelledGeneric[IceCream].to(IceCream("Sundae", 1, false))
"""

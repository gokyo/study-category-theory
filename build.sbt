name := "study-category-theory"

lazy val scalazTest = (project in file("scalaz-test"))
  .settings(
    libraryDependencies += "org.scalaz" %% "scalaz-core" % "7.2.16",
    libraryDependencies += "org.typelevel" %% "scalaz-scalatest" % "1.1.2" % Test,
    libraryDependencies += "org.scalamock" %% "scalamock-scalatest-support" % "3.6.0" % Test,
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.4" % Test
  )

// see: https://github.com/typelevel/cats/blob/master/CHANGES.md
val catsVersion = "1.0.0-RC1"
lazy val catsTest = (project in file("cats-test"))
  .settings(
    libraryDependencies += "org.typelevel" %% "cats-core" % catsVersion, // required
    libraryDependencies += "org.typelevel" %% "cats-macros" % catsVersion, // required
    libraryDependencies += "org.typelevel" %% "cats-kernel" % catsVersion, // required
    libraryDependencies += "org.typelevel" %% "cats-laws" % catsVersion,
    libraryDependencies += "org.typelevel" %% "cats-free" % catsVersion,
    libraryDependencies += "org.typelevel" %% "cats-testkit" % catsVersion,
    libraryDependencies += "org.typelevel" %% "alleycats-core" % catsVersion,
    libraryDependencies += "org.typelevel" %% "mouse" % "0.12", // https://github.com/typelevel/mouse
    libraryDependencies += "org.typelevel" %% "cats-mtl-core" % "0.0.2", // https://github.com/typelevel/cats-mtl
    libraryDependencies += "org.typelevel" %% "cats-effect" % "0.5", // https://github.com/typelevel/cats-effect
    libraryDependencies += "com.github.mpilquist" %% "simulacrum" % "0.11.0",
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.4" % Test
  )

lazy val shapeless = (project in file("shapeless"))
  .settings(
    libraryDependencies += "com.github.mpilquist" %% "simulacrum" % "0.11.0",
    libraryDependencies += "com.chuusai" %% "shapeless" % "2.3.2",
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.4" % Test
  )

lazy val typeclasses = (project in file("typeclasses"))
  .settings(
    libraryDependencies += "com.github.mpilquist" %% "simulacrum" % "0.11.0",
    libraryDependencies += "org.scalaz" %% "scalaz-core" % "7.2.16",
    libraryDependencies += "org.typelevel" %% "scalaz-scalatest" % "1.1.2" % Test,
    libraryDependencies += "org.scalamock" %% "scalamock-scalatest-support" % "3.6.0" % Test,
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.4" % Test
  )

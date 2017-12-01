import sbt.{Def, _}
import sbt.Keys._

import scalariform.formatter.preferences._
import com.typesafe.sbt.SbtScalariform

object ProjectAutoPlugin extends AutoPlugin {
  override def trigger: PluginTrigger = allRequirements

  override def requires: Plugins = plugins.JvmPlugin

  // settings that should be applied to all projects
  override def globalSettings: Seq[Def.Setting[_]] = Seq(
    organization := "com.github.dnvriend",
    version := "1.0.0-SNAPSHOT",
    addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.1" cross CrossVersion.full),
  ) ++ resolversSettings ++ commonSettings ++ scalariFormSettings ++ sbtHeaderSettings

  lazy val resolversSettings = Seq(
    resolvers += "scalaz" at "http://dl.bintray.com/scalaz/releases",
    resolvers += "Scalaz Bintray Repo" at "http://dl.bintray.com/stew/snapshots",
    resolvers += Resolver.sonatypeRepo("releases"),
    resolvers += Resolver.sonatypeRepo("snapshots"),
  )

  lazy val commonSettings = Seq(
    scalaVersion := "2.12.4",
    scalacOptions += "-Ypartial-unification",
    scalacOptions += "-Ydelambdafy:inline",
    fork in Test := true,
    parallelExecution := false,
    licenses += ("Apache-2.0", url("http://opensource.org/licenses/apache2.0.php")),
  )

  lazy val sbtHeaderSettings = Seq(
    organizationName := "Dennis Vriend",
    startYear := Some(2018),
    licenses := Seq(("Apache-2.0", new URL("https://www.apache.org/licenses/LICENSE-2.0.txt"))),
  )

  lazy val scalariFormSettings = Seq (
    SbtScalariform.autoImport.scalariformPreferences := {
      SbtScalariform.autoImport.scalariformPreferences.value
        .setPreference(AlignSingleLineCaseStatements, true)
        .setPreference(AlignSingleLineCaseStatements.MaxArrowIndent, 100)
        .setPreference(DoubleIndentConstructorArguments, true)
        .setPreference(DanglingCloseParenthesis, Preserve)
    }
  )
}
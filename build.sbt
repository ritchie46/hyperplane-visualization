lazy val root = project.
  enablePlugins(ScalaJSPlugin)

enablePlugins(JSDependenciesPlugin)

name := "hyperplane"

// This is an application with a main method
scalaJSUseMainModuleInitializer := true

skip in packageJSDependencies := false

libraryDependencies += "org.scala-js" %%% "scalajs-dom" % "0.9.3"
libraryDependencies += "org.singlespaced" %%% "scalajs-d3" % "0.3.4"

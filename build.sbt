name := """training-log"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava).settings(
  watchSources ++= (baseDirectory.value / "public/ui" ** "*").get
)

resolvers += Resolver.sonatypeRepo("snapshots")

scalaVersion := "2.12.2"

val akkaVersion = "2.5.18"

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2" % Test
libraryDependencies += "com.h2database" % "h2" % "1.4.196"

// Akka
libraryDependencies += "com.typesafe.akka" %% "akka-stream" % akkaVersion
libraryDependencies += "com.typesafe.akka" %% "akka-stream-testkit" % akkaVersion
libraryDependencies += "com.typesafe.akka" %% "akka-stream-kafka" % "1.0-M1"
libraryDependencies += "com.typesafe.akka" %% "akka-slf4j" % akkaVersion
libraryDependencies += "com.typesafe.akka" %% "akka-testkit" % akkaVersion % Test
libraryDependencies += "com.typesafe.akka" %% "akka-stream-kafka-testkit" % "1.0-M1" % Test

// GraphQL
libraryDependencies += "org.sangria-graphql" %% "sangria" % "1.4.1"
libraryDependencies += "org.sangria-graphql" %% "sangria-play-json" % "1.0.4"

// Metrics
libraryDependencies += "nl.grons" %% "metrics4-scala" % "4.0.1"
libraryDependencies += "nl.grons" %% "metrics4-akka_a24" % "4.0.1"
libraryDependencies += "nl.grons" %% "metrics4-scala-hdr" % "4.0.1"

// JWT
libraryDependencies += "com.auth0" % "jwks-rsa" % "0.6.1"
libraryDependencies += "com.auth0" % "java-jwt" % "3.4.1"

libraryDependencies += "com.typesafe.scala-logging" %% "scala-logging" % "3.9.0"

// Testing
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2" % Test

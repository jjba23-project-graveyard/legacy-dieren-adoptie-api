val zioVersion            = "2.0.10"
val zioJsonVersion        = "0.4.2"
val zioConfigVersion      = "3.0.7"
val logbackClassicVersion = "1.4.5"
val testContainersVersion = "0.40.11"
//val zioLoggingVersion     = "2.1.9"
val zioHttpVersion        = "0.0.5"
val dockerReleaseSettings = Seq(
  dockerExposedPorts   := Seq(8080),
  dockerExposedVolumes := Seq("/opt/docker/logs"),
  dockerBaseImage      := "eclipse-temurin:17.0.4_8-jre"
)

val betterMonadicForVersion = "0.3.1"
val jansiVersion            = "2.4.0"
val mongoDriverVersion      = "4.9.0"

lazy val It = config("it").extend(Test)

scalacOptions -= "-Xlint:infer-any"

val root = (project in file("."))
  .configs(It)
  .settings(
    inConfig(It)(Defaults.testSettings),
    inThisBuild(
      List(
        organization := "nl.teutonicjoe",
        scalaVersion := "2.13.10"
      )
    ),
    name           := "dieren-adoptie-api",
    addCompilerPlugin("com.olegpy" %% "better-monadic-for" % betterMonadicForVersion),
    libraryDependencies ++= Seq(
      "dev.zio"             %% "zio-http"            % zioHttpVersion,
      "dev.zio"             %% "zio-json"            % zioJsonVersion,
      "dev.zio"             %% "zio"                 % zioVersion,
      "dev.zio"             %% "zio-config"          % zioConfigVersion,
      "dev.zio"             %% "zio-config-magnolia" % zioConfigVersion,
      "dev.zio"             %% "zio-config-typesafe" % zioConfigVersion,
      "org.mongodb.scala"   %% "mongo-scala-driver"  % mongoDriverVersion,
//      "dev.zio"             %% "zio-logging"         % zioLoggingVersion,
//      "dev.zio"             %% "zio-logging-slf4j"   % zioLoggingVersion,
      "ch.qos.logback"       % "logback-classic"     % logbackClassicVersion,
      //"org.fusesource.jansi" % "jansi"               % jansiVersion,
      "dev.zio"             %% "zio-test-sbt"        % zioVersion % Test
    ),
    testFrameworks := Seq(new TestFramework("zio.test.sbt.ZTestFramework")),
    dockerReleaseSettings
  )
  .enablePlugins(DockerPlugin, JavaAppPackaging)

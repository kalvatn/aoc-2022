import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  kotlin("jvm") version "1.7.20"
}

group = "me.kalvatn"
version = "1.0-SNAPSHOT"

repositories {
  mavenCentral()
}

dependencies {
  testImplementation(kotlin("test"))
}

tasks.test {
  useJUnitPlatform()
}

tasks.withType<KotlinCompile>().configureEach {
  kotlinOptions {
    useK2 = true
    apiVersion = "1.7"
    languageVersion = "1.7"
    jvmTarget = "17"
    suppressWarnings = true
//     freeCompilerArgs += "-progressive"
    freeCompilerArgs += "-Xjdk-release=17"
  }
}

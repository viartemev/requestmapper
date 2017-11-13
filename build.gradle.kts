import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.kotlin.dsl.*
import org.gradle.plugins.ide.idea.model.IdeaLanguageLevel
import org.gradle.plugins.ide.idea.model.IdeaModel
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {

    val kotlinVersion by extra { "1.1.51" }
    val sonarqubeGradlePlugin by extra { "2.5" }
    val gradleIntellijPluginVersion by extra { "0.2.17" }
    val jUnitPluginVersion by extra { "1.0.1" }
    val hamcrestVersion by extra { "1.3" }

    repositories {
        jcenter()
        mavenCentral()
        maven { setUrl("https://plugins.gradle.org/m2/") }
    }

    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
        classpath("gradle.plugin.org.jetbrains.intellij.plugins:gradle-intellij-plugin:$gradleIntellijPluginVersion")
        classpath("org.sonarsource.scanner.gradle:sonarqube-gradle-plugin:$sonarqubeGradlePlugin")
        classpath("org.junit.platform:junit-platform-gradle-plugin:$jUnitPluginVersion")
    }
}

repositories {
    jcenter()
    mavenCentral()
}

plugins {
    idea
    java
    jacoco
    id("org.jetbrains.intellij") version "0.2.17"
    id("org.sonarqube") version "2.5"
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

intellij {
    pluginName = "Request mapper"
    type = "IC"
    version = "2016.3"
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

configure<IdeaModel> {
    project {
        languageLevel = IdeaLanguageLevel(JavaVersion.VERSION_1_8)
    }
    module {
        isDownloadJavadoc = true
        isDownloadSources = true
    }
}

val kotlinVersion: String by extra
val jUnitPluginVersion: String by extra
val hamcrestVersion: String by extra

dependencies {
    compile(kotlin("stdlib", kotlinVersion))
    compile(kotlin("reflect", kotlinVersion))

    testCompile("org.hamcrest:hamcrest-library:$hamcrestVersion")
    testCompile("org.junit.jupiter:junit-jupiter-api:5.0.2")
    testRuntime("org.junit.jupiter:junit-jupiter-engine:5.0.2")
    testRuntime("org.junit.platform:junit-platform-launcher:$jUnitPluginVersion")
}

task<Wrapper>("wrapper") {
    gradleVersion = "4.3.1"
    distributionUrl = "https://services.gradle.org/distributions/gradle-$gradleVersion-all.zip"
}

tasks.withType<Test> {
    testLogging {
        exceptionFormat = TestExceptionFormat.FULL
    }
}

task<Task>("validate") {
    File("./src/main/kotlin")
            .walkTopDown()
            .filter { it.isFile && it.extension == "kt" }
            .forEach { ktFile ->
                if (ktFile.readText().contains("println(")) {
                    throw GradleException("Project has invalid constructions")
                }
            }
}

val buildPlugin: DefaultTask by tasks
buildPlugin.dependsOn(tasks["validate"])

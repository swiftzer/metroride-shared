import io.gitlab.arturbosch.detekt.Detekt
import org.jetbrains.dokka.gradle.DokkaTask
import java.io.FileInputStream
import java.net.URL
import java.util.Properties

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotest.multiplatform)
    alias(libs.plugins.kotlinx.binaryCompatibilityValidator)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.dokka)
    alias(libs.plugins.kover)
    alias(libs.plugins.detekt)
    `maven-publish`
}

val publishingPropertiesFile: File = rootProject.file("publishing.properties")
val publishingProperties = Properties()
if (publishingPropertiesFile.exists()) {
    publishingProperties.load(FileInputStream(publishingPropertiesFile))
}

group = "net.swiftzer.metroride"
version = "1.0.0-SNAPSHOT"

repositories {
    mavenCentral()
}

kotlin {
    explicitApi()
    applyDefaultHierarchyTemplate()
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "1.8"
        }
        testRuns["test"].executionTask.configure {
            useJUnitPlatform()
        }
    }
    js(IR) {
        browser()
        nodejs()
    }
    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlinx.serialization.core)
        }
        commonTest.dependencies {
            implementation(libs.kotest.frameworkEngine)
            implementation(libs.kotest.frameworkDatatest)
            implementation(libs.kotest.assertionsCore)
            implementation(libs.kotlinx.serialization.json)
        }
        jvmTest.dependencies {
            implementation(libs.kotest.runnerJunit5)
        }
        jsMain.dependencies {
            implementation(npm("bignumber.js", "9.1.2"))
        }
        all {
            languageSettings {
                optIn("kotlinx.serialization.ExperimentalSerializationApi")
            }
        }
    }
    targets.configureEach {
        compilations.configureEach {
            compilerOptions.configure {
                freeCompilerArgs.add("-Xexpect-actual-classes")
            }
        }
    }
}

tasks.withType<DokkaTask>().configureEach {
    dokkaSourceSets.configureEach {
        sourceLink {
            val relPath = rootProject.projectDir.toPath().relativize(projectDir.toPath())
            localDirectory = projectDir.resolve("src")
            remoteUrl = URL("https://github.com/swiftzer/metroride-shared/tree/main/$relPath/src")
            remoteLineSuffix = "#L"
        }
    }
}

publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/swiftzer/metroride-shared")
            credentials {
                username = getProperty("githubUsername", "GITHUB_ACTOR")
                password = getProperty("githubPersonalAccessToken", "GITHUB_TOKEN")
            }
        }

        val javadocJar = tasks.register<Jar>("javadocJar") {
            dependsOn(tasks.dokkaHtml)
            archiveClassifier = "javadoc"
            from(layout.buildDirectory.file("dokka"))
        }

        publications {
            withType<MavenPublication> {
                artifact(javadocJar)
                pom {
                    name = "MetroRide shared"
                    description = "Shared library for MetroRide"
                    licenses {
                        license {
                            name = "Apache-2.0"
                            url = "https://opensource.org/licenses/Apache-2.0"
                        }
                    }
                    url = "https://github.com/swiftzer/metroride-shared"
                    issueManagement {
                        system = "GitHub"
                        url = "https://github.com/swiftzer/metroride-shared/issues"
                    }
                    scm {
                        connection = "scm:git:github.com/swiftzer/metroride-shared.git"
                        developerConnection = "scm:git:ssh://github.com/swiftzer/metroride-shared.git"
                        url = "https://github.com/swiftzer/metroride-shared/tree/main"
                    }
                    developers {
                        developer {
                            id = "ericksli"
                            name = "Eric Li"
                            email = "eric@swiftzer.net"
                        }
                    }
                }
            }
        }
    }
}

tasks.register("detektAll") {
    group = "verification"
    dependsOn(tasks.withType<Detekt>())
}

fun getProperty(propertyName: String, environmentVariable: String? = null): String? =
    providers.environmentVariable(environmentVariable ?: propertyName).orNull ?: publishingProperties.getProperty(propertyName)

import at.asitplus.gradle.Logger
import at.asitplus.gradle.kotest
import at.asitplus.gradle.serialization
import at.asitplus.gradle.setupDokka

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.asitplus.gradle.conventions)
    id("maven-publish")
    id("org.jetbrains.dokka")
    id("signing")
    alias(libs.plugins.testballoon)
}

/* required for maven publication */
val artifactVersion: String by extra
group = "at.asitplus.wallet"
version = artifactVersion

kotlin {
    jvm()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        commonMain {
            dependencies {
                api(serialization("json"))
                api(libs.vck)
            }
        }
    }
}

val javadocJar = setupDokka(baseUrl = "https://github.com/a-sit-plus/healthid/tree/main/")

publishing {
    publications {
        withType<MavenPublication> {
            artifact(javadocJar)
            pom {
                name.set("International Patient Summary (IPS)")
                description.set("Use data representing IPS as a SD-JWT credential, using VC-K")
                url.set("https://github.com/Lena-Marina/EUDI49-IpsScheme.git") //hier ist nun unser Repo verlinkt statt: https://github.com/a-sit-plus/healthid/
                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }
                developers {
                    developer {
                        id.set("nodh")
                        name.set("EUDI49-Gruppe")
                        email.set("we have non as a team")
                    }
                }
                scm {
                    connection.set("scm:git:git@github.com:Lena-Marina/EUDI49-IpsScheme.git")
                    developerConnection.set("scm:git:git@github.com:Lena-Marina/EUDI49-IpsScheme.git")
                    url.set("https://github.com/Lena-Marina/EUDI49-IpsScheme")
                }
            }
        }
    }
    repositories {
        mavenLocal {
            signing.isRequired = false
        }
    }
}

signing {
    val signingKeyId: String? by project
    val signingKey: String? by project
    val signingPassword: String? by project
    useInMemoryPgpKeys(signingKeyId, signingKey, signingPassword)
    sign(publishing.publications)
}


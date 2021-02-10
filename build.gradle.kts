import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

/*
 * This file was generated by the Gradle 'init' task.
 *
 * This is a general purpose Gradle build.
 * Learn how to create Gradle builds at https://guides.gradle.org/creating-new-gradle-builds/
 */

plugins {
    kotlin("jvm") version "1.4.30"
    `maven-publish`
}

group = "com.github.duck8823"
version = "0.0.2"
java.sourceCompatibility = JavaVersion.VERSION_1_8

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("io.gitlab.arturbosch.detekt:detekt-api:1.15.0")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.6.2")
    testImplementation("org.assertj:assertj-core:3.11.1")

    testImplementation("io.gitlab.arturbosch.detekt:detekt-test:1.15.0")

}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "1.8"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/duck8823/detekt-kdoc-ruleset")
            credentials {
                username = "duck8823"
                password = System.getenv("GITHUB_TOKEN")
            }
        }
    }
    publications {
        create<MavenPublication>("gpr") {
            from(components["java"])
        }
    }
}

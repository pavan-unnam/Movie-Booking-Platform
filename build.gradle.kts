import org.gradle.kotlin.dsl.assign

plugins {
    java
}


allprojects {
    group = "com.booking"
    version = System.getenv("VERSION") ?: "1.0.0"
    description = "MovieTicketingPlatform project for Spring Boot"
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(25)
    }
}
apply(from = "$rootDir/gradle/conventions.gradle.kts")



import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.spring) apply false
    alias(libs.plugins.spring.boot)
    alias { libs.plugins.spring.dependency.management }
    java
}

dependencies {
    implementation(libs.lombok)
    annotationProcessor(libs.lombok)
    implementation(project(":shared-domain"))
    implementation(libs.spring.boot.starter.web)
    implementation(libs.spring.boot.starter.validation)
}

tasks.named<Jar>("jar") {
    enabled = true
}

tasks.named<BootJar>("bootJar") {
    enabled = false
}

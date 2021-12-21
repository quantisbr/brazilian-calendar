plugins {
    kotlin("jvm") version "1.6.10"
    `java-library`
    `maven-publish`
}

java.sourceCompatibility = JavaVersion.VERSION_1_9
java.targetCompatibility = JavaVersion.VERSION_1_9

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
    testImplementation("io.mockk:mockk:1.12.1")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = "1.9"
    }
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "com.github.quantisbr"
            artifactId = "brazilian-holidays"
            version = "1.0.0"
            from(components["java"])
        }
    }
}

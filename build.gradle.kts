plugins {
    kotlin("jvm") version "1.6.10"
    `java-library`
    `maven-publish`
}

java.sourceCompatibility = JavaVersion.VERSION_1_8

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.2")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.8.2")
    testImplementation("io.mockk:mockk:1.12.1")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "br.com.quantis.libraries"
            artifactId = "brazilian-holidays"
            version = "1.0.1"
            from(components["java"])
        }
    }
}

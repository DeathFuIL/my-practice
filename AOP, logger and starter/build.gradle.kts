plugins {
    id("java-library")
    id("org.springframework.boot") version "3.2.3"
    id("maven-publish")
}

group = "ru.kpfu.itis"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}


configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}



publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "ru.kpfu.itis"
            artifactId = "logger-starter"
            version = "1.0.0-SNAPSHOT"
            from(components["java"])
        }
    }
    repositories {
        mavenLocal()
    }
}

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-rest:3.2.3")
    compileOnly("org.projectlombok:lombok")
    developmentOnly("org.springframework.boot:spring-boot-devtools:3.2.3")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor:3.2.3")
    annotationProcessor("org.projectlombok:lombok:1.18.30")
    implementation("org.springframework.boot:spring-boot-starter-log4j2:3.2.2")
    implementation("org.aspectj:aspectjweaver:1.9.21")
}
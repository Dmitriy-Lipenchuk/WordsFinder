plugins {
    application
}

group = "org.example"
version = "1.0-SNAPSHOT"

application {
    mainClass.set("application.Application")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":utils"))
    implementation(project(":db_utils"))

    implementation("org.springframework.boot:spring-boot-starter:3.0.1")
    implementation("org.springframework.boot:spring-boot-starter-web:3.0.1")
    implementation("org.springframework:spring-jdbc:6.0.4")

    implementation("org.flywaydb:flyway-core:9.11.0")
    implementation("org.postgresql:postgresql:42.5.1")
}
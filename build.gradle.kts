import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("java")
    id("application")
    id("com.github.johnrengelman.shadow") version "8.1.1"
}
group = "ru.kpfu.itis.lldan"
version = "1.0-SNAPSHOT"

application {
    mainClass = "ru.kpfu.itis.lldan.Main"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework:spring-webmvc:${properties["springVersion"]}")
    implementation("org.apache.tomcat.embed:tomcat-embed-jasper:${properties["tomcatVersion"]}")
    implementation("org.json:json:20240205")
}

tasks.withType<ShadowJar> {
    mergeServiceFiles()
}

plugins {
    java
    kotlin("jvm") version ("1.3.50")
//    id("java")
//    id("org.jetbrains.kotlin.jvm") version ("1.3.41")
}

group = "Kotlin-new"
version = "1.0-SNAPSHOT"


java {
    sourceCompatibility = JavaVersion.VERSION_1_8
}


repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("com.squareup.retrofit2:retrofit:2.6.3")
    implementation("com.squareup.retrofit2:converter-gson:2.6.3")
    implementation("com.google.code.gson:gson:2.8.1")
    testCompile(group = "junit", name = "junit", version = "4.12")
}

val compileKotlin: org.jetbrains.kotlin.gradle.tasks.KotlinCompile by tasks

compileKotlin.kotlinOptions {
    jvmTarget = "1.8"
}

val compileTestKotlin: org.jetbrains.kotlin.gradle.tasks.KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
    jvmTarget = "1.8"
}

plugins {
    kotlin("jvm")
}

group = "com.biritum.pdf"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":pdf"))
    implementation(project(":afmConvert"))
    implementation(kotlin("stdlib"))
}

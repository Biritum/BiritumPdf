plugins {
    kotlin("jvm")
}

group = "com.biritum.pdf"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    google()
}

dependencies {
    implementation(kotlin("test"))
}

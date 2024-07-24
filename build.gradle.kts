plugins {
    kotlin("jvm") version "2.0.0"
    id("com.google.protobuf") version "0.9.3"
    id("com.github.johnrengelman.shadow") version "8.+"
    kotlin("plugin.serialization") version "2.0.0"
}

group = "org.cubewhy.cosmetics"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://jitpack.io")
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:3.23.4"
    }
}

tasks.shadowJar {
    archiveClassifier.set("patch")
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE

    exclude("native-binaries/**")

    exclude("LICENSE.txt")

    exclude("META-INF/maven/**")
    exclude("META-INF/versions/**")

    exclude("org/junit/**")
}

tasks.jar {
    dependsOn("shadowJar")

    manifest {
        attributes(
            "Main-Class" to "org.cubewhy.patch.MainKt",
        )
        attributes(
            "Charset" to "UTF-8"
        )
    }
}


dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.1")
    implementation("org.apache.logging.log4j:log4j-core:2.23.1")
    implementation("org.ow2.asm:asm:9.4")
    implementation("org.ow2.asm:asm-tree:9.4")
    implementation("com.github.Nilsen84:kt-bytecode-dsl:v1.1")
    implementation("com.google.protobuf:protobuf-kotlin:3.23.4")

    implementation(files("libs/celepatch.jar"))
}

kotlin {
    jvmToolchain(17)
}
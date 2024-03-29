plugins {
    id("com.github.ben-manes.versions") version "0.46.0"
    `java-library`
    kotlin("jvm") version "1.8.21"
   // kotlin("plugin.serialization") version "1.5.21"
}

group = "com.begemot"
version = "1.0"

/*repositories {
    //mavenCentral()
    mavenLocal(){
        metadataSources {
            mavenPom()
            // artifact()
            ignoreGradleMetadataRedirection()
        }
    }
    //jcenter()
    mavenCentral()
    //maven { url = uri("https://kotlin.bintray.com/ktor") }
}*/

kotlin{
    jvmToolchain(8)
}

dependencies {
    //implementation(platform("com.begemot.knewsplatform-bom:deps:0.0.1"))
    implementation(platform("com.begemota:sharedlibrary"))
    implementation("com.begemot:KNewsCommon")
    implementation("org.jetbrains.kotlin:kotlin-stdlib")

    implementation("org.jsoup:jsoup")
    implementation("io.ktor:ktor-client-serialization-jvm")
    implementation("com.google.cloud:google-cloud-storage")
    //implementation( "ch.qos.logback:logback-classic")
    implementation("io.github.microutils:kotlin-logging-jvm")
    //implementation(project(mapOf("path" to ":")))
    //implementation(project(mapOf("path" to ":")))
    //implementation(project(mapOf("path" to ":")))
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
}
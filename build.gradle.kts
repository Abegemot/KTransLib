plugins {
    `java-library`
    kotlin("jvm") version "1.3.72"
}

group = "com.begemot"
version = "1.0"

repositories {
    //mavenCentral()
    mavenLocal(){
        metadataSources {
            mavenPom()
            // artifact()
            ignoreGradleMetadataRedirection()
        }
    }
    jcenter()
    //maven { url = uri("https://kotlin.bintray.com/ktor") }
}


dependencies {
    implementation(platform("com.begemot.knewsplatform-bom:deps:0.0.1"))
    implementation("com.begemot:knewscommon")
    implementation(kotlin("stdlib-jdk8"))
    implementation("org.jsoup:jsoup")
    implementation("io.ktor:ktor-client-serialization-jvm")
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
}
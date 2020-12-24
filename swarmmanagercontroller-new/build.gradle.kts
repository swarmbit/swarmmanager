plugins {
    id("org.jetbrains.kotlin.jvm") version "1.4.21"
    id("org.jetbrains.kotlin.kapt") version "1.4.21"
    id("org.jetbrains.kotlin.plugin.allopen") version "1.4.21"
    id("com.github.johnrengelman.shadow") version "6.1.0"
    id("io.micronaut.application") version "1.2.0"
}

version = "0.1"
group = "com.swarmbit"

val kotlinVersion = project.properties["kotlinVersion"]
repositories {
    mavenCentral()
    jcenter()
}

micronaut {
    runtime("netty")
    testRuntime("kotest")
    processing {
        incremental(true)
        annotations("com.swarmbit.*")
    }
}

dependencies {
    implementation("io.micronaut:micronaut-validation")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:${kotlinVersion}")
    implementation("org.jetbrains.kotlin:kotlin-reflect:${kotlinVersion}")
    implementation("io.micronaut.kotlin:micronaut-kotlin-runtime")
    implementation("io.micronaut:micronaut-runtime")
    implementation("javax.annotation:javax.annotation-api")
    implementation("io.micronaut:micronaut-http-client")

    implementation("com.kohlschutter.junixsocket:junixsocket-common:2.0.4")
    implementation("com.kohlschutter.junixsocket:junixsocket-native-common:2.0.4")

    implementation("com.fasterxml.jackson.jaxrs:jackson-jaxrs-json-provider:2.8.7")
    implementation("org.glassfish.jersey.core:jersey-client:2.25.1")
    implementation("org.glassfish.jersey.connectors:jersey-apache-connector:2.25.1")
    implementation("org.bouncycastle:bcprov-jdk15on:1.56")
    implementation("org.bouncycastle:bcpkix-jdk15on:1.54")
    implementation("org.apache.httpcomponents:httpcore:4.4.9")
    implementation("org.apache.httpcomponents:httpclient:4.5.5")
    implementation("commons-io:commons-io:2.5")

    compileOnly("javax.json:javax.json-api:1.1")
    runtimeOnly("ch.qos.logback:logback-classic")
    runtimeOnly("com.fasterxml.jackson.module:jackson-module-kotlin")
}


application {
    mainClass.set("com.swarmbit.ApplicationKt")
}

java {
    sourceCompatibility = JavaVersion.toVersion("11")
}

tasks {
    compileKotlin {
        kotlinOptions {
            jvmTarget = "11"
        }
    }
    compileTestKotlin {
        kotlinOptions {
            jvmTarget = "11"
        }
    }


}


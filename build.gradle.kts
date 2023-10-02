import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "3.1.4"
	id("io.spring.dependency-management") version "1.1.3"
	id("org.jetbrains.kotlin.jvm") version "1.8.22"
	id("org.jetbrains.kotlin.plugin.spring") version "1.8.22"
	id("org.jetbrains.kotlin.plugin.jpa") version "1.8.22"
}

group = "com.petitcl"
version = "0.0.1-SNAPSHOT"

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("io.arrow-kt:arrow-core:1.2.1")
	runtimeOnly("com.h2database:h2")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xcontext-receivers", "-Xjsr305=strict")
		jvmTarget = "17"
	}
}

tasks.test {
	useJUnitPlatform()
}

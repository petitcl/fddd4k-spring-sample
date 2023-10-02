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
	// web
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

	// core
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("io.arrow-kt:arrow-core:1.2.1")

	// infrastructure
//	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
	implementation("org.jetbrains.exposed:exposed-spring-boot-starter:0.44.0")
	runtimeOnly("com.h2database:h2")

	// test
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("io.kotest:kotest-assertions-core:5.7.2")
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

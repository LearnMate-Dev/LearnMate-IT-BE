plugins {
	kotlin("jvm") version "1.9.25"
	kotlin("plugin.spring") version "1.9.25"
	id("org.springframework.boot") version "3.3.4"
	id("io.spring.dependency-management") version "1.1.7"
	kotlin("plugin.jpa") version "1.9.25"
}

group = "learn-mate-it"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(17)
	}
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()

	maven {
		url = uri("https://repo.spring.io/milestone")
	}

	maven {
		url = uri("https://jitpack.io")
	}
}

ext {
	set("springAiVersion", "1.0.0-M2")
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation ("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.3.0")
	implementation("org.springframework.boot:spring-boot-starter-validation")

	// db
	implementation("org.postgresql:postgresql")
	// ai
	implementation("org.springframework.ai:spring-ai-openai-spring-boot-starter")
	// security & oauth
	implementation ("org.springframework.boot:spring-boot-starter-oauth2-client")
	implementation ("org.springframework.boot:spring-boot-starter-security")
	// jwt
	implementation ("io.jsonwebtoken:jjwt-api:0.12.3")
	implementation ("io.jsonwebtoken:jjwt-impl:0.12.3")
	implementation ("io.jsonwebtoken:jjwt-jackson:0.12.3")
	// redis
	implementation ("org.springframework.boot:spring-boot-starter-data-redis")
	// coroutine
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:1.7.3")
	// email
	implementation ("org.springframework.boot:spring-boot-starter-mail")
	implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
	// discord
	implementation("com.github.napstr:logback-discord-appender:1.0.0")

	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

val springAiVersion: String by extra
dependencyManagement {
	imports {
		mavenBom("org.springframework.ai:spring-ai-bom:$springAiVersion")
	}
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

allOpen {
	annotation("jakarta.persistence.Entity")
	annotation("jakarta.persistence.MappedSuperclass")
	annotation("jakarta.persistence.Embeddable")
}

tasks.withType<Test> {
	useJUnitPlatform()
}

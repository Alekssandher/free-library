import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

plugins {
	java
	id("org.springframework.boot") version "3.4.3"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "alekssandher"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.flywaydb:flyway-core")
	implementation("org.flywaydb:flyway-database-postgresql")
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.8.5")
	implementation("at.favre.lib:bcrypt:0.10.2")
	implementation("org.hibernate.validator:hibernate-validator:9.0.0.CR1")
	implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.18.3")
	// https://mvnrepository.com/artifact/com.auth0/java-jwt
	implementation("com.auth0:java-jwt:4.5.0")

	// https://mvnrepository.com/artifact/com.cloudinary/cloudinary-http44
	implementation("com.cloudinary:cloudinary-http44:1.34.0")

	compileOnly("org.projectlombok:lombok")

	runtimeOnly("org.postgresql:postgresql")

	annotationProcessor("org.projectlombok:lombok")
	
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.security:spring-security-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.named("build") {
	doLast{
		val trigger = file("src/main/resources/trigger.txt")
		if (!trigger.exists()){
			trigger.createNewFile()
		}
		trigger.writeText(Date().time.toString())
	}
}

tasks.register("generateFlywayMigrationFile"){

	description = "Generate flyway migration"
	group = "Flyway"

	doLast {
		val migrationsDir = file("src/main/resources/db/migration")
		if (!migrationsDir.exists()) {
			migrationsDir.mkdirs()
		}

		val migrationNameFromConsole = project.findProperty("migrationName") as String?
			?: throw IllegalArgumentException("You should provide a name for the migration with the parameter -PmigrationName=<nome>.")

		val timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))
		val migrationName = "V${timestamp}__${migrationNameFromConsole}.sql"

		val migrationFile = file("${migrationsDir.path}/$migrationName")
		migrationFile.writeText("-- $migrationName generated in ${migrationsDir.path}")

		logger.lifecycle("Migration file created: ${migrationFile.path}")

	}
}


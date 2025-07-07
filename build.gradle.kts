plugins {
    id("java")
}

java.targetCompatibility = JavaVersion.VERSION_21
java.sourceCompatibility = JavaVersion.VERSION_21

group = "dev.boiarshinov"
version = "1.0-SNAPSHOT"

dependencies {
    implementation(platform("org.springframework.boot:spring-boot-dependencies:3.3.2"))
    implementation("org.springframework.boot:spring-boot-starter-web")

    testImplementation("io.github.classgraph:classgraph:4.8.180")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}
plugins {
    id("org.springframework.boot") version "3.4.0"
}


dependencies {
    implementation("org.projectlombok:lombok:1.18.36")
    annotationProcessor("org.projectlombok:lombok:1.18.36")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
}
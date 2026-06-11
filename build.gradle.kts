plugins {
    id("java")
    id("checkstyle")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:6.0.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testImplementation("org.junit.platform:junit-platform-suite:6.0.1")
}

tasks.test {
    useJUnitPlatform()

    exclude("**/*Suite.class")

    reports {
        junitXml.required.set(false)
        html.required.set(true)
    }
}

tasks.register<Test>("testPositiveSuite") {
    description = "Запускає набір позитивних тестів через клас Suite"
    group = "verification"
    useJUnitPlatform()

    testClassesDirs = sourceSets["test"].output.classesDirs
    classpath = sourceSets["test"].runtimeClasspath

    include("**/PositiveTestsSuite.class")

    reports {
        junitXml.required.set(false)
        html.required.set(true)
    }
}

tasks.register<Test>("testNegativeSuite") {
    description = "Запускає набір негативних тестів через клас Suite"
    group = "verification"
    useJUnitPlatform()

    testClassesDirs = sourceSets["test"].output.classesDirs
    classpath = sourceSets["test"].runtimeClasspath

    include("**/NegativeTestsSuite.class")

    reports {
        junitXml.required.set(false)
        html.required.set(true)
    }
}

tasks.register<Test>("testPositive") {
    description = "Запускає лише позитивні тести (Happy Path)"
    group = "verification"

    testClassesDirs = sourceSets["test"].output.classesDirs
    classpath = sourceSets["test"].runtimeClasspath

    exclude("**/*Suite.class")

    useJUnitPlatform {
        includeTags("positive")
    }

    reports {
        junitXml.required.set(false)
        html.required.set(true)
    }
}

tasks.register<Test>("testNegative") {
    description = "Запускає лише негативні тести (Очікування помилок)"
    group = "verification"

    testClassesDirs = sourceSets["test"].output.classesDirs
    classpath = sourceSets["test"].runtimeClasspath

    exclude("**/*Suite.class")

    useJUnitPlatform {
        includeTags("negative")
    }

    reports {
        junitXml.required.set(false)
        html.required.set(true)
    }
}
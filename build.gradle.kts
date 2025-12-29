plugins {
    id("java")
    id("maven-publish")
    id("com.gradleup.shadow") version "9.3.0"
}


allprojects {

    apply(plugin = "java")
    apply(plugin = "maven-publish")
    apply(plugin = "com.gradleup.shadow")

    tasks.withType<JavaCompile> {
        options.encoding = "UTF-8"
        options.release.set(17)
    }
}
plugins {
    id("java")
    id("maven-publish")
}

val projectVersion : String by project
val projectGroup : String by project

subprojects {

    apply(plugin = "java")
    apply(plugin = "org.gradle.maven-publish")

    tasks.jar {
        archiveFileName = "AntiGriefLib-${projectVersion}.jar"
    }

    tasks.withType<JavaCompile> {
        options.encoding = "UTF-8"
        options.release.set(17)
    }

    if ("project" == project.name) {
        publishing {
            publications {
                create<MavenPublication>("mavenJava") {
                    groupId = "net.momirealms"
                    artifactId = "AntiGriefLib"
                    version = rootProject.version.toString()
                    artifact(tasks.build)
                }
            }
        }
    }
}
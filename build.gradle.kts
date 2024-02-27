plugins {
    id("java")
    id("maven-publish")
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

val projectVersion : String by project
val projectGroup : String by project

allprojects {

    apply(plugin = "java")
    apply(plugin = "maven-publish")
    apply(plugin = "com.github.johnrengelman.shadow")

    tasks.withType<JavaCompile> {
        options.encoding = "UTF-8"
        options.release.set(17)
    }

    if ("project" == project.name) {

        tasks.shadowJar {
            destinationDirectory.set(file("$rootDir/target"))
            archiveClassifier.set("")
            archiveFileName.set("AntiGriefLib-${projectVersion}.jar")
        }

        publishing {
            publications {
                create<MavenPublication>("mavenJava") {
                    groupId = "net.momirealms"
                    artifactId = "AntiGriefLib"
                    version = rootProject.version.toString()
                    artifact(tasks.shadowJar)
                }
            }
        }
    }
}
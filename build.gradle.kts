plugins {
    id("java")
    id("maven-publish")
    id("com.gradleup.shadow") version "9.2.2"
}

val projectVersion : String by project
val projectGroup : String by project

allprojects {

    apply(plugin = "java")
    apply(plugin = "maven-publish")
    apply(plugin = "com.gradleup.shadow")

    tasks.withType<JavaCompile> {
        options.encoding = "UTF-8"
        options.release.set(21)
    }

    if ("project" == project.name) {

        tasks.shadowJar {
            destinationDirectory.set(file("$rootDir/target"))
            archiveClassifier.set("")
            archiveFileName.set("AntiGriefLib-${projectVersion}.jar")
        }

        publishing {
            repositories {
                maven {
                    url = uri("https://repo.momirealms.net/releases")
                    credentials(PasswordCredentials::class) {
                        username = System.getenv("REPO_USERNAME")
                        password = System.getenv("REPO_PASSWORD")
                    }
                }
            }
            publications {
                create<MavenPublication>("mavenJava") {
                    groupId = "net.momirealms"
                    artifactId = "antigrieflib"
                    version = projectVersion
                    from(components["shadow"])
                }
            }
        }
    }
}
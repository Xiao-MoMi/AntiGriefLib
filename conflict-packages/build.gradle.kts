repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")                 // Paper
    maven("https://jitpack.io/")                                              // SaberFactions
    maven("https://repo.codemc.org/repository/maven-public/")                 // NBTAPI
}

@Suppress
dependencies {
    implementation(project(":common"))
    compileOnly("io.papermc.paper:paper-api:1.20.4-R0.1-SNAPSHOT")                // Paper
    compileOnly("com.github.SaberLLC:Saber-Factions:4.1.4-STABLE")                // SaberFactions
    compileOnly(files("libs/PlotSquared-pruned.jar"))                      // PlotSquaredV5
}
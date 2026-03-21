import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import java.io.File

plugins {
    kotlin("jvm") version "1.9.23"
    id("org.jetbrains.compose") version "1.6.1"
}

repositories {
    google()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

dependencies {
    // 使用跨平台依赖，而不是currentOs
    implementation(compose.desktop.common)
    implementation(compose.desktop.linux_x64)  // Linux x64原生库
    implementation(compose.desktop.windows_x64)  // Windows x64原生库,*FOR DEBUG ONLY*
    implementation("org.jetbrains.compose.material3:material3:1.6.1")
    implementation("org.jetbrains.compose.material:material-icons-extended:1.6.1")
    implementation("com.github.oshi:oshi-core:6.4.10")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
}

kotlin {
    jvmToolchain(21)
}

compose.desktop {
    application {
        mainClass = "main.kotlin.MainAppKt"
        
        nativeDistributions {
            // Linux only
            targetFormats(TargetFormat.Deb, TargetFormat.Rpm, TargetFormat.AppImage)
            packageName = "NNETBsNotEverythingToolbox"
            packageVersion = "0.0.1"
            
            linux {
                iconFile.set(project.file("images/logo.png"))
            }
            
            appResourcesRootDir.set(project.layout.projectDirectory.dir("images"))
        }
    }
}

// Copy /res directory to distribution after evaluation (when Compose plugin creates tasks)
afterEvaluate {
    try {
        tasks.named("createRuntimeImage") {
            doLast {
                println("Copying /res directory to app...")
                copy {
                    from(file("res"))
                    into(file("${project.buildDir}/compose/binaries/main/app/res"))
                }
            }
        }
    } catch (e: Exception) {
        println("Warning: Could not configure resource copying: ${e.message}")
    }
}

// Ensure /res is included in the distribution before packaging
tasks.register<Copy>("includeResInDistribution") {
    group = "distribution"
    description = "Copy res folder into compose distribution app folder"
    from(file("res"))
    into(file("${project.buildDir}/compose/binaries/main/app/res"))
}

// Make packageDistributionForCurrentOS depend on copying resources
afterEvaluate {
    try {
        tasks.named("packageDistributionForCurrentOS") {
            dependsOn("includeResInDistribution")
        }
    } catch (e: Exception) {
        println("Warning: Could not wire includeResInDistribution into packaging: ${e.message}")
    }
}


// Task to create a fat/uber JAR with all dependencies
tasks.register<Jar>("fatJar") {
    group = "distribution"
    description = "Create a fat JAR with all dependencies"
    archiveClassifier.set("all")
    
    from(sourceSets.main.get().output)
    dependsOn(configurations.runtimeClasspath)
    
    manifest {
        attributes["Main-Class"] = "main.kotlin.MainAppKt"
    }
    
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    
    from({
        configurations.runtimeClasspath.get().filter { it.name.endsWith("jar") }.map { zipTree(it) }
    })
}

// Task to create a runnable JAR with dependencies in a lib folder
tasks.register<Jar>("runnableJar") {
    group = "distribution"
    description = "Create a runnable JAR with dependencies in lib folder"
    archiveClassifier.set("runnable")
    
    from(sourceSets.main.get().output)
    
    manifest {
        attributes["Main-Class"] = "main.kotlin.MainAppKt"
        attributes["Class-Path"] = configurations.runtimeClasspath.get().filter { it.name.endsWith("jar") }
            .map { "lib/${it.name}" }
            .joinToString(" ")
    }
}

// Task to copy dependencies to lib folder
tasks.register<Copy>("copyDependencies") {
    group = "distribution"
    description = "Copy dependencies to lib folder"
    from(configurations.runtimeClasspath)
    into("${buildDir}/libs/lib")
}

// Convenience task for full packaging
tasks.register("packageApplication") {
    group = "distribution"
    description = "Build standalone application for current OS"
    dependsOn("packageDistributionForCurrentOS")
}

// Convenience task for JAR distribution
tasks.register("packageJar") {
    group = "distribution"
    description = "Build JAR distribution"
    dependsOn("fatJar", "copyDependencies")
}

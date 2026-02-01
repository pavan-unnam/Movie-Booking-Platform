subprojects {

    tasks.withType<Test> {
        useJUnitPlatform()  // Use JUnit 5 for testing
    }


    tasks.register<Delete>("flush") {
        group = "build"
        description = "Deletes build and log directories"

        delete(
            layout.buildDirectory,
            file("logs"),
            file("log"),
            file("out/logs")
        )
    }

    tasks.register("ensureBuildDir") {
        group = "build"
        description = "Ensures the project's build directory exists"
        doLast {
            val bd = layout.buildDirectory.get().asFile
            if (!bd.exists()) {
                bd.mkdirs()
                logger.lifecycle("Created build dir for ${project.path}: $bd")
            }
        }
    }

    tasks.register("install") {
        group = "build"
        description = "Builds bootJar (apps) or jar (libs)"

        // Always run flush and tests, and depend on any packaging tasks that exist in the project.
        dependsOn("ensureBuildDir", "flush", "test")
        dependsOn(tasks.matching { it.name == "bootJar" || it.name == "jar" })

        doLast {
            val built = listOfNotNull(
                tasks.findByName("bootJar")?.takeIf { it.enabled },
                tasks.findByName("jar")?.takeIf { it.enabled }
            )
            if (built.isEmpty()) {
                logger.lifecycle("No packaging tasks found in ${project.path}")
            } else {
                logger.lifecycle("Packaging completed: ${built.joinToString { it.name }}")
            }
        }
    }

    tasks.register<Copy>("collectJars") {
        description = "Collects bootJar/jar outputs from all subprojects into build/artifacts"
        group = "build"

        // Ensure packaging tasks run first across subprojects
        dependsOn(subprojects.flatMap { sp ->
            sp.tasks.matching { it.name == "bootJar" || it.name == "jar" }.toList()
        })

        from(subprojects.map { it.layout.buildDirectory.dir("libs") }) {
            include("*.jar")
        }
        into(layout.buildDirectory.dir("artifacts"))

        doLast {
            logger.lifecycle("Collected jars into: ${layout.buildDirectory.dir("artifacts").get().asFile}")
        }
    }
}
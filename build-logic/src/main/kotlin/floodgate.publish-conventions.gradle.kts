plugins {
    id("floodgate.shadow-conventions")
    id("com.jfrog.artifactory")
    id("maven-publish")
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            groupId = project.group as String
            artifactId = project.name
            version = project.version as String

            artifact(tasks["shadowJar"])
            artifact(tasks["sourcesJar"])
        }
    }
}

artifactory {
    setContextUrl("https://repo.opencollab.dev/artifactory")
    publish {
        repository {
            setRepoKey(if (isSnapshot()) "maven-snapshots" else "maven-releases")
            setMavenCompatible(true)
        }
        defaults {
            publications("mavenJava")
            setPublishArtifacts(true)
            setPublishPom(true)
            setPublishIvy(false)
        }
    }
}
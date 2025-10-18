plugins {
    kotlin("jvm") version "1.9.21"
    kotlin("plugin.serialization") version "1.9.21"
    id("org.jetbrains.compose") version "1.5.11"
}

group = "com.cobrien.myjournal"
version = "1.0.0"

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    google()
}

dependencies {
    implementation(compose.desktop.currentOs)
    implementation(compose.material3)
    implementation(compose.materialIconsExtended)
    
    // PDF Generation
    implementation("com.itextpdf:itext7-core:7.2.5")
    
    // JSON for data persistence
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")
    
    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
}

compose.desktop {
    application {
        mainClass = "com.cobrien.myjournal.MainKt"
        
        nativeDistributions {
            targetFormats(
                org.jetbrains.compose.desktop.application.dsl.TargetFormat.Deb,
                org.jetbrains.compose.desktop.application.dsl.TargetFormat.Rpm
            )
            
            packageName = "MyJournal"
            packageVersion = "1.0.0"
            description = "A customizable journal application with handwriting support"
            copyright = "© 2024 Your Name. All rights reserved."
            vendor = "cobrien"
            
            linux {
                iconFile.set(project.file("src/main/resources/icon.png"))
                packageName = "com.cobrien.myjournal"
                debMaintainer = "cobrien@example.com"
                menuGroup = "Office"
                appCategory = "Office"
            }
        }
    }
}

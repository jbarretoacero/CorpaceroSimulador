// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.13.2" apply false
    id("org.jetbrains.kotlin.android") version "2.0.20" apply false
    id("org.jetbrains.kotlin.plugin.compose") version "2.0.20" apply false
}

// Redirige las carpetas build/ fuera de OneDrive para evitar bloqueos de archivos
// durante mergeReleaseResources / transforms.
val outOfSourceBuildRoot = java.io.File(System.getProperty("user.home"), ".android-builds/CorpaceroSimulador")

allprojects {
    layout.buildDirectory.set(java.io.File(outOfSourceBuildRoot, name))
}

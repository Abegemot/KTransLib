
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        mavenLocal()
    }
    includeBuild("H:/prg/KnewsPlatform2")
    includeBuild("../KNewsCommon")
}
rootProject.name = "KTransLib"


package wizard.files

import wizard.ProjectFile

class Gitignore : ProjectFile {
    override val path = ".gitignore"
    override val content = """
*.iml
.gradle
.idea
.kotlin
.DS_Store
build
*/build
captures
.externalNativeBuild
.cxx
local.properties
xcuserdata/
Pods/
*.jks
*.gpg
*yarn.lock
"""
}
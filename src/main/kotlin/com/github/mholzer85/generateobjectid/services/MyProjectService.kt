package com.github.mholzer85.generateobjectid.services

import com.intellij.openapi.project.Project
import com.github.mholzer85.generateobjectid.MyBundle

class MyProjectService(project: Project) {

    init {
        println(MyBundle.message("projectService", project.name))
    }
}

package dsl

import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogPanel
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.ui.layout.GrowPolicy
import com.intellij.ui.layout.panel
import services.createUsersAndProjectsTextField
import javax.swing.JComponent
import javax.swing.JTextField


fun createDslModal(project: Project?): DialogPanel {
    val reviewersList = mutableListOf<String>("testing")
    val subscribersList = mutableListOf<String>()
    val reviewers = createUsersAndProjectsTextField(project, reviewersList)
    val subscribers = createUsersAndProjectsTextField(project, subscribersList)
    val titleTextField = JTextField()
    return panel {
        this.row("Title:") {
            titleTextField(growPolicy = GrowPolicy.SHORT_TEXT)
        }
        row("Reviewers:") {
            reviewers(growPolicy = GrowPolicy.MEDIUM_TEXT).focused()
        }
        row("Reviewers List:") {
            for (reviewer in reviewersList) {
                label(reviewer)
            }
        }
        row("Subscribers:") {
            subscribers(growPolicy = GrowPolicy.MEDIUM_TEXT)
        }
        row("Subscribers List:") {
            for (subscriber in subscribersList) {
                label(subscriber)
            }
        }
    }
}

class SampleDialogWrapper(private val project: Project?) : DialogWrapper(true) {
    init {
        init()
        title = "Test DialogWrapper"
    }// use current window as parent

    override fun createCenterPanel(): JComponent? {
        return createDslModal(project)
    }
}
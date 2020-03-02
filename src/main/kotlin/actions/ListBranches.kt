package actions

import ListBranchesController
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import forms.ListBranchesDialog

class ListBranches : AnAction() {
    override fun actionPerformed(event: AnActionEvent) {
        val project = event.project
        val dialog = ListBranchesDialog()
        val controller = ListBranchesController(dialog, project!!)
        controller.init()
    }
}
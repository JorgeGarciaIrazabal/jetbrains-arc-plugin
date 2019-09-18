package actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import forms.SampleDialogWrapper

class CreateDiffAction : AnAction() {
    override fun actionPerformed(event: AnActionEvent) {
        val project = getEventProject(event)
//        CreateDiffForm.main(project)
        SampleDialogWrapper(project).show()
//        createDslModal().isVisible = true
    }
}
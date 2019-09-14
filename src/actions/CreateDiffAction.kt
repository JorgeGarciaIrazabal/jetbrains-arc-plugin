package actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.ui.Messages
import forms.CreateDiffForm

class CreateDiffAction : AnAction() {
    override fun actionPerformed(event: AnActionEvent) {
        val project = getEventProject(event)
        CreateDiffForm.main()
    }
}
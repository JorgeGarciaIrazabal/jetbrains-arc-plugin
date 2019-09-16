package services

import com.intellij.codeInsight.completion.CompletionParameters
import com.intellij.openapi.editor.event.DocumentEvent
import com.intellij.openapi.project.Project
import com.intellij.ui.TextFieldWithAutoCompletion
import java.awt.event.KeyEvent
import javax.swing.KeyStroke


fun getUsersAndProjects(): List<String> {
    val list = mutableListOf<String>()
    for (user in ArcAPI.getUsers()) {
        list.add("${user.name} (${user.username})")
    }

    for (project in ArcAPI.getProjects()) {
        list.add(project.name)
    }
    return list
}

fun createUsersAndProjectsTextField(
    project: Project?,
    stringList: MutableList<String>
): TextFieldWithAutoCompletion<String> {
    val provider = object : TextFieldWithAutoCompletion.StringsCompletionProvider(null, null) {
        override fun getItems(prefix: String?, cached: Boolean, parameters: CompletionParameters?): Collection<String> {
            setItems(getUsersAndProjects())
            return super.getItems(prefix, cached, parameters)
        }
    }
    val textField = object : TextFieldWithAutoCompletion<String>(
        project,
        provider,
        false,
        ""
    ) {
        override fun processKeyBinding(ks: KeyStroke, e: KeyEvent, condition: Int, pressed: Boolean): Boolean {
            if (e.keyCode == KeyEvent.VK_ENTER) {
                stringList.add(this.text)
                return true
            }
            return false
        }
    }
    textField.addDocumentListener(object : com.intellij.openapi.editor.event.DocumentListener {
        override fun documentChanged(event: DocumentEvent) {
            super.documentChanged(event)
            println(textField.text)
        }
    })
    return textField
}

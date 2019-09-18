package services

import User
import com.intellij.codeInsight.completion.CompletionParameters
import com.intellij.openapi.editor.event.DocumentEvent
import com.intellij.ui.TextFieldWithAutoCompletion
import forms.CreateDiffForm
import java.awt.event.KeyEvent
import javax.swing.JLabel
import javax.swing.KeyStroke
import javax.swing.Timer
import Project as ArcProject


fun getUsersAndProjects(): MutableMap<String, Any> {
    val list = mutableMapOf<String, Any>()

    for (user in ArcAPI.getUsers()) {
        list["${user.name} - ${user.username}"] = user
    }

    for (project in ArcAPI.getProjects()) {
        list["#${project.name}"] = project
    }
    return list
}

fun createUsersAndProjectsTextField(
    callback: (Any) -> Unit
): TextFieldWithAutoCompletion<String> {
    val usersAndProject = getUsersAndProjects()
    val provider = object : TextFieldWithAutoCompletion.StringsCompletionProvider(null, null) {
        override fun getItems(prefix: String?, cached: Boolean, parameters: CompletionParameters?): Collection<String> {
            setItems(usersAndProject.keys)
            return super.getItems(prefix, cached, parameters)
        }
    }
    val textField = object : TextFieldWithAutoCompletion<String>(
        CreateDiffForm.project,
        provider,
        false,
        ""
    ) {
        override fun processKeyBinding(ks: KeyStroke, e: KeyEvent, condition: Int, pressed: Boolean): Boolean {
            if (e.keyCode == KeyEvent.VK_ENTER) {
                usersAndProject[text]?.let { callback(it) }
                return true
            }
            return false
        }
    }
    textField.addDocumentListener(object : com.intellij.openapi.editor.event.DocumentListener {
        override fun documentChanged(event: DocumentEvent) {
            super.documentChanged(event)
            usersAndProject[textField.text]?.let { callback(it) }
        }
    })
    return textField
}

class CreateDiffController(private val form: CreateDiffForm) {
    private val reviewersSelected = mutableListOf<Any>()
    private val subscribersSelected = mutableListOf<Any>()
    fun setup() {
        form.reviewersTextField = createUsersAndProjectsTextField() { item ->
            handleOnUserOrProjectSelected(
                field = form.reviewersTextField,
                labelObj = form.reviewersLabel,
                selectedItem = item,
                itemList = reviewersSelected
            )
        }
        form.subscribersTextField = createUsersAndProjectsTextField() { item ->
            handleOnUserOrProjectSelected(
                field = form.subscribersTextField,
                labelObj = form.subscribersLabel,
                selectedItem = item,
                itemList = subscribersSelected
            )
        }

        // todo move this logic to a different place after the dialog is fully shown
        val timer = Timer(100) {
            form.reviewersClearButton.addActionListener {
                form.reviewersLabel.text = ""
                reviewersSelected.clear()
            }
            form.subscribersClearButton.addActionListener {
                form.subscribersLabel.text = ""
                reviewersSelected.clear()
            }
        }
        timer.isRepeats = false
        timer.start()
    }

    private fun handleOnUserOrProjectSelected(
        field: TextFieldWithAutoCompletion<String>,
        labelObj: JLabel,
        selectedItem: Any,
        itemList: MutableList<Any>
    ) {
        if (selectedItem !in itemList) itemList.add(selectedItem)
        else return

        val labelText = mutableListOf<String>()
        for (item in itemList) {
            if (item is User && item.username !in labelText) labelText.add(item.username)
            else if (item is ArcProject && item.name !in labelText) labelText.add(item.name)
        }
        labelObj.text = "<html>${labelText.joinToString(", ")}</html>"

        val timer = Timer(100) {
            field.text = ""
        }
        timer.isRepeats = false
        timer.start()
    }

}


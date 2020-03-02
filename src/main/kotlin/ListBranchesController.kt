import com.intellij.openapi.project.Project
import forms.ListBranchesDialog
import javax.swing.JCheckBox
import javax.swing.event.ChangeListener

class ListBranchesController(private val dialog: ListBranchesDialog, val project: Project) {
    fun init() {
        val listener = ChangeListener {
            listAllBranches()
        }
        dialog.branchesRadioButton.addChangeListener(listener)
        dialog.tagsRadioButton.addChangeListener(listener)

        dialog.isVisible = true
    }

    fun listAllBranches() {
        dialog.branchesList.removeAll()
        if (dialog.branchesRadioButton.isSelected) {
            dialog.branchesList.add(JCheckBox("This is a test"))
        }
    }

}
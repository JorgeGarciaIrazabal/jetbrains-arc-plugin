package forms;

import com.intellij.openapi.project.Project;
import com.intellij.ui.TextFieldWithAutoCompletion;
import services.CreateDiffController;

import javax.swing.*;

public class CreateDiffForm extends JDialog {
    public static Project project;
    public JPanel contentPanel;
    public JLabel reviewersLabel;
    public JLabel subscribersLabel;
    public JTextField titleTextField;
    public TextFieldWithAutoCompletion<String> subscribersTextField;
    public TextFieldWithAutoCompletion<String> reviewersTextField;
    public JButton reviewersClearButton;
    public JButton subscribersClearButton;

    public static CreateDiffForm main(Project project) {
        CreateDiffForm.project = project;
        return new CreateDiffForm();
    }

    private void createUIComponents() {
        new CreateDiffController(this).setup();
    }
}

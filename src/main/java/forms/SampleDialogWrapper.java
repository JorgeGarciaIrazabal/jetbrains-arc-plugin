package forms;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class SampleDialogWrapper extends DialogWrapper {
    private final Project project;

    public SampleDialogWrapper(Project project) {
        super(true); // use current window as parent
        this.project = project;
        init();
        setTitle("Test DialogWrapper");
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        CreateDiffForm dialog = CreateDiffForm.main(this.project);
        return dialog.contentPanel;
    }
}
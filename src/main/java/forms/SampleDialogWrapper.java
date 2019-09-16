package forms;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogPanel;
import com.intellij.openapi.ui.DialogWrapper;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

import static dsl.ArcDiffKt.createDslModal;

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
        DialogPanel panel = createDslModal(project);
        return panel;

//        JPanel dialogPanel = new JPanel(new BorderLayout());
//
//        JLabel label = new JLabel("testing");
//        label.setPreferredSize(new Dimension(100, 100));
////        dialogPanel.add(label, BorderLayout.CENTER);
//
//        TextFieldWithAutoCompletionListProvider<String> provider = new TextFieldWithAutoCompletion.StringsCompletionProvider(null, null) {
//            @NotNull
//            @Override
//            public Collection<String> getItems(String prefix, boolean cached, CompletionParameters parameters) {
//                setItems(getUsersAndProjects());
//                return super.getItems(prefix, cached, parameters);
//            }
//        };
//        TextFieldWithAutoCompletion myTaskField = new TextFieldWithAutoCompletion<String>(this.project, provider, true, "");
//        dialogPanel.add(myTaskField, BorderLayout.CENTER);
////
////        return dialogPanel;
    }
}
package forms;

import services.ArcAPI;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.*;

public class CreateDiffForm extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JComboBox<String> subscribersCB;
    private JComboBox<String> reviewersCB;
    private JTextField textField1;
    private JTextField textField3;
    private JTextField textField2;

    public CreateDiffForm() {
        ArcAPI a = new ArcAPI();
        a.getProjects();
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        subscribersCB.addItem("aa");
        subscribersCB.addItem("bb");
        subscribersCB.addItem("ab");
        subscribersCB.addItem("ac");
        subscribersCB.addItem("cc");
        final JTextComponent tc = (JTextComponent) subscribersCB.getEditor().getEditorComponent();
        tc.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                subscribersCB.showPopup();
                String text = (String) subscribersCB.getEditor().getItem();
                // todo add filtering logic
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                subscribersCB.showPopup();
                String text = (String) subscribersCB.getEditor().getItem();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        // add your code here
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void main() {
        CreateDiffForm dialog = new CreateDiffForm();
        dialog.pack();
        dialog.setVisible(true);
        dialog.setSize(new Dimension(500, 500));
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}

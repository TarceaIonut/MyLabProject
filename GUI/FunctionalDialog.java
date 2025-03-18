package org.example.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

public class FunctionalDialog extends JDialog implements ActionListener {
    protected JButton okButton = new JButton("OK");
    protected JButton cancelButton = new JButton("Cancel");

    protected ProjectManager manager;

    protected JPanel mainPanel = new JPanel();
    private JPanel buttonPanel = new JPanel();
    protected JPanel contentPanel = new JPanel();

    protected Display owner;
    private ArrayList<JTextField> textFields = new ArrayList<>();

    private Consumer<ArrayList<String>> consumer;

    public FunctionalDialog(ProjectManager manager, Display owner, Consumer<ArrayList<String>> consumer, ArrayList<String> labelInputs) {
        this.consumer = consumer;

        this.setModal(true);
        this.setSize(500,500);
        this.resize(500,500);
        this.manager = manager;
        this.owner = owner;
        okButton.addActionListener(this);
        cancelButton.addActionListener(this);

        mainPanel.setLayout(new GridLayout(2, 1));
        mainPanel.add(contentPanel);
        mainPanel.add(buttonPanel);

        buttonPanel.setLayout(new GridLayout(1, 2));
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);


        contentPanel.setLayout(new GridLayout(labelInputs.size(),2));
        labelInputs.forEach((s) -> {
            JTextField textField = new JTextField();
            contentPanel.add(new JLabel(s));
            contentPanel.add(textField);
            textFields.add(textField);
        });
        setDialog();
    }
    protected void setDialog(){
        this.add(this.mainPanel);
        this.setSize(500, 300);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setLocation(owner.getLocation().x + 300, owner.getLocation().y + 300);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == cancelButton) {
            dispose();
        }
        if (e.getSource() == okButton) {
            try{
                ArrayList<String> listOfMethodInputs = new ArrayList<>();
                AtomicBoolean empty = new AtomicBoolean(false);
                textFields.forEach((field) -> {
                    if (field.getText().equals("")) {
                        empty.set(true);
                    }
                    listOfMethodInputs.add(field.getText());
                });
                if (empty.get()) throw new Exception("The fields must not be empty");
                consumer.accept(listOfMethodInputs);
                dispose();
            }catch (Exception ex){
                new ErrorMessage(ex.getMessage(), this);
            }
        }
    }
}

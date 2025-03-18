package org.example.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ErrorMessage extends JDialog implements ActionListener {
    JButton ok = new JButton("OK");
    public ErrorMessage(String message, Window parent) {
        super();
        this.setModal(true);
        JDialog j = new JDialog();
        this.setTitle("Input Error");
        ok.addActionListener(this);
        JLabel l = new JLabel(message);

        this.setLayout(new BorderLayout());
        this.add(l, BorderLayout.CENTER);
        this.add(ok, BorderLayout.SOUTH);

        this.setSize(new Dimension(1000, 200));
        this.setLocation(parent.getLocation().x + 250, parent.getLocation().y + 350);
        this.setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == ok) {
            this.dispose();
        }
    }
}

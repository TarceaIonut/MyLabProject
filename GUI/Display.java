package org.example.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Display extends JFrame implements ActionListener {
    private ProjectManager manager;
    private JPanel mainPanel = new JPanel();
    private JPanel ButtonPanel = new JPanel();
    private JButton addEmployee = new JButton("Add Employee");
    private JButton addSimpleTask = new JButton("Add Simple Task");
    private JButton addComplexTask = new JButton("Add Complex Task");
    private JButton addTaskToEmployee = new JButton("Add Task to Employee");
    private JButton addTaskToComplexTask = new JButton("Add Task to Complex Task");
    private JButton viewEmployees =  new JButton("View Employees");
    private JButton modifyTaskStatus =  new JButton("Modify Task Status");
    private JButton calculateEmployeeWorkDuration = new JButton("Calculate Employee Work Duration");
    private JButton filter40Hours = new JButton("Filter 40 Hours");
    private JButton calcTaskStatusForEmployees = new JButton("Calc Task Status for Employees");
    private JButton close = new JButton("Save & Close");
    private JTextArea textArea = new JTextArea();

    public boolean operationDone = false;

    public Display() throws Exception {
        manager = new ProjectManager();
        try{
            manager.loadData();
        }catch(Exception e){
            new ErrorMessage(e.getMessage(), this);
        }
        this.setTitle("Display");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(mainPanel);
        this.pack();
        this.setVisible(true);
        this.setSize(1920,1080);

        mainPanel.repaint();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(ButtonPanel, BorderLayout.WEST);
        mainPanel.add(textArea, BorderLayout.CENTER);

        ButtonPanel.setLayout(new GridLayout(11,1));

//        ButtonPanel.setLayout(new GridLayout(3,3));
        ButtonPanel.add(addEmployee);
        ButtonPanel.add(addSimpleTask);
        ButtonPanel.add(addComplexTask);
        ButtonPanel.add(addTaskToEmployee);
        ButtonPanel.add(addTaskToComplexTask);
        ButtonPanel.add(viewEmployees);
        ButtonPanel.add(modifyTaskStatus);
        ButtonPanel.add(calculateEmployeeWorkDuration);
        ButtonPanel.add(filter40Hours);
        ButtonPanel.add(calcTaskStatusForEmployees);
        ButtonPanel.add(close);

        textArea.setEditable(false);

        addEmployee.addActionListener(this);
        addSimpleTask.addActionListener(this);
        addComplexTask.addActionListener(this);
        addTaskToEmployee.addActionListener(this);
        addTaskToComplexTask.addActionListener(this);
        viewEmployees.addActionListener(this);
        modifyTaskStatus.addActionListener(this);
        calculateEmployeeWorkDuration.addActionListener(this);
        filter40Hours.addActionListener(this);
        calcTaskStatusForEmployees.addActionListener(this);
        close.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try{
            textArea.setText("");
            if (e.getSource() == addEmployee) {
                //new AddEmployeeDialog(manager, this);
                ArrayList<String> a = new ArrayList<>();
                a.add("Employee name");
                new FunctionalDialog(manager, this, inputs -> {
                    try {
                        manager.addEmployees(inputs.get(0));
                    } catch (Exception ex) {
                        new ErrorMessage(ex.getMessage(),this);
                    }
                }, a);
                textArea.setText(manager.simpleViewEmployees());
            }
            if (e.getSource() == addSimpleTask) {
                //new AddSimpleTaskDialog(manager, this);
                ArrayList<String> a = new ArrayList<>();
                a.add("Start hour");
                a.add("End hour");
                new FunctionalDialog(manager, this, inputs -> {
                    try {
                        manager.addTaskSimple(inputs);
                    }
                    catch (Exception ex) {
                        new ErrorMessage(ex.getMessage(),this);
                    }
                }, a);
                textArea.setText(manager.simpleViewTasks());
            }
            if (e.getSource() == addComplexTask){
                manager.addTaskComplex();
                textArea.setText(manager.simpleViewTasks());
            }
            if (e.getSource() == addTaskToEmployee) {
                //new AddTaskToEmployeeDialog(manager, this);
                ArrayList<String> a = new ArrayList<>();
                a.add("Employee id");
                a.add("Task id");
                new FunctionalDialog(manager, this, inputs -> {
                    try {
                        manager.addTaskToEmployee(inputs);
                    } catch (Exception ex) {
                        new ErrorMessage(ex.getMessage(),this);
                    }
                }, a);
            }
            if (e.getSource() == addTaskToComplexTask) {
                //new AddTaskToComplexTaskDialog(manager, this);
                ArrayList<String> a = new ArrayList<>();
                a.add("Complex task id");
                a.add("Task id");
                new FunctionalDialog(manager, this, inputs -> {
                    try {
                        manager.addTaskToTask(inputs);
                    } catch (Exception ex) {
                        new ErrorMessage(ex.getMessage(),this);
                    }
                }, a);
            }
            if (e.getSource() == viewEmployees) {
                textArea.setText(manager.viewEmployees());
            }
            if (e.getSource() == modifyTaskStatus) {
                //new ModifyTaskStatusDialog(manager, this);
                ArrayList<String> a = new ArrayList<>();
                a.add("Employee id");
                a.add("Task id");
                new FunctionalDialog(manager, this, inputs -> {
                    try {
                        manager.modifyStatusOfTask(inputs);
                    } catch (Exception ex) {
                        new ErrorMessage(ex.getMessage(),this);
                    }
                }, a);
            }
            if (e.getSource() == calculateEmployeeWorkDuration) {
                //new CalculateEmployeeWorkDurationDialog(manager, this);
                ArrayList<String> a = new ArrayList<>();
                a.add("Employee id");
                new FunctionalDialog(manager, this, inputs -> {
                    try {
                        setTextArea("" + manager.calculateEmployeeWorkDuration(inputs));
                    } catch (Exception ex) {
                        new ErrorMessage(ex.getMessage(), this);
                    }
                }, a);
            }
            if (e.getSource() == filter40Hours) {
                textArea.setText(manager.filter40Hours());
            }
            if (e.getSource() == calcTaskStatusForEmployees) {
                textArea.setText(manager.calcTaskStatusForEmployees());
            }
            if (e.getSource() == close) {
                manager.saveData();
                this.dispose();
            }
        }
        catch (Exception ex){
            new ErrorMessage(ex.getMessage(), this);
        }
        finally {
            operationDone = false;
        }
    }
    
    public void setTextArea(String text) {
        textArea.setText(text);
    }
}
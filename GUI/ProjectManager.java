package org.example.GUI;

import org.example.BusinessLogic.*;
import org.example.DataModel.*;


import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class ProjectManager {
    private final TasksManagement tasksManagement;
    private final Utility utility;
    private Set<Employee> employees = new HashSet<>();
    private Set<Task> tasks;


    public ProjectManager(){
        this.tasksManagement = new TasksManagement();
        this.utility = new Utility(tasksManagement);
        this.tasks = tasksManagement.tasks;
        this.employees = tasksManagement.employees;
    }
    public void saveData() throws Exception {
        tasksManagement.saveData();
    }
    public void loadData() throws Exception {
        tasksManagement.load();
    }

    public void addEmployees(String name) throws Exception {
        if (name.isEmpty()) throw new Exception("Name can not be null");
        AtomicInteger count = new AtomicInteger(0);
        employees.forEach(employee -> {
            if (employee.idEmployee > count.get()) {
                count.set(employee.idEmployee);
            }
        });
        count.incrementAndGet();
        Employee newEmployee = new Employee(count.get(), name);
        employees.add(newEmployee);
        tasksManagement.taskToEmployee.put(newEmployee, new ArrayList<Task>());
    }
    public void addTaskSimple(ArrayList<String> a) throws Exception{
        int start, end;
        try{
            start = Integer.parseInt(a.get(0));
            end = Integer.parseInt(a.get(1));
        } catch (NumberFormatException e) {
            throw new Exception("The fields must be integers");
        }
        AtomicInteger newId = new AtomicInteger(0);
        tasks.forEach(task -> {
            if (task.idTask > newId.get())
                newId.set(task.idTask);
        });

        tasks.add(new SimpleTask(newId.get() + 1, start, end));

    }
    public void addTaskComplex(){
        AtomicInteger newId = new AtomicInteger(0);
        tasks.forEach(task -> {
            if (task.idTask > newId.get())
                newId.set(task.idTask);
        });
        tasks.add(new ComplexTask(newId.get() + 1));
    }
    public void addTaskToEmployee(ArrayList<String> a) throws Exception {
        int idTask, idEmployee;
        try{
            idEmployee = Integer.parseInt(a.get(0));
            idTask = Integer.parseInt(a.get(1));
        } catch (NumberFormatException e) {
            throw new Exception("The fields must be integers");
        }
        if (!taskExists(idTask)) throw new Exception("Task dose not exists");
        if (!employeeExists(idEmployee)) throw new Exception("Employee dose not exists");

        AtomicReference<Task> taskToBeAdded = new AtomicReference<>();

        tasks.forEach(task -> {
            if (task.idTask == idTask) {
                taskToBeAdded.set(task);
            }
        });

        tasksManagement.assignTaskToEmployee(idEmployee, taskToBeAdded.get());
    }
    public void addTaskToTask(ArrayList<String> a) throws Exception {
        int taskId, complexTaskId;
        try{
            complexTaskId = Integer.parseInt(a.get(0));
            taskId = Integer.parseInt(a.get(1));
        }catch (NumberFormatException e) {
            throw new Exception("The fields must be integers");
        }
        if (!taskExists(taskId)) throw new Exception("Task dose not exists");
        if (!taskExistsComplex(complexTaskId)) throw new Exception("Complex task dose not exists");
        AtomicReference<Task> taskToBeAdded = new AtomicReference<>();
        tasks.forEach(task -> {
            if (task.idTask == taskId) {
                taskToBeAdded.set(task);
            }
        });
        Task t = taskToBeAdded.get();
        for (Task task : tasks) {
            if (task.idTask == complexTaskId) {
                ((ComplexTask)task).addTask(t);
            }
        }
    }
    public String viewEmployees() {
        StringBuffer info = new StringBuffer("");
        Set<Integer> s = new HashSet<>();
        tasksManagement.taskToEmployee.forEach((employee, tasks) -> {
            s.add(employee.idEmployee);
            info.append(employee.name + "\n");
            info.append("number of tasks = " + tasks.size() + "\n");
            AtomicInteger totalTime = new AtomicInteger(0);
            tasks.forEach(task -> {
                info.append("Task Id = " + task.idTask + " Estimated Duration = " + task.estimateDuration() + '\n');
                totalTime.addAndGet(task.estimateDuration());
            });
            info.append("Total Task Time = " + totalTime + '\n');
        });
        employees.forEach(employee -> {
            if (!s.contains(employee.idEmployee)) {
                info.append("Employee Id = " + employee.idEmployee + " No tasks " + '\n');
            }
        });
        return info.toString();
    }
    public void modifyStatusOfTask(int idEmployee, int idTask) throws Exception {
        if (!taskExists(idTask)) throw new Exception("Task dose not exists");
        if (!employeeExists(idEmployee)) throw new Exception("Employee dose not exists");
        tasksManagement.modifyTaskStatus(idEmployee, idTask);
    }
    public void modifyStatusOfTask(ArrayList<String> a) throws Exception {
        int idEmployee, idTask;
        try{
            idEmployee = Integer.parseInt(a.get(0));
            idTask = Integer.parseInt(a.get(1));
        }catch (NumberFormatException e) {
            throw new Exception("The fields must be integers");
        }
        if (!taskExists(idTask)) throw new Exception("Task dose not exists");
        if (!employeeExists(idEmployee)) throw new Exception("Employee dose not exists");
        tasksManagement.modifyTaskStatus(idEmployee, idTask);
    }
    public String calcTaskStatusForEmployees(){
        Map<Employee, Map<String, Integer>> map = utility.calcTaskStatusForEmployees();
        Iterator<Map.Entry<Employee, Map<String, Integer>>> it = map.entrySet().iterator();
        StringBuilder info = new StringBuilder("");
        map.forEach((employee, sMap) -> {
            info.append(employee.name + " with id = " + employee.idEmployee + " : Uncompleted Tasks = " +
                    sMap.get("Uncompleted") + " : Completed Tasks = " + sMap.get("Completed") + "\n");
        });
        return info.toString();
    }
    public String filter40Hours(){
        return utility.filter40Hours();
    }
    public int calculateEmployeeWorkDuration(ArrayList<String> a) throws Exception {
        int idEmployee;
        try{
            idEmployee = Integer.parseInt(a.get(0));
        }catch (NumberFormatException e) {
            throw new Exception("The fields must be integers");
        }
        if (!employeeExists(idEmployee)) throw new Exception("employee dose not exist");
        return tasksManagement.calculateEmployeeWorkDuration(idEmployee);
    }
    public boolean employeeExists(int idEmployee) {
        return employees.contains(new Employee(idEmployee, ""));
    }
    public boolean taskExists(int idTask) throws Exception {
        return tasks.contains(new SimpleTask(idTask, 0, 0)) || tasks.contains(new ComplexTask(idTask));
    }
    public boolean taskExistsComplex(int idTask) throws Exception {
        return tasks.contains(new ComplexTask(idTask));
    }
    public String simpleViewEmployees() {
        StringBuffer info = new StringBuffer("");
        employees.forEach(employee -> {
            info.append(employee.name + " With id = " + employee.idEmployee + "\n");
        });
        return info.toString();
    }
    public String simpleViewTasks() {
        StringBuffer info = new StringBuffer("");
        tasks.forEach(task -> {
            if (task instanceof SimpleTask){
                info.append("Simple Task Id = " + task.idTask + " start hour = " + ((SimpleTask) task).startHour + " end hour" + ((SimpleTask)task).endHour + "\n");
            }
            else if (task instanceof ComplexTask){
                info.append("Complex Task Id = " + task.idTask + "\n");
            }
        });
        return info.toString();
    }
}

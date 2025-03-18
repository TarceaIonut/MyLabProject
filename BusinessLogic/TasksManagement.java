package org.example.BusinessLogic;


import org.example.DataAccess.*;
import org.example.GUI.*;
import org.example.DataModel.*;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class TasksManagement {
    public Map<Employee, List<Task>> taskToEmployee;
    public Set<Task> tasks;
    public Set<Employee> employees;

    public TasksManagement(){
        taskToEmployee = new HashMap<>();
        tasks = new HashSet<>();
        employees = new HashSet<>();
    }
    public void load() throws Exception {
        Data.load(tasks, employees, taskToEmployee);
    }
    public void saveData() throws Exception {
        Data.save(tasks, taskToEmployee);
    }
    public void assignTaskToEmployee(int idEmployee, Task task) {
        taskToEmployee.forEach((employee, tasks) -> {
            if (employee.idEmployee == idEmployee) {
                tasks.add(task);
            }
        });
    }
    public int calculateEmployeeWorkDuration(int idEmployee) {
        List<Task> t = taskToEmployee.get(new Employee(idEmployee));
        AtomicInteger atomicRez = new AtomicInteger(0);
        t.forEach((task) -> {
            if (task.statusTask.equals("Uncompleted"))
                atomicRez.addAndGet(task.estimateDuration());
        });
        return atomicRez.get();
    }
    public void modifyTaskStatus(int idEmployee, int idTask) {
        List<Task> t = taskToEmployee.get(new Employee(idEmployee));
        t.forEach((task) -> {
            if (task.idTask == idTask){
                if (task.statusTask.equals("Completed"))
                    task.statusTask = "Uncompleted";
                else task.statusTask = "Completed";
            }
        });
    }
}

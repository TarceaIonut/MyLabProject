package org.example.BusinessLogic;

import org.example.DataModel.*;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Utility {
    public class EmployeeDuration implements Comparable<EmployeeDuration> {
        public String name;
        public int duration;
        public EmployeeDuration(String name, int duration){
            this.name = name;
            this.duration = duration;
        }

        @Override
        public int compareTo(EmployeeDuration o) {
            return this.duration - o.duration;
        }
    }
    private final TasksManagement tasksManagement;

    public Utility(TasksManagement tasksManagement) {
        this.tasksManagement = tasksManagement;
    }

    public String filter40Hours() {
        StringBuffer sb = new StringBuffer("");
        ArrayList<EmployeeDuration> array = new ArrayList<EmployeeDuration>(tasksManagement.taskToEmployee.size());
        System.out.println(tasksManagement.taskToEmployee.size());
        tasksManagement.taskToEmployee.forEach((employee, task) -> {
            int nr = tasksManagement.calculateEmployeeWorkDuration(employee.idEmployee);
            System.out.println(nr + " " + employee.idEmployee);
            if (nr > 40)
                array.add(0, new EmployeeDuration(employee.name, nr));
        });
        array.sort(EmployeeDuration::compareTo);
        for (EmployeeDuration e : array) {
            sb.append(e.name + " with nr of hours = " + e.duration + "\n");
        }
        return sb.toString();
    }
    public Map<Employee, Map<String, Integer>> calcTaskStatusForEmployees(){
        Map<Employee, Map<String, Integer>> employeesHours = new HashMap<>();

        tasksManagement.taskToEmployee.forEach((employee, tasks) -> {
            Map<String, Integer> employeeHours = new HashMap<>();
            AtomicInteger nrCompleted = new AtomicInteger(0);
            AtomicInteger nrUncompleted = new AtomicInteger(0);
            tasks.forEach((task) -> {
                if (task.statusTask.equals("Completed"))
                    nrCompleted.incrementAndGet();
                else nrUncompleted.incrementAndGet();
            });
            employeeHours.put("Completed", nrCompleted.get());
            employeeHours.put("Uncompleted", nrUncompleted.get());
            employeesHours.put(employee, employeeHours);
        });
        return employeesHours;
    }
}

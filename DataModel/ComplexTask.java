package org.example.DataModel;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class ComplexTask extends Task {

    public ArrayList<Task> tasks = new ArrayList<>();

    public ComplexTask(int idTask) {
        super(idTask);
    }

    @Override
    public int estimateDuration() {
//        int rez = 0;
//        for (Task task : tasks) {
//            rez += task.estimateDuration();
//        }
        if (super.statusTask.equals("Completed"))
            return 0;
        AtomicInteger ai = new AtomicInteger(0);
        tasks.forEach(task -> {
            ai.addAndGet(task.estimateDuration());
        });
        return ai.get();
    }
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ComplexTask) {
            if (((Task) obj).idTask == this.idTask) {
                return true;
            }
        }
        return false;
    }
    public void addTask(Task task) {
        tasks.add(task);
    }
    public void deleteTask(Task task) {
        tasks.remove(task);
    }
}

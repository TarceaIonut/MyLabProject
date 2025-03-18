package org.example.DataModel;

import java.io.Serializable;

public abstract class Task implements Serializable {
    public final int idTask;
    public String statusTask;

    public abstract int estimateDuration();

    public Task(int idTask) {
        this.idTask = idTask;
        this.statusTask = "Uncompleted";
    }
    @Override
    public int hashCode() {
        return idTask;
    }
}

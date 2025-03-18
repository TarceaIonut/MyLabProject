package org.example.DataModel;

public class SimpleTask extends Task {
    public int startHour;
    public int endHour;

    public SimpleTask(int idTask, int startHour, int endHour) throws Exception {
        super(idTask);
        this.startHour = startHour;
        this.endHour = endHour;
        if (startHour > endHour)
            throw new Exception("startHour should be smaller than endHour");
    }
    public SimpleTask(int idTask){
        super(idTask);
    }
    @Override
    public int estimateDuration() {
        if (super.statusTask.equals("Completed"))
            return 0;
        return endHour - startHour;
    }
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof SimpleTask) {
            if (((Task) obj).idTask == this.idTask) {
                return true;
            }
        }
        return false;
    }
}

package org.example.DataModel;

import java.io.Serializable;

public class Employee implements Serializable {
    public int idEmployee;
    public String name;

    public Employee(int idEmployee, String name) {
        this.idEmployee = idEmployee;
        this.name = name;
    }
    public Employee(int idEmployee) {
        this.idEmployee = idEmployee;
        this.name = "";
    }
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Employee) {
            if (this.idEmployee == ((Employee)obj).idEmployee) {
                return true;
            }
        }
        return false;
    }
    @Override
    public int hashCode() {
        return idEmployee;
    }
}

package org.example.DataAccess;

import org.example.DataModel.*;
import org.example.GUI.*;

import java.io.*;
import java.util.*;

public class Data {
    public static void save(Set<Task> set, Map<Employee,List<Task>> map) throws Exception {
        Map<String,Object> m = new HashMap<>();
        m.put("tasks",set);
        m.put("link",map);
        File f = new File("data");
        try(ObjectOutputStream oot = new ObjectOutputStream(new FileOutputStream(f))){
            oot.writeObject(m);
        } catch (Exception e) {
            throw new Exception("Save failed");
        }
    }
    public static void load(Set<Task> set, Set<Employee> employeeSet, Map<Employee,List<Task>> map) throws Exception {
        File f = new File("data");
        if (!f.exists()) {
            System.out.println("Data file does not exist");
        }
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))){
            Object obj = ois.readObject();
            Map<String,Object> m = (Map<String,Object>) obj;
            set.addAll((Set<Task>) m.get("tasks"));
            map.putAll((Map<Employee,List<Task>>)m.get("link"));
            employeeSet.addAll(map.keySet());
        } catch (Exception e) {
            throw new Exception("Load failed");
        }
    }
}

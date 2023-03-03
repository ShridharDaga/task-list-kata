package com.codurance.training.tasks;


import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

public class TaskCheck implements Check {

    private final Map<String, List<Task>> tasks;

    private final PrintWriter out;

    public TaskCheck( Map<String, List<Task>> tasks, PrintWriter out) {
        this.tasks = tasks;
        this.out = out;
    }

    @Override
    public void check(String idString, boolean status) {
        updateStatus(idString, status);
    }

    private void updateStatus(String idString, boolean done) {
        int id = Integer.parseInt(idString);
        for (Map.Entry<String, List<Task>> project : tasks.entrySet()) {
            for (Task task : project.getValue()) {
                if (task.getId() == id) {
                    task.setDone(done);
                    return;
                }
            }
        }
        out.printf("Could not find a task with an ID of %d.", id);
        out.println();
    }
}

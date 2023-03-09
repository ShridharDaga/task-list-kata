package com.codurance.training.tasks;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

public class TaskDelete implements Delete{
    private final PrintWriter out;

    private Map<String, List<Task>> tasks;

    public TaskDelete(Map<String, List<Task>> tasks, PrintWriter out) {
        this.out = out;
        this.tasks = tasks;
    }

    @Override
    public void deleteTask(long idString) {
        for (Map.Entry<String, List<Task>> project : tasks.entrySet()) {
            project.getValue().removeIf(task -> task.getId() == idString);
        }
        out.printf("Could not find a task with an ID of %s.", idString);
        out.println();
    }
}

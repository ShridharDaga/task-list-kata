package com.codurance.training.tasks;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.*;

public class TaskView implements View{

    private final PrintWriter out;

    private final Map<String, List<Task>> tasks;

    private static final String SAMPLE = "    [%c] %s: %s%n";

    public TaskView(Map<String, List<Task>> tasks, PrintWriter out) {
        this.out = out;
        this.tasks = tasks;
    }


    @Override
    public void viewByProject() {
        for (Map.Entry<String, List<Task>> project : tasks.entrySet()) {
            out.println(project.getKey());
            for (Task task : project.getValue()) {
                out.printf(SAMPLE, (task.isDone() ? 'x' : ' '), task.getId(), task.getDescription());
            }
            out.println();
        }
    }

    @Override
    public void viewDueTodayTasks() {
        Date today = new Date();

        for (Map.Entry<String, List<Task>> project : tasks.entrySet()) {
            out.println(project.getKey());
            for (Task task : project.getValue()) {
                if(task.getDeadline() != null && parseDate(task.getDeadline()).equals(parseDate(today)))
                    out.printf(SAMPLE, (task.isDone() ? 'x' : ' '), task.getId(), task.getDescription());
            }
            out.println();
        }
    }

    @Override
    public void viewByDate() {
        Comparator<Task> compareByDate = Comparator.comparing(p -> parseDate(p.getCreatedDate()));


        for (Map.Entry<String, List<Task>> project : tasks.entrySet()) {
            out.println(project.getKey());
            List<Task> newTasks = project.getValue();
            newTasks.sort(compareByDate);
            for (Task task : newTasks) {
                out.printf(SAMPLE, (task.isDone() ? 'x' : ' '), task.getId(), task.getDescription());
            }
            out.println();
        }

    }

    @Override
    public void viewByDeadline() {
        Comparator<Task> compareByDate = Comparator.comparing(p -> parseDate(p.getDeadline()));

        for (Map.Entry<String, List<Task>> project : tasks.entrySet()) {
            out.println(project.getKey());
            List<Task> newTasks = project.getValue();
            newTasks.sort(compareByDate);
            for (Task task : newTasks) {
                out.printf(SAMPLE, (task.isDone() ? 'x' : ' '), task.getId(), task.getDescription());
            }
            out.println();
        }
    }

    private String parseDate(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        return formatter.format(date);
    }
}

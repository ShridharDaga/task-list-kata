package com.codurance.training.tasks;

import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class TaskAdd implements Add{

    private long lastId = 0;
    private final Map<String, List<Task>> tasks;

    private final PrintWriter out;

    public TaskAdd(Map<String, List<Task>> tasks, PrintWriter out) {
        this.tasks = tasks;
        this.out = out;
    }


    @Override
    public void add(String commandLine) {
        String[] subcommandRest = commandLine.split(" ", 2);
        String subcommand = subcommandRest[0];
        if (subcommand.equals("project")) {
            addProject(subcommandRest[1]);
        } else if (subcommand.equals("task")) {
            String[] projectTask = subcommandRest[1].split(" ", 2);
            addTask(projectTask[0], projectTask[1]);
        }
    }

    public void addProject(String name) {
        tasks.put(name, new ArrayList<>());
    }

    public void addTask(String project, String description) {
        List<Task> projectTasks = tasks.get(project);
        if (projectTasks == null) {
            out.printf("Could not find a project with the name \"%s\".", project);
            out.println();
            return;
        }
        projectTasks.add(new Task(nextId(), description, false));
    }

    @Override
    public void addDeadlineToTask(long taskId, String deadline) {
        Date date = parseDate(deadline);
        for (Map.Entry<String, List<Task>> project : tasks.entrySet()) {
            for(Task task: project.getValue()) {
                if(task.getId() == taskId) {
                    task.setDeadline(date);
                }
            }
        }
        out.printf("Could not find a task with an ID of %s", taskId);
        out.println();
    }

    private Date parseDate(String deadline) {
        Date date = null;
        try {
            date = new SimpleDateFormat("dd-MM-yyyy").parse(deadline);
        }
        catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

    private long nextId() {
        return ++lastId;
    }

}
package com.codurance.training.tasks;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

public class TaskExecute implements Execute{
    private final Info taskInfo;

    private final Check taskCheck;

    private final Add taskAdd;

    private final View taskView;

    Map<String, List<Task>> tasks;

    PrintWriter out;

    public TaskExecute(Map<String, List<Task>> tasks, PrintWriter out) {
        this.taskInfo = new TaskInfo(tasks, out);
        this.taskCheck = new TaskCheck(tasks, out);
        this.taskAdd = new TaskAdd(tasks, out);
        this.taskView = new TaskView(tasks, out);
    }

    @Override
    public void execute(String commandLine) {
        String[] commandRest = commandLine.split(" ", 2);
        String command = commandRest[0];
        switch (command) {
            case "show":
                taskInfo.show();
                break;
            case "add":
                taskAdd.add(commandRest[1]);
                break;
            case "check":
                taskCheck.check(commandRest[1], true);
                break;
            case "uncheck":
                taskCheck.check(commandRest[1], false);
                break;
            case "help":
                taskInfo.help();
                break;
            case "delete":
                new TaskDelete(tasks, out).deleteTask(Long.parseLong(commandRest[1]));
                break;
            case "deadline":
                taskAdd.addDeadlineToTask(Long.parseLong(commandRest[0]), commandRest[1]);
                break;
            case "today":
                taskView.viewDueTodayTasks();
                break;
            case "view":
                String viewBy = commandRest[1];
                switch (viewBy) {
                    case "by date":
                        taskView.viewByDate();
                        break;
                    case "by deadline":
                        taskView.viewByDeadline();
                        break;
                    case "by project":
                        taskView.viewByProject();
                        break;
                    default:
                        taskInfo.error(command);
                        break;
                }
            default:
                taskInfo.error(command);
                break;
        }
    }

}
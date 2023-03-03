package com.codurance.training.tasks;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

public class TaskExecute implements Execute{
    private final Info taskInfo;

    private final Check taskCheck;

    private final TaskAdd taskAdd;

    public TaskExecute(Map<String, List<Task>> tasks, PrintWriter out) {
        this.taskInfo = new TaskInfo(tasks, out);
        this.taskCheck = new TaskCheck(tasks, out);
        this.taskAdd = new TaskAdd(tasks, out);
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
            default:
                taskInfo.error(command);
                break;
        }
    }

}

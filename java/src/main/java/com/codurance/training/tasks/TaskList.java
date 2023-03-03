package com.codurance.training.tasks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class TaskList implements Runnable {
    private static final String QUIT = "quit";
    private final Execute taskExecute;
    private final BufferedReader in;
    private final PrintWriter out;

    Map<String, List<Task>> tasks;

    public TaskList(BufferedReader in, PrintWriter out) {
        this.in = in;
        this.out = out;
        tasks = new LinkedHashMap<>();
        this.taskExecute = new TaskExecute(tasks, out);
    }

    public void run() {
        while (true) {
            out.print("> ");
            out.flush();
            String command;
            try {
                command = in.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (command.equals(QUIT)) {
                break;
            }
            taskExecute.execute(command);
        }
    }
}

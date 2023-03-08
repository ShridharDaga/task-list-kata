package com.codurance.training.tasks;

public interface Add {
    void add(String commandLine);
    void addDeadlineToTask(long taskId, String deadline);
}

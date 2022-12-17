package by.bsu.algorithms.lab3.task4.entity;

import lombok.NonNull;

import java.util.Arrays;

public class Employee extends Vertex {
    private final int[] taskList;

    public Employee(int index, @NonNull int...tasks) {
        super(index);
        taskList = Arrays.copyOf(tasks, tasks.length);
    }

    public int[] getTaskList() {
        return taskList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Employee employee = (Employee) o;

        return Arrays.equals(taskList, employee.taskList);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(taskList);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Employee{");
        sb.append("index=").append(getIndex());
        sb.append(", taskList=").append(Arrays.toString(taskList));
        sb.append('}');
        return sb.toString();
    }
}
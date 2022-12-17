package by.bsu.algorithms.lab3.task4;

import by.bsu.algorithms.lab3.task4.entity.Employee;
import by.bsu.algorithms.lab3.task4.entity.Vertex;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class HopcroftCarpAlgorithm {
    private final Employee[] employees;
    private final Vertex[] tasks;

    public HopcroftCarpAlgorithm(int[][] adjacentLists) {
        employees = new Employee[adjacentLists.length];
        tasks = new Vertex[adjacentLists.length];
        for (int i = 0; i < adjacentLists.length; i++) {
            employees[i] = new Employee(i, adjacentLists[i]);
            tasks[i] = new Vertex(i);
        }
    }

    public void doAlgorithm() {
        doInitialPartition();
        List<Vertex> freeTask = Arrays.stream(tasks).filter(Vertex::isFree).toList();
        if (freeTask.isEmpty()) {
            printAnswer();
            return;
        }
        List<Employee> freeEmployees = Arrays.stream(employees)
                .filter(Employee::isFree)
                .toList();
        System.out.println("initial step");
        printAnswer();
        for (Employee freeEmployee: freeEmployees) {
            breadthFirstSearch(freeEmployee);
            System.out.println();
            System.out.println("step");
            printAnswer();
        }
        System.out.println("free tasks");
        System.out.println(Arrays.toString(Arrays.stream(tasks)
                .filter(Vertex::isFree)
                .mapToInt(Vertex::getIndex)
                .toArray()));
        System.out.println("free employees");
        System.out.println(Arrays.toString(Arrays.stream(employees)
                .filter(Vertex::isFree)
                .mapToInt(Vertex::getIndex)
                .toArray()));
    }

    public void doInitialPartition() {
        for (Employee employee : employees) {
            int[] employeeTaskList = employee.getTaskList();
            int j = 0;
            while (j < employeeTaskList.length) {
                Vertex task = tasks[employeeTaskList[j]];
                if (task.isFree()) {
                    task.setLink(employee);
                    employee.setLink(task);
                    break;
                }
                j++;
            }
        }
    }

    private void breadthFirstSearch(Employee freeEmployee) {
        PriorityQueue<Vertex> verticesToVisitQueue = new PriorityQueue<>(Comparator.comparingInt(Vertex::getMark));
        List<Vertex> visited = new ArrayList<>();
        verticesToVisitQueue.add(freeEmployee);
        while (!verticesToVisitQueue.isEmpty()) {
            Vertex vertex = verticesToVisitQueue.poll();
            visited.add(vertex);
            switch (vertex) {
                case Employee employee -> {
                    var unvisitedTasks = Arrays.stream(employee.getTaskList())
                            .mapToObj(i -> tasks[i])
                            .filter(task -> !visited.contains(task))
                            .toList();
                    for (Vertex task : unvisitedTasks) {
                        if (!task.isMarked()) {
                            task.mark(vertex.getMark() + 1, vertex);
                        }
                        if (task.isFree()) {
                            fixPartition(task);
                            resetMarking();
                            return;
                        }
                        verticesToVisitQueue.add(task);
                    }
                }
                case Vertex task -> {
                    Employee taskEmployee = (Employee) task.getLink();
                    taskEmployee.mark(task.getMark() + 1, task);
                    verticesToVisitQueue.add(taskEmployee);
                }
            }
        }
    }

    private void fixPartition(Vertex freeTask) {
        Vertex vertex = freeTask;
        int indicator = 0;
        while (vertex.getMarker() != null) {
            if (indicator % 2 == 0) {
                vertex.setLink(vertex.getMarker());
                vertex.getMarker().setLink(vertex);
            }
            indicator++;
            vertex = vertex.getMarker();
        }
    }

    private void printAnswer() {
        for (int i = 0; i < employees.length; i++) {
            System.out.println(employees[i] + " task " + (employees[i].getLink() != null ? employees[i].getLink().getIndex() : null));
        }
    }

    private void resetMarking() {
        Arrays.stream(employees).forEach(Vertex::resetMarking);
        Arrays.stream(tasks).forEach(Vertex::resetMarking);
    }
}
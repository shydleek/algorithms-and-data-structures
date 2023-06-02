package org.example;

import java.util.*;
import java.util.stream.Collectors;

class Equation {
    public int x1;
    public int x2;
    public int x3;
    public int x4;
    public int x5;
    private final Random rand = new Random(System.currentTimeMillis());

    @Override
    public String toString() {
        return "Equation{" +
                "x1=" + x1 +
                ", x2=" + x2 +
                ", x3=" + x3 +
                ", x4=" + x4 +
                ", x5=" + x5 +
                '}';
    }

    public Equation(int x1, int x2, int x3, int x4, int x5) {
        this.x1 = x1;
        this.x2 = x2;
        this.x3 = x3;
        this.x4 = x4;
        this.x5 = x5;
    }
    public Equation(){}

    public Equation generateOne(int minValue, int maxValue) {
        return new Equation(rand.nextInt(maxValue) + minValue, rand.nextInt(maxValue) + minValue,rand.nextInt(maxValue) + minValue, rand.nextInt(maxValue) + minValue,rand.nextInt(maxValue) + minValue);
    }

    public int generateGenome(int minValue, int maxValue) {
        return rand.nextInt(maxValue) + minValue;
    }

    public ArrayList<Equation> generatePopulation(int size, int minValue, int maxValue) {
        ArrayList<Equation> list = new ArrayList<>(size);
        for(int i = 0; i < size; i++) {
            list.add(generateOne(minValue, maxValue));
        }
        return list;
    }
}

class EquationSolver {
    private final int minValue = -100;
    private final int maxValue = 100;
    private final int populationCount = 10;
    private final double mutationChanceForGoodOffspring = 0.5;
    private final double mutationChanceForBadOffspring = 0.75;
    private int[][] powers;
    private int result;

    public double propability(Random random){
        return random.nextDouble(1)+0;
    }

    public EquationSolver(int[][] powers, int result) {
        this.powers = powers;
        this.result = result;
    }

    private double solutionError(Equation equation) {
        return Math.abs(
                Math.pow(equation.x1, powers[0][0])*Math.pow(equation.x2, powers[0][1])*Math.pow(equation.x3, powers[0][2])*Math.pow(equation.x4, powers[0][3])*Math.pow(equation.x5, powers[0][4]) + Math.pow(equation.x1, powers[1][0])*Math.pow(equation.x2, powers[1][1])*Math.pow(equation.x3, powers[1][2])*Math.pow(equation.x4, powers[1][3])*Math.pow(equation.x5, powers[1][4]) + Math.pow(equation.x1, powers[2][0])*Math.pow(equation.x2, powers[2][1])*Math.pow(equation.x3, powers[2][2])*Math.pow(equation.x4, powers[2][3])*Math.pow(equation.x5, powers[2][4]) + Math.pow(equation.x1, powers[3][0])*Math.pow(equation.x2, powers[3][1])*Math.pow(equation.x3, powers[3][2])*Math.pow(equation.x4, powers[3][3])*Math.pow(equation.x5, powers[3][4]) + Math.pow(equation.x1, powers[4][0])*Math.pow(equation.x2, powers[4][1])*Math.pow(equation.x3, powers[4][2])*Math.pow(equation.x4, powers[4][3])*Math.pow(equation.x5, powers[4][4]) - result
        );
    }

    private double fitnessFunction(Equation equation) {
        return 1/Math.abs(0.00000001 + ((double) solutionError(equation)));
    }

    public void start() {
        List<Equation> population = new Equation().generatePopulation(populationCount, minValue, maxValue);
        double bestSolutionError = fitnessFunction(population.get(0));
        Equation bestSolution = population.get(0);
        int iteration = 0;
        while(bestSolutionError < 1) {
            List<Equation> newPopulation = selection(new ArrayList<>(population));
            List<Equation> nextPopulation = substitution(population, newPopulation);
            double newBestSolutionError = fitnessFunction(nextPopulation.get(0));
            Equation newBestSolution = nextPopulation.get(0);
            if (newBestSolutionError > bestSolutionError) {
                bestSolution = newBestSolution;
                bestSolutionError = newBestSolutionError;
            }
            population = nextPopulation;
            iteration++;
        }
        System.out.println("Best solution\n" + bestSolution.toString() + "\nIteration: " + iteration + "\nSolution: " + solutionError(bestSolution)+"\n--------------------\n");


    }

    public ArrayList<Equation> substitution(List<Equation> oldPopulation, List<Equation> newPopulation) {
        List<Equation> mergedPopulation = new ArrayList<>();
        mergedPopulation.addAll(oldPopulation);
        mergedPopulation.addAll(newPopulation);

        mergedPopulation.sort(Comparator.comparingDouble(this::fitnessFunction).reversed());

        return new ArrayList<>(mergedPopulation.subList(0, populationCount));
    }

    public ArrayList<Equation> selection(ArrayList<Equation> old) {
        List<Equation> children = new ArrayList<>();
        List<Double> errorsOfEquations = new ArrayList<>();
        for (Equation i : old) {
            double tmp = fitnessFunction(i);
            errorsOfEquations.add(tmp);
        }

        Random rand = new Random(System.currentTimeMillis());
        for (int i = 0; i < populationCount; i++) {
            List<Equation> tournament = new ArrayList<>();
            int tournamentSize = 3;
            for (int j = 0; j < tournamentSize; j++) {
                int randomIndex = rand.nextInt(old.size());
                tournament.add(old.get(randomIndex));
            }
            Equation winner = tournament.stream()
                    .max(Comparator.comparingDouble(this::fitnessFunction))
                    .orElseThrow(NoSuchElementException::new);
            children.add(winner);
        }

        children = crossover((ArrayList<Equation>) children);
        children = mutation((ArrayList<Equation>) children);

        return (ArrayList<Equation>) children;
    }

    public List<Equation> crossover(ArrayList<Equation> old) {
        List<Equation> newChildren = new ArrayList<>();
        Random rand  = new Random(System.currentTimeMillis());
        for(int i = 0; i < old.size() - 1; i+=2){
            Equation parent1 = old.get(i);
            Equation parent2 = old.get(i+1);
            for(int j = 0; j < old.size() - 1; j++)
            {
                if(propability(rand) > mutationChanceForGoodOffspring) {
                    newChildren.add(new Equation(parent1.x1, parent1.x2, parent1.x3, parent1.x4, parent1.x5));
                    newChildren.add(new Equation(parent2.x1, parent2.x2, parent2.x3, parent2.x4, parent2.x5));
                }
                else {
                    newChildren.add(new Equation(parent2.x1, parent2.x2, parent2.x3, parent2.x4, parent2.x5));
                    newChildren.add(new Equation(parent1.x1, parent1.x2, parent1.x3, parent1.x4, parent1.x5));
                }
            }
        }
        return newChildren;
    }

    private List<Equation> mutation(ArrayList<Equation> old) {
        return old.stream().map(i -> fitnessFunction(i) > 0.5 ? new Equation(
                isTrue(mutationChanceForGoodOffspring) ? i.generateGenome(minValue, maxValue): i.x1,
                isTrue(mutationChanceForGoodOffspring) ? i.generateGenome(minValue, maxValue): i.x2,
                isTrue(mutationChanceForGoodOffspring) ? i.generateGenome(minValue, maxValue): i.x3,
                isTrue(mutationChanceForGoodOffspring) ? i.generateGenome(minValue, maxValue): i.x4,
                isTrue(mutationChanceForGoodOffspring) ? i.generateGenome(minValue, maxValue): i.x5
        ):new Equation(
                isTrue(mutationChanceForBadOffspring) ? i.generateGenome(minValue, maxValue): i.x1,
                isTrue(mutationChanceForBadOffspring) ? i.generateGenome(minValue, maxValue): i.x2,
                isTrue(mutationChanceForBadOffspring) ? i.generateGenome(minValue, maxValue): i.x3,
                isTrue(mutationChanceForBadOffspring) ? i.generateGenome(minValue, maxValue): i.x4,
                isTrue(mutationChanceForBadOffspring) ? i.generateGenome(minValue, maxValue): i.x5
        )).collect(Collectors.toList());
    }

    private boolean isTrue(double mutationValue) {
        Random rand = new Random();
        return rand.nextDouble(1)+0 < mutationValue;
    }
}

public class Main {
    public static void main(String[] args) {
        int[][] firstEquationPowers = {{1,1,1,1,2},{0,0,0,0,1},{0,0,2,2,0},{0,1,0,2,0},{1,0,0,2,2}};
        //int[][] secondEquationPowers = {{1,0,0,1,2},{0,0,1,1,2},{0,0,0,0,1},{1,1,1,2,1},{0,1,2,1,2}};
        int[][] thirdEquationPowers = {{1,1,0,0,0},{0,0,0,0,1},{0,2,2,1,0},{2,1,1,1,0},{2,0,0,0,0}};
        new EquationSolver(firstEquationPowers, -50).start();
        //new EquationSolver(secondEquationPowers, -50).start();
        new EquationSolver(thirdEquationPowers, -14).start();
    }
    //#1
    //Best solution
    //Equation{x1=-69, x2=-4, x3=-1, x4=-4, x5=-2}
    //Iteration: 338
    //Solution: 0.0
    //--------------------
    //
    //#2
    //Error
    //--------------------
    //
    //#3
    //Best solution
    //Equation{x1=-46, x2=-1, x3=-1, x4=-1, x5=-59}
    //Iteration: 119
    //Solution: 0.0
    //--------------------
}
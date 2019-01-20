import java.io.*;
import java.util.*;

class Main {
    public static void main(String args[])  // entry point from OS
    {
        ShoemakerProblem10026v2.begin();
    }
}

class ShoemakerProblem10026v2 {

    public static void main(String args[])  // entry point from OS for testing
    {
        ShoemakerProblem10026v2.begin();
    }

    public static void begin() {
        Scanner reader = new Scanner(System.in);
        int numberOfShoemakers = reader.nextInt();
        for (int i = 0; i < numberOfShoemakers; i++) {
            int numberOfAssigments = reader.nextInt();
            Shoemakerv2 shomaker = new Shoemakerv2(numberOfAssigments);
            for (int i2 = 0; i2 < numberOfAssigments; i2++) {
                shomaker.addNext(new Assignmentv2(reader.nextInt(), reader.nextInt()));
            }
            shomaker.printWorkOrder();
            if (i<numberOfShoemakers-1){
                System.out.println("");
            }
        }
    }
}

class Shoemakerv2 {
    int toAdd = 0;
    Assignmentv2[] assignments;

    Shoemakerv2(int numberOfAssignements) {
        assignments = new Assignmentv2[numberOfAssignements];
    }

    //return was last entry ==true
    boolean addNext(Assignmentv2 assignment) {
        assignment.position = toAdd + 1;
        assignments[toAdd] = assignment;
        toAdd++;
        return (toAdd == assignments.length);
    }

    void printWorkOrder() {
        StringBuilder sb = new StringBuilder();
        Arrays.sort(assignments);
        for (int i = 0; i < assignments.length; i++) {
            if (i == assignments.length - 1) {
                sb.append(assignments[i].position);
            } else {
                sb.append(assignments[i].position).append(" ");
            }

        }
        System.out.println(sb.toString());
    }

}

class Assignmentv2 implements Comparable<Assignmentv2> {
    final double priority;
    int position;

    public Assignmentv2(int time, int fine) {
        priority = (double) fine / (double) time;
    }

    @Override
    public int compareTo(Assignmentv2 a) {
        return Double.compare(a.priority, this.priority);
    }

}

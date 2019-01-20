import java.io.*;
import java.util.*;

/*class Main {
    public static void main(String args[])  // entry point from OS
    {
        ShoemakerProblem10026.Begin();
    }
}*/

class ShoemakerProblem10026 {
    static String ReadLn(int maxLg)  // utility function to read from stdin
    {
        byte lin[] = new byte[maxLg];
        int lg = 0, car = -1;
        String line = "";

        try {
            while (lg < maxLg) {
                car = System.in.read();
                if ((car < 0) || (car == '\n')) break;
                lin[lg++] += car;
            }
        } catch (IOException e) {
            return (null);
        }

        if ((car < 0) && (lg == 0)) return (null);  // eof
        return (new String(lin, 0, lg));
    }

    public static void main(String args[])  // entry point from OS for testing
    {
        ShoemakerProblem10026.Begin();
    }

    public static void Begin() {
        String input;
        boolean isReadNumberOfAssignments = false;
        boolean isReadNumberOfShoemakers = false;
        boolean skip = false;

        int numberOfShoemaker = 0;
        int currentShoemaker=0;
        Shoemaker shomaker = null;

        //Read the inputs and generate classes.
        while ((input = ShoemakerProblem10026.ReadLn(255)) != null) {
            if (skip) {//skip empty column
                skip = false;
            } else if (!isReadNumberOfShoemakers) {//Read the number of shoemakers
                isReadNumberOfShoemakers = true;
                skip = true;
                numberOfShoemaker = Integer.parseInt(input);
            } else if (!isReadNumberOfAssignments) { //read number of assignments for the active shoemaker
                shomaker = new Shoemaker(Integer.parseInt(input));
                isReadNumberOfAssignments = true;
            } else { //Generate the Shoemakers and the Assignments
                String[] timeFine = input.split(" ");
                Assignment assignment = new Assignment(Integer.parseInt(timeFine[0]), Integer.parseInt(timeFine[1]));
                if (shomaker.addNext(assignment)) {// When the last Assignment go in the if
                    currentShoemaker++;
                    skip = true;
                    isReadNumberOfAssignments = false;
                    shomaker.printWorkOrder();


                    if (numberOfShoemaker==currentShoemaker){break;}
                    else {System.out.println("");}
                }
            }
        }
    }


}
class Shoemaker {
    int toAdd = 0;
    Assignment[] assignments;

    Shoemaker(int numberOfAssignements) {
        assignments = new Assignment[numberOfAssignements];
    }

    //return was last entry ==true
    boolean addNext(Assignment assignment) {
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

class Assignment implements Comparable<Assignment> {
    final double priority;
    int position;

    public Assignment(int time, int fine) {
        priority = (double) fine / (double) time;
    }

    @Override
    public int compareTo(Assignment a) {
        return Double.compare(a.priority, this.priority);
    }

}

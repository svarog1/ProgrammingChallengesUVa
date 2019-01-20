import java.io.*;
import java.util.*;

/*class Main {
    public static void main(String args[])  // entry point from OS
    {
        ShoemakerProblem10026v3.Begin();
    }
}*/

class ShoemakerProblem10026v3 {
    static class Assignmentv3 implements Comparable<Assignmentv3> {
        int time;
        int penalty;
        int position;

        @Override
        public int compareTo(Assignmentv3 a) {
            int v1 = time*a.penalty;
            int v2 = a.time*penalty;
            if (v1 != v2) return v1 - v2;
            else return position - a.position;

        }

    }

    static String readLn(int maxLg)  // utility function to read from stdin
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
        ShoemakerProblem10026v3.Begin();
    }

    public static void Begin() {
        String input;
        boolean isReadNumberOfAssignments = false;
        boolean isReadNumberOfShoemakers = false;
        boolean skip = false;

        int numberOfShoemaker = 0;
        int currentShoemaker=0;

        int currentAssigments=0;
        Assignmentv3[] assigments= null;

        //Read the inputs and generate classes.
        while ((input = ShoemakerProblem10026v3.readLn(255)) != null) {
            if (skip) {//skip empty column
                skip = false;
            } else if (!isReadNumberOfShoemakers) {//Read the number of shoemakers
                isReadNumberOfShoemakers = true;
                skip = true;
                numberOfShoemaker = Integer.parseInt(input);
            } else if (!isReadNumberOfAssignments) { //read number of assignments for the active shoemaker
                assigments = new Assignmentv3[Integer.parseInt(input)];
                isReadNumberOfAssignments = true;
                currentAssigments=0;
            } else { //Generate the Shoemakers and
                // the Assignments
                String[] timeFine = input.split(" ");
                assigments[currentAssigments]=new Assignmentv3();
                assigments[currentAssigments].position=currentAssigments+1;
                assigments[currentAssigments].time=Integer.parseInt(timeFine[0]);
                assigments[currentAssigments].penalty=Integer.parseInt(timeFine[1]);
                currentAssigments++;
                if (currentAssigments==assigments.length) {// When the last Assignment go in the if
                    currentShoemaker++;
                    skip = true;
                    isReadNumberOfAssignments = false;
                    printWorkOrder(assigments);
                    if (numberOfShoemaker==currentShoemaker){break;}
                    else {System.out.println("");}
                }
            }
        }
    }

    public static void printWorkOrder(Assignmentv3[] assignments){
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


import java.io.IOException;

/**
 * UVa 10032 - Tug of War
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&page=show_problem&category=0&problem=973
 *
 *
 * Ansatz: I used the same Idea as in the Knapsack problem.
 * https://en.wikipedia.org/wiki/Knapsack_problem
 *
 * @author Santino De-Sassi
 * @version 2020.01.12
 */


@SuppressWarnings("WrongPackageStatement")
class Main {
    public static void main(String args[])  // entry point from OS
    {
        TugOfWar10032 euclidProblem10104 = new TugOfWar10032();
        euclidProblem10104.start();
    }
}

class TugOfWar10032 {

    Integer[] peoples;
    int groupMaxWight = 0;
    boolean[][][] optimisationArray; //used for optimisation
    boolean isUsed=false; //used for optimisation
    boolean foundResult = false; //used for optimisation

    public TugOfWar10032(){
        //It is faster to init the optimisationArray with ist possible max size.
        //Then it is to create each time a new Array.
        optimisationArray=new boolean[101][51][22501];
    }

    //Reads each line.
    String readLn(int maxLg)  // utility function to read from stdin
    {
        byte lin[] = new byte[maxLg];
        int lg = 0, car = -1;

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

    /**
     * Generate reads the data and starts the calculation for each group.
     * Simplification: if it has an odd number of elements, 1 element with the value 0 is added.
     */
    public void start() {
        String input;
        int numberOfElements = 0;
        boolean isBlank = false;
        int numberOfCasses = 0;
        peoples = null;
        while ((input = readLn(255)) != null) {
            if (isBlank) {
                isBlank = false;
            } else if (numberOfCasses == 0) {
                numberOfCasses = Integer.parseInt(input);
                isBlank = true;
            } else if (numberOfElements == 0) {
                numberOfElements = Integer.parseInt(input);
                if (numberOfElements % 2 == 1) {
                    peoples = new Integer[numberOfElements + 1];
                    peoples[numberOfElements] = 0;
                } else {
                    peoples = new Integer[numberOfElements];
                }
            } else {
                peoples[numberOfElements - 1] = Integer.parseInt(input);
                numberOfElements--;
                if (numberOfElements == 0) {

                    this.peoples = peoples;
                    calc();
                    numberOfCasses--;
                    if (numberOfCasses == 0) {
                        return;
                    } else {
                       System.out.println("");
                    }

                    isBlank = true;
                }

            }
        }

    }


    /**
     * Start the calculation. Prepares the needed values for a Case and starts the recursion.
     */
    public void calc() {
        foundResult = false;
        int peopleInGroup = peoples.length / 2;
        int totalWight = 0;
        for (int i = 0; i < peoples.length; i++) {
            totalWight += peoples[i];
        }
        groupMaxWight = totalWight / 2;

        //It is faster to set all needed values on false then it is to create each time a new array. (optimisation)
        if (isUsed){
            for (int i = 0; i < peoples.length+1 ; i++) {
                for (int j = 0; j < peopleInGroup+1 ; j++) {
                    for (int k = 0; k < groupMaxWight + 1; k++) {
                        optimisationArray[i][j][k]=false;
                    }
                }
            }
        }
        isUsed=true;
        int result = recursion(peoples.length , peopleInGroup, 0);
         System.out.println(result + " " + (totalWight - result));



    }


    /**
     * Calculate with recursion the wight of the lighter group.
     * @param currentPeople Selects the current person in the peoples array.
     * @param missingPeople Contains how many peoples can still be added to this group.
     * @param currentWight The wight of the current group.
     * @return valid: wen a valid answer is found return the wight of the group. Invalid: return 0
     */
    public int recursion(int currentPeople, int missingPeople, int currentWight) {
        //Cancels this path wen: already the best result is found or the group is to heavy or this case has been already calculated.
        if (foundResult||currentWight > groupMaxWight ||optimisationArray[currentPeople ][missingPeople][currentWight] ) {
            return 0;
        //When the group is full. Could be a valid result.
        }else if(missingPeople==0){
            //Is it the best possible solution.
            if (currentWight==groupMaxWight){
                foundResult = true;
                return groupMaxWight;
            } else {
                optimisationArray[currentPeople ][missingPeople][currentWight] = true;
                return currentWight;
            }
        // there can be no possible solution when there are not enough people left to fill the group.
        }else if(currentPeople < missingPeople){
            optimisationArray[currentPeople ][missingPeople][currentWight] = true;
            return 0;
        // Goes tough every Possible solution. Always selects the bigger possible solution.
        }  else {
            int result2 = recursion(currentPeople - 1, missingPeople - 1,
                    currentWight + peoples[currentPeople-1]);
            int result1 = recursion(currentPeople - 1, missingPeople, currentWight);
            optimisationArray[currentPeople ][missingPeople][currentWight] = true;

            if (result1 > result2) {
                return result1;
            } else {
                return result2;
            }
        }
    }
}

import java.io.IOException;

/**
 * UVa 10032 - Tug of War
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&page=show_problem&category=0&problem=973
 *
 *
 * Approach: I used the same Idea as in the Knapsack problem.
 * https://en.wikipedia.org/wiki/Knapsack_problem
 *
 * Idee des Programmes.
 * Mann kennt das maximale Gewicht der Teilgruppe mit dem geringeren Gewicht. Mann kennt auch die maximale Anzahl
 * Element in einer Teilgruppe. Nun kann man alle varianten der teilgruppe mit dem kleineren Gewicht ausrechnen welche
 * die benötigten Anzahl Elemente enthalten. Von diesen varianten wird dann das grösste Gewicht genommen und ausgegeben.
 * Die zweite gruppe hat immer das Totale Gewicht der Personen minus das Gewicht der kleineren Gruppen.
 *
 * Vereinfachungen:
 * Bei einer ungeraden Zahl an Personen wird ein Dummy Person mit Gewicht 0 hinzugefügt. Somit haben immer beide
 * Gruppen gleich viele Personen.
 *
 * Optimierungen:
 *
 * Der optimisationArray: Damit nicht alle Varianten berechnet werden müssen werden in diesem Array angegeben ob dieser
 * Path bereits berechnet wurde. Dieser Array enthält für jede Anzahl hinzugefügten Personen mit einem Gewicht ob dieses
 * bereits berechnet wurde oder nicht. Wenn ja kann hier abgebrochen werden da kein andere variante berechnet werden
 * kann.
 * Der optimisationArray: Da die Allokation des Speicherplatzes des Arrays langsam ist wird dieser mit der maximal
 * möglichen grösse einmal initialisiert. Und bei jeder Berechnung werden die Benötigten Elemente, mit einem for, für
 * die Berechnung wieder auf false gesetzt. (Dies wurde getestet und ist wirklich schneller).
 *
 * Die foundResult variable: Wenn ein Resultat gefunden wurde, welches das maximalen Gewicht der
 * Teilgruppe gefunden wurde kann die Rekursion abgebrochen werde, da es nicht möglich ist einen besseren variante zu finden.
 *
 * @author Santino De-Sassi
 * @version 2020.01.13
 */


@SuppressWarnings("WrongPackageStatement")
/*class Main {
    public static void main(String args[])  // entry point from OS
    {
        TugOfWar10032 tugOfWar10032 = new TugOfWar10032();
        tugOfWar10032.start();
    }
}*/

class TugOfWar10032 {
    Integer[] peoples; //All people.
    int groupMaxWight = 0;
    boolean[][] optimisationArray; //used for optimisation
    boolean isUsed=false; //used for optimisation
    boolean foundResult = false; //used for optimisation

    public TugOfWar10032(){
        //It is faster to init the optimisationArray with ist possible max size.
        //Then it is to create each time a new Array.
        //50 mac number of people in 1 group.+1 for recursion simplification/optimisation
        //22501 max wight in a group. +1 for recursion simplification/optimisation
        optimisationArray=new boolean[51][22501];
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
                    //calc();
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
        foundResult = false; //is set to true when te perfect result is found (optimisation)
        int peopleInGroup = peoples.length / 2; //Number of peoples in one group.
        int totalWight = 0; //Wight of all persons in this case.
        for (int i = 0; i < peoples.length; i++) {
            totalWight += peoples[i];
        }
        groupMaxWight = totalWight / 2; //max wight of the smaler group.

        //It is faster to set all needed values on false then it is to create each time a new array. (optimisation)
        if (isUsed){ //is not needed the first time because all values are initialised with false.

                for (int j = 0; j < peopleInGroup+1 ; j++) {
                    for (int k = 0; k < groupMaxWight + 1; k++) {
                        optimisationArray[j][k]=false;
                    }
                }

        }
        isUsed=true;

        //Starts the calculation.
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
        if (foundResult||currentWight > groupMaxWight ||optimisationArray[missingPeople][currentWight] ) {
            return 0;
        //When the group is full. Could be a valid result.
        }else if(missingPeople==0){
            //Is it the best possible solution.
            if (currentWight==groupMaxWight){
                foundResult = true;
                return groupMaxWight;
            } else {
                optimisationArray[missingPeople][currentWight] = true;
                return currentWight;
            }
        // there can be no possible solution when there are not enough people left to fill the group.
        }else if(currentPeople < missingPeople){
            optimisationArray[missingPeople][currentWight] = true;
            return 0;
        // Goes tough every Possible solution. Always selects the bigger possible solution.
        }  else {
            int result2 = recursion(currentPeople - 1, missingPeople - 1,
                    currentWight + peoples[currentPeople-1]);
            int result1 = recursion(currentPeople - 1, missingPeople, currentWight);
            optimisationArray[missingPeople][currentWight] = true;

            if (result1 > result2) {
                return result1;
            } else {
                return result2;
            }
        }
    }
}

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;


@SuppressWarnings("WrongPackageStatement")
class Main {
    public static void main(String args[])  // entry point from OS
    {
        TugOfWar10032 euclidProblem10104 = new TugOfWar10032();
        euclidProblem10104.start();
    }
}

class TugOfWar10032 {

    String readLn(int maxLg)  // utility function to read from stdin
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

    Integer[] peoples;
    int groupMaxWight = 0;
    boolean[][][] optimasationArray;
    boolean isUsed=false;
    boolean foundResult = false;

    public TugOfWar10032(){
        optimasationArray=new boolean[101][51][22501];
    }

    public void calc() {

        foundResult = false;
        int peopleInGroup = peoples.length / 2;
        int TotalWight = 0;
        for (int i = 0; i < peoples.length; i++) {
            TotalWight += peoples[i];
        }
        groupMaxWight = TotalWight / 2;
        if (isUsed){
            for (int i = 0; i < peoples.length+1 ; i++) {
                for (int j = 0; j < peopleInGroup+1 ; j++) {
                    for (int k = 0; k < groupMaxWight + 1; k++) {
                            optimasationArray[i][j][k]=false;
                    }
                }
            }
        }
        isUsed=true;
        int resul = recursion(peoples.length , peopleInGroup, 0);
         System.out.println(resul + " " + (TotalWight - resul));



    }


    public int recursion(int currentPeople, int missingPeople, int currentWight) {
        if (foundResult||currentWight > groupMaxWight ||optimasationArray[currentPeople ][missingPeople][currentWight] ) {
            return 0;
        }else if(missingPeople==0){
            if (currentWight==groupMaxWight){
                foundResult = true;
                return groupMaxWight;
            } else { //case currentWight<groupMaxWight
                optimasationArray[currentPeople ][missingPeople][currentWight] = true;
                return currentWight;
            }
        }else if(currentPeople == 0){
            optimasationArray[currentPeople ][missingPeople][currentWight] = true;
            return 0;
        }  else {
            int result2 = recursion(currentPeople - 1, missingPeople - 1,
                    currentWight + peoples[currentPeople-1]);
            int result1 = recursion(currentPeople - 1, missingPeople, currentWight);
            optimasationArray[currentPeople ][missingPeople][currentWight] = true;

            if (result1 > result2) {
                return result1;
            } else {
                return result2;
            }
        }
    }
}

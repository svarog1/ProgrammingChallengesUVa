import java.io.IOException;
import java.util.*;

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
        ArrayList<Integer> peoples = null;
        while ((input = readLn(255)) != null) {
            if (isBlank) {
                isBlank = false;
            } else if (numberOfCasses == 0) {
                numberOfCasses = Integer.parseInt(input);
                isBlank = true;
            } else if (numberOfElements == 0) {
                numberOfElements = Integer.parseInt(input);
                peoples = new ArrayList<>();
            } else {
                peoples.add(Integer.parseInt(input));
                numberOfElements--;
                if (numberOfElements == 0) {
                    this.peoples = peoples;
                    calc();
                    numberOfCasses--;
                    if (numberOfCasses == 0) {
                        return;
                    }
                    isBlank = true;
                }

            }
        }
    }

    public void calc(){
        if (peoples.size()%2==1){
            peoples.add(0);
        }
        Collections.sort(peoples, Collections.reverseOrder());
        int peopleInGroup=peoples.size()/2;
        int groupMaxhwight=0;
        for (int i = 0; i < peoples.size(); i++) {
            groupMaxhwight+=peoples.get(i);
        }
        groupMaxhwight+=groupMaxhwight%2;
        groupMaxhwight=groupMaxhwight/2;
    }

    public int recursion(int currentPeople,int missingPeople,int groupMaxWight){
            if (currentPeople==-1||missingPeople==0){

            }
    }


    public void calc8() {
        Collections.sort(peoples, Collections.reverseOrder());
        ArrayList<Integer> group1 = new ArrayList<>();
        int group1Summe = 0;
        ArrayList<Integer> group2 = new ArrayList<>();
        int group2Summe = 0;

        int back = 0;
        int front = 0;
        while (-back + front < peoples.size()) {
            //Big
            if (-back + front + 2 <= peoples.size()) {
                if (group1Summe > group2Summe) {
                    group1.add(peoples.get(front + 1));
                    group2.add(peoples.get(front));
                    group1Summe += peoples.get(front + 1);
                    group2Summe += peoples.get(front );
                } else {
                    group2.add(peoples.get(front + 1));
                    group1.add(peoples.get(front));
                    group2Summe += peoples.get(front + 1);
                    group1Summe += peoples.get(front);
                }
                front += 2;
            }

            if (-back + front + 2 <= peoples.size()) {
                if (group1Summe > group2Summe) {
                    group1.add(peoples.get(peoples.size() - 1 + back));
                    group2.add(peoples.get(peoples.size() - 1 + back - 1));
                    group1Summe += peoples.get(peoples.size() - 1 + back);
                    group2Summe += peoples.get(peoples.size() - 1 + back - 1);
                } else {
                    group2.add(peoples.get(peoples.size() - 1 + back));
                    group1.add(peoples.get(peoples.size() - 1 + back - 1));
                    group2Summe += peoples.get(peoples.size() - 1 + back);
                    group1Summe += peoples.get(peoples.size() - 1 + back - 1);
                }
                back -= 2;
            } else if (-back + front + 1 <= peoples.size()) {
                if (group1Summe>group2Summe){
                    group2.add(peoples.get(front));
                    group2Summe += peoples.get(front );
                } else {
                    group1.add(peoples.get(front));
                    group1Summe += peoples.get(front );
                }
                front++;
            }
        }

        /*if (group1.size()>group2.size()&&group1Summe>group2Summe){
            int smales=500;
            for (int val:
                 group1) {
                if (val<smales){
                    smales=val;
                }
            }
            group1Summe-=smales;
            group2Summe+=smales;
        }else if (group2.size()>group1.size()&&group2Summe>group1Summe){
            int smales=500;
            for (int val:
                    group2) {
                if (val<smales){
                    smales=val;
                }
            }
            group2Summe-=smales;
            group1Summe+=smales;
        } */

        if (group1.size()>group2.size()){
            group1.add(0);
        } else if(group1.size()<group2.size()){
            group1.add(0);
        }



        if (group1Summe>group2Summe){
            System.out.println(group2Summe+" "+group1Summe);
        }else{
            System.out.println(group1Summe+" "+group2Summe);
        }

        System.out.println("");

    }

    public


    ArrayList<Integer> peoples;
    Integer[][] optimasitionArray;

    public void calc6() {
        Collections.sort(peoples);
        optimasitionArray = new Integer[peoples.size() + 1][peoples.size() / 2 + 1];
        int goalGroupWight = 0;
        for (int value :
                peoples) {
            goalGroupWight += value;
        }
        int groupWightAverage = goalGroupWight / 2 ;

        int group1 = recursifCAlc(peoples.size(), peoples.size() / 2, groupWightAverage);
        int group2 = goalGroupWight - group1;
        if (group1 > group2) {
            System.out.println(group2 + " " + group1);
        } else {
            System.out.println(group1 + " " + group2);
        }
        System.out.println("");
    }

    class Answer {
        boolean isOk;
        int groupWight;

        public Answer(boolean isOk, int groupWight) {
            this.isOk = isOk;
            this.groupWight = groupWight;
        }
    }

    public int recursifCAlc(int arrayPosition, int numberOfAddedElements, int goalGroupWight) {
        if (arrayPosition == 0 || numberOfAddedElements == 0 || goalGroupWight == 0) {
            if (numberOfAddedElements == 0) {
                return 0;
            } else {
                return -1;
            }

        } else if (optimasitionArray[arrayPosition][numberOfAddedElements] != null) {
            return optimasitionArray[arrayPosition][numberOfAddedElements];

        } else if (goalGroupWight < peoples.get(arrayPosition - 1)) {
            return recursifCAlc(arrayPosition - 1, numberOfAddedElements, goalGroupWight);
        } else {
            int temp1 = recursifCAlc(arrayPosition - 1, numberOfAddedElements, goalGroupWight);
            int temp2 = recursifCAlc(arrayPosition - 1, numberOfAddedElements - 1,
                    goalGroupWight - peoples.get(arrayPosition - 1));//+peoples.get(arrayPosition-1);
            if (temp1 == -1 && temp2 == -1) {
                return -1;
            } else if (temp2 != -1) {
                temp2 += peoples.get(arrayPosition - 1);
            }
            if (temp1 > temp2) {
                optimasitionArray[arrayPosition][numberOfAddedElements] = temp1;
                return temp1;
            } else {
                optimasitionArray[arrayPosition][numberOfAddedElements] = temp2;
                return temp2;
            }
        }
    }


    public void calc3(ArrayList<Integer> peoples) {


        //bot sides equal sice.
        if (false) {
            Collections.sort(peoples);
            ArrayList<Integer> group1 = new ArrayList<>();
            int group1Wight = 0;
            ArrayList<Integer> group2 = new ArrayList<>();
            int group2Wight = 0;
            //Split peoples in 2 groups
            for (int i = 0; i < peoples.size(); i += 2) {
                group2.add(peoples.get(i));
                group2Wight += peoples.get(i);
                if (peoples.size() > i + 1) {
                    group1.add(peoples.get(i + 1));
                    group1Wight += peoples.get(i + 1);
                }
            }


            doBest(group1, group1Wight, group2, group2Wight);
        } else {
            Collections.sort(peoples, Collections.reverseOrder());
            ArrayList<Integer> group1 = new ArrayList<>();
            int group1Wight = 0;
            ArrayList<Integer> group2 = new ArrayList<>();
            int group2Wight = 0;
            int addedLastEntry = 0;
            for (int i = 0; i < peoples.size() - addedLastEntry; ) {
                if (group1Wight > group2Wight) {
                    if (group2.size() - group1.size() == 1) {
                        group1.add(peoples.get(peoples.size() - 1 - addedLastEntry));
                        group1Wight += peoples.get(peoples.size() - 1 - addedLastEntry);
                        addedLastEntry++;
                    } else {
                        group2Wight += peoples.get(i);
                        group2.add(peoples.get(i));
                        i++;
                    }

                } else {
                    if (group1.size() - group2.size() == 1) {
                        group2.add(peoples.get(peoples.size() - 1 - addedLastEntry));
                        group2Wight += peoples.get(peoples.size() - 1 - addedLastEntry);
                        addedLastEntry++;
                    } else {
                        group1Wight += peoples.get(i);
                        group1.add(peoples.get(i));
                        i++;
                    }
                }
            }

            doBest(group1, group1Wight, group2, group2Wight);

        }


    }

    private void doBest(ArrayList<Integer> group1, int group1Wight, ArrayList<Integer> group2, int group2Wight) {
        boolean foundNewBest;
        int groupDiv = (group1Wight - group2Wight);
        do {
            foundNewBest = false;

            for (int i = 0; i < group1.size(); i++) {
                for (int j = 0; j < group2.size(); j++) {
                    int temp2 = Math.abs(group2.get(j) - group1.get(i));
                    int temp3 = (group2.get(j) - group1.get(i));
                    boolean temp = false;
                    int iTemp = group1.get(i);
                    int jTemp = group2.get(j);
                    boolean temp56 = iTemp != jTemp;
                    boolean temp5 = group1.get(i) != group2.get(j);
                    if (group1.get(i) < group2.get(j)) {
                        if (group1Wight < group2Wight) {
                            temp = true;
                        }
                    } else {
                        if (group2Wight < group1Wight) {
                            temp = true;
                        }
                    }
                    if (groupDiv != 0 && temp && temp56 && Math.abs(group2.get(j) - group1.get(i)) <= Math.abs(groupDiv)) {
                        group1Wight -= group1.get(i);
                        group2Wight -= group2.get(j);
                        group1Wight += group2.get(j);
                        group2Wight += group1.get(i);
                        groupDiv = (group1Wight - group2Wight);
                        int tempValue = group2.get(j);
                        group2.set(j, group1.get(i));
                        group1.set(i, tempValue);
                        foundNewBest = true;
                    }
                }
            }
        } while (foundNewBest);

        print(group1Wight, group2Wight);
    }

    private void print(int group1Wight, int group2Wight) {
        if (group1Wight > group2Wight) {
            System.out.println(group2Wight + " " + group1Wight);
        } else {
            System.out.println(group1Wight + " " + group2Wight);
        }
        System.out.println("");
    }

    public void calc2(ArrayList<Integer> peoples) {
        Collections.sort(peoples, Collections.reverseOrder());
        Group g = addValueGroup1(peoples, 0, 0);
        System.out.println(g.group1 + " " + g.group2);


    }

    public Group addValueGroup1(ArrayList<Integer> peoples, int group1, int group2) {
        Group group = null;
        for (int i = 0; i < peoples.size(); i++) {
            ArrayList newList = new ArrayList<Integer>(peoples);
            newList.remove(i);
            Group temp = addValueGroup2(newList, group1 + peoples.get(i), group2);
            if (group == null) {
                group = temp;
            } else if (group.div > temp.div) {
                group = temp;
            }
        }
        if (peoples.size() == 0) {
            if (group1 > group2) {
                return new Group(group1, group2);
            } else {
                return new Group(group2, group1);
            }
        }
        return group;
    }

    public Group addValueGroup2(ArrayList<Integer> peoples, int group1, int group2) {
        Group group = null;
        for (int i = 0; i < peoples.size(); i++) {

            ArrayList newList = new ArrayList<Integer>(peoples);
            newList.remove(i);
            Group temp = addValueGroup1(newList, group1, group2 + peoples.get(i));
            if (temp == null) {

            } else if (group == null) {
                group = temp;
            } else if (group.div > temp.div) {
                group = temp;
            }
        }
        if (peoples.size() == 0) {
            if (group1 > group2) {
                return new Group(group1, group2);
            } else {
                return new Group(group2, group1);
            }
        }
        return group;
    }

    class Group {
        public Group(int group1, int group2) {
            this.group1 = group1;
            this.group2 = group2;
            this.div = group1 - group2;
        }

        public int group1 = 0;
        public int group2 = 0;
        public int div = 0;
    }


}

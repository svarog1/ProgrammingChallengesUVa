import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

@SuppressWarnings("WrongPackageStatement")
class Main {
    public static void main(String args[])  // entry point from OS
    {
        TheGrandDinner10249 theGrandDinner10249 = new TheGrandDinner10249();
        theGrandDinner10249.start();
    }
}

class TheGrandDinner10249 {
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

    public void start() {
        String input = "";
        int numberOfTeams = 0;
        int numberOfTables = 0;
        boolean isNewCase = true;
        boolean isTeamNumbers = false;
        boolean isTabelNumbers = false;
        Teams[] teams = null;
        Table[] tabels = null;
        while ((input = readLn(255)) != null) {
            if (isNewCase) {
                String[] sizes = input.split(" ");
                numberOfTeams = Integer.parseInt(sizes[0]);
                numberOfTables = Integer.parseInt(sizes[1]);
                if (numberOfTeams==0&&numberOfTables==0){
                    return;
                }
                isNewCase = false;
                isTeamNumbers = true;
            } else if (isTeamNumbers) {
                String[] sTeams = input.split(" ");
                teams = new Teams[numberOfTeams];
                for (int i = 0; i < numberOfTeams; i++) {
                    teams[i] =new Teams(i+1,Integer.parseInt(sTeams[i]));
                }
                isTeamNumbers = false;
                isTabelNumbers = true;
            } else if (isTabelNumbers) {
                String[] sTables = input.split(" ");
                tabels = new Table[numberOfTables];
                for (int i = 0; i < numberOfTables; i++) {
                    tabels[i] = new Table(Integer.parseInt(sTables[i]),i+1);
                }
                if (!calc(teams,tabels)){
                    System.out.println("0");
                }

                isTabelNumbers = false;
                isNewCase = true;
            }
        }


    }

    /**
     * The calculation of the problem
     * @param teams
     * @param tables
     * @return
     */
    public boolean calc (Teams[]teams, Table[]tables){


        for (int i = 0; i < teams.length; i++) {
            Arrays.sort(tables,Collections.reverseOrder());
            int skiedTables=0;//Is used
            for (int j = 0; j < teams[i].tableIDs.length+skiedTables; j++) {
                if (tables.length>j&&tables[j].leftChairs>0){
                    tables[j].leftChairs--;
                    teams[i].tableIDs[j-skiedTables]=tables[j].tableID;
                    teams[i].seatedMembers--;
                }else if(j>=tables.length){
                    return false;
                }
                else  {
                    skiedTables++;
                }
            }
            teams[i].seatedMembers=0;

        }

        System.out.println("1");
        String output="";
        for (int i = 0; i < teams.length; i++) {
            for (int j = 0; j < teams[i].tableIDs.length; j++) {
                if (teams[i].tableIDs.length==j+1){
                    output+=teams[i].tableIDs[j];
                }else{
                    output+=teams[i].tableIDs[j]+" ";
                }

            }
            System.out.println(output);
            output="";
        }
        return  true;


    }

    static  class Table implements Comparable<Table>{
        Integer leftChairs;
        int tableID;
        Table(Integer pLeftSeatingSpace,int tableId){
            leftChairs=pLeftSeatingSpace;
            this.tableID=tableId;


        }

        @Override
        public int compareTo(Table o) {
            if (leftChairs==o.leftChairs){
                return Integer.compare(o.tableID,tableID);
            }else{
                return Integer.compare(leftChairs,o.leftChairs);
            }

        }
    }

    static  class Teams{
        int teamNumber;
        int seatedMembers;
        Integer[] tableIDs;
        Teams(int teamNumber,int membersCount){
            this.teamNumber=teamNumber;
            tableIDs=new Integer[membersCount];
            seatedMembers=membersCount;

        }
    }

}


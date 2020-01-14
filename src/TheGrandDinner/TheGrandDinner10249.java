import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

@SuppressWarnings("WrongPackageStatement")
/**
 * 10249 - The Grand Dinner
 *https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&page=show_problem&category=0&problem=1190&mosmsg=Submission+received+with+ID+24418988
 *
 * Idee des Programmes
 * Für diese Challenge habe ich einen Greedy-Algorithmus verwendet. Die Teams werden der übergebenen
 * Reihenfolge durchgegangen und auf den Tisch mit den meisten Freien Stühlen verteilt.
 * Hierzu geht man die Liste der Teams der Reinach durch. Bei jedem Team werden als erstes die Tische
 * in Absteigender reinfolge sortiert. Als nächstes werden die Tische durchgegangen und ein Team
 * Mitglied zu jedem Tisch hinzugefügt welcher noch Freie Stühle hat. Wenn es noch Mittglieder in einem Tam hat
 * aber keine Freie Tische ist dieser Fall nicht lösbar.
 *
 * @author Santino De-Sassi
 * @version 2020.01.14
 */
/*class Main {
    public static void main(String args[])  // entry point from OS
    {
        TheGrandDinner10249 theGrandDinner10249 = new TheGrandDinner10249();
        theGrandDinner10249.start();
    }
}*/

class TheGrandDinner10249 {
    /**
     * Read thi input lins
     * @param maxLg
     * @return
     */
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
     * Peepers the data for the calculation
     */
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
                if (numberOfTeams == 0 && numberOfTables == 0) {
                    return;
                }
                isNewCase = false;
                isTeamNumbers = true;
            } else if (isTeamNumbers) {
                String[] sTeams = input.split(" ");
                teams = new Teams[numberOfTeams];
                for (int i = 0; i < numberOfTeams; i++) {
                    teams[i] = new Teams( Integer.parseInt(sTeams[i]));
                }
                isTeamNumbers = false;
                isTabelNumbers = true;
            } else if (isTabelNumbers) {
                String[] sTables = input.split(" ");
                tabels = new Table[numberOfTables];
                for (int i = 0; i < numberOfTables; i++) {
                    tabels[i] = new Table(Integer.parseInt(sTables[i]), i + 1);
                }
                if (!calc(teams, tabels)) {
                    System.out.println("0");
                }

                isTabelNumbers = false;
                isNewCase = true;
            }
        }
    }

    /**
     * The calculation of the problem
     *
     * @param teams
     * @param tables
     * @return
     */
    public boolean calc(Teams[] teams, Table[] tables) {
        //Start of the calculation
        //does each team after another
        for (int i = 0; i < teams.length; i++) {
            Arrays.sort(tables, Collections.reverseOrder());
            int skiedTables = 0;//Is used wen a table needs to be skipped.
            //Adds each team member to a free table.
            for (int j = 0; j < teams[i].tableIDs.length + skiedTables; j++) {
                //Adds team members to a table when there ar still tables with free chairs
                if (tables.length > j && tables[j].leftChairs > 0) {
                    tables[j].leftChairs--;
                    teams[i].tableIDs[j - skiedTables] = tables[j].tableID;
                //There are no mor tables to ad the members of this teams.
                } else if (j >= tables.length) {
                    return false;
                //When the current table has no free chairs. Skip this table.
                } else {
                    skiedTables++;
                }
            }
        }
        //End of the calculation

        //Print the results
        System.out.println("1");
        String output = "";
        for (int i = 0; i < teams.length; i++) {
            for (int j = 0; j < teams[i].tableIDs.length; j++) {
                if (teams[i].tableIDs.length == j + 1) {
                    output += teams[i].tableIDs[j];
                } else {
                    output += teams[i].tableIDs[j] + " ";
                }

            }
            System.out.println(output);
            output = "";
        }
        return true;


    }

    /**
     * The Tabels
     */
    static class Table implements Comparable<Table> {
        // how many chairs are still free
        int leftChairs;
        //Needed for the output.
        int tableID;

        Table(Integer pLeftSeatingSpace, int tableId) {
            leftChairs = pLeftSeatingSpace;
            this.tableID = tableId;


        }

        @Override
        public int compareTo(Table o) {
            return Integer.compare(leftChairs, o.leftChairs);
        }
    }

    static class Teams {
        //Seating table of each member
        Integer[] tableIDs;
        Teams(int membersCount) {
            tableIDs = new Integer[membersCount];
        }
    }

}


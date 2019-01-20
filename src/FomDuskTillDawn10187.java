//Lösung Ansatz:
//Vlad kann jeden Tag 12 Stunden reisen. Somit sollen alle Städte die er innerhalb von 12 h erreicht abgespeichert
// und als neue Ausgangspunkte für den nächsten Tag verwendet werden. Die Stadt soll nur bei ersten möglichen Ankunft in die Liste gespeichert werden.
//Verwendeter Algorithmus BFS
//Abspeichern der Knoten und Kanten wurde das Adjiazenzlisten Prinzip verwendet.



import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

class Main {
    public static void main(String args[])  // entry point from OS
    {
        FomDuskTillDawn10187.start();
    }
}

class FomDuskTillDawn10187 {


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
        FomDuskTillDawn10187.start();
    }

    public static void start() {
        HashMap<String, City> cites = new HashMap<>(100); //All cities on a map
        int testcase = -1;
        int currentTestcase = 0;
        int connections = -1;
        int currentConnection = 0;
        String input = "";
        while ((input = FomDuskTillDawn10187.ReadLn(255)) != null) {
            if (testcase == -1) { //Saves how many test case there are.
                testcase = Integer.parseInt(input);
            } else if (connections == -1) { //Saves how many connections there are for a test case.
                connections = Integer.parseInt(input);
            } else if (currentConnection < connections) {
                currentConnection++;
                String[] inputs = input.split(" "); //0=departCity 1=targetCity 2=departTime 3=duration of the drive
                int departTime = getSimpleTime(inputs[2]);
                if (departTime == -1) {
                    continue;
                }
                int arriveTime = departTime + Integer.parseInt(inputs[3]); //Converted to arrive time for simplification.
                if (arriveTime > 12) { //Depart Time need to be always smaller else he would drive through the day.
                    continue;
                }

                //Create new city.
                if (!cites.containsKey(inputs[0])) {
                    cites.put(inputs[0], new City(inputs[0]));
                }

                //Create new city for the target.
                City targetCity;
                if ((targetCity = cites.get(inputs[1])) == null) {
                    targetCity = new City(inputs[1]);
                    cites.put(inputs[1], targetCity);
                }

                //add the connection to the city. Also adds the target City (reference) to the connection.
                cites.get(inputs[0]).connections.add(new Connection(targetCity, departTime, arriveTime));
            } else {
                //Searches the city.
                String[] inputs = input.split(" ");
                City startCity = cites.get(inputs[0]);
                if (inputs[0].equals(inputs[1])) {
                    print(currentTestcase, 0);
                } else if (startCity == null) {
                    print(currentTestcase, -1);
                } else {
                    print(currentTestcase, findWay(cites.get(inputs[0]), cites.get(inputs[1])));
                }

                currentTestcase++;
                currentConnection = 0;
                connections = -1;
                cites.clear();

            }
        }
    }

    public static void print(int testCase, int days) {
        System.out.println("Test Case " + (testCase + 1) + ".");
        if (days >= 0) {
            System.out.println("Vladimir needs " + days + " litre(s) of blood.");
        } else {
            System.out.println("There is no route Vladimir can take.");
        }
    }

    //Started to search for a possible way.
    //-1 no way found.
    public static int findWay(City startCity, City targetCity) {
        //Try one day travel after another.
        Queue<City> calcCities1 = new LinkedList<>();
        Queue<City> calcCities2 = new LinkedList<>();
        int days = 0;
        //Add first elements to queue
        for (Connection connection : startCity.connections) {
            ((LinkedList<City>) calcCities1).add(startCity);
        }
        boolean isWay = false;
        City departureCity;
        //goes through 2 list. every list has entries from the same day. Simplification for day counter.
        while (calcCities1.size() > 0) {
            while (!(isWay || (departureCity = calcCities1.poll()) == null)) {
                if (calcPossibleWays(departureCity, calcCities2, targetCity, 0)) {
                    return days;
                }
            }
            days++;
            while (!(isWay || (departureCity = calcCities2.poll()) == null)) {
                if (calcPossibleWays(departureCity, calcCities1, targetCity, 0)) {
                    return days;
                }
            }
            days++;
        }
        return -1;
    }

    //Trays all ways within a day. Adds the new found cities to calcCities 1 or 2. Increases the traveled day counter.
    public static boolean calcPossibleWays(City departureCity, Queue<City> calcCities, City targetCity, int travelTimeToday) {
        if (departureCity == targetCity) {
            return true;
        }
        boolean isOneWayNotPossible = false; //Is needed for: Ways which are at the moment not possible.
        for (Connection connection : departureCity.connections) {
            if (travelTimeToday <= connection.depart) { //This connection can be made before 6:00
                if (calcPossibleWays(connection.targetCity, calcCities, targetCity, connection.arrive)) {
                    return true;
                }
            } else { //This connection can't be reached before 6:00. Need to try this next day.
                isOneWayNotPossible = true; //City should just be added once.
            }
        }

        if (isOneWayNotPossible && (!departureCity.added)) {// no duplicates.
            departureCity.added = true;
            calcCities.add(departureCity);
        }
        return false;

    }


    //Vlad can drive only from 1800 to 0600 so convert to 1800=0000 0600=1200
    //Converts this times to simpler times.
    public static int getSimpleTime(String time) {
        int myInt = Integer.parseInt(time) % 24;
        switch (myInt) {
            case 18:
                return 0;
            case 19:
                return 1;
            case 20:
                return 2;
            case 21:
                return 3;
            case 22:
                return 4;
            case 23:
                return 5;
            case 24:
            case 0:
                return 6;
            case 1:
                return 7;
            case 2:
                return 8;
            case 3:
                return 9;
            case 4:
                return 10;
            case 5:
                return 11;
            default:
                return -1;
        }
    }


}


class Connection {
    public City targetCity;
    int depart, arrive;

    public Connection(City targetCity, int depart, int arrive) {
        this.depart = depart;
        this.arrive = arrive;
        this.targetCity = targetCity;
    }
}

class City {
    public String cityName;
    public boolean added = false;
    public LinkedList<Connection> connections = new LinkedList<>();

    public City(String cityName) {
        this.cityName = cityName;
    }
}







import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

/*class Main {
    public static void main(String args[])  // entry point from OS
    {
        FomDuskTillDawn10187.start();
    }
}*/

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
        HashMap<String, City> citys = new HashMap<>(100); //All citys on a map
        DepartureCity found; // Wen a result is found it is saved her.
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
                if (!citys.containsKey(inputs[0])) {
                    citys.put(inputs[0], new City(inputs[0]));
                }

                //Create new city for the target.
                City targetCity;
                if ((targetCity = citys.get(inputs[1])) == null) {
                    targetCity = new City(inputs[1]);
                    citys.put(inputs[1], targetCity);
                }

                //add the connection to the city. Also adds the targetCity (reference) to the connection.
                citys.get(inputs[0]).connections.add(new Connection(targetCity, departTime, arriveTime));
            } else {
                //Searches the city.
                String[] inputs = input.split(" ");
                City startCity = citys.get(inputs[0]);
                if (inputs[0].equals(inputs[1])) {
                    print( currentTestcase,0);
                } else if (startCity == null) {
                    print( currentTestcase,-1);
                } else {
                    print( currentTestcase, findWay(citys.get(inputs[0]), citys.get(inputs[1])));
                }

                currentTestcase++;
                currentConnection = 0;
                connections = -1;
                citys.clear();

            }
        }
    }

    public static void print(int testCase, int days) {
        System.out.println("Test Case " + (testCase + 1) + ".");
        if (days>=0) {
            System.out.println("Vladimir needs " + days + " litre(s) of blood.");
        } else {
            System.out.println("There is no route Vladimir can take.");
        }
    }

    //Started to search for a possible way.
    //-1 no way found.
    public static int findWay(City startCity, City targetCity) {
        //Trays one day travel after another.
        Queue<DepartureCity> calcCities = new LinkedList<>();
        //Add first elements to queue
        for (Connection connection : startCity.connections) {
            ((LinkedList<DepartureCity>) calcCities).add(new DepartureCity(startCity, 0, 0));
        }
        boolean isWay = false;
        DepartureCity departureCity;
        while (!(isWay || (departureCity = calcCities.poll()) == null)) { //do it until a way is found or the list is empty.
            if (calcPossibleWays(departureCity, calcCities, targetCity)) {
                return  departureCity.travelDays;
            }

        }
        return -1;
    }

    //Trays all ways within a day. Adds the new found cities to calcCities. Increases the traveled day counter per DepartureCity.
    public static boolean calcPossibleWays(DepartureCity departureCity, Queue<DepartureCity> calcCities, City targetCity) {
        boolean isOneWayNotPossible = false; //is needed for: Ways which are at the moment not possible.
        for (Connection connection : departureCity.city.connections) {
            if (departureCity.travelTimePerDay <= connection.depart) { //This connection can be made before 6:00
                if (calcPossibleWays(new DepartureCity(connection.targetCity, connection.arrive, departureCity.travelDays), calcCities, targetCity)) {
                    return true;
                }
            } else { //This connection can't be reached before 6:00. Ned to tray this on the next day.
                isOneWayNotPossible = true; //1 city should just be added once.
            }
        }


        if (departureCity.city == targetCity) {
            return true;
        } else if (isOneWayNotPossible && (!departureCity.city.added)) {// no duplicates. Next day starting city.
            departureCity.travelDays += 1;
            departureCity.travelTimePerDay = 0;
            departureCity.city.added = true;
            calcCities.add(departureCity);
        }
        return false;

    }


    //Vlads can drive onli from 1800 to 0600 so convert to 1800=0000 0600=1200
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

class DepartureCity {
    int travelTimePerDay;
    int travelDays = 0;
    City city;

    public DepartureCity(City cityName, int travelTime, int travelDays) {
        this.city = cityName;
        this.travelTimePerDay = travelTime;
        this.travelDays = travelDays;
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







import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

class Main {
    public static void main(String args[])  // entry point from OS
    {
        RomDuskTillDawn10187 myWork = new RomDuskTillDawn10187();  // create a dinamic instance
        myWork.readInput();
    }
}


public class RomDuskTillDawn10187 {

    HashMap<String, City> citys = new HashMap<>(100);
    DepartureCity found;

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
        RomDuskTillDawn10187 myWork = new RomDuskTillDawn10187();  // create a dinamic instance
        myWork.readInput();

    }

    public void readInput() {
        System.out.println("Start");
        int testcase = 0;
        int currentTestcase = 0;
        int connections = -1;
        int currentConnection = 0;
        String input = "";
        while ((input = RomDuskTillDawn10187.ReadLn(255)) != null) {
            if (testcase == 0) {
                testcase = Integer.parseInt(input);
            } else if (connections == -1) {
                connections = Integer.parseInt(input);
            } else if (currentConnection < connections) {
                currentConnection++;
                String[] inputs = input.split(" "); //0=departCity 1=targetCity 2=departTime 3=arriveTime
                int departTime = getSimpleTime(inputs[2]);
                if (departTime == -1) {
                    continue;
                }
                int arriveTime = departTime + Integer.parseInt(inputs[3]);
                if (arriveTime > 12) { //Depart Time need to be always smaller else he would drive through the day.
                    continue;
                }


                if (!citys.containsKey(inputs[0])) {
                    citys.put(inputs[0], new City(inputs[0]));
                }//Create new city.

                City targetCity;
                if ((targetCity = citys.get(inputs[1])) == null) {
                    targetCity = new City(inputs[1]);
                    citys.put(inputs[1], targetCity);
                }

                citys.get(inputs[0]).connections.add(new Connection(targetCity, departTime, arriveTime));
            } else {
                String[] inputs = input.split(" ");
                City startCity = citys.get(inputs[0]);
                if (startCity == null) {
                    if (inputs[0].equals(inputs[1])){
                        found=new DepartureCity(null,0,0);
                        print(true, currentTestcase);
                    }else {
                        print(false, currentTestcase);
                    }


                }
                else {
                    print(findWay(citys.get(inputs[0]), citys.get(inputs[1])), currentTestcase);
                }

                currentTestcase++;
                currentConnection = 0;
                connections=-1;
                citys.clear();

            }
        }

        int i =1;
        i++;
        i=i;
        while (testcase<currentTestcase){
            print(false,currentTestcase);
            currentTestcase++;
        }
    }

    public void print(boolean isOk, int testCasse) {
        System.out.println("Test Case " + (testCasse+1) + ".");
        if (isOk) {
            System.out.println("Vladimir needs " + found.travelDays + " litre(s) of blood.");
        } else {
            System.out.println("There is no route Vladimir can take.");
        }
    }

    public boolean findWay(City startCity, City targetCity) {
        Queue<DepartureCity> calcCyties = new LinkedList<>();
        //Add first elements to queue
        for (Connection conection : startCity.connections) {
            ((LinkedList<DepartureCity>) calcCyties).add(new DepartureCity(startCity, 0, 0));
        }
        boolean isWay = false;
        DepartureCity departureCity;
        while (!(isWay || (departureCity = calcCyties.poll()) == null)) { //do it until a way is found or the list is empty.
            if (calcPossibleWays(departureCity, calcCyties, targetCity)) {
                return true;
            }

        }
        return false;
    }

    public boolean calcPossibleWays(DepartureCity departureCity, Queue<DepartureCity> calcCyties, City targetCity) {
        boolean isOneWayNotPossible = false; //is needed for: Ways which are at the moment not possible.
        boolean foundCyty = false;
        for (Connection connection : departureCity.city.connections) {
            if (foundCyty){break;}
            else if (connection.targetCity.added) {
            } else if (departureCity.travelTimePerDay <= connection.depart) {
                foundCyty = calcPossibleWays(new DepartureCity(connection.targetCity, connection.arrive, departureCity.travelDays), calcCyties, targetCity);
            } else {
                isOneWayNotPossible = true;
            }
        }


        if (departureCity.city == targetCity) {
            foundCyty = true;
            found = departureCity;
        }
        else if (isOneWayNotPossible && (!departureCity.city.added)) {
            departureCity.travelDays += 1;
            departureCity.travelTimePerDay = 0;
            departureCity.city.added=true;
            calcCyties.add(departureCity);
        }
        return foundCyty;

    }


    //Vlads can drive onli from 1800 to 0600 so convert to 1800=0000 0600=1200
    //Converts this times to simpler times.
    public int getSimpleTime(String time) {
        switch (time) {
            case "18":
                return 0;
            case "19":
                return 1;
            case "20":
                return 2;
            case "21":
                return 3;
            case "22":
                return 4;
            case "23":
                return 5;
            case "24":
            case "0":
                return 6;
            case "1":
                return 7;
            case "2":
                return 8;
            case "3":
                return 9;
            case "4":
                return 10;
            case "5":
                return 11;
            case "6":
                return 12;
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
    int depart, arrive, travelTime;

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





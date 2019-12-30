package LongestNap;

import java.io.IOException;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.PriorityQueue;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

class Main {
    public static void main(String args[])  // entry point from OS
    {
        try {
            LongestNap10191.start();
        } catch (ParseException e){

        }

    }
}

public class LongestNap10191 {

    //is needed to convert string to Date (HH:mm)


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

    static SimpleDateFormat formater;
    public static void start() throws ParseException {
        formater = new SimpleDateFormat("HH:mm");
        formater.setTimeZone(new SimpleTimeZone(0,"no Timezone"));
        String input = "";
        int cDay = 0; //current test case day
        Day day = null;
        int numberOfAppointmentsLeft = 0;
        while ((input = LongestNap10191.ReadLn(255))!=null){
            if (numberOfAppointmentsLeft==0){
                cDay++;
                numberOfAppointmentsLeft = Integer.parseInt(input);
                day = new Day(cDay);
                continue;
            } else {
                day.appointments.add(new Day.Appointment(input.substring(0,5),input.substring(6,11)));
                numberOfAppointmentsLeft--;
            }

            if(numberOfAppointmentsLeft==0){
                day.calcLongestNapTime();
            }
        }

    }

    static void print(int day, Date napStart, long duration){
        String sNapStart=napStart.getHours()+":"+napStart.getMinutes();
        Date dDuration = new Date(duration);
        System.out.println("Day #"+day+"the longest nap starts at "+sNapStart+" and will last for "+ dDuration.getHours()+" hours and "+dDuration.getHours()+" minutes.");

    }


    public static class Day{

        public PriorityQueue<Appointment> appointments = new PriorityQueue<>();
        int day;
        public Day(int day){
            this.day=day;
        }

        public void calcLongestNapTime() throws ParseException {
            long longestNapDuration =0;
            Date longestNapStart = null;
            Date endAppointment =(formater.parse("10:00"));
            Appointment currentAppointment;
            while ((currentAppointment= appointments.poll())!=null){
                if (currentAppointment.startTime.getTime()-endAppointment.getTime()>longestNapDuration){
                    longestNapDuration=currentAppointment.startTime.getTime()-endAppointment.getTime();
                    longestNapStart =endAppointment;
                }
                endAppointment=currentAppointment.endTime;
            }

            if (-formater.parse("18:00").getTime()-endAppointment.getTime()>longestNapDuration){
                longestNapDuration=currentAppointment.startTime.getTime()-endAppointment.getTime();
                longestNapStart =endAppointment;
            }


            print(this.day,longestNapStart,longestNapDuration);

        }


        public static class Appointment implements Comparable{
            public Date startTime;
            public Date endTime;


            public Appointment(String startTime,String endTime) throws ParseException {
                this.startTime =formater.parse(startTime);
                this.endTime = formater.parse(endTime);
            }

            @Override
            public int compareTo(Object o) {
                return this.startTime.compareTo(((Appointment)o).startTime);
            }
        }
    }






    /**public static void start() {
        HashMap<String, City> cites = new HashMap<>(100); //All cities on a map
        int testcase = -1;
        int currentTestcase = 0;
        int connections = -1;
        int currentConnection = 0;
        String input = "";
        while ((input = FromDuskTillDawn10187.ReadLn(255)) != null) {
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
    }*/


}

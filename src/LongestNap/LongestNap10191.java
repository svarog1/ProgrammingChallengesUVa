
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

@SuppressWarnings("WrongPackageStatement")
/*class Main {
    public static void main(String args[])  // entry point from OS
    {
        LongestNap10191.start();
    }
}*/

class LongestNap10191 {

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

    public static void start() {
        String input = "";
        int cDay = 0; //current test case day
        Day day = null;
        int numberOfAppointmentsLeft = 0;
        while ((input = LongestNap10191.ReadLn(255)) != null) {
            if (input.isEmpty()){
                //System.out.println("a");
                break;
            }
            if (numberOfAppointmentsLeft == 0) {
                cDay++;
                numberOfAppointmentsLeft = Integer.parseInt(input);
                day = new Day(cDay);
            } else {
                day.addAppointment(convertTimeStringToInt(input.substring(0, 5)), convertTimeStringToInt(input.substring(6, 11)));
                numberOfAppointmentsLeft--;
            }

            if (numberOfAppointmentsLeft == 0) {
                day.calcLongestNapTime();
            }
        }
       System.out.println("");
    }

    //Converts time string HH:mm to an integer
    static int convertTimeStringToInt(String time) {
        int timeInMinutes = Integer.parseInt(time.substring(0, 2)) * 60;
        return timeInMinutes + Integer.parseInt(time.substring(3, 5));
    }

    static void print(int day, int napStart, int duration) {

        int napStartHours = (napStart) / 60;
        String sNapStart = "";
        if (napStart > 9) {
            sNapStart = Integer.toString(napStartHours);
        } else {
            sNapStart = "0" + napStartHours;
        }
        sNapStart += ":";
        int napStartMinutes = napStart% 60;
        if (napStartMinutes > 9) {
            sNapStart += Integer.toString(napStartMinutes);
        } else {
            sNapStart += "0" + napStartMinutes;
        }

        //Convert the duration to the needed duration.
        String durationString = "";
        int durationHours = (duration) / 60;
        if (durationHours > 0) {
            durationString = durationHours + " hours and " + (duration %60) + " minutes.";
        } else {
            durationString = duration + " minutes.";
        }

        System.out.println("Day #" + day + ": the longest nap starts at " + sNapStart + " and will last for " + durationString);

    }
}

class Appointment implements Comparable<Appointment>{
    int startAppointment;
    int endAppointment;
    public Appointment(int startAppointment, int endAppointment){
        this.startAppointment=startAppointment;
        this.endAppointment=endAppointment;
    }

    @Override
    public int compareTo(Appointment o) {
        return Integer.compare(startAppointment,o.startAppointment);
    }
}

class Day {

    int day;
    ArrayList<Appointment> appointments=new ArrayList<>();

    public Day(int day) {
        this.day=day;
    }

    public void addAppointment(int start, int end) {
        appointments.add(new Appointment(start,end));
    }

    public void calcLongestNapTime() {
        int napStart=600; //(10:00)
        int napDuration=0;;
        int endAppointment=600;
        Collections.sort(appointments);
        for (Appointment ap: appointments ) {
            if (ap.startAppointment<=endAppointment){
                if (ap.endAppointment>endAppointment){
                    endAppointment=ap.endAppointment;
                }
            }else {
                int tempNapduration=ap.startAppointment-endAppointment;
                if (tempNapduration>napDuration){
                    napStart=endAppointment;
                    napDuration=tempNapduration;
                }

                endAppointment=ap.endAppointment;
            }
        }

        if (1080-endAppointment>napDuration){
            napStart=endAppointment;
            napDuration=1080-endAppointment;
        }

        LongestNap10191.print(day, napStart , napDuration);
    }
}


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

/**
 *C ode Idee:
 * Anmerkung die Berechnung wird in der Klasse Day in der Funktion calcLongestNapTime() gemacht.
 *
 *  Jeder Tag enthält eine Liste von Terminen. Jeder Dieser Termine enthält den Start Zeitpunkt und den End Zeitpunkt.
 * Einfachheitshalber werden alle Termin Start und end Zeiten in Minuten umgewandelt und in einem int abgespeichert.
 *
 * Nun kann die eigentliche Berechnung in der Funktion calcLongestNapTime() in der Klasse Day beginnen.
 *
 * Hier wird durch die Sortierte Liste durchgegangen. Mann kann nun relativ einfach immer die Endzeit der
 * vorhergehenden Appointment minus die Startzeit der Jetzigen Appointment Rechnen und erhält somit die länge des
 * Möglichen Naps. Nun muss nur noch der längste Nap abgespeichert werden und ausgegeben.
 * Hier muss man aufpassen, weil Appointments sich überschneiden könne.
 *
 * @version 2020.01.19
 */
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
        int currentDay = 0; //current test case day
        Day day = null;
        int numberOfAppointmentsLeft = 0;
        while ((input = LongestNap10191.ReadLn(255)) != null) {
            if (input.isEmpty()) {
            } else {
                if (numberOfAppointmentsLeft == 0) {
                    currentDay++;
                    numberOfAppointmentsLeft = Integer.parseInt(input);
                    day = new Day(currentDay);
                } else {
                    day.addAppointment(convertTimeStringToInt(input.substring(0, 5)), convertTimeStringToInt(input.substring(6, 11)));
                    numberOfAppointmentsLeft--;
                }
                if (numberOfAppointmentsLeft == 0) {
                    day.calcLongestNapTime();
                }
            }
        }
    }

    //Converts time string HH:mm to an integer HH*60+mm
    static int convertTimeStringToInt(String time) {
        int timeInMinutes = Integer.parseInt(time.substring(0, 2)) * 60;
        return timeInMinutes + Integer.parseInt(time.substring(3, 5));
    }

    /**
     * Prints the LongestNap output.
     * @param day current day
     * @param napStart start time of the nap in int should be converted to HH:mm
     * @param duration  duration in minutes.
     */
    static void print(int day, int napStart, int duration) {

        //create the nap starting time the hours part.
        int napStartHours = napStart  / 60;
        String sNapStart = "";
        if (napStart > 9) {
            sNapStart = Integer.toString(napStartHours);
        }

        //Creates the nap Starting time the minutes part.
        sNapStart += ":";
        int napStartMinutes = napStart % 60;
        if (napStartMinutes > 9) {
            sNapStart += Integer.toString(napStartMinutes);
        } else {
            sNapStart += "0" + napStartMinutes;
        }

        //Creates the duration output.
        String durationString = "";
        int durationHours = duration  / 60;
        if (durationHours > 0) {
            durationString = durationHours + " hours and " + duration % 60 + " minutes.";
        } else {
            durationString =duration + " minutes.";
        }

        System.out.println("Day #" + day + ": the longest nap starts at " + sNapStart + " and will last for " + durationString);

    }
}


/**
 * Appointments
 */
class Appointment implements Comparable<Appointment> {
    int startAppointment;
    int endAppointment;

    public Appointment(int startAppointment, int endAppointment) {
        this.startAppointment = startAppointment;
        this.endAppointment = endAppointment;
    }

    @Override
    public int compareTo(Appointment o) {
        return Integer.compare(startAppointment, o.startAppointment);
    }
}

/**
 * Day
 */
class Day {

    int day;
    ArrayList<Appointment> appointments= new ArrayList<>() ;

    /**
     *
     * @param day Each day needs an day number.
     */
    public Day(int day) {
        this.day = day;
    }

    //Each day has x appointments.
    public void addAppointment(int start, int end) {
        appointments.add(new Appointment(start, end));
    }

    public void calcLongestNapTime() {
        int napStart = 600;
        int napDuration = 0;
        int endAppointment = 600;//(10:00) The end of the last Appointment
        int tempNapDuration=0;
        Collections.sort(appointments);
        for (Appointment ap : appointments) {
            //Starts the Appointment before the last appointment ended?
            if (ap.startAppointment <= endAppointment) {
                //Ends the Appointment before the last appointment ended?
                if (ap.endAppointment > endAppointment) {
                    endAppointment = ap.endAppointment;
                }
            } else {
                tempNapDuration = ap.startAppointment - endAppointment;
                //Is the current nap time better then the last nap time?
                if (tempNapDuration > napDuration) {
                    napStart = endAppointment;
                    napDuration = tempNapDuration;
                }
                endAppointment = ap.endAppointment;
            }
        }

        //The longest nap could be from the last appointment until 18:00 (1080==18:00)
        if (1080 - endAppointment > napDuration) {
            napStart = endAppointment;
            napDuration = 1080 - endAppointment;
        }

        LongestNap10191.print(day, napStart, napDuration);
    }
}

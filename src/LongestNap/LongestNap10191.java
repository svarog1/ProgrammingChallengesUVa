
import java.io.*;
/**
@SuppressWarnings("WrongPackageStatement")
class Main {
    public static void main(String args[])  // entry point from OS
    {
        LongestNap10191.start();
    }
}
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
       //System.out.println("");
    }

    //Converts time string HH:mm to an integer
    static int convertTimeStringToInt(String time) {
        int timeInMinutes = Integer.parseInt(time.substring(0, 2)) * 60;
        return timeInMinutes + Integer.parseInt(time.substring(3, 5));
    }

    static void print(int day, int napStart, int duration) {
        //Convert napStart int to the needed String

        int napStartHours = napStart / 60;
        String sNapStart = "";
        if (napStart > 9) {
            sNapStart = Integer.toString(napStartHours);
        } else {
            sNapStart = "0" + napStartHours;
        }
        sNapStart += ":";
        int napStartMinutes = napStart - napStartHours * 60;
        if (napStartMinutes > 9) {
            sNapStart += Integer.toString(napStartMinutes);
        } else {
            sNapStart += "0" + napStartMinutes;
        }

        //Convert the duration to the needed duration.
        String durationString = "";
        int durationHours = duration / 60;
        if (durationHours > 0) {
            durationString = durationHours + " hours and " + (duration - durationHours * 60) + " minutes.";
        } else {
            durationString = duration + " minutes.";
        }

        System.out.println("Day #" + day + ": the longest nap starts at " + sNapStart + " and will last for " + durationString);

    }
}

class Day {

    int day;
    Boolean[] appointmentScheduler = new Boolean[480];

    public Day(int day) {
        this.day = day;
        for (int i = 0; i < appointmentScheduler.length; i++) {
            appointmentScheduler[i] = true;
        }
    }

    public void addAppointment(int start, int end) {
        //no start before 10:00
        start = start - 10 * 60;
        end = end - 10 * 60;
        for (int i = start; i < end; i++) {
            appointmentScheduler[i] = false;
        }
    }

    public void calcLongestNapTime() {
        int napStart = 0;
        int napDuration = 0;
        int tempNapStart = 0;
        boolean inFreeTime = false;
        for (int i = 0; i < appointmentScheduler.length; i++) {
            if (appointmentScheduler[i]) {
                if (inFreeTime == false) {
                    tempNapStart = i;
                    inFreeTime = true;
                }
            } else {
                if (inFreeTime) {
                    inFreeTime = false;
                    if (i - tempNapStart > napDuration) {
                        napStart = tempNapStart;
                        napDuration = i - tempNapStart;
                    }
                }
            }
        }
        if (inFreeTime) {
            if (appointmentScheduler.length - tempNapStart > napDuration) {
                napStart = tempNapStart;
                napDuration = appointmentScheduler.length - tempNapStart;
            }
        }

        LongestNap10191.print(day, napStart + 10 * 60, napDuration);
    }
}

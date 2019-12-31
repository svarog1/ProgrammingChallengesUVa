import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("WrongPackageStatement")
class Main {
    public static void main(String args[])  // entry point from OS
    {
        EuclidProblem10104 euclidProblem10104 = new EuclidProblem10104();
        euclidProblem10104.start();
    }
}

class GGTEntry {
    public long mult;

    public GGTEntry(long a, long b, long mult, long rest) {

        this.mult = mult;
    }
}

class EuclidProblem10104 {

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
        while ((input = readLn(255)) != null) {
            if (input.isEmpty()) {
                break;
            }
            String[] numbers = input.split(" ");
            calc(Long.parseLong(numbers[0]), Long.parseLong(numbers[1]));

        }

        //System.out.println("");

    }

    public void calc(long a, long b) {
        calcGGT(a, b);
    }

    public  void printLn(long x, long y, long d) {
        System.out.println(x + " " + y + " " + d);
    }

    public  void calcGGT(long a, long b) {
        ArrayList<GGTEntry> l = new ArrayList<>();
        GGTEntry entry;
        boolean isSwitched=false;
        if (a == 0) {
            printLn(0, 1, b);
            return;
        } else if (b == 0) {
            printLn(1, 0, a);
            return;
        } else if (a == b) {
            printLn(0, 1, a);
            return;
        }
       if (a<b){

       }
       long ggt=0;
       long tempa;
        while (true) {
            entry = new GGTEntry(a, b, a / b, a % b);
            tempa=a;
            a = b;

            b = tempa%b;
            if (b == 0) {
                ggt=a;
                break;
            }
            l.add(entry);
        }

        if (l.size()==0){
            printLn(0,1,ggt);
            return;
        }


        long multX = 1;
        long multY = -l.get(l.size() - 1).mult;

        long tempMultmx = 0;

        for (int i = l.size() - 2; i >= 0; i--) {
            tempMultmx = multY;
            multY = multX + (multY * (-l.get(i).mult));
            multX = tempMultmx;
        }
        printLn(multX, multY, ggt);
    }
}




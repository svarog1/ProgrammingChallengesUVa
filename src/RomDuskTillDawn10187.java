import java.io.IOException;

/*class Main {
    public static void main(String args[])  // entry point from OS
    {
        RomDuskTillDawn10187 myWork = new RomDuskTillDawn10187();  // create a dinamic instance
    }
}*/


public class RomDuskTillDawn10187 {

    static String ReadLnR(int maxLg)  // utility function to read from stdin
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
        ShoemakerProblem10026 myWork = new ShoemakerProblem10026();  // create a dinamic instance
        myWork.Begin();            // the true entry point
    }
}



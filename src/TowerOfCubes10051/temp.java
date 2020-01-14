import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;


@SuppressWarnings("WrongPackageStatement")
/*class Main {
    public static void main(String args[])  // entry point from OS
    {
        temp towerOfCubes10051 = new temp();
        towerOfCubes10051.start();
    }
}*/


class temp {

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

        int currentCasse = 0;
        int numberOfcubes = 0;
        int addedCubs = 0;
        while ((input = readLn(255)) != null) {
            if (!input.isEmpty()) {
                if (numberOfcubes == 0) {
                    currentCasse++;
                    numberOfcubes = Integer.parseInt(input);
                    if (numberOfcubes == 0) {
                        break;
                    }
                    System.out.println("Case #" + currentCasse);

                } else {
                    String[] cubeColors = input.split(" ");


                    addedCubs++;
                    if (addedCubs == numberOfcubes) {
                        numberOfcubes = 0;
                        addedCubs = 0;

                    }

                }
            }
        }
    }
}

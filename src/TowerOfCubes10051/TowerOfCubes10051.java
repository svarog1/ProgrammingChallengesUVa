import java.io.IOException;
import java.util.ArrayList;


@SuppressWarnings("WrongPackageStatement")
class Main {
    public static void main(String args[])  // entry point from OS
    {
        TowerOfCubes10051 towerOfCubes10051 = new TowerOfCubes10051();
        towerOfCubes10051.start();
    }
}


/**
 * Code Idee
 * Bei jedem Würfel hat jede Seite eine von 100 Farben. Jeder Würfel muss auf einem Grösseren Würfel stehen. Somit sind 100 unterschiedliche Türme möglich. Nun wird beim Kleinsten begonnen.
 * Nun wird für jeden Würfel folgendes gemacht.
 * Es wird den Turm der Entsprechenden Farbe aus der Turmliste Hinausgelesen. Die liste kopiert und der Würfel hinzugefügt. Der Turm erhält eine neue Farbe. Die Farbe auf dem Würfel welche sich auf der Gegenüberliegenden Sete der Ursprünglichen Farbe befindet. Dies wird für alle Seiten gemacht.
 * Nun sollten 6 neue Türme entstanden sein. Diese 6 Türme müssen nun zu der Allgemeinen Turm liste hinzugefügt werden. Es wird kontrolliert ob der Turm, der bereits diese Farbe hat kleiner ist als der neue Turm. Wenn ja wird dieser Ersetzt dann mit dem nächsten fortgefahren.
 *
 * Klassen
 * Towers:
 * Sind die Türme welche aus den Würfeln erstellt wurden. Sie beinhalten immer mindestens 1 Würfel und haben immer eine Farbe. Diese Farbe beschreibt welche Würfelfarbe als nächstes zu dem Turm hinzugefügt werden kann.
 * Cubs:
 * Dies sind die eingelesenen Würfel mit den 6 Seiten, welche alle eine Farbe haben. Hat auch eine ID
 * TowerCubs
 * Dies sind die Würfel, welche zu einem Turm hinzugefügt wurde. Hier ist nur interessant auf welcher Seite sie Stehen und welche ID/Gewicht der Würfel hat.
 *
 * @version 2020.01.19
 */
class TowerOfCubes10051 {

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
        Cube[] cubs = null;
        int currentCase = 0;
        int numberOfCubes = 0;
        int addedCubs = 0;
        while ((input = readLn(255)) != null) {
            if (!input.isEmpty()) {
                if (numberOfCubes == 0) {
                    currentCase++;
                    numberOfCubes = Integer.parseInt(input);
                    if (numberOfCubes == 0) {
                        break;
                    }
                    System.out.println("Case #" + currentCase);
                    cubs = new Cube[numberOfCubes];
                } else {
                    String[] cubeColors = input.split(" ");
                    Cube c = new Cube();
                    c.wight = addedCubs + 1;
                    for (int i = 0; i < cubeColors.length; i++) {
                        c.cubeColors[i] = Integer.parseInt(cubeColors[i])-1;
                    }
                    if (cubeColors.length!=6){
                        throw new IllegalArgumentException();
                    }
                    cubs[addedCubs] = c;
                    addedCubs++;
                    if (addedCubs == numberOfCubes) {
                        numberOfCubes = 0;
                        addedCubs = 0;
                        for (Cube q:
                                cubs) {
                        }
                        calc(cubs);
                    }

                }
            }
        }
    }

    void calc(Cube[] cubs) {
        //Maximal number of Colors. There can be for each color just 1 tower.
        Tower[] towers = new Tower[100];
        for (Cube cube : cubs) {
            ArrayList<Tower> newTowers = new ArrayList<>();
            //Each cube has 6 sides. Each side could be a new Tower bottom.
            for (int i = 0; i < 6; i++) {
                Tower towerTuUpdate = towers[cube.cubeColors[i]];
                Tower newTower;
                //The cube could be the first cube with this color.
                if (towerTuUpdate == null) {
                    newTower = new Tower();
                } else {
                    newTower = new Tower(towerTuUpdate);
                }

                //Create a new cube witch can be added to a tower.
                TowerCubs newTowerCubs = new TowerCubs(i,cube.wight);
                newTower.add(newTowerCubs, cube.cubeColors[getOboistSide(i)]);
                newTowers.add(newTower);




            }

            //Adds the new towers to the Towers list when the new tower is buggier then the tower in the current color.
            for (Tower tower : newTowers) {
                Tower oldTower = towers[tower.nextTowerColor];
                if (oldTower == null) {
                    towers[tower.nextTowerColor] = tower;
                } else if (towers[tower.nextTowerColor].towerCubs.size() < tower.towerCubs.size()) {
                    towers[tower.nextTowerColor] = tower;
                }
            }
        }

        //Get the largest Tower
        Tower largesTower = null;
        int largestTowerSize = 0;
        for (Tower tower : towers) {
            if (tower != null) {
                if (largestTowerSize < tower.towerCubs.size()) {
                    largesTower = tower;
                    largestTowerSize = tower.towerCubs.size();
                }
            }

        }

        //Print the largest possible tower from current case.
        System.out.println(largesTower.towerCubs.size());
        for (int i = 0; i < largesTower.towerCubs.size(); i++) {
            System.out.println(largesTower.towerCubs.get(i).cubeId + " " + getPrintDirection(largesTower.towerCubs.get(i).standsOn));
        }
        System.out.println("");

    }

    public String getPrintDirection(int direction) {
        switch (direction) {
            case 0:
                return "front";
            case 1:
                return "back";
            case 2:
                return "left";
            case 3:
                return "right";
            case 4:
                return "top";
            case 5:
                return "bottom";
            default:
                return "ops somting went wront";

        }
    }

    /**
     * Get the oposit id of the cube
     *
     * @param site id site
     * @return opposite site.
     */
    public int getOboistSide(int site) {
        switch (site) {
            case 0:
                return 1;
            case 1:
                return 0;
            case 2:
                return 3;
            case 3:
                return 2;
            case 4:
                return 5;
            case 5:
                return 4;
        }
        return 100;
    }

    /**
     * This are the cubs where each side has a color
     */
    class Cube implements Comparable<Cube> {
        int wight; //wight and as well ID.
        Integer[] cubeColors = new Integer[6]; // 0==front,1==back,2=left,3==right,4==top,5==bot

        @Override
        public int compareTo(Cube o) {
            return Integer.compare(wight, o.wight);
        }
    }

    /**
     * This cubs are added to a Tower here ar only important the color he stands on and the cubeID
     */
    class TowerCubs {
        int standsOn;
        int cubeId;

        TowerCubs(int standOn, int cubeId) {
            this.standsOn = standOn;
            this.cubeId = cubeId;
        }
    }

    /**
     * this is the tower with the cubs witch are in this tower.
     */
    class Tower {
        ArrayList<TowerCubs> towerCubs;
        public int nextTowerColor;

        Tower(Tower tower) {
            this.towerCubs = new ArrayList<>(tower.towerCubs);
            nextTowerColor = tower.nextTowerColor;
        }

        Tower() {
            towerCubs = new ArrayList<>();
        }

        public void add(TowerCubs towerCube, int nextTowerColor) {
            towerCubs.add(towerCube);
            this.nextTowerColor = nextTowerColor;
        }

    }
}

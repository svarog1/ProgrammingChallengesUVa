import sun.security.x509.InvalidityDateExtension;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;


@SuppressWarnings("WrongPackageStatement")
class Main {
    public static void main(String args[])  // entry point from OS
    {
        TowerOfCubes10051 towerOfCubes10051 = new TowerOfCubes10051();
        towerOfCubes10051.start();
    }
}


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
                        c.cubeColors[i] = Integer.parseInt(cubeColors[i]);
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
                            int test=q.cubeColors[5];
                            int test2 =q.cubeColors.length;
                        }
                        calc(cubs);
                    }

                }
            }
        }
    }

    void calc(Cube[] cubs) {
        Tower[] towers = new Tower[101];
        for (Cube cube : cubs) {

            ArrayList<Tower> newTowers = new ArrayList<>();
            for (int i = 0; i < 6; i++) {
                int temp = cube.cubeColors.length;

                Tower towerTuUpdate = towers[cube.cubeColors[i]];
                Tower newTower;
                if (towerTuUpdate == null) {
                    newTower = new Tower();
                } else {
                    newTower = new Tower(towerTuUpdate);
                }

                /*Temp t= new Temp();*/

                TowerCubs newTowerCubs = new TowerCubs(i,cube.wight);
                newTower.add(newTowerCubs, cube.cubeColors[getOpositSide(i)]);

                newTowers.add(newTower);




            }
            for (Tower tower :
                    newTowers) {
                Tower oldTower = towers[tower.nextTowerColor];
                if (oldTower == null) {
                    towers[tower.nextTowerColor] = tower;
                } else if (towers[tower.nextTowerColor].towerCubs.size() < tower.towerCubs.size()) {
                    towers[tower.nextTowerColor] = tower;
                }
            }
        }

        Tower largesTower = null;
        int largestTowerSicze = 0;
        for (Tower tower :
                towers) {
            if (tower != null) {
                if (largestTowerSicze < tower.towerCubs.size()) {
                    largesTower = tower;
                    largestTowerSicze = tower.towerCubs.size();
                }
            }

        }
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
    public int getOpositSide(int site) {
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

    class Temp {

    }

    class Cube implements Comparable<Cube> {
        int wight; //wight and as well ID.
        Integer[] cubeColors = new Integer[6]; // 0==front,1==back,2=left,3==right,4==top,5==bot

        @Override
        public int compareTo(Cube o) {
            return Integer.compare(wight, o.wight);
        }
    }

    class TowerCubs {
        int standsOn;
        int cubeId;
        int size;

        TowerCubs(int standOn, int cubeId) {
            this.standsOn = standOn;
            this.cubeId = cubeId;
            size = 1;
        }
    }

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

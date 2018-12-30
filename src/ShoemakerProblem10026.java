import java.io.*;
import  java.util.*;

class Main {
    public static void main (String args[])  // entry point from OS
    {
        ShoemakerProblem10026 myWork = new ShoemakerProblem10026();  // create a dinamic instance
        myWork.Begin();            // the true entry point
    }
}

class ShoemakerProblem10026 {
    static String ReadLn (int maxLg)  // utility function to read from stdin
    {
        byte lin[] = new byte [maxLg];
        int lg = 0, car = -1;
        String line = "";

        try
        {
            while (lg < maxLg)
            {
                car = System.in.read();
                if ((car < 0) || (car == '\n')) break;
                lin [lg++] += car;
            }
        }
        catch (IOException e)
        {
            return (null);
        }

        if ((car < 0) && (lg == 0)) return (null);  // eof
        return (new String (lin, 0, lg));
    }

    public static void main (String args[])  // entry point from OS
    {
        ShoemakerProblem10026 myWork = new ShoemakerProblem10026();  // create a dinamic instance
        myWork.Begin();            // the true entry point
    }

    void Begin()
    {
        String input;
        boolean isReadNumberOfAssignements=false;
        boolean isReadNumberOfShomakers =false;
        boolean skip=false;

        Shoemaker[] shoemakers=null;
        int shomakerToAdd=0;

        while ((input = ShoemakerProblem10026.ReadLn (255)) != null)
        {
            if(skip)
            {
                skip=false;
            }
            else if (!isReadNumberOfShomakers)
            {
                shoemakers=new Shoemaker[Integer.parseInt(input)];
                isReadNumberOfShomakers = true;
                skip=true;
            }
            else if (!isReadNumberOfAssignements){
                shoemakers[shomakerToAdd]=new Shoemaker(Integer.parseInt(input));
                isReadNumberOfAssignements=true;
            }
            else{
                String[] timeFine =input.split(" ");
                Assignement assignement =new Assignement(Integer.parseInt(timeFine[0]),Integer.parseInt(timeFine[1]));
                if (shoemakers[shomakerToAdd].addNext(assignement)){
                    System.out.println(shoemakers[shomakerToAdd].getWorkOrder());
                    shomakerToAdd++;
                    skip=true;
                    isReadNumberOfAssignements=false;
                    if (shomakerToAdd==shoemakers.length)
                    {
                        return;

                    }
                    else {
                        System.out.println("");
                    }
                }
            }
        }
    }

    class Shoemaker
    {
        private int toAdd=0;
        public Assignement[] assignements;
        public Shoemaker(int numberOfAssignements){
            assignements=new Assignement[numberOfAssignements];
        }

        //return was last entry ==true
        public boolean addNext(Assignement assignement)
        {
            assignement.position=toAdd+1;
            assignements[toAdd]=assignement;
            toAdd++;
            return  (toAdd==assignements.length ? true : false);
        }

        public String getWorkOrder(){
            String workOrder="";
            Arrays.sort(assignements);
            for (int i=0; i<assignements.length;i++)
            {
                if (i==assignements.length-1)
                {
                    workOrder+= assignements[i].position;
                }
                else {
                    workOrder+= assignements[i].position +" ";
                }

            }
            return workOrder;
        }

    }

    class Assignement implements Comparable<Assignement>{
        final public int time;
        final public int fine;
        final public  double priority;
        public int position;
        public Assignement(int time, int fine)
        {
            this.time=time;
            this.fine=fine;
            priority=(double)fine/(double)time;
        }

        @Override
        public int compareTo(Assignement a)
        {
            return Double.compare(a.priority,this.priority);
        }

    }

}

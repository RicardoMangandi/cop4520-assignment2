import java.util.*;


class Labyrnth extends Thread
{

    public static long broughtCakeOut = 0;
    public static boolean cupcakeReady = true;
   
   @Override
    public void run()
    {
        logicToeatCake();
    }


    public synchronized void logicToeatCake()
    {   
       
       
        if(!Party.haveVisted[Party.currentGuestInside] && isThereAcupcake())
        {
            eatCupcake();
            Party.haveVisted[Party.currentGuestInside] = true;
        }

        else if(!Party.haveVisted[Party.currentGuestInside] && !isThereAcupcake())
        {
            long getNumberOfTimesEveryOneHasAskedForCake = askServantForCupcake();

            if (getNumberOfTimesEveryOneHasAskedForCake == Party.numberOfGuests - 1)
            {
                Party.partyGoesOn = false;
            }

            else
            {
                Party.partyGoesOn = true;
                eatCupcake();
            }
            Party.haveVisted[Party.currentGuestInside] = true;
        }   

        else if(Party.haveVisted[Party.currentGuestInside] && !isThereAcupcake())
        {
            //continue there is nothing to do, DO NOT ORDER A CUPCAKE
            doNotEatCupcake();
        }

        //could never happen technically because they already went inside
        else if (Party.haveVisted[Party.currentGuestInside] && isThereAcupcake())
        {
            doNotEatCupcake();
        } 

    }


    public static boolean isThereAcupcake()
    {
        if(cupcakeReady) return true;
        
        else return false;
    }

    public static void eatCupcake()
    {  
        if(cupcakeReady)
        {
            cupcakeReady = false;
        }
    }

    public static void doNotEatCupcake()
    {
        //DO NOTHING
    }

    public static long askServantForCupcake()
    {   
        cupcakeReady = true;
        broughtCakeOut++;
        return broughtCakeOut;
    }

}


public class Party {

    static int numberOfGuests;
    static ArrayList<Thread> arrayOfGuestsThreads;
    static boolean [] haveVisted;
    static boolean partyGoesOn = true;
    static int currentGuestInside;
    public static void main(String[] args) throws InterruptedException
    {
        
        Random minotaurPicksGuestsRandomly = new Random();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Please enter how many guests are coming to the party: ");
        numberOfGuests =  scanner.nextInt();
        scanner.close();
        
        //allocate memory
        haveVisted = new boolean[numberOfGuests];
        arrayOfGuestsThreads = new ArrayList<>();
        
        //initialize haveVisited Array for logic to reach goal
        for(int i = 0; i < numberOfGuests; i++)
        {
            haveVisted[i] = false;
        }
        
      //wininning strategy is to find out how many guests there are before the game starts
      //then when the game does start ask the servant how many times they have brought cake out
      //when the servant has brought cake out at n - 1 times then we are sure everyone has eaten cake at least one
      //and has gone through the labrynth at least once. 
         
       ////////////////////////////////////////////////////////////////////////////////////////////////////////////
       int threadCounter = 0;
       long partyStartTime = System.nanoTime();
       if(numberOfGuests > 1)
       {
            while(partyGoesOn)
            {   
                currentGuestInside = minotaurPicksGuestsRandomly.nextInt(numberOfGuests);
            
                arrayOfGuestsThreads.add(new Labyrnth());

                arrayOfGuestsThreads.get(threadCounter).start();

                //the join method allows one thread to wait until another thread completes it execution
                arrayOfGuestsThreads.get(threadCounter).join();
            
                threadCounter++;
            }
        }   
        else
        {
            arrayOfGuestsThreads.add(new Labyrnth());
            arrayOfGuestsThreads.get(0).start();
            arrayOfGuestsThreads.get(0).join();
        }
        
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////
        long partyEndTime = System.nanoTime();
        long partyTotalTime = partyEndTime - partyStartTime;
        System.out.println("The party had " + numberOfGuests + " guests and ended in: "+(partyTotalTime) / (Math.pow(10, 9)) + " seconds");
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////
    }    
}



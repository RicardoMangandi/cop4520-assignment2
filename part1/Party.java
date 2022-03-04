import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;


class Labyrnth extends Thread
{

   // public static AtomicBoolean cupcakeReady = new AtomicBoolean(true);
   // public static AtomicLong numberOfTimesBroughtCupcakeOut = new AtomicLong(0);
    public static long broughtCakeOut = 0;
    public static boolean cupcakeReady = true;
   
   @Override
    public void run()
    {
        logicToeatCake();

    }


    public synchronized void logicToeatCake()
    {   
        System.out.println("Guest "+Party.currentGuestInside+" has gone inside the labrynth");
       
        if(!Party.haveVisted[Party.currentGuestInside] && isThereAcupcake())
        {
            eatCupcake();
            Party.haveVisted[Party.currentGuestInside] = true;
        }

        else if(!Party.haveVisted[Party.currentGuestInside] && !isThereAcupcake())
        {
            long getAsked = askServantForCupcake();

            if (getAsked == Party.numberOfGuests - 1)
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
    static Thread [] arrayOfGuestsThreads;
    static boolean [] haveVisted;
    static boolean partyGoesOn = true;
    static int currentGuestInside;
    public static void main(String[] args) throws InterruptedException
    {
        
        Random minotaurPicksGuestsRandomly = new Random();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Please enter how many guests are coming to the party: ");
        //numberOfGuests =  scanner.nextInt();
        numberOfGuests = 3;
        System.out.print(numberOfGuests);
        System.out.println();
        scanner.close();
        
        arrayOfGuestsThreads = new Labyrnth[numberOfGuests];
        haveVisted = new boolean[numberOfGuests];

            
     
        for(int i = 0; i < numberOfGuests; i++)
        {
            Thread aThread = new Labyrnth();
            arrayOfGuestsThreads[i] = aThread;
            haveVisted[i] = false;
        }

       ////////////////////////////////////////////////////////////////////////////////////////////////////////////
       int i = 0;
       while(partyGoesOn)
        {   
            currentGuestInside = minotaurPicksGuestsRandomly.nextInt(numberOfGuests);
            
            if(arrayOfGuestsThreads[currentGuestInside].getState().toString() == "NEW"){
                
                arrayOfGuestsThreads[currentGuestInside].start();
               
            }

            else if (arrayOfGuestsThreads[currentGuestInside].getState().toString() == "RUNNABLE"
                    || arrayOfGuestsThreads[currentGuestInside].getState().toString() == "BLOCKED" ||
                    arrayOfGuestsThreads[currentGuestInside].getState().toString() == "WAITING" 


            )
            {
                continue;
            }
            
     
            else //if(arrayOfGuestsThreads[currentGuestInside].getState().toString() == "TERMINATED")
            {   

               // System.out.println(arrayOfGuestsThreads[currentGuestInside].getState().toString());
                arrayOfGuestsThreads[currentGuestInside].run();
            }

       

            i++;
        }

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////
        
        System.out.println("Party status:" + partyGoesOn);
        System.out.println("Number of times asked servant: "+ Labyrnth.broughtCakeOut);
        System.out.println(i);


        ////////////////////////////////////////////////////////////////////////////////////////////////////////////

       for(Thread atThread : arrayOfGuestsThreads)
        {   
            atThread.join();
        } 

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////
         
    }    
}



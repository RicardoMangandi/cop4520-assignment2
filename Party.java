import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.*;

class Labyrnth extends Thread
{

    public static AtomicBoolean cupcakeReady = new AtomicBoolean(true);
    public static AtomicLong numberOfTimesBroughtCupcakeOut = new AtomicLong(0);
  
   static Lock changeStatusOfGuests = new ReentrantLock();

    public synchronized void run()
    {
        changeStatusOfGuests.lock();
        logicToeatCake();
        if(!Party.haveVisted[Party.currentGuestInside])
            Party.haveVisted[Party.currentGuestInside] = true;
        changeStatusOfGuests.unlock();
        
    }


    public void logicToeatCake()
    {

        if (!Party.haveVisted[Party.currentGuestInside] && isThereAcupcake())
        {
            eatCupcake();
        }
        
        else if (!Party.haveVisted[Party.currentGuestInside] && !isThereAcupcake())
        {
            AtomicLong numberOfTimesAskedServant = askServantForCupcake();
            
            if (numberOfTimesAskedServant.compareAndSet(Party.numberOfGuests - 1,Party.numberOfGuests - 1))
            {
                Party.partyGoesOn = false;//the game has ended
            }
        
            else
            {
                Party.partyGoesOn = true;
                eatCupcake();
            }
        }
        
        
        //could happen if guests get chosen twice in a row or more
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


    public boolean isThereAcupcake()
    {
        if(cupcakeReady.get()) return true;
        
        else return false;
    }

    public void eatCupcake()
    {  
        if(cupcakeReady.get())
        {
            cupcakeReady.set(false);
        }
    }

    public void doNotEatCupcake()
    {
        cupcakeReady.get();
    }

    public AtomicLong askServantForCupcake()
    {   
        cupcakeReady.set(true);
        numberOfTimesBroughtCupcakeOut.getAndAdd(1);
        return numberOfTimesBroughtCupcakeOut;
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

            
        //everyone knows the number of guests(threads) at party
        for(int i = 0; i < numberOfGuests; i++)
        {
            Thread aThread = new Labyrnth();
            arrayOfGuestsThreads[i] = aThread;
          
        }

       
        
        while(partyGoesOn)
        {   
            currentGuestInside = minotaurPicksGuestsRandomly.nextInt(numberOfGuests);
            
            System.out.println("Guest "+currentGuestInside+" has gone inside the labrynth");
            
            if(arrayOfGuestsThreads[currentGuestInside].getState().toString() == "NEW")
                arrayOfGuestsThreads[currentGuestInside].start();

            else arrayOfGuestsThreads[currentGuestInside].run();
           
        }
        
        System.out.println("Party status:" + partyGoesOn);
        System.out.println("Number of times asked servant: "+ Labyrnth.numberOfTimesBroughtCupcakeOut);

        for(Thread atThread : arrayOfGuestsThreads)
        {   
            atThread.join();
        }
         
    }    
}



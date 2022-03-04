import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.ArrayList;

class Showroom extends Thread
{   
    
    @Override
    public void run()
    {   
         try
        {
            int currentThreadInsideShowRoom =  VaseParty.blockingQueue.take();
            isInsideShowRoom(currentThreadInsideShowRoom);
        }
        

        catch(Exception error)
        {
            error.printStackTrace();
        } 
    }

    public static synchronized void isInsideShowRoom(int currentGuest)
    {   
        try
        {   
            System.out.println("Guest " + currentGuest +" is currently inside");
        }
        catch(Exception error)
        {
            error.printStackTrace();
        }
    }
}


public class VaseParty {
    
    static ArrayList<Thread> threadOfGuests;
    static BlockingQueue<Integer> blockingQueue;
    
    
    public static void main(String[] args) throws InterruptedException{
        

        Scanner scanner = new Scanner(System.in);
        System.out.print("Please enter how many guests are coming to the party: ");
        int numberOfGuestsAtVaseParty = scanner.nextInt();
       // System.out.print(numberOfGuestsAtVaseParty);
        //System.out.println();
        scanner.close();
        

        blockingQueue = new LinkedBlockingQueue<Integer>(); //the queue size is Integer.MAX_VALUE
        threadOfGuests = new ArrayList<>();
        Random randomGuestsQueueIn = new Random();
        int counterThread = 0;
        int guestCounter = 0;
        
        long end = System.currentTimeMillis() + 1000;
        while(System.currentTimeMillis() < end)
        {   
           threadOfGuests.add(new Showroom());

           threadOfGuests.get(counterThread).start();

           blockingQueue.put((Integer)randomGuestsQueueIn.nextInt(numberOfGuestsAtVaseParty));

           counterThread++;
           guestCounter++;
        }
        
        
        for(Thread aThread : threadOfGuests)
            aThread.join();
        
        System.out.println("Number of guests that were able to go inside: "+guestCounter);
    }
}


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
            //System.out.println("Guest " + currentGuest +" is currently inside");
            //System.out.println("Size of queue: " +VaseParty.blockingQueue.size());
            
        }
        catch(Exception error)
        {
            error.printStackTrace();
        }
    }
}


public class VaseParty {
    
    public static ArrayList<Thread> threadOfGuests;
    public static BlockingQueue<Integer> blockingQueue;
    
    
    public static void main(String[] args) throws InterruptedException{
        

        Scanner scanner = new Scanner(System.in);
        System.out.print("Please enter how many guests are coming to the party: ");
        int numberOfGuestsAtVaseParty = scanner.nextInt();
        scanner.close();
        

        blockingQueue = new LinkedBlockingQueue<Integer>(); //the queue size is Integer.MAX_VALUE
        threadOfGuests = new ArrayList<>();
        int counterThread = 0;
        int guestCounter = 0;
        
        long end = System.currentTimeMillis() + 1000;

        for(int i = 0; i < numberOfGuestsAtVaseParty; i++)
        {
            blockingQueue.put(i);
        }
        
        while(System.currentTimeMillis() < end)
        {  
            
           threadOfGuests.add(new Showroom());

           threadOfGuests.get(counterThread).start();

           threadOfGuests.get(counterThread).join();
           
           if(guestCounter >= numberOfGuestsAtVaseParty)
                guestCounter = 0;

           blockingQueue.put(guestCounter);
           
           counterThread++;
           guestCounter++;
        }
        
        System.out.println("Number of guests including repetitions that were able to go inside within alloted time: "+counterThread);
        

        if (blockingQueue.isEmpty())
        {
            System.out.println("The queue got emptied out");
        }
        else {System.out.println("The queue is of size "+ blockingQueue.size()+ " will empty the queue out now...");
        
        
        while(!blockingQueue.isEmpty())
        {   
            //technically it should not run these lines because the party is over
            /*
            threadOfGuests.add(new Showroom());

            threadOfGuests.get(counterThread).start();
 
            threadOfGuests.get(counterThread).join();
            */
            blockingQueue.take();
            counterThread++;
        }   
    }
    
    System.out.println("Queue is finally empty queue size: "+ blockingQueue.size());
    }
}


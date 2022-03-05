# Part 2

## Disadvantages and Advantages:

1. First I will discuss the disadvantages to choicing choice 1.
   
   Choice one is the worst choice due to it having no mutual exclusion involved. All threads need to have some sort of mutual exclusion such as 
   Atomic Variables, Semaphores, or Synchronized blocks or statements. If there is no mutual exclusion involved then it could lead to a data race.
   
2. Second I will discuss the disadvantages and advantages to choice 2.

    Choice two sounds like a good idea and some of its advantage is it allows for mutual exclusion to occur. Meaning only one thread (guest) 
    is able to enter the critical section which is the showroom. The only problem that could arise with this solution is if two threads were 
    to potentially both attempt to read and write to the flag at the same time. This could cause a deadlock and potentially make it where no other
    thread is able to advance. 
  

3. The third choice is the best because we are able to avoid thread conflicts with a blockingqueue which in Java is considered thread-safe.

    Choice three allows for the usage of a Blockingqueue which allows me to implement a sequential flow of guests going inside the ballroom. It allows me to queue in 
    an unlimited amount of guests based on certain conditions.
    

## How to Run Program
  
  1. Open a terminal
  2. Run the following command to compile the program:
   ```bash
   javac VaseParty.java
   ```
  3. Run the following command to execute the compiled program:
   ```bash
   java VaseParty
   ```
  4. The program will ask for an integer value to be entered which represents the number of guests, enter an integer value:
  ```bash
  Please enter how many guests are coming to the party:
  ```
  5. The program will print out the number of guests that were able to see the vase potentially including guests who requeued.
  6. The program will let the user know the size of the queue after it has finished looping for a second.
  7. The program will then empty out the queue for the user.

## Program Implementation
  
  The user is able to pick a certain number of guests to invite to the party. The party is simulated by a loop which runs for about a second. This loop
  allows for about 18,000 - 20,000 guests to be placed in the queue. However, the guests are queued at random due to the problem stating that they can queue multiple times.
  So its not gurantee that everyone will be able to queue when the number of guests is very large at the party. The party can only last for so long. 
  
  
## Proof of Correctness

  My approach on using the data structure interface BlockingQueue from Java and used LinkedBlockingQueue which allowed for a bounded length of ```MAX_VALUE = 2147483647```.
  Due to my implementation of allowing my while loop run for a second it would be impossible to overflow the queue. I was thinking of running the while loop for more longer to allow 
  more guests to be able to go inside the critical section, but this would cause the amount of threads created to reach a memory limit.
  
  In addition to my BlockingQueue I have a synchronized method that allows to execute a single thread at a time. It does not allow items to be taken from the queueu until its predecessor has executed.
  

## Experiments ran

My concern in regards to my implementation is I do not have control in regards to the number of guests that are able to enter all the time. This is due to the time limit I am enforcing. The guests are able to requeue but it seems that in a second there are 18,000 - 20,000 guests that are able to be queued in. I could potentially increase the factor to allow more guests in an alloted time based on the input size. Below you will find some runs of the number of guests that were queued within a second.
  
  | Run # | Total # of Guests Invited | # of Guests Queued and saw Vase| 
  | --- | --- | --- |
  | 1 | 90 | 19167 |
  | 2 | 10000 | 22159 |
  | 3 | 100000| 19588 |
  | 4 | 12 | 20527 |
  | 5 | 900909 | 18832 |
  
  



# Part 1

## How to Run Program:

1. Open a terminal
2. Run the following command to compile the program:
```bash
javac Party.java
```
3. Run the following command to execute the program:
```bash
java Party
```
4. The program will ask for an integer value to be entered which represents the number of guests, enter an integer value:
```bash
Please enter how many guests are coming to the party:
```
5. The program will print out the following statement dependent on your initial input:
```bash
The party had 10 guests and ended in: 0.003341666 seconds
```


## Program Design:

To simulate the Minotaur's Party I had a while loop looping until everyone had gone inside the labrynth. To simulate mutual exclusion and allow for a sequential flow
I implemented a the join() method inside this while loop. The reasoning behind this is because the join method allows one thread to wait until another thread completes it execution.
I also added in a synchronization method whenever the threads do go inside the critical section. I tested my program without the synchronized method and it still accomplishes mutual sequential
exclusion due to the join() method property.

## Winning Strategy Explained:

1. My winning strategy is to first ensure every guests knows how many guests there are in total before the game starts.
2. Everyone is informed to only eat cake once! When they eat cake once, this makes the next guest order a new cake.
3. The problem statement states that guests cannot communicate with other guests therefore, I communicate to the servant inside the critical section. The servant serves as the guests shared resource throughout.
4. The guests make sure whenever they need a cake to also ask the servant how many times he has brought cake out.
   a. If cake has been brought guests - 1 times then we know everyone has eaten cake at least once therefore everyone has visited the labrynth at least once. Party ends.
   b. Else continue.


## Proof of correctness:

My runtime for this program is on average O(n^2) due to the following. Everyone is to go inside the labrynth at least once which produces O(n) runtime. 
But for every guest they can go inside n times due to the Minotaur picking guests randomly. 


## Experiments ran

Here are some of the runtimes for n and the amount of time it took in seconds:

| Run # | # of Guests | Time in Seconds to complete |
| --- | --- | --- |
| 1 | 100 | 0.046972042 |
| 2 | 1_000 | 0.235011083 |
| 3 | 10_000 | 3.087469541 |
| 4 | 100_000 | 35.5128865 |
| 5 | 1_000_000| undefined |

For run 5 it is undefined due to the the JVM running out of memory to continue allocating memory for threads.



import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created by glenc on Feb 2021
 **/
public class RunnerForConcurrency {

    public static void main(String[] args) {
//        firstWay();

        coordinateConcurrency();
    }

    //this was implemented originally without marking methods in bank as synchronized
    private static void firstWay() {
        ExecutorService es = Executors.newFixedThreadPool(5);
        BankAccount account = new BankAccount(100);
        for (int i = 0; i < 5; i++) {
            Worker worker = new Worker(account);
            es.submit(worker);
        }
        //shutdown and wait
        es.shutdown();
        try {
            es.awaitTermination(60, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //problem
//        due to our balance+=amount in the BankAccount class, it involves
        //reading from memory and adding amount to that and reassigning the new value to memory
        //now with our current implementation we get wrong values e.g
//        thread1 gets current value =100, adds 10 to it,  it assigns back to the system
        // thread 5 gets the 100 as current and not the new 110 because thread1 hasnt written its value
        //when thread 1 assigns its value to balance, it becomes 110 and when thread 5 returns it will override that balance and put in back 110 it returns

    }

    private static void coordinateConcurrency() {
        //solving for previous method firstWay();
//        Synchronized methods - coordinate thread access to methods
        //class can have as many synchronized methods it needs
        //it is managed per instance
        //no more than one thread can be in any synchronized method at a time
        //protects modification but multiple threads, what we need to solve now from the previous task
        // protects modification when we are reading a value from another thread

//        why not
//        has significant overhea
//        use only in multithreading scenarios

        //constructors are never synchronized, cause any given object instance is always created on exactly one thread


        ExecutorService es = Executors.newFixedThreadPool(5);
        BankAccount account = new BankAccount(100);
        for (int i = 0; i < 5; i++) {
            Worker worker = new Worker(account);
            es.submit(worker);
        }
        //shutdown and wait
        es.shutdown();
        try {
            es.awaitTermination(60, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    //manual synchronization
//    synchronized methods automated concurrency management and used a lock on current object instance
    //all java objects have a lock, thus can manually acquire a lock
//    use synchronized statement block
    //check class l build BankAccountManualSynchronized

//    why these synchronized statement blocks even though they do the same as automatic ones
    //provide more flexibility
//    enables use of non-thread safe classes in a thread safe way
//    can protext complex blocks of code without moving to each method


    private static void ManualSynchronization() {


        ExecutorService es = Executors.newFixedThreadPool(5);
        BankAccountManualSynchronized account = new BankAccountManualSynchronized(100);
        for (int i = 0; i < 5; i++) {
            WorkerManualSynchronized worker = new WorkerManualSynchronized(account);
            es.submit(worker);
        }
        //shutdown and wait
        es.shutdown();
        try {
            es.awaitTermination(60, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


    //sometimes manual synchronized methods is not enough and the solution:, so we may use auto synchronized


    //Concurrency safe collection access
//    java provides Synchronized collection wrappers
//    most collections are not thread safe
//    create thread safe wrappers:
//    synchronizedList
//    synchronizedMap e.t.c
//    Wrapper is a thread sage proxy, actual work occurs in original object

//    Blocking Collections
//            for coordinating producers and consumers
//            often one or more threads produce content
//            often one or more other threads consume content
//                must wait for content if not available
//                java attempts blocking queues
//            attemps to read blocks and if empty wakes up when content available
//            examples
//                LinkedBlockingQueue
//                PriorityBlockingQueue etc

}




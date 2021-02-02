/**
 * Created by glenc on Feb 2021
 **/
public class BankAccountManualSynchronized {

    //concurrency issue

    private int balance;

    public BankAccountManualSynchronized(int balance) {
        this.balance = balance;
    }


    //add manual synchronize to protect from modification
    public  int getBalance() {
        return balance;
    }

    //add manual synchronize to protect from modification
    public void deposit(int amount){
        balance+=amount;
    }
}

//worker class
class WorkerManualSynchronized implements Runnable{
    private final BankAccountManualSynchronized account;

    public WorkerManualSynchronized(BankAccountManualSynchronized account) {
        this.account = account;
    }

    @Override
    public void run() {

        for(int i=0;i<10;i++) {

            //manual synchronization for any class even those that dont support the automatic synchronization
            synchronized (account) {
                int startBalance = account.getBalance();
                account.deposit(10);
                int endBalance = account.getBalance();
                System.out.println("workers wages: " + endBalance);
            }
        }
    }
}


/**
 * Created by glenc on Feb 2021
 **/
public class BankAccount {

    //concurrency issue

    private int balance;

    public BankAccount(int balance) {
        this.balance = balance;
    }


    //add synchronize to protect from modification
    public synchronized int getBalance() {
        return balance;
    }

    //add synchronize to protect from modification
    public synchronized void deposit(int amount){
        balance+=amount;
    }
}

//worker class

class Worker implements Runnable{
    private BankAccount account;

    public Worker(BankAccount account) {
        this.account = account;
    }

    @Override
    public void run() {

        for(int i=0;i<10;i++) {
            int startBalance = account.getBalance();
            account.deposit(10);
            int endBalance = account.getBalance();
            System.out.println("workers wages: "+ endBalance);
        }
    }
}

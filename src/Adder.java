/**
 * Created by glenc on Feb 2021
 **/
public class Adder implements Runnable {

    private String name;
    private String surname;

    public Adder(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    public void adder(){
        String fullname = this.name + " " +  this.surname;
        System.out.println(fullname);
    }

    //for use with the Callable interface we use this one
    public int adding(){
        int total = 0;
        if(surname != "" && name !=""){
            total+=1;
        }
        return total;
    }

    @Override
    public void run() {
        //the method to run on a seperate thread
        adder();
        adding();
    }
}

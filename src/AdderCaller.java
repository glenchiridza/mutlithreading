import java.io.IOException;
import java.util.concurrent.Callable;

/**
 * Created by glenc on Feb 2021
 **/
public class AdderCaller implements Callable<Integer> {

    private String name;
    private String surname;

    public AdderCaller(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }



    //for use with the Callable interface we use this one
    public int adding() {
        int total = 0;
        int i =1;
        if(surname != "" && name !=""){
            i++;
            total+=i;

        }

        return total;
    }

    @Override
    public Integer call() {

          return adding();

    }
}
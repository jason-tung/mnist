import java.util.ArrayList;

public class array extends ArrayList<Object>{
    int[] shape;
    //ensure that all these arrays are rectangular


    private void recursive_parse(Object T){
        while(!(T instanceof Double)){
            for(Object i: (Iterable<Object>) T){
                recursive_parse(i);
            }
        }
    }

    public array(Object T){

    }
}
package np;
import java.util.*;
import static np.py.*;

public class np {

    public static double sum(tensor arr){return 0;}
    public static double mean(tensor arr){return 0;}
    public static double max(tensor arr){return 0;}
    public static double min(tensor arr){return 0;}

    public static boolean equal(tensor arr1, tensor arr2){return true;}

    
//    public static tensor matmul(tensor arr){};
    
    public static tensor rowMaxs(tensor x) {
        int[] f = {x.shape[0]};
        tensor rTensor = tensor.zeros(f);
        for (int i = 0; i < x.shape[0]; i++) {
            int[] g = {i, 0};
            Double max = val(x.get(g));
            for (int j = 1; j <= x.shape[1]; j++) {
                int[] h = {i, j};
                if (val(x.get(h)) > max) {
                    max = val(x.get(h));
                }
            }
            int[] j = {i};
            rTensor.set(j, max);
        }
        return rTensor;
    }

    //only deals with 2d arrays for now i dont know what funky stuff you did for n dimensional arrays
    public static tensor subtract(tensor a, tensor b){
	if (Arrays.equals(a.shape, b.shape)){
	    int[] shape = a.shape;
	    tensor rTensor = tensor.zeros(shape);
	    for (int i = 0; i < shape[0]; i++){
		for (int j = 0; j < shape[1]; j++){
		    int[] currentLoc = {i,j};
		    rTensor.set(currentLoc, a.get(currentLoc).data - b.get(currentLoc).data);
		    
		}
	    }
	    return rTensor;
	}
	throw new IllegalArgumentException("tensors of different shapes");
    }

    public static tensor exp(tensor a){
	
    }





}

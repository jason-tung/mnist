package net;
import np.*;
import static py.py.*;

import java.util.*;

public class Preprocessing{

    //returns an image in the form of an int[][][] in rgb format, numbers should be from 0-256
    public static int[][][] parse(String s){ // should be in form (rows, cols, channels)
        throw new IllegalStateException("Not implemented yet, this is here so it compiles");
    }

    //takes an ndimensional tensor and collapses it into a 1D tensor
    public static tensor flatten(tensor t){
        ArrayList<Double> tmp = new ArrayList<>();

        for(int[] i: t){
            tmp.add(val(t.g(i)));
        }

        tensor res = tensor.zeros(new int[]{tmp.size()});

        for(int[] i: res){
            res.s(i, tmp.get(i[0]));
        }
        return res;
    }

    //creates the one-hot layer with which to compare output layer
    public static tensor to_categorical(tensor t, int num_classes){
        if(t.shape.length != 1) throw new IllegalArgumentException("t.shape.length must be == 1");
        tensor r = tensor.zeros(new int[]{t.shape[0], num_classes});

        for(int[] i: t){
            r.s(new int[]{i[0], (int) val(t.g(i))}, 1.0);
        }

        return r;
    }

    public static tensor normalize(tensor t){
        class normalize extends Callable{
            public Double call(double k) {
                return (k - 128)/128;
            }
        }
        return np.vectorize(t, new normalize());
    }

}

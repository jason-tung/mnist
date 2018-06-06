package net;
import np.*;
import py.*;

public class LossFunction {
    public String callType;

    public LossFunction(String s) {
        callType = s;
    }

    public double call(tensor output, tensor onehot) {
        switch (callType) {
            case "mean squared":
                return meanSquared(output, onehot);
            case "cosine proximity":
                return 0;
            case "categorical crossentropy":
                return 0;
        }
        throw new IllegalArgumentException("The function you specified is invalid");
    }

    public double meanSquared(tensor output, tensor onehot){
        tensor lossFunc = tensor.zeros(output.shape);
        double tot = 0;
        for (int i = 0; i < output.shape[0]; i++){
            int[] loc = {i};
            tot +=  Math.pow(py.val(output.get(loc)) - py.val(onehot.get(loc)), 2);
        }
        return tot;
    }
}




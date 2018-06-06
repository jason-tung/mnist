package net;
import np.*;
import py.*;

public abstract class LossFunction {
    public String callType;

    public LossFunction(String s) {
        callType = s;
    }

    public tensor call(tensor output, tensor onehot) {
        switch (callType) {
            case "mean squared":
                return meanSquared(output, onehot);
            case "cosine proximity":
                return null;
            case "categorical crossentropy":
                return null;
        }
        throw new IllegalArgumentException("The function you specified is invalid");
    }

    public tensor meanSquared(tensor output, tensor onehot){
        double totSquares = 0;
        for (int i = 0; i < output.shape[0]; i++){
            int[] loc = {i};
            totSquares += Math.pow(py.val(output.get(i)) - py.val(onehot.get(i)), 2);
        }
        return totSquares -
    }
}




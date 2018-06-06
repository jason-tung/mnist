package net;
import np.*;
import py.*;

public class LossFunction {
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
        tensor lossFunc = tensor.zeros(output.shape);
        for (int i = 0; i < output.shape[0]; i++){
            int[] loc = {i};
            lossFunc.s(loc, Math.pow(py.val(output.get(loc)) - py.val(onehot.get(loc)), 2));
        }
        return lossFunc;
    }
}




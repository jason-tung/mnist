package net;

//import java.util.*;

import np.*;
import py.*;

public class Activation {
    private String callType;

    public Activation(String s) {
        callType = s;
        apply(10); //Catch errors in the constructor without repeating the switch statement with a test
    }

    //applies a function class as an actual function
    public double apply(double x) {
        switch (callType) {
            case "relu":
                return relu(x);
            //--------------------------------------------- TO BE FIXED LATER ---------------------------------------------------------------------------------------------------
//            case "softmax":
//                return softmax(x);
            case "sigmoid":
                return sigmoid(x);
            case "tanh":
                return tanh(x);
        }
        throw new IllegalArgumentException("The activation you specified is invalid");
    }

    //static math functions
    private static double relu(double x) {
        return Math.max(0, x);
    }

    private static tensor softmax(tensor x) {
        tensor max = np.max(x, 0);
        tensor newarray = tensor.zeros(x.shape);
        for (int i = 0; i < x.shape[0]; i++) {
            for (int j = 0; j < x.shape[1]; j++) {
                int[] loc = {i, j};
                int[] maxloc = {i};
                newarray.s(loc, py.val(max.g(maxloc)));
            }
        }
        tensor difference = np.exp(np.subtract(x,newarray));
        tensor sumdiff = np.sum(difference, 1);
        for (int i = 0; i < x.shape[0]; i++) {
            for (int j = 0; j < x.shape[1]; j++) {
                int[] loc = {i, j};
                int[] maxloc = {i};
                newarray.s(loc, py.val(sumdiff.g(maxloc)));
            }
        }
        tensor dog = np.divide(difference, newarray);
        return dog;
    }


    private static double sigmoid(double x) {
        return 1 / (1 + Math.exp(-x));
    }

    private static double tanh(double x) {
        return sinh(x) / cosh(x);
    }

    private static double sinh(double x) {
        return (Math.exp(x) - Math.exp(-x)) / 2;
    }

    private static double cosh(double x) {
        return (Math.exp(x) + Math.exp(-x)) / 2;
    }
}

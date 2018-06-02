package net;

//import java.util.*;

import np.*;

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
            case "softmax":
                return softmax(x);
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

    private static double softmax(double x) {

        //tensor maximus = x.rowMaxs;
        return 0.0;

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

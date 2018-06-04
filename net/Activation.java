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

    private static tensor softmax(tensor x) {
	tensor max = np.max(x, 0);
	tensor newarray = tensor.zeros(x.shape);
	for (int i = 0; i < x[0]; i++){
	    for (int j = 0; j < x[1]; j++){
		int[] loc = {i,j};
		int[] maxloc = {i};
		newarray.s(loc, max.g(i));
	    }
	}
	tensor difference = np.exp(x.subtract(newarray));
	tensor sumdiff = np.sum(difference, 1);
	for (int i = 0; i < x[0]; i++){
	    for (int j = 0; j < x[1]; j++){
		int[] loc = {i, j};
		int[] maxloc = {i};
		newarray.s(loc, sumdiff.g(i));
	    }
	}
	tensor dog = np.divide(difference, sumdiff);
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

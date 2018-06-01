package net;

import java.util.*;
import np.tensor;

public class Activation {
    String callType;

    public Activation(String s){
        callType = s;
    }

    //applies a function class as an actual function
    public double apply(double x){
        switch(callType){
            case "relu":
                return relu(x);
            case "softmax":
                return softmax(x);
            case "sigmoid":
                return sigmoid(x);
            case "tanh":
                return tanh(x);
                //add a default here that throws an error that says invalid activation function
        }
    }

    //static math functions
    private static double relu(double x ){
        return Math.max(0,x);
    }
    private static double softmax(double x ){
<<<<<<< HEAD
        tensor maximus = x.rowMaxs;
	return null;
	
    }

    private static tensor rowMaxs(tensor x){
        int[] f = {x.shape[0]};
	tensor rTensor = tensor.zeros(f);
	for (int i =0; i < x.shape[0]; i++){
	    int[] g = {i, 0};
	    Double max = x.get(g).data;
	    for (int j = 1; j <= x.shape[1]; j++){
	        int[]h = {i,j};
		if (x.get(h).data > max){
		}
	    }
	    int[] j = {i};
	    rTensor.set(j, max);
	}
	return rTensor;
=======
        // https://github.com/liulhdarks/darks-learning/blob/master/src/main/java/darks/learning/common/utils/MatrixHelper.java
        //http://jblas.org/javadoc/org/jblas/DoubleMatrix.html
        return null;
    }

    private static tensor rowMaxs(tensor x){
        return null;
>>>>>>> cd1e0900a6cb4d5b884b7b370a2ce28b7be8f92f
    }

    private static double sigmoid(double x ){
        return 1 / (1 + Math.exp(-x));
    }
    private static double tanh(double x ){
        return sinh(x)/cosh(x);
    }

    private static double sinh(double x){
        return (Math.exp(x) - Math.exp(-x)) / 2;
    }

    private static double cosh(double x){
        return (Math.exp(x) + Math.exp(-x)) / 2;
    }
}

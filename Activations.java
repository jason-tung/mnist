import java.util.*;

public class Activations{
    String callType;

    public Activations(String s){
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
        }
    }

    //static math functions
    private static double relu(double x ){
        return Math.max(0,x);
    }
    private static double softmax(double x ){
        // https://github.com/liulhdarks/darks-learning/blob/master/src/main/java/darks/learning/common/utils/MatrixHelper.java
        //http://jblas.org/javadoc/org/jblas/DoubleMatrix.html
        return null;
    }

    private static tensor rowMaxs(tensor x){
        return null;
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

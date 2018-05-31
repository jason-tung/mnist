import java.utils.*;

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
        tensor maximus = x.rowMaxs;
	
    }

    private static tensor rowMaxs(tensor x){
	tensor rTensor = tensor.zeros({x.shape[0]});
	for (int i =0; i < x.shape[0]; i++){
	    int max = tensor.get(i,0);
	    for (int j = 1; j <= x.shape[1]; j++){
		if (tensor.get(i,j) > max){
		    max = tensor.get(i,j);
		}
	    }
	    rTensor.s(max, i);
	}
	return rTensor;
    }
    
    private static double sigmoid(double x ){
	return 1 / (1 + Math.exp(-x));
    }
    private static double tanh(double x ){
	return sinh(x)/cos(x);
    }

    private static double sinh(double x){
	return (Math.exp(x) - Math.exp(-x)) / 2;
    }
    
    private static double cosh(double x){
	return (Math.exp(x) + Math.exp(-x)) / 2;
    }
}

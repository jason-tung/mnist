package net;

import np.np;
import np.tensor;
import py.*;

public class Network{
    LossFunction loss_function;
    Layer[] layers;
    int count_layers;
    int count_nodes;
    String descent_mode;
    Activation activation_function;
    double bias;

    public Network(int numLayers, int numNodes, String activFunct, String lossFunc, String descent_mode, double bias){
        loss_function = lossFunc;
        count_layers = numLayers;
        count_nodes = numNodes;
        this.descent_mode = descent_mode;
        activation_function = new Activation(activFunct);
        this.bias = bias;
    }



    //descent
    public void fit(){

    }

    public void fdpass(){

    }

    public void bkpass(){

    }

    public double nextActivation(tensor activation, tensor weights){
        double activationSum = 0;
        for (int i = 0; i < activation.shape[0]; i++){
            int[] shape = {i};
            activationSum += py.val(activation.g(shape)) * py.val(weights.g(shape));
        }
        return activation_function.apply(activationSum);
    }

    //takes in file name and predicts number
    public int predict(String fileName){
        throw new IllegalStateException("Not implemented yet, this is here so it compiles");
    }




}

class Layer{
    tensor iWeights;
    tensor oWeights;
    tensor activations;

}

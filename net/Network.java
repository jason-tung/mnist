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
    public Layer input_layer;
    public Layer output_layer;

    public Network(int numLayers, int numNodes, String activFunct, String lossFunc, String descent_mode, double bias){
        loss_function = new LossFunction(lossFunc);
        count_layers = numLayers;
        count_nodes = numNodes;
        this.descent_mode = descent_mode;
        activation_function = new Activation(activFunct);
        this.bias = bias;
    }

    public tensor getOneHot(){
        return null;
    }
    public tensor getCost(tensor oneHot){
        return loss_function.call(output_layer.activations, oneHot);
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
        return activation_function.apply(activationSum - bias);
    }

    //takes in file name and predicts number
    public int predict(String fileName){
        throw new IllegalStateException("Not implemented yet, this is here so it compiles");
    }




}

class Layer{
    public tensor iWeights;
    public tensor oWeights;
    public tensor activations;



}

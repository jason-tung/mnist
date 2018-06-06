package net;

import np.np;
import np.tensor;

public class Network{
    LossFunction loss_function;
    Layer[] layers;
    int count_layers;
    int count_nodes;
    String descent_mode;

    public Network(int numLayers, int numNodes, Activation activFunc, LossFunction lossFunc){
        loss_function = lossFunc;
        count_layers = numLayers;
        count_nodes = numNodes;
        descent_mode = activFunc.callType;
    }



    //descent
    public void fit(){

    }

    public void fdpass(){

    }

    public void bkpass(){

    }

    public static double getNewActivation(tensor activation, tensor weights){
        int[784]
    }

    //takes in file name and predicts number
    public int predict(String fileName){
        throw new IllegalStateException("Not implemented yet, this is here so it compiles");
    }




}

class Layer{

}

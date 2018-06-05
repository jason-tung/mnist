package net;

public class Network{
    LossFunction loss_function;
    Layer[] layers;
    int count_layers;
    int count_nodes;
    String descent_mode;

    public Network(int numLayers, int numNodes, Activation activFunc, LossFunction lossFunc){
    }

    //descent
    public void fit(){
    }

    //takes in file name and predicts number
    public int predict(String fileName){
        throw new IllegalStateException("Not implemented yet, this is here so it compiles");
    };


}

class Layer{

}

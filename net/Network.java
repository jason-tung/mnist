package net;

import java.util.*;

import np.np;
import np.tensor;
import py.*;


public class Network{
    LossFunction loss_function;
    ArrayList<Layer> layers;
    int count_layers; //hidden layers ?
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
        setupLayers();
        setupWeights();
    }

    /**
     * adds "count layers" number of hidden layers as well as the input and output to the layers array list
     */
    public void setupLayers(){
        layers = new ArrayList<>();
        for (int i = 0; i < count_layers; i++){
            layers.add(new Layer(count_nodes));
        }
        input_layer = new Layer(784);
        output_layer = new Layer (10);
        layers.add(0,input_layer);
        layers.add(output_layer);
    }

    /**
     * sets up the empty 2d tensors for the weights
     */
    public void setupWeights(){
        setupWeights(0);
    }

    /**
     * sets up weights for some index i in the layers array list
     * @param i the index of the arraylist layers
     */
    public void setupWeights(int i){
        if(i < layers.size() - 1){
            Layer ref = layers.get(i);
            
        }
    }
    //to be implemented
    public tensor getOneHot(String filename){
        return null;
    }

    public tensor getCostFunction(){
        int[] shape = {count_nodes};
        tensor costFunction = tensor.zeros(shape);
        //for all the files:
            //costFunction.s(NUMBER, getCost(oneHot));
        return costFunction;
    }

    public double getCost(tensor oneHot){
        return loss_function.call(output_layer.activations, oneHot);
    }

    //descent
    public void fit(){

    }

    /**
     * performs a full forward pass through the network
     */
    public void fdpass(){
        fdpass(1);
    }

    /**
     * helper function that will get the activations for each layer with a certain index
     * @param index index of the layer inside of the layer array
     */
    public void fdpass(int index){
        Layer ref = layers.get(index);

    }

    public void bkpass(){

    }

// TO BE FIXED
    public double singleActivation(tensor activation, tensor weights){
        double activationSum = 0;
        for (int i = 0; i < activation.shape[0]; i++){
            int[] shape = {i};
            activationSum += py.val(activation.g(shape)) * py.val(weights.g(shape));
        }
        return activation_function.apply(activationSum - bias);
    }

    /**
     * gives the full activation layer for the next layer
     * @param activation activation layer of current layer
     * @param weights weights for the current layer
     * @return activations for the next layer
     */
    public tensor nextActivation(tensor activation, tensor weights){

    }

    /**
     * reads a filename and predicts the number
     * @param fileName name of a 28x28 pixel jpg file
     * @return the number the model believes to be stored in the jpg
     */
    public int predict(String fileName){
        throw new IllegalStateException("Not implemented yet, this is here so it compiles");
    }




}

class Layer{
    public tensor weights;
    public tensor activations;

    /**
     * makes a layer with count_nodes dimensions
     * @param count_nodes
     */
    public Layer(int count_nodes){
        int[] shape = {count_nodes};
        activations = tensor.zeros(shape);
    }

}

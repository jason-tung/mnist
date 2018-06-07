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
    public Layer input_layer;
    public Layer output_layer;

    public Network(int numLayers, int numNodes, String activFunct, String lossFunc, String descent_mode){
        loss_function = new LossFunction(lossFunc);
        count_layers = numLayers;
        count_nodes = numNodes;
        this.descent_mode = descent_mode;
        activation_function = new Activation(activFunct);
        setupLayers(); //makes empty layers with the right activation length
        setupWeightsBiases(); //adds the empty weights and biases to the layers
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
     * sets up the empty 2d tensors for the weights and biases
     */
    public void setupWeightsBiases(){
        setupWeightsBiases(0);
    }

    /**
     * sets up weights & biases for some index i in the layers array list
     * @param i the index of the arraylist layers
     */
    public void setupWeightsBiases(int i){
        if(i < layers.size() - 1){
            Layer ref = layers.get(i);
            int nextLayerSize = layers.get(i + 1).activations.shape[0];
            int[] shape = {nextLayerSize, ref.activations.shape[0]};
            ref.weights = tensor.zeros(shape);
            int[] shapeBiases = {nextLayerSize, 1};
            ref.biases = tensor.zeros(shape);
            setupWeightsBiases(i + 1);
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
        fdpass(0);
    }

    /**
     * helper function which sets the next layer's (index + 1) activations
     * @param index index of the current layer inside of the layer array
     */
    public void fdpass(int index){
        if (index < layers.size() - 1){
            Layer ref = layers.get(index);
            Layer nxt = layers.get(index + 1);
            nxt.activations = ref.nextActivation(activation_function);
            fdpass(index + 1);
        }
    }

    public void bkpass(){

    }

//    /**
//     * broken function -- to be used as scrap
//     * @param activation
//     * @param weights
//     * @return
//     */
//    public double singleActivation(tensor activation, tensor weights){
//        double activationSum = 0;
//        for (int i = 0; i < activation.shape[0]; i++){
//            int[] shape = {i};
//            activationSum += py.val(activation.g(shape)) * py.val(weights.g(shape));
//        }
//        return activation_function.apply(activationSum );
//    }



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
    public tensor biases;

    /**
     * makes a layer with count_nodes dimensions
     * @param count_nodes
     */
    public Layer(int count_nodes){
        int[] shape = {count_nodes};
        activations = tensor.zeros(shape);
    }

    /**
     * gives the full activation layer for some layer
     * @param activationFunction the activation type for the network
     * @return activations for the next layer
     */
    public tensor nextActivation(Activation activationFunction){
        return activationFunction.apply(np.add(np.matmul(weights, activations), biases));
    }

}

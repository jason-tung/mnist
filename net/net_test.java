package net;

import np.*;
import static py.py.*;

import java.util.ArrayList;
import java.util.Arrays;

public class net_test {

    int num_layers;
    int[] num_nodes;
    ArrayList<Layer> layers;
    String loss;
    double error = 0;
    double learning_rate=0;


    public net_test(int num_layers, int[] num_nodes, Activation[] activation, String loss){
        this.num_layers = num_layers;
        this.num_nodes = num_nodes;
        layers = new ArrayList<>();
        this.loss = loss;

        if(num_layers != num_nodes.length)throw new IllegalArgumentException("num_nodes.length != num_layers");

        for(int i: range(num_layers)){
            if(i != num_layers - 1){
                Layer layer_i = new Layer(num_nodes[i], num_nodes[i+1], activation[i]);
                this.layers.add(layer_i);
            } else {
                Layer layer_i = new Layer(num_nodes[i], 0, activation[i]);
                this.layers.add(layer_i);
                System.out.println(i);
            }

        }
    }

    public void check_training(int batch_size, tensor X, tensor y){
        if(len(X) % batch_size != 0) throw new IllegalStateException("batch_size % samples != 0");
        if(len(X) != len(y)) throw new IllegalArgumentException("number of X samples != number of y");
        for(int i: range(len(X))){
            if(len(X.g(new int[]{i})) != this.num_nodes[0]) throw new IllegalStateException("len of each sample must == num input nodes");
            if(len(y.g(new int[]{i})) != this.num_nodes[num_nodes.length-1]) throw new IllegalStateException("len of each label must == num output nodes");
        }
    }

    public void train(tensor X, tensor y, int batch_size, int epochs, double learning_rate){
        this.learning_rate = learning_rate;
        check_training(batch_size, X, y);
        System.out.println(epochs);
        for(int i: range(epochs)){
            int k = 0;
            System.out.println("EPOCH: " + i + "/" + epochs);
            while(k+batch_size != len(X)){
                this.error = 0;
                tensor t = new tensor(slice(data(X), k, k+batch_size));
                forward_pass(new tensor(slice(data(X), k, k+batch_size)));
                calc_loss(new tensor(slice(data(y), k, k+batch_size)));
                back_pass(new tensor(slice(data(y), k, k+batch_size)));
                k += batch_size;
            }
            this.error /= batch_size;
            System.out.println("Error: " + this.error);
        }
    }

    public void forward_pass(tensor X){
        System.out.println(Arrays.toString(X.shape));
        this.layers.get(0).activations = X;
        for(int i: range(this.num_layers - 1)){
            System.out.println(i + " Shapes: " + Arrays.toString(this.layers.get(i).activations.shape) + " " + Arrays.toString(this.layers.get(i).weights.shape) + " " + Arrays.toString(this.layers.get(i).bias.shape));
            tensor tmp = np.add(
                    np.matmul(this.layers.get(i).activations,
                    this.layers.get(i).weights),
                    this.layers.get(i).bias);
            this.layers.get(i+1).activations = np.vectorize(tmp, this.layers.get(i+1).activ);
        }
    }

    public void calc_loss(tensor y){
        System.out.println(len(y.get(new int[]{0})) + " " +  this.layers.get(this.num_layers-1).num_nodes);
        if(len(y.get(new int[]{0})) != this.layers.get(this.num_layers-1).num_nodes) throw new IllegalArgumentException();
        if(this.loss.equals("mse")){
            class helper extends Callable{
                public Double call(double x){
                    return x / 2;
                }
            }
            this.error += np.mean(np.vectorize(np.square(np.subtract(y, np.log(this.layers.get(this.num_layers-1).activations))), new helper()));
        }
        if(this.loss.equals("cross_entropy")){
            this.error -= np.sum(np.multiply(y, np.log(this.layers.get(this.num_layers - 1).activations)));
        }
    }

    public void back_pass(tensor Y){
        int i = this.num_layers - 1;
        tensor y = this.layers.get(i).activations;

        class helper extends Callable{
            public Double call(double x){
                return 1-x;
            }
        }


        tensor deltab = np.multiply(y, np.multiply(np.vectorize(y, new helper()), np.subtract(Y, y)));
        tensor deltaw = np.matmul(this.layers.get(i-1).activations.T(), deltab);

        class helper2 extends Callable{
            public Double call(double x){
                return x * learning_rate;
            }
        }

        tensor n_weights = np.subtract(this.layers.get(i-1).weights, np.vectorize(deltaw, new helper2()));
        tensor n_bias = np.subtract(this.layers.get(i-1).bias, np.vectorize(deltab, new helper2()));

        for(int ind: range(i-1, 0, -1)) {
            y = this.layers.get(ind).activations;

//            deltab = np.multiply(y,
//                    np.multiply(np.vectorize(y, new helper()),
//                    np.multiply(n_bias, this.layers.get(ind).bias)));
            System.out.println(Arrays.toString(n_bias.shape) + " " + this.layers.get(ind).bias);
            deltab = np.multiply(y, np.multiply(np.vectorize(y, new helper()),
                    np.sum(
                            np.multiply(n_bias, this.layers.get(ind).bias))));
            deltaw = np.matmul((this.layers.get(ind-1).activations).T(), np.multiply(y, np.multiply(np.vectorize(y, new helper()), np.sum(np.multiply(n_weights, this.layers.get(ind).weights),1, true).T())));
            this.layers.get(ind).weights = n_weights;
            this.layers.get(ind).bias = n_bias;
            n_weights = np.subtract(this.layers.get(ind - 1).weights,
                    np.vectorize(deltaw, new helper2()));
            n_bias = np.subtract(this.layers.get(ind-1).bias,
                    np.vectorize(deltab, new helper2()));
        }
        this.layers.get(0).weights = n_weights;
        this.layers.get(0).bias = n_bias;
    }


    public class Layer{

        int num_nodes;
        int num_next;
        Activation activ;
        tensor activations;
        tensor weights;
        tensor bias;

        public Layer(int num_nodes, int num_next, Activation activation){
            this.num_next = num_next;
            this.num_nodes = num_nodes;
            this.activ = activation;
            this.activations = tensor.zeros(new int[]{num_nodes, 1});
            if(num_next != 0){
                this.weights = tensor.rand_normal(new int[]{num_nodes, num_next}, 0.0, 0.001);
                this.bias = tensor.rand_normal(new int[]{1, num_next}, 0.0, 0.001);
                System.out.println("CONSTRUCT_SHAPES: " + Arrays.toString(this.activations.shape) + " " + Arrays.toString(this.weights.shape) + " " + Arrays.toString(this.bias.shape));
            } else {
                System.out.println("CONSTRUCT_ACTIVATION " + Arrays.toString(this.activations.shape));
            }
        }
    }



}

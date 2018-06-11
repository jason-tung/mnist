package net;

import np.quantity;
import np.tensor;
import np.np;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

import static py.py.*;

public class driver {

    //Helper functions for loading and training with mnist
    public static ArrayList<String> showFiles(String directory) {
        File[] files = new File(directory).listFiles();
        ArrayList<String> res = new ArrayList<>();
        for (File file : files) {
            if (file.isDirectory()) {
                throw new IllegalStateException("Given directory contains subdirectories");
            } else {
                res.add(directory + "\\" +  file.getName());
            }
        }
        return res;
    }

    public static ArrayList<Double> mnist_help(String directory) {
        File[] files = new File(directory).listFiles();
        ArrayList<Double> res = new ArrayList<>();
        for (File file : files) {
            if (file.isDirectory()) {
                throw new IllegalStateException("Given directory contains subdirectories");
            } else {
                res.add((double) Integer.parseInt("" + file.getName().charAt(0)));
            }
        }
        return res;
    }

    /**
     * Trains a model on a dataset
     * @param data_directory Directory where the images are stored
     * @param save_directory Directory to save trained models
     */
    public static void train_mnist_net(String data_directory, String save_directory){
        String dirname = data_directory;


        ArrayList<quantity> tmpx = new ArrayList<>();
        ArrayList<Double> tmpy = new ArrayList<>();


        for (String file: showFiles(dirname)){
            System.out.println(file);
            tmpx.add(Preprocessing.parse(file));
        }

        for (Double val: mnist_help(dirname)){
            tmpy.add(val);
        }


        //Shuffle training data so network learns all digits not just one at a time
        ArrayList<Integer> shuff = list(range(tmpy.size()));
        Collections.shuffle(shuff);

        ArrayList<quantity> n_tmpx = new ArrayList<>();
        ArrayList<Double> n_tmpy = new ArrayList<>();

        for(Integer i: shuff){
            n_tmpx.add(tmpx.get(i));
            n_tmpy.add(tmpy.get(i));
        }


        tmpx = n_tmpx;
        tmpy = n_tmpy;

        tensor y = tensor.zeros(new int[]{tmpy.size()});

        for (int[] i : y) {
            y.s(i, tmpy.get(i[0]));
        }

        tensor x = new tensor(tmpx);
        y = Preprocessing.to_categorical(y, 10);

        neural_net mnist_net = new neural_net(784, 100, 10, 0.1);

        mnist_net.train(x, y, 100, 20, save_directory);
    }

    /**
     * Returns the label of an image given a filename
     * @param n a trained neural network
     * @param filepath path to a mnist image
     * @return the predicted label of the image
     */

    public static int predict(neural_net n, String filepath){

        tensor img = Preprocessing.parse(filepath);
        tensor[] tmp = new tensor[1];
        tmp[0] = img;
        tensor f = n.predict(new tensor(tmp));
        for(int[] i: f){
            return (int) val(f.get(i));
        }
        throw new IllegalStateException("this is here so it compiles error");
    }



    public static void main(String[] args){


        System.out.println("to train a model run train_mnist_net. You need to provide a directory where the images are located, and a directory to save your models");

        String data_directory = "C:\\Users\\Jason\\IdeaProjects\\mnist\\training_sets\\validation";
        String save_directory = "C:\\Users\\Jason\\IdeaProjects\\mnist\\net\\saved_models";
        //To train the network uncomment the below line:
        train_mnist_net(data_directory, save_directory);
        neural_net net = neural_net.load_from_file("C:\\Users\\Jason\\IdeaProjects\\mnist\\net\\saved_models\\3.ser");
        for (String file: showFiles(data_directory)) {
            System.out.println(file + predict(net, file));
        }

    }
}

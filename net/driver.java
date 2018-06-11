package net;

import np.quantity;
import np.tensor;
import np.np;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

import static py.py.list;
import static py.py.range;

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

        nn mnist_net = new nn(784, 300, 10, 1e-6);

        mnist_net.train(x, y, 100, 5, save_directory);
    }



    public static void main(String[] args){

        System.out.println("to train a model uncomment the below line and modify the paths");
        train_mnist_net("C:\\Users\\Jason\\IdeaProjects\\mnist\\training_sets\\validation", "C:\\Users\\Jason\\IdeaProjects\\mnist\\net\\saved_models");


//        nn n = nn.load_from_file("C:\\Users\\Jason\\IdeaProjects\\mnist\\net\\saved_models\\1.ser");
//
//        for(String file:showFiles("C:\\Users\\Jason\\IdeaProjects\\mnist\\training_sets\\validation")){
//            tensor[] t = new tensor[1];
//            t[0] = Preprocessing.parse(file);
//            tensor k = new tensor(t);
//            System.out.println(file + " " + np.argmax(n.predict(k), 1) + " " + n.predict(k));
//
//        }

    }


}

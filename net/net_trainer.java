package net;
import np.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import static net.Preprocessing.parse;
import static net.Preprocessing.to_categorical;

public class net_trainer {
    public static void main(String[] args){
//        String dir = "C:\\Users\\Jason\\IdeaProjects\\mnist\\training_sets\\train";
//        File[] files = new File(dir).listFiles();
//        ArrayList<quantity> t = new ArrayList<>();
//        ArrayList<Integer> labels = new ArrayList<>();
//        int k = 1;
//        for(File f: files){
//            String s = dir + '\\' + f.getName();
//            labels.add(Integer.parseInt("" + f.getName().charAt(0)));
//            t.add(parse(s));
//            k++;
//            if (k > 200) break;
//        }

        tensor x = tensor.rand_normal(new int[]{100, 784});
        tensor y = tensor.zeros(new int[]{100});
        y = to_categorical(y, 10);
        System.out.println(Arrays.toString(y.shape));

        net_test net = new net_test(3, new int[]{784, 20, 10}, new Activation[]{new Activation("none"), new Activation("tanh"), new Activation("sigmoid")}, "cross_entropy");

        net.train(x, y, 1, 50, 0.001);



    }
}

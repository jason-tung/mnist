package net;
import np.*;

import java.io.*;

import java.util.ArrayList;
import java.util.Collections;

import static net.Activation.expand;
import static py.py.*;

public class nn implements Serializable{


    private int D_in;
    private int H;
    private int D_out;

    private tensor w1;
    private tensor w2;

    private double lr;

    /**
     * Create a new neural network with the following parameters:
     * @param D_in The dimensionality of each sample. i.e. a mnist image would be 784 (28*28 image --> 784 D vector)
     * @param H Number of neurons in the hidden layer
     * @param D_out The dimensionality of each output. i.e. a mnist image has 10 classes, so this would be 10
     * @param lr Learning rate of the model
     */
    public nn(int D_in, int H, int D_out, double lr){

        this.D_in = D_in;
        this.H = H;
        this.D_out = D_out;

        w1 = tensor.rand_normal(new int[]{D_in, H}, 0.25, 1.25);
        w2 = tensor.rand_normal(new int[]{H, D_out}, 0.25, 1.25);

        this.lr = lr;
    }

    public void train(tensor x, tensor y, int epochs, int batch_size){
        train(x, y, epochs, batch_size, "");
    }

    public void train(tensor x, tensor y, int epochs, int batch_size, String savedir){
        if(x.shape[1] != D_in || y.shape[1] != D_out || x.shape[0] != y.shape[0]) throw new IllegalArgumentException("Shape mismatch");

        for(int i: range(epochs)){

            for(int batch: range(batch_size, len(x), batch_size)) {

                int start = batch;
                int end = batch + batch_size;

                tensor X = new tensor(slice(data(x), start, end));
                tensor Y = new tensor(slice(data(y), start, end));

                X = X.clone(); //Safety measure, shapes might get reused and changes happening can mess everything up by reference
                Y = Y.clone();

                //Forward pass
                tensor h = np.matmul(X, w1);
                tensor h_relu = relu(h);
                tensor y_pred = np.matmul(h_relu, w2);


                //Calculate loss
                double loss = np.sum(np.square(np.subtract(y_pred, Y)));


                //Backwards pass and calculate gradient (backpropagation)
                tensor grad_y_pred = np.multiply(np.subtract(y_pred, Y), 2.0);
                tensor grad_w2 = np.matmul(h_relu.T(), grad_y_pred);
                tensor grad_h_relu = np.matmul(grad_y_pred, w2.T());
                tensor grad_h = relu(grad_h_relu);
                tensor grad_w1 = np.matmul(X.T(), grad_h);


                //Update weights
//                print(grad_w1);
//                print(grad_w2);
                w1 = np.subtract(w1, np.multiply(grad_w1, lr));
                w2 = np.subtract(w2, np.multiply(grad_w2, lr));


                System.out.println("Epoch " + i + " loss: " + loss + " Sample: " + batch + " / " + len(x));
            }
            if(!savedir.equals("")){
                this.save(savedir + '\\' + i + ".ser");
            }

        }

    }
    public tensor predict(tensor x){

        //Forward pass
        tensor h = np.matmul(x, w1);
        tensor h_relu = relu(h);
        return np.matmul(h_relu, w2);
    }


    private static tensor relu(tensor x){
        class helper extends Callable{
            public Double call(double x){
                return Math.max(x, 0);
            }
        }
        return np.vectorize(x, new helper());
    }




    public void save(String path){
        try{
            FileOutputStream out = new FileOutputStream(path);
            ObjectOutputStream objOut = new ObjectOutputStream(out);
            objOut.writeObject(this);
            objOut.close();
            System.out.println("saved to " + path);

        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public static nn load_from_file(String path){
        try {
            FileInputStream fis = new FileInputStream(path);
            ObjectInputStream ois = new ObjectInputStream(fis);
            nn result = (nn) ois.readObject();
            ois.close();
            return result;
        } catch (Exception ex){
            System.out.println("I know that this is bad practice okay");
            ex.printStackTrace();
        }
        throw new IllegalStateException("this is here so it compiles lmao");
    }




    public static void main(String[] args) {
//        String dirname = "C:\\Users\\Jason\\IdeaProjects\\mnist\\training_sets\\train";
//
//
//        ArrayList<quantity> tmpx = new ArrayList<>();
//        ArrayList<Double> tmpy = new ArrayList<>();
//
//
//        for (String file: showFiles(dirname)){
//            System.out.println(file);
//            tmpx.add(Preprocessing.parse(file));
//        }
//
//        for (Double val: mnist_help(dirname)){
//            tmpy.add(val);
//        }
//
//
//        //Shuffle training data so network learns all digits not just one at a time
//        ArrayList<Integer> shuff = list(range(tmpy.size()));
//        Collections.shuffle(shuff);
//
//        ArrayList<quantity> n_tmpx = new ArrayList<>();
//        ArrayList<Double> n_tmpy = new ArrayList<>();
//
//        for(Integer i: shuff){
//            n_tmpx.add(tmpx.get(i));
//            n_tmpy.add(tmpy.get(i));
//        }
//        tmpx = n_tmpx;
//        tmpy = n_tmpy;
//
//        tensor y = tensor.zeros(new int[]{tmpy.size()});
//
//        for (int[] i : y) {
//            y.s(i, tmpy.get(i[0]));
//        }
//
//        tensor x = new tensor(tmpx);
//        y = Preprocessing.to_categorical(y, 10);
//
//        nn mnist_net = new nn(784, 100, 10, 1e-6);
//
//        mnist_net.train(x, y, 100, 5, "C:\\Users\\Jason\\IdeaProjects\\mnist\\net\\saved_models");

    }


}



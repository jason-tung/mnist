package net;
import np.*;
import static py.py.*;

public class nn {

    private static tensor relu(tensor x){
        class helper extends Callable{
            public Double call(double x){
                return Math.max(x, 0);
            }
        }
        return np.vectorize(x, new helper());
    }

    public static void main(String[] args){

        int N = 64;
        int D_in = 784;
        int H = 100;
        int D_out = 10;



        tensor x = tensor.rand_normal(new int[]{N, D_in});
        tensor y = tensor.rand_normal(new int[]{N, D_out});

        tensor w1 = tensor.rand_normal(new int[]{D_in, H});
        tensor w2 = tensor.rand_normal(new int[]{H, D_out});

        double lr = 1e-6;

        for(int i: range(500)){
            tensor h = np.matmul(x, w1);

            tensor h_relu = relu(h);
            tensor y_pred = np.matmul(h_relu, w2);

            double loss = np.sum(np.square(np.subtract(y_pred, y)));

            tensor grad_y_pred = np.multiply(np.subtract(y_pred, y), 2.0);
            tensor grad_w2 = np.matmul(h_relu.T(), grad_y_pred);
            tensor grad_h_relu = np.matmul(grad_y_pred, w2.T());
            tensor grad_h = relu(grad_h_relu);
            tensor grad_w1 = np.matmul(x.T(), grad_h);

            w1 = np.subtract(w1, np.multiply(grad_w1, lr));
            w2 = np.subtract(w2, np.multiply(grad_w2, lr));


            System.out.println("Epoch " + i + " loss: " + loss);


        }


    }


}

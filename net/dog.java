package net;
import np.*;
import java.util.*;
import static py.py.*;

public class dog {

    public static ArrayList<tensor> feed_forward(tensor x, ArrayList<tensor> weights){
        ArrayList<tensor> a = new ArrayList<>();
        a.add(x);
        for (tensor w : weights){
            a.add(np.max(np.matmul(a.get(a.size() - 1), w), 0));
        }
        return a;
    }

    public static tensor grads(tensor x, tensor y, ArrayList<tensor> weights){
        tensor grads = tensor.zeros(weights.shape());
        ArrayList<tensor> a = feed_forward(x, weights);
        tensor delta = np.subtract(a.get(a.size() - 1), y );
        //grads[-1] = a[-2].T().dot(delta)
        for (int i : range(a.size() - 2, 0, -1)){
            //delta = nn.relu(np.matmul (delta, weights[i].T());
            //grads[i-1] = np.matmul(a.get(i-1).T(), delta)
        }
        //return divide(grads, x.shape[0]);
    }

    private static tensor divide(tensor t, double d){
        tensor rTensor = tensor.zeros(t.shape());
        for (int[] a : t){
            rTensor.set(a, val(t.get(a))/ d);
        }
        return rTensor;
    }


}

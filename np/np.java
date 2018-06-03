package np;

import java.util.*;
import java.lang.Math;
import static py.py.*;

public class np {

    //special operations that require specification of axis
    //this requires a dfs algorithm that I will implement later, i.e. rowMaxs will be replaced with
    //max(tensor t, int axis) instead of finding the global max

    //Actually it'll just be easier if for these methods we do 2D only

    //np.argmax() goes here
    //np.

    public static double mean(tensor a){
        int sum = 0;
        int count = 0;
        for (int[] index: a){
            sum += val(a.g(index));
            count += 1;
        }
        return sum / count;
    }

    public static tensor rowMaxs(tensor x) {
        int[] f = {x.shape[0]};
        tensor rTensor = tensor.zeros(f);
        for (int i = 0; i < x.shape[0]; i++) {
            int[] g = {i, 0};
            Double max = val(x.get(g));
            for (int j = 1; j <= x.shape[1]; j++) {
                int[] h = {i, j};
                if (val(x.get(h)) > max) {
                    max = val(x.get(h));
                }
            }
            int[] j = {i};
            rTensor.set(j, max);
        }
        return rTensor;
    }


    public static double sum(tensor arr) {
        return 0;
    }

    public static double max(tensor t) {
        double max = val(t.get(new int[t.shape.length]));
        for(int[] i: t){
            max = Math.max(max, val(t.get(i)));
        }
        return max;
    }

    public static double min(tensor t) {
        double min = val(t.get(new int[t.shape.length]));
        for(int[] i: t){
            min = Math.min(min, val(t.get(i)));
        }
        return min;
    }

    //np.matmul() goes here

    //dot and matmul and helper for them here

    private static tensor get_col(tensor a, int ind){
        if(!(a.shape.length==2)) throw new IllegalArgumentException("both tensors in matmul must be 2D");
        if(ind >= a.shape[1]) throw new IndexOutOfBoundsException();
        tensor r = tensor.zeros(new int[]{a.shape[0]});
        for(int i:range(a.shape[0])){
            r.s(new int[]{i}, val(a.g(new int[]{i, ind})));
        }
        return r;
    }

    public static tensor matmul(tensor a, tensor b){
        if(!(a.shape.length==2 && b.shape.length==2)) throw new IllegalArgumentException("both tensors in matmul must be 2D");
        if(!(a.shape[1] == b.shape[0])) throw new IllegalArgumentException("Cannot multiply tensors because cols a != rows b");
        tensor r = tensor.zeros(new int[]{a.shape[0], b.shape[1]});
        for(int[] i: r){
            r.s(i, dot(get_col(b, i[1]), (tensor) a.g(new int[]{i[0]})));
        }
        return r;
    }

    public static double dot(tensor a, tensor b){
        if(!(a.shape.length==1 && b.shape.length==1)) throw new IllegalArgumentException("both tensors in dot product must be 1D");
        if(!(a.shape[0]==b.shape[0])) throw new IllegalArgumentException("1D vectors don't have the same length");
        double sum = 0;
        for(int[] i: a){
            sum += val(a.g(i)) * val(b.g(i));
        }
        return sum;
    }




    //regular arithmetic /logical operators for two tensors
    //These now support ndarrays now that tensor is iterable, all arithmetic operators are here:

    public static boolean equal(tensor arr1, tensor arr2) {
        if(!Arrays.equals(arr1.shape, arr2.shape)) return false;
        for(int[] i: arr1){
            if (val(arr1.g(i)) != val(arr2.g(i))) return false;
        } return true;
    }


    public static tensor subtract(tensor a, tensor b) {
        if (Arrays.equals(a.shape, b.shape)) {
            tensor rTensor = tensor.zeros(a.shape);
            for (int[] index : a) {
                rTensor.s(index, val(a.g(index)) - val(b.g(index)));
            }
            return rTensor;
        }
        throw new IllegalArgumentException("tensors of different shapes");
    }

    public static tensor multiply(tensor a, tensor b) {
        if (Arrays.equals(a.shape, b.shape)) {
            tensor rTensor = tensor.zeros(a.shape);
            for (int[] index : a) {
                rTensor.s(index, val(a.g(index)) * val(b.g(index)));
            }
            return rTensor;
        }
        throw new IllegalArgumentException("tensors of different shapes");
    }

    public static tensor add(tensor a, tensor b) {
        if (Arrays.equals(a.shape, b.shape)) {
            tensor rTensor = tensor.zeros(a.shape);
            for (int[] index : a) {
                rTensor.s(index, val(a.g(index)) + val(b.g(index)));
            }
            return rTensor;
        }
        throw new IllegalArgumentException("tensors of different shapes");
    }

    public static tensor divide(tensor a, tensor b) {
        if (Arrays.equals(a.shape, b.shape)) {
            tensor rTensor = tensor.zeros(a.shape);
            for (int[] index : a) {
                rTensor.s(index, val(a.g(index)) / val(b.g(index)));
            }
            return rTensor;
        }
        throw new IllegalArgumentException("tensors of different shapes");
    }

    //single tensor regular operators

    public static tensor vectorize(tensor a, Callable k){
        tensor rTensor = tensor.zeros(a.shape);
        for (int[] index : a) {
            rTensor.s(index, k.call(val(a.g(index))));
        }
        return rTensor;
    }

    public static tensor log(tensor a, double base) {
        tensor rTensor = tensor.zeros(a.shape);
        for (int[] index : a) {
            rTensor.s(index, Math.log(val(a.g(index))) / Math.log(base));
        }
        return rTensor;
    }

    public static tensor exp(tensor a, double power) {
        tensor rTensor = tensor.zeros(a.shape);
        for (int[] index : a) {
            rTensor.s(index, Math.pow(val(a.g(index)), power));
        }
        return rTensor;
    }

    public static tensor log(tensor a) {
        return log(a, Math.E);
    }

    public static tensor exp(tensor a) {
        return exp(a, Math.E);
    }

    public static tensor square(tensor a){
        return exp(a, 2);
    }

    public static tensor sqrt(tensor a){
        return exp(a, 0.5);
    }

    //additional constructors for tensor

    ///make identity matrix
    public static tensor eye(int n){
        tensor t = tensor.zeros(new int[]{n, n});
        for(int i: range(n)){
            t.set(new int[]{i, i}, 1.0);
        }
        return t;
    }

    public static void main(String[] args){
        tensor a = tensor.ones(new int[]{3, 3});
        tensor c = tensor.ones(new int[]{3, 3});

        tensor q = np.add(a, c) ;
        tensor b = tensor.ones(new int[]{3, 2});


        System.out.println(matmul(q, b));
    }


}

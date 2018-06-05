package np;

import java.util.*;
import java.lang.Math;
import static py.py.*;

/**
 * Class with functions to manipulate tensors
 */
public class np {

    //special operations that require specification of axis
    //this requires a dfs algorithm that I will implement later, i.e. rowMaxs will be replaced with
    //max(tensor t, int axis) instead of finding the global max

    //Actually it'll just be easier if for these methods we do 2D only

    //np.argmax() goes here

    //defining an algorithm to collapse an axis for these implementations.

    //iter_all returns all indices, need to iterate in a certain order - ind of select axis change most


    //nice hack that lets me not code anything complicated:
    //build a new tensor with the same shape, except swap shape's last index with shape[axis]
    //iterate through this order, but swap the axis' back for appropriate order
    //this iterates through everything through a certain axis really nicely

    private static int[] rm_one(int[] ar, int i){
        if(i >= ar.length) throw new IndexOutOfBoundsException();
        ArrayList<Integer> tmp = new ArrayList<>();
        for(int v: range(ar.length)){
            if (v != i){
                tmp.add(ar[v]);
        }
    }
        return tmp.stream().mapToInt(z->z).toArray();
}

    private static ArrayList<int[][]> build_partitions(tensor t, int axis){
        if(axis >= t.shape.length) throw new IndexOutOfBoundsException();
        int[][] v = t.ordered_iterate(axis);
        return partition(v, t.shape[axis]);
    }

    private static tensor gen_newtensor(tensor t, int axis){
        return tensor.zeros(rm_one(t.shape, axis));
    }

    /**
     *
     * @param a tensor object that you want to take the mean of
     * @param axis axis that is collapsed - the means are taken along this axis
     * @return a tensor where entries along axis axis have been collapsed into one mean entry
     */
    public static tensor mean(tensor a, int axis){

        tensor r = gen_newtensor(a, axis);
        ArrayList<int[][]> partitions = build_partitions(a, axis);
        for(int[][] i: partitions){
            int count = 0;
            double sum = 0;
            for(int[] index:i){
                count += 1;
                sum += val(a.g(index));
            }
            r.set(rm_one(i[0], axis), sum / count);
        }
        return r;
    }

    /**
     *
     * @param a tensor object
     * @return global mean of a
     */
    public static double mean(tensor a){
        int sum = 0;
        int count = 0;
        for (int[] index: a){
            sum += val(a.g(index));
            count += 1;
        }
        return sum / count;
    }
    /**
     *
     * @param a tensor object that you want to take the sum of
     * @param axis axis that is collapsed - the sums are taken along this axis
     * @return a tensor where entries along axis axis have been collapsed into one sum entry
     */
    public static tensor sum(tensor a, int axis){

        tensor r = gen_newtensor(a, axis);
        ArrayList<int[][]> partitions = build_partitions(a, axis);
        for(int[][] i: partitions){
            double sum = 0;
            for(int[] index:i){
                sum += val(a.g(index));
            }
            r.set(rm_one(i[0], axis), sum);
        }
        return r;
    }

    /**
     *
     * @param t tensor object
     * @return global sum of t
     */
    public static double sum(tensor t) {
        double sum = 0;
        for(int[] i: t){
            sum += val(t.get(i));
        }
        return sum;
    }

    /**
     *
     * @param a tensor object that you want to take the max of
     * @param axis axis that is collapsed - the maxes are taken along this axis
     * @return a tensor where entries along axis axis have been collapsed into one max entry
     */
    public static tensor max(tensor a, int axis){

        tensor r = gen_newtensor(a, axis);
        ArrayList<int[][]> partitions = build_partitions(a, axis);
        for(int[][] i: partitions){
            double max = val(a.get(i[0]));
            for(int[] index:i){
                max = Math.max(val(a.g(index)), max);
            }
            r.set(rm_one(i[0], axis), max);
        }
        return r;
    }

    /**
     *
     * @param t tensor object
     * @return global max of t
     */
    public static double max(tensor t) {
        double max = val(t.get(new int[t.shape.length]));
        for(int[] i: t){
            max = Math.max(max, val(t.get(i)));
        }
        return max;
    }
    /**
     *
     * @param a tensor object that you want to take the min of
     * @param axis axis that is collapsed - the mins are taken along this axis
     * @return a tensor where entries along axis axis have been collapsed into one min entry
     */
    public static tensor min(tensor a, int axis){

        tensor r = gen_newtensor(a, axis);
        ArrayList<int[][]> partitions = build_partitions(a, axis);
        for(int[][] i: partitions){
            double min = val(a.get(i[0]));
            for(int[] index:i){
                min = Math.min(val(a.g(index)), min);
            }
            r.set(rm_one(i[0], axis), min);
        }
        return r;
    }

    /**
     *
     * @param t tensor object
     * @return global min of t
     */
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

    /**
     * Performs matrix multiplication on two tensors a and b. Only works on 2D and lower tensors.
     * @param a tensor object
     * @param b tensor object
     * @return a tensor that is equivalent to a * b
     */
    public static tensor matmul(tensor a, tensor b){
        if(!(a.shape.length==2 && b.shape.length==2)) throw new IllegalArgumentException("both tensors in matmul must be 2D");
        if(!(a.shape[1] == b.shape[0])) throw new IllegalArgumentException("Cannot multiply tensors because cols a != rows b");
        tensor r = tensor.zeros(new int[]{a.shape[0], b.shape[1]});
        for(int[] i: r){
            r.s(i, dot(get_col(b, i[1]), (tensor) a.g(new int[]{i[0]})));
        }
        return r;
    }

    /**
     * Finds the dot product of two 1D tensors
     * @param a tensor object
     * @param b tensor object
     * @return a dot b
     */
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

    /**
     *
     * @param t1 tensor object
     * @param t2 tensor object
     * @return boolean that is true is t1 is equivalent to t2 otherwise false
     */
    public static boolean equal(tensor t1, tensor t2) {
        if(!Arrays.equals(t1.shape, t2.shape)) return false;
        for(int[] i: t1){
            if (val(t1.g(i)) != val(t2.g(i))) return false;
        } return true;
    }

    /**
     * Creates a new tensor where each element is the corresponding element in a - the corresponding element in b
     * @param a tensor object
     * @param b tensor object
     * @return tensor that is the element-wise subtraction of a and b (a-b)
     */
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
    /**
     * Creates a new tensor where each element is the corresponding element in a * the corresponding element in b
     * @param a tensor object
     * @param b tensor object
     * @return tensor that is the element-wise multiplication of a and b (a*b)
     */
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
    /**
     * Creates a new tensor where each element is the corresponding element in a + the corresponding element in b
     * @param a tensor object
     * @param b tensor object
     * @return tensor that is the element-wise addition of a and b (a+b)
     */
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

    /**
     * Creates a new tensor where each element is the corresponding element in a / the corresponding element in b
     * @param a tensor object
     * @param b tensor object
     * @return tensor that is the element-wise division of a and b (a/b)
     */

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

    /**
     * Loops through each element in a and applies k.call(element) to it. The aggregation of the results from each
     * call form a new tensor that is returned. For example if the call(double) in a callable is defined as:
     * public Double call(double d){return d*2;}
     *
     * vectorize(t, callable) will return a tensor where each element in t is multiplied by 2
     *
     * @param a tensor object
     * @param k Callable object with Double call(double) defined
     * @return a new tensor where each element from a is modified by k
     */

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

    /**
     *
     * @param n number of rows and cols
     * @return a 2D tensor that represents the identity matrix of size n
     */
    public static tensor eye(int n){
        tensor t = tensor.zeros(new int[]{n, n});
        for(int i: range(n)){
            t.set(new int[]{i, i}, 1.0);
        }
        return t;
    }

    public static void main(String[] args){
        tensor a = tensor.rand_normal(new int[]{3, 4});
        tensor c = tensor.rand_normal(new int[]{3, 4});
        System.out.println(a);

        System.out.println(max(a, 1));

        System.out.println(mean(a, 1));

        tensor q = np.add(a, c) ;
        tensor b = tensor.ones(new int[]{4, 2});


        tensor k = matmul(q, b);

        print(k);

        tensor z = matmul(k, eye(2));
        print(z);

    }


}

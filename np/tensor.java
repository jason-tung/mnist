package np;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.*;
import static py.py.*;

/**
 * Implementation of tensors (ndarrays) in java
 */
public class tensor extends quantity implements Iterable<int[]>{

    private class scalar extends quantity{
        private Double data;
        public quantity[] __data__(){throw new UnsupportedOperationException("Use val() for scalars, data() on tensors");}
        public scalar(Double value){ this.data = value; }
        public String toString(){ return __str__(); }
        public String __str__(){ return Double.toString(data); }
        public double __val__(){ return this.data; }
        public int __len__(){ throw new UnsupportedOperationException("You tried to call len() on a scalar, scalars don't have a length!"); }
        public Object g(){ return __val__(); }
        public quantity g(int[] a, int b){ return this; }
        public void s(int[] a, Double b){this.data = b; }
        public tensor transpose(){throw new UnsupportedOperationException("You tried to call transpose() on a scalar");}
        public int[] shape(){throw new UnsupportedOperationException();}
    }
    public int[] shape(){return shape.clone();}

    //public constructors and helper classes for function passing

    static class ones extends Callable{
        public Double call(){
            return 1.0;
        }
    }

    static class zeros extends Callable{
        public Double call() {
            return 0.0;
        }
    }

    static class normal extends Callable{
        Double mean;
        Double variance;
        Random r = new Random();

        public normal(){
            this.mean = 0.0;
            this.variance = 1.0;
        }

        public normal(Double mean, Double variance){
            this.mean = mean;
            this.variance = variance;
        }

        public Double call(){
            return mean + r.nextGaussian()*variance;
        }
    }

    private quantity[] data;
    public int[] shape;

    /**
     *
     * @param shape desired shape of the resulting tensor
     * @return tensor filled with zeros of shape shape
     */

    public static tensor zeros(int[] shape){
        return new tensor(shape, new zeros());
    }

    /**
     *
     * @param shape desired shape of the resulting tensor
     * @return tensor filled with ones of shape shape
     */

    public static tensor ones(int[] shape){
        return new tensor(shape, new ones());
    }

    /**
     *
     * @param shape desired shape of the resulting tensor
     * @param k A callable object where Double call() is defined
     * @return tensor filled by a user-defined function of shape shape
     */

    public static tensor fill_function(int[] shape, Callable k){return new tensor(shape, k);};

    /**
     *
     * @param shape desired shape of the resulting tensor
     * @return tensor filled from a gaussian distribution of shape shape. mean = 0, variance = 1
     */
    public static tensor rand_normal(int[] shape){
        return rand_normal(shape, 0.0, 1.0);
    }

    /**
     *
     * @param shape desired shape of the resulting tensor
     * @param mean mean of gaussian distribution
     * @param variance variance of gaussian distribution
     * @return tensor filled from a gaussian distribution of shape shape. mean = mean, variance = variance.
     */

    public static tensor rand_normal(int[] shape, Double mean, Double variance){ return new tensor(shape, new normal(mean, variance)); }

    /**
     * Constructs a new tensor from a bunch of smaller tensors with the same shape
     * @param tarr an array of tensors
     */
    public tensor(quantity[] tarr){
        int[] shape = tarr[0].shape();
        for(int i=0; i < tarr.length; i++){
            if(!Arrays.equals(shape, tarr[i].shape())) throw new IllegalArgumentException("Shapes of tensors not constant");
            tarr[i] = np.exp((tensor) tarr[i], 1);
        }
        ArrayList<Integer> tmp = new ArrayList<>();
        tmp.add(tarr.length);
        for(int i: tarr[0].shape()){
            tmp.add(i);
        }

        this.shape = tmp.stream().mapToInt(i -> i).toArray();
        this.data = tarr;
    }

    public tensor(ArrayList<quantity> t){
        this(t.toArray(new quantity[t.size()]));
    }

    //private constructor for flexible construction and helper methods
    private tensor(int[] shape, Callable fill_func){
        this.data = new quantity[shape[0]];
        this.shape = shape;
        construct(this.data, this.shape, fill_func);
    }

    private int len(int[] obj){return obj.length;}

    private void construct(quantity[] current_obj, int[] shape, Callable fill_func){
        int[] nshape = Arrays.copyOfRange(shape, 1, shape.length);
        int val = shape[0];
        if(len(nshape)==0){
            for(int i = 0; i < val; i++){
                current_obj[i] = new scalar(fill_func.call());
            }
        } else{
            for(int i = 0; i < val; i++){
                tensor t = new tensor(nshape, fill_func);
                current_obj[i] = t;
            }
        }
    }

    //getters, setters, and toString

    /**
     * Alias for tensor.g()
     * @param inds
     * @return
     */

    public quantity get(int[] inds){
        return g(inds);
    }

    /**
     * Alias fr tensor.s()
     * @param inds
     * @param newval
     */

    public void set(int[] inds, Double newval){
        s(inds, newval);
    }

    /**
     * returns the value of an index in the tensor
     * @param inds An index in the tensor you want to retrieves
     * @return Quantity object located at inds
     */

    public quantity g(int[] inds){
        return g(inds, 0);
    }

    public quantity g(int[] inds, int pos){
        if(pos==len(inds)) return this;
        return data[inds[pos]].g(inds, pos+1);
    }

    /**
     * Changes the value of one scalar in the tensor.
     *
     * @param inds the index of the area in the tensor you want to change
     * @param newval the value you want to change the entry in tensor[inds] to
     */

    public void s(int[] inds, Double newval){
        if(len(inds) != len(shape)) throw new UnsupportedOperationException("Cannot set value for entire tensors");
        quantity scal = g(inds);
        scal.s(inds, newval);

    }

    /**
     *
     * @return a tensor that represents the transpose of the current tensor. Only works on 2D and under tensors.
     */

    public tensor transpose(){
        if(len(this.shape) > 2) throw new UnsupportedOperationException("I'm too stupid to implement transpose for 3D and above arrays");
        if(len(this.shape) == 1){
            tensor t = tensor.zeros(this.shape.clone());
            for(int[] i: this){
                t.s(i, val(g(i)));
            }
            return t;
        }
        tensor t = tensor.zeros(new int[]{this.shape[1], this.shape[0]});
        for(int[] i: this){
            int[] n = new int[]{i[1], i[0]};
            t.s(n, val(this.g(i)));
        }
        return t;
    }

    public tensor T(){
        return this.transpose();
    }

    public tensor reshape(int[] target_shape){
        int s1=1;
        for(int i: this.shape){
            s1 *=i;
        }
        int s2=1;
        for(int i:target_shape){
            s2*=i;
        }

        if(s1 != s2){
            System.out.println(Arrays.toString(this.shape));
            throw new IllegalArgumentException("sizes are different");
        }
        tensor res = tensor.zeros(target_shape);
        ArrayList<int[]> map_to = list(res);
        ArrayList<int[]> map_from = list(this);
        for(int i = 0; i < map_to.size(); i++) {
            res.s(map_to.get(i), val(this.g(map_from.get(i))));
        }
        return res;
    }

    /**
     * @param c Callable object with call_bool defined - call_bool returns a boolean given a double
     * @return an ArrayList of all indices in the tensor where the value meets the criteria in call_bool
     */
    public ArrayList<int[]> find(Callable c){
        ArrayList<int[]> res = new ArrayList<>();
        for(int[] i: this) {
            if (c.call_bool(val(this.g(i)))) {
                res.add(i);
            }
        }
        return res;
    }

    /**
     *
     * @return an iterator that returns tensor indices in a pre-order transversal
     */

    public Iterator<int[]> iterator(){
        return new iter_all(this);
    }

    /**For example assume I have tensor
     *  1 2 3
     *  4 5 6
     *  The iteration over defined inside iterator would take this order: 1, 2, 3, 4, 5, 6 - primarily iterating over the last axis (cols). The cols axis changes the most.
     *  What if I wanted to iterate over the rows axis instead? In order 1, 4, 2, 5, 3, 6?
     *  ordered_iterate implements this functionality. Rows are axis = 0, so ordered_iterate(0) would do the job.
     *
     * @param axis The axis to iterate over
     * @return an ArrayList of indices in order where the given axis changes the most
     */
    public int[][] ordered_iterate(int axis){
        if(axis >= this.shape.length) throw new IndexOutOfBoundsException();
        tensor iter = tensor.zeros(swap(this.shape, this.shape.length-1, axis));
        ArrayList<int[]> res = list(iter);
        for(int[] i:res){
            swap2(i, this.shape.length-1, axis);
        }
        return res.toArray(new int[res.size()][]);

    }

    /**
     *
     * @return A deep copy of this tensor
     */

    public tensor clone(){
        return np.multiply(this, 1);
    }

    /**
     *
     * @return a string representation of the tensor
     */

    public String toString(){
        return this.__str__();
    }

    public int __len__(){return this.data.length;}

    public double __val__(){
        throw new UnsupportedOperationException("You called float() on a tensor, tensors cannot be converted to float");
    }

    public quantity[] __data__(){
        return this.data;
    }

    public String __str__(){
        String res = "[";
        for(quantity q :data){
            if (q instanceof tensor){
                res += q.__str__();
            } else{
                res +=  q.__str__() + " ";
            }
        }
        return res + "]\n";
    }

    public static String tensorToStr(tensor t){
        String str = "";
        for (int i : t.shape()){
            str += i + " ";
        }
        for (int[] i : t){
            str += val(t.get(i)) + " ";
        }
        return str;
    }

    public static void main(String[] args){
        tensor t = tensor.ones(new int[]{5, 4});
        tensor k = tensor.ones(new int[]{5, 4});
        tensor a = np.add(t, k);
        System.out.println(list(a).getClass());
        for(int[] v : a.ordered_iterate(1)){
            System.out.println(Arrays.toString(v));
        }
    }

}

package np;

import java.util.*;
import static py.py.*;
public class tensor extends quantity implements Iterable<int[]>{

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

    public static tensor zeros(int[] shape){
        return new tensor(shape, new zeros());
    }

    public static tensor ones(int[] shape){
        return new tensor(shape, new ones());
    }

    public static tensor fill_function(int[] shape, Callable k){return new tensor(shape, k);};

    public static tensor rand_normal(int[] shape){
        return rand_normal(shape, 0.0, 1.0);
    }

    public static tensor rand_normal(int[] shape, Double mean, Double variance){ return new tensor(shape, new normal(mean, variance)); }


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

    public quantity get(int[] inds){
        return g(inds);
    }

    public void set(int[] inds, Double newval){
        s(inds, newval);
    }

    public quantity g(int[] inds){
        return g(inds, 0);
    }

    public quantity g(int[] inds, int pos){
        if(pos==len(inds)) return this;
        return data[inds[pos]].g(inds, pos+1);
    }

    public void s(int[] inds, Double newval){
        if(len(inds) != len(shape)) throw new UnsupportedOperationException("Cannot set value for entire tensors");
        quantity scal = g(inds);
        scal.s(inds, newval);

    }

    public tensor transpose(){
        if(len(this.shape) > 2) throw new UnsupportedOperationException("I'm too stupid to implement transpose for 3D and above arrays");
        if(len(this.shape) == 1){
            tensor t = tensor.zeros(this.shape);
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



    public Iterator<int[]> iterator(){
        return new iter_tensor(this);
    }

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

    public static void main(String[] args){
        tensor t = tensor.ones(new int[]{5, 4});
        tensor k = tensor.ones(new int[]{5, 4});
        tensor a = np.add(t, k);
        for(int[] v : a){
            System.out.println(a.g(v));
        }
    }

}

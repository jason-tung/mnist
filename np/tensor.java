package np;

import java.util.*;

public class tensor extends quantity implements Iterable<int[]>{

    private quantity[] data;
    public int[] shape;

    //public constructors and helper classes for function passing

    static class ones implements Callable{
        public Double call(){
            return 1.0;
        }
    }

    static class zeros implements Callable{
        public Double call() {
            return 0.0;
        }
    }

    static class normal implements Callable{
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

    public static tensor zeros(int[] shape){
        return new tensor(shape, new zeros(), 0);
    }

    public static tensor ones(int[] shape){
        return new tensor(shape, new ones(), 0);
    }

    public static tensor rand_normal(int[] shape){
        return rand_normal(shape, 0.0, 1.0);
    }

    public static tensor rand_normal(int[] shape, Double mean, Double variance){
        return new tensor(shape, new normal(mean, variance), 0);
    }


    //private constructor for flexible construction and helper methods
    private tensor(int[] shape, Callable fill_func, int pos){
        this.data = new quantity[shape[pos]];
        this.shape = shape;
        construct(this.data, this.shape, pos, fill_func);
    }

    private int len(int[] obj){return obj.length;}

    private void construct(quantity[] current_obj, int[] shape, int pos,  Callable fill_func){
        int val = shape[pos];

        if(pos == len(shape)-1){
            for(int i = 0; i < val; i++){
                current_obj[i] = new scalar(fill_func.call());
            }
        } else{
            for(int i = 0; i < val; i++){
                tensor t = new tensor(shape, fill_func, pos+1);
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
        tensor t = tensor.rand_normal(new int[]{5, 5, 5, 5, 5, 5, 5});

        for(int[] a : t){
            System.out.println(Arrays.toString(a));
            System.out.println(t.g(a));
        }
    }

}

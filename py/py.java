package py;

import np.quantity;

import java.util.ArrayList;
import java.util.Arrays;

public class py {
    /**
     *
     * @param s pyobj
     * @return a string representation of s
     */
    public static String str(pyobj s){
        return s.__str__();
    }

    /**
     *
     * @param s pyobj
     * @return the length of s
     */
    public static int len(pyobj s){
        return s.__len__();
    }

    /**
     * The main use here is extracting the double value from a Scalar object returned from indexing a tensor.
     * Double k = val(tensor.get(...)); //works just fine
     * Double k = tensor.get(...).data; //fails because indexing can return tensors too, and data can also be a quantity[]
     *
     * @param s pyobj
     * @return the value of s
     */

    public static double val(pyobj s){
        return s.__val__();
    }

    /**
     * Works just like val() but is the counterpart for tensor objects. Used to retrieve the data in a tensor object
     * without the compiler knowing that it's explicitly a tensor object
     *
     * @param s pyobj
     * @return the data of s
     */

    public static quantity[] data(pyobj s){
        return s.__data__();
    }

    /**
     * System.out.println(str(s))
     * @param s pyobj
     */
    public static void print(pyobj s){
        System.out.println(str(s));
    }

    public static range range(int stop){return range(0, stop);}

    public static range range(int start, int stop){return range(start, stop, 1);}

    /**
     * Works just like the range in python!
     * @param start (defaults to 0)
     * @param stop
     * @param step (defaults to 1)
     * @return a range object that can be iterated through
     */

    public static range range(int start, int stop, int step){
        return new range(start, stop, step);
    }

    /**
     * Turns an iterable into an ArrayList
     * @param iter iterable object
     * @return all the objects yielded by iter in ArrayList form
     */
    public static <T extends Object> ArrayList<T> list(Iterable<T> iter){
        ArrayList<T> res= new ArrayList<T>();
        for(T i: iter){
            res.add(i);
        }
        return res;
    }

    public static int[] swap(int[] b, int i1, int i2){
        int[] a = b.clone();
        int tmp = a[i1];
        a[i1] = a[i2];
        a[i2] = tmp;
        return a;
    }

    public static int[] swap2(int[] a, int i1, int i2){
        int tmp = a[i1];
        a[i1] = a[i2];
        a[i2] = tmp;
        return a;
    }



    /**
     * alias for Arrays.copyOfRange(arr, start, end);
     * @return sliced array
     */
    public static <T extends Object> T[] slice(T[] arr, int start, int end){
        return Arrays.copyOfRange(arr, start, end);
    }

    //partition, equivalent to def partition(arr, k): return [arr[i:i+k] for i in range(0, len(arr), k)]
    //man I miss list comprehensions
    public static <T extends Object> ArrayList<T[]> partition(T[] arr, int k){
        if(arr.length % k != 0) throw new IllegalArgumentException("len(arr) must be divisible by k");
        ArrayList<T[]> res = new ArrayList<>();
        for(int i: range(0, arr.length, k)){
            res.add(slice(arr, i, i+k));
        }
        return res;
    }

    public static void main(String[] args){
        Integer[] k = new Integer[]{1, 2, 3, 4, 5, 6};

        ArrayList<Integer> arrayList = new ArrayList<Integer>(Arrays.asList(k));


        System.out.println(list(arrayList));
    }
}

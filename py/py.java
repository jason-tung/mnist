package py;

import np.quantity;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class py {

    public static String str(pyobj s){
        return s.__str__();
    }

    public static int len(pyobj s){
        return s.__len__();
    }

    public static double val(pyobj s){
        return s.__val__();
    }

    public static quantity[] data(pyobj s){
        return s.__data__();
    }

    public static void print(pyobj s){
        System.out.println(str(s));
    }

    public static range range(int stop){return range(0, stop);}

    public static range range(int start, int stop){return range(start, stop, 1);}

    public static range range(int start, int stop, int step){
        return new range(start, stop, step);
    }

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

    //partition, equivalent to def partition(arr, k): return [arr[i:i+k] for i in range(0, len(arr), k)]
    //man I miss list comprehensions

    public static <T extends Object> T[] slice(T[] arr, int start, int end){
        return Arrays.copyOfRange(arr, start, end);
    }


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

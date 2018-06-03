package py;

import np.quantity;
import java.util.ArrayList;

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

    public <T extends Object> T[] list(Iterable<T> iter){
        ArrayList<T> tmp= new ArrayList<T>();
        for(T i: iter){
            tmp.add(i);
        }
        return tmp.toArray((T[]) new Object[tmp.size()]);
    }

}

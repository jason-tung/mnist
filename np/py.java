package np;

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

}

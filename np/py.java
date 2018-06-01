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



}

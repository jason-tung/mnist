package np;
import py.*;

public abstract class quantity implements pyobj {

    public abstract String __str__();
    public abstract int __len__();
    public abstract double __val__();

    public abstract quantity g(int[] a, int b);
    public abstract void s(int[] a, Double newval);

    public abstract tensor transpose();

}

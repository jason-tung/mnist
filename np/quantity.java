package np;
import py.*;

import java.io.Serializable;

public abstract class quantity implements pyobj, Serializable {
    public abstract String __str__();
    public abstract int __len__();
    public abstract double __val__();
    public abstract quantity g(int[] a, int b);
    public abstract void s(int[] a, Double newval);
    public abstract tensor transpose();
    public abstract int[] shape();

}

package py;
import np.quantity;
public interface pyobj {

    public String __str__();

    public int __len__();

    public double __val__();

    public quantity[] __data__();


}

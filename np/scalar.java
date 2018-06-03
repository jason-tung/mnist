package np;

public class scalar extends quantity{

    private Double data;

    public quantity[] __data__(){throw new UnsupportedOperationException("Use val() for scalars, data() on tensors");}

    public scalar(Double value){
        this.data = value;
    }

    public String toString(){
        return __str__();
    }

    public String __str__(){
        return Double.toString(data);
    }

    public double __val__(){
        return this.data;
    }

    public int __len__(){
        throw new UnsupportedOperationException("You tried to call len() on a scalar, scalars don't have a length!");
    }

    public Object g(){
        return __val__();
    }


    public quantity g(int[] a, int b){
        return this;
    }

    public void s(int[] a, Double b){
        this.data = b;
    }

    public tensor transpose(){throw new UnsupportedOperationException("You tried to call transpose() on a scalar");}



}
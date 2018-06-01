package np;

public class scalar extends quantity{

    Double data;


    public scalar(Double value){
        this.data = value;
    }

    public String toString(){
        return __str__();
    }

    public String __str__(){
        return Double.toString(data);
    }

    public Object g(int[] a, int b){
        return this.data;
    }

}
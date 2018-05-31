import java.util.ArrayList;

public class tensor{
    int[] shape;
    //ensure that all these arrays are rectangular
    //ensure that the dtype of all elements is constant
    public array(int[] shape){
	ArrayList<Object> data = new ArrayList<Object>();
	this.shape = shape;
	construct(data, this.shape, 0);
    }

    private int len(int[] obj){return obj.size();}

    private void construct(ArrayList<Object> current_obj, int[] shape, int pos){

	int val = shape[pos];
	
	if(pos == len(shape)-1){
	    for(int i = 0; i < val; i++){
		current_obj.add(Double.valueOf(0));
	    }
	} else{
	    for(i = 0; i < val; i++){
		ArrayList<Object> nxt = new ArrayList<Object>();
		current_obj.add(nxt);
		construct(nxt, shape, pos+1);
	    }
	}
			
    }

    public Object i
}

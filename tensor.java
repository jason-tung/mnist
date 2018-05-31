import java.util.ArrayList;


public class tensor{
	private int[] shape;

	public int[] shape(){return shape;}

	public tensor zeros(int[] shape){
	    return new tensor(shape, 0);
    }

    public tensor ones(int[] shape){
	    return new tensor(shape, 1);
    }






	private tensor(int[] shape, double fill){
		ArrayList<Object> data = new ArrayList<Object>();
		this.shape = shape;
		construct(data, this.shape, 0, fill);
	}

	private int len(int[] obj){return obj.length;}

	private void construct(ArrayList<Object> current_obj, int[] shape, int pos, double fillvalue){

		int val = shape[pos];

		if(pos == len(shape)-1){
			for(int i = 0; i < val; i++){
				current_obj.add(0);
			}
		} else{
			for(int i = 0; i < val; i++){
				ArrayList<Object> nxt = new ArrayList<Object>();
				current_obj.add(nxt);
				construct(nxt, shape, pos+1, fillvalue);
			}
		}
	}


}

import java.util.ArrayList;
import java.util.Random;


public class tensor{

    class ones implements Callable{
        public Double call(){
            return 1.0;
        }
    }

    class zeros implements Callable{
        public Double call() {
            return 0.0;
        }
    }

    class normal implements Callable{
        Double mean;
        Double variance;
        Random r = new Random();

        public normal(){
            this.mean = 0.0;
            this.variance = 1.0;
        }

        public normal(Double mean, Double variance){
            this.mean = mean;
            this.variance = variance;
        }

        public Double call(){
            return mean + r.nextGaussian()*variance;
        }
    }


	private int[] shape;

	public int[] shape(){return shape;}

	public tensor zeros(int[] shape){
	    return new tensor(shape, new zeros());
    }

    public tensor ones(int[] shape){
	    return new tensor(shape, new ones());
    }

    public tensor rand_normal(int[] shape){
	    return rand_normal(shape, 0.0, 1.0);
    }

    public tensor rand_normal(int[] shape, Double mean, Double variance){
	    return new tensor(shape, new normal(mean, variance));
    }


    public Object g(int[] inds){
	    Object res = 
	    for(int i : inds){

        }
    }



	private tensor(int[] shape, Callable fill_func){
		ArrayList<Object> data = new ArrayList<Object>();
		this.shape = shape;
		construct(data, this.shape, 0, fill_func);
	}

	private int len(int[] obj){return obj.length;}

	private void construct(ArrayList<Object> current_obj, int[] shape, int pos,  Callable fill_func){

		int val = shape[pos];

		if(pos == len(shape)-1){
			for(int i = 0; i < val; i++){
				current_obj.add(fill_func.call());
			}
		} else{
			for(int i = 0; i < val; i++){
				ArrayList<Object> nxt = new ArrayList<Object>();
				current_obj.add(nxt);
				construct(nxt, shape, pos+1, fill_func);
			}
		}
	}


}

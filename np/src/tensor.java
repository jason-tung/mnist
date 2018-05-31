import java.util.ArrayList;
import java.util.Random;


public class tensor extends quantity{

    private quantity[] data;

    static class ones implements Callable{
        public Double call(){
            return 1.0;
        }
    }

    static class zeros implements Callable{
        public Double call() {
            return 0.0;
        }
    }

    static class normal implements Callable{
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


	public int[] shape;

	public static tensor zeros(int[] shape){
	    return new tensor(shape, new zeros(), 0);
    }

    public static tensor ones(int[] shape){
	    return new tensor(shape, new ones(), 0);
    }

    public static tensor rand_normal(int[] shape){
	    return rand_normal(shape, 0.0, 1.0);
    }

    public static tensor rand_normal(int[] shape, Double mean, Double variance){
	    return new tensor(shape, new normal(mean, variance), 0);
    }


    public Object g(int[] inds){
	    quantity res = this;
	    for(int i : inds){
	        try{
                res = res.data[i];
            } catch (Exception e){
	            e.printStackTrace();
	            return res.data;
            }
        }
        return res;
    }




	private tensor(int[] shape, Callable fill_func, int pos){
		data = new tensor[shape[0]];
		this.shape = shape;
		construct(data, this.shape, pos,fill_func);
	}

	private int len(int[] obj){return obj.length;}

	private void construct(quantity[] current_obj, int[] shape, int pos,  Callable fill_func){

		int val = shape[pos];

		if(pos == len(shape)-1){
			for(int i = 0; i < val; i++){
				current_obj[i] = new scalar(fill_func.call());
			}
		} else{
			for(int i = 0; i < val; i++){
			    tensor t = new tensor(shape, fill_func, pos+1);
			    current_obj[i] = t;
			}
		}
	}

	public static void main(){
	    int[] shape = {5, 5, 5, 5, 5};
	    tensor t = tensor.rand_normal(shape);
    }

}

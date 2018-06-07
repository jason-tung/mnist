package py;

import java.util.Iterator;
public class range implements Iterator<Integer>, Iterable<Integer>{

    private int start;
    private int stop;
    private int step;

    public range(int stop){this(0, stop);}

    public range(int start, int stop){this(start, stop, 1);}

    public range(int start, int stop, int step){
        this.start = start;
        this.stop = stop;
        this.step = step;
    }

    @Override
    public Iterator<Integer> iterator() {
        return this;
    }

    @Override
    public boolean hasNext() {
        return (start < stop && step > 0) || (stop < start && step < 0);
    }

    public Integer next(){
        int b = start;
        start += step;
        return b;
    }

    public static void main(String[] args){
        for(int i: new range(10, 1, -1)){
            System.out.println(i);
        }
    }
}

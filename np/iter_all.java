package np;
import java.util.ArrayList;
import java.util.Iterator;

import py.py;


public class iter_all implements Iterator<int[]> {

    private int[] shape;
    private ArrayList<int[]> res;
    private Iterator<int[]> iter;


    public iter_all(tensor t){
        shape = t.shape;
        res = new ArrayList<>();
        build();
    }

    private void build(){
        buildhelp(new ArrayList<Integer>(), 0);
        iter = res.iterator();
    }

    private void buildhelp(ArrayList<Integer> curr, int pos) {
        if(curr.size() == shape.length){
            res.add(curr.stream().mapToInt(i->i).toArray());
            return;
        }
        for (int i : py.range(shape[pos])) {
            ArrayList<Integer> newList = new ArrayList<>(curr);
            newList.add(i);
            buildhelp(newList, pos+1);
        }
    }

    public int[] next() {
        return iter.next();
    }

    public boolean hasNext() {
        return iter.hasNext();
    }
}

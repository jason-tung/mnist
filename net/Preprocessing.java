package net;

import np.*;

import static py.py.*;

import java.util.*;
import java.awt.image.*;
import java.awt.*;
import java.io.*;
import javax.imageio.*;

public class Preprocessing {

    public static BufferedImage openFile(String fileName) {
        File file = new File(fileName);
        BufferedImage img = null;
        try {
            img = ImageIO.read(file);
        } catch (Exception e) {
            System.out.println("bad filename");
            e.printStackTrace();
        }
        return img;
    }

    public static tensor imgToTensor(BufferedImage img) {
        int[] shape = {28,28,3};
        tensor rgb =  tensor.zeros(shape);
        for (int i = 0; i < 28; i++) {
            for (int j = 0; j < 28; j++) {
                int[] loc0= {i,j,0};
                int[] loc1= {i,j,1};
                int[] loc2= ,i,j,2};
                Color color = new Color(img.getRGB(i, j));
                rgb.set(loc0, (double) color.getRed());
                rgb.set(loc1, (double) color.getGreen());
                rgb.set(loc2, (double) color.getBlue());
            }
        }
        return rgb;
    }

    //returns an image in the form of an int[][][] in rgb format, numbers should be from 0-256
    public static tensor parse(String fileName) { // should be in form (rows, cols, channels)
        return imgToTensor(openFile(fileName));
    }

    //takes an ndimensional tensor and collapses it into a 1D tensor
    public static tensor flatten(tensor t) {
        ArrayList<Double> tmp = new ArrayList<>();

        for (int[] i : t) {
            tmp.add(val(t.g(i)));
        }

        tensor res = tensor.zeros(new int[]{tmp.size()});

        for (int[] i : res) {
            res.s(i, tmp.get(i[0]));
        }
        return res;
    }

    //creates the one-hot layer with which to compare output layer
    public static tensor to_categorical(tensor t, int num_classes) {
        if (t.shape.length != 1) throw new IllegalArgumentException("t.shape.length must be == 1");
        tensor r = tensor.zeros(new int[]{t.shape[0], num_classes});

        for (int[] i : t) {
            r.s(new int[]{i[0], (int) val(t.g(i))}, 1.0);
        }
        return r;
    }

    public static tensor normalize(tensor t) {
        class normalize extends Callable {
            public Double call(double k) {
                return (k - 128) / 128;
            }
        }
        return np.vectorize(t, new normalize());
    }

}

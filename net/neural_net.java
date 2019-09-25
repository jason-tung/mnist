package net;

import static py.py.data;
import static py.py.len;
import static py.py.range;
import static py.py.slice;
import static py.py.val;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import np.Callable;
import np.np;
import np.tensor;

public class neural_net implements Serializable {
	private ArrayList<tensor> weights;
	private double lr;

	/**
	 * Creates a new neural net
	 * 
	 * @param input_dim     Dimensionality of the inputs. i.e. mnist image is 28 *
	 *                      28 --> 784 D vector, so this will be 784
	 * @param hidden_dim    Number of neurons in the hidden layer
	 * @param output_dim    Dimensionality of hte outputs. i.e. mnist image has 10
	 *                      classes, so this will be 10
	 * @param learning_rate Learning rate of the model
	 */
	public neural_net(int input_dim, int hidden_dim, int output_dim, double learning_rate) {
		int[] w1 = new int[] { input_dim, hidden_dim };
		int[] w2 = new int[] { hidden_dim, output_dim };
		weights = new ArrayList<>();

		weights.add(np.multiply(tensor.rand_normal(w1), 0.1));
		weights.add(np.multiply(tensor.rand_normal(w2), 0.1));

		lr = learning_rate;

	}

	private static tensor relu(tensor x) {
		class helper extends Callable {
			public Double call(double x) {
				return Math.max(x, 0);
			}
		}
		return np.vectorize(x, new helper());
	}

	private ArrayList<tensor> fwd_pass(tensor X) {
		ArrayList<tensor> t = new ArrayList<>();
		t.add(X);
		for (tensor w : weights) {
			t.add(relu(np.matmul(t.get(t.size() - 1), w)));
		}
		return t;
	}

	private ArrayList<tensor> grads(tensor X, tensor Y) {
		ArrayList<tensor> grads = new ArrayList<>();
		for (tensor t : weights) {
			grads.add(tensor.zeros(t.shape.clone()));
		}

		ArrayList<tensor> t = fwd_pass(X);

		tensor delta = np.subtract(t.get(t.size() - 1), Y);

		grads.set(grads.size() - 1, np.matmul(t.get(t.size() - 2).T(), delta));

		for (int i : range(t.size() - 2, 0, -1)) {
			delta = np.matmul(delta, weights.get(i).T());

			for (int[] inds : delta) {
				if (val(t.get(i).get(inds)) <= 0) {
					delta.s(inds, 0.0);
				}
			}
			grads.set(i - 1, np.matmul(t.get(i - 1).T(), delta));
		}

		ArrayList<tensor> res = new ArrayList<>();
		for (tensor ttt : grads) {
			res.add(np.divide(ttt, len(X)));
		}
		return res;
	}

	/**
	 * Train a model
	 * 
	 * @param X          a tensor that represents all of your Xs
	 * @param Y          a tensor of labels that correspond to your X values
	 * @param epochs     number of times to iterate over the data
	 * @param batch_size number of samples to iterate over at once
	 * @param savedir    directory to save trained models
	 */
	public void train(tensor X, tensor Y, int epochs, int batch_size, String savedir) {
		for (int i : range(epochs)) {
			for (int j : range(0, len(X), batch_size)) {
				tensor x = new tensor(slice(data(X), j, j + batch_size));
				tensor y = new tensor(slice(data(Y), j, j + batch_size));

				x = x.clone(); // Safety measure, shapes might get reused and changes happening can mess
								// everything up by reference
				y = y.clone();

				ArrayList<tensor> grads = grads(x, y);
				for (int w : range(weights.size())) {
					weights.set(w, np.subtract(weights.get(w), np.multiply(grads.get(w), lr)));
				}
				ArrayList<tensor> p = fwd_pass(x);
				tensor preds = np.argmax(p.get(p.size() - 1), 1);
				tensor f = np.argmax(y, 1);

				double t = 0;
				int c = 0;
				for (int[] ind : preds) {
					if (val(preds.get(ind)) == val(f.get(ind)))
						t += 1;
					c += 1;
				}
				System.out.println("Epoch: " + i + " Batch: " + j + "/" + len(X) + " Acc: " + t / c);
			}
			if (!savedir.equals("")) {
				this.save(savedir + '/' + i + ".ser");
			}
		}
	}

	public tensor predict(tensor x) {
		ArrayList<tensor> t = fwd_pass(x);
		return np.argmax(t.get(t.size() - 1), 1);
	}

	/**
	 * Saves a neural network
	 * 
	 * @param path path to save to
	 */
	public void save(String path) {
		try {
			FileOutputStream out = new FileOutputStream(path);
			ObjectOutputStream objOut = new ObjectOutputStream(out);
			objOut.writeObject(this);
			objOut.close();
			System.out.println("saved to " + path);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Load a neural network from file
	 * 
	 * @param path path to load from
	 * @return a neural network
	 */
	public static neural_net load_from_file(String path) {
		try {
			FileInputStream fis = new FileInputStream(path);
			ObjectInputStream ois = new ObjectInputStream(fis);
			neural_net result = (neural_net) ois.readObject();
			ois.close();
			return result;
		} catch (Exception ex) {
			System.out.println("I know that this is bad practice okay");
			ex.printStackTrace();
		}
		throw new IllegalStateException("this is here so it compiles lmao");
	}

	public static void main(String[] args) {
		neural_net tst = new neural_net(784, 100, 10, 0.01);

		tensor X = tensor.rand_normal(new int[] { 100, 784 });
		tensor Y = tensor.rand_normal(new int[] { 100, 10 });

		tst.train(X, Y, 1, 5, "");

		tst.save("net/saved_models/mod.ser");
		neural_net tft = load_from_file("net/saved_models/2.ser");

	}

}

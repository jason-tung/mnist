package net;

import static py.py.list;
import static py.py.range;
import static py.py.val;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.io.*; 
import java.nio.file.Files; 
import java.nio.file.*; 

import np.quantity;
import np.tensor;

public class driver {

	// Helper functions for loading and training with mnist
	public static ArrayList<String> showFiles(String directory) {
		File[] files = new File(directory).listFiles();
		System.out.println(new File(directory));
		ArrayList<String> res = new ArrayList<>();
		for (File file : files) {
			if (file.isDirectory()) {
				throw new IllegalStateException("Given directory contains subdirectories");
			} else {
				res.add(directory + "/" + file.getName());
			}
		}
		return res;
	}

	public static ArrayList<Double> mnist_help(String directory) {
		File[] files = new File(directory).listFiles();
		ArrayList<Double> res = new ArrayList<>();
		for (File file : files) {
			if (file.isDirectory()) {
				throw new IllegalStateException("Given directory contains subdirectories");
			} else {
				res.add((double) Integer.parseInt("" + file.getName().charAt(0)));
			}
		}
		return res;
	}

	/**
	 * Trains a model on a dataset
	 * 
	 * @param data_directory Directory where the images are stored
	 * @param save_directory Directory to save trained models
	 */
	public static void train_mnist_net(String data_directory, String save_directory) {
		String dirname = data_directory;

		ArrayList<quantity> tmpx = new ArrayList<>();
		ArrayList<Double> tmpy = new ArrayList<>();

		for (String file : showFiles(dirname)) {
//			System.out.println(file);
			tmpx.add(Preprocessing.parse(file));
		}

		for (Double val : mnist_help(dirname)) {
			tmpy.add(val);
		}

		// Shuffle training data so network learns all digits not just one at a time
		ArrayList<Integer> shuff = list(range(tmpy.size()));
		Collections.shuffle(shuff);

		ArrayList<quantity> n_tmpx = new ArrayList<>();
		ArrayList<Double> n_tmpy = new ArrayList<>();

		for (Integer i : shuff) {
			n_tmpx.add(tmpx.get(i));
			n_tmpy.add(tmpy.get(i));
		}

		tmpx = n_tmpx;
		tmpy = n_tmpy;

		tensor y = tensor.zeros(new int[] { tmpy.size() });

		for (int[] i : y) {
			y.s(i, tmpy.get(i[0]));
		}

		tensor x = new tensor(tmpx);
		y = Preprocessing.to_categorical(y, 10);

		neural_net mnist_net = new neural_net(784, 100, 10, 0.1);

		mnist_net.train(x, y, 100, 20, save_directory);
	}

	/**
	 * Returns the label of an image given a filename
	 * 
	 * @param n        a trained neural network
	 * @param filepath path to a mnist image
	 * @return the predicted label of the image
	 */

	public static int predict(neural_net n, String filepath) {

		tensor img = Preprocessing.parse(filepath);
		tensor[] tmp = new tensor[1];
		tmp[0] = img;
		tensor f = n.predict(new tensor(tmp));
		for (int[] i : f) {
			return (int) val(f.get(i));
		}
		throw new IllegalStateException("this is here so it compiles error");
	}

	public static void main(String[] args) {

		System.out.println(
				"to train a model run train_mnist_net. You need to provide a directory where the images are located, and a directory to save your models");

		String data_directory = "input/";
		String save_directory = "output/";
		if (args.length == 2) {
			data_directory = args[0];
			save_directory = args[1];
		}
		copyFile("makePicture/test.png","input/test.png", true);
		// To train the network uncomment the below line:
//        train_mnist_net("train/training_sets/train", "net/saved_models");
		neural_net net = neural_net.load_from_file("net/saved_models/0.ser");
		for (String file : showFiles(data_directory)) {
			System.out.println(file + "PREDICTION:   " + predict(net, file));
		}

	}

	public static void copyFile(String from, String to, Boolean overwrite) {

		try {
			File fromFile = new File(from);
			File toFile = new File(to);

			if (!fromFile.exists()) {
				throw new IOException("File not found: " + from);
			}
			if (!fromFile.isFile()) {
				throw new IOException("Can't copy directories: " + from);
			}
			if (!fromFile.canRead()) {
				throw new IOException("Can't read file: " + from);
			}

			if (toFile.isDirectory()) {
				toFile = new File(toFile, fromFile.getName());
			}

			if (toFile.exists() && !overwrite) {
				throw new IOException("File already exists.");
			} else {
				String parent = toFile.getParent();
				if (parent == null) {
					parent = System.getProperty("user.dir");
				}
				File dir = new File(parent);
				if (!dir.exists()) {
					throw new IOException("Destination directory does not exist: " + parent);
				}
				if (dir.isFile()) {
					throw new IOException("Destination is not a valid directory: " + parent);
				}
				if (!dir.canWrite()) {
					throw new IOException("Can't write on destination: " + parent);
				}
			}

			FileInputStream fis = null;
			FileOutputStream fos = null;
			try {

				fis = new FileInputStream(fromFile);
				fos = new FileOutputStream(toFile);
				byte[] buffer = new byte[4096];
				int bytesRead;

				while ((bytesRead = fis.read(buffer)) != -1) {
					fos.write(buffer, 0, bytesRead);
				}

			} finally {
				if (from != null) {
					try {
						fis.close();
					} catch (IOException e) {
						System.out.println(e);
					}
				}
				if (to != null) {
					try {
						fos.close();
					} catch (IOException e) {
						System.out.println(e);
					}
				}
			}

		} catch (Exception e) {
			System.out.println("Problems when copying file.");
		}
	}


}

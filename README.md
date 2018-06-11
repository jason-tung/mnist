# mnist
How to compile this mess:
```
./javac.sh
```

**Usage**:

np -> provides tensors and tensor operations

py -> provides syntactic sugar like what python has. I miss python. Alot.

net -> densely connected neural nets

We also have javadocs, which is probably a lot better than this mess. You can open that up in the docs folder or you can just look through the code for somewhat well written comments. Jason didn't comment his code though so all the docs you'll get for makePicture will be here.





**Making your own validation set**:

located in the makePicture directory is the makePicture.pde file. click the pencil to draw, the eraser to erase, and the floppy disk to save the drawing in the window. then, run the bash script to move the picture into the mnist/training_sets/tests folder. MAKE SURE THAT THE PICTURE IS BIG AND CENTERED LIKE THE SAMPLE DATA.

**Preprocessing Inputs**

Preprocessing.java makes changing inputs and outputs into tensors easier. Preprocessing.parse(String fileName)
converts an image into a tensor. At the moment it only only converts black and white images, but you can 
have it convert all images with an easy fix to the source code.

Instead of normalize(black_white_imgToTensor(openFile(fileName))) use 
Instead of normalize(imgToTensor(openFile(fileName))), which will return RGB values.

**Training the model**:

There is a zip file in training_sets called training_sets.zip. Unzip that file and you should get two folders: train and validation

train contains 60000 mnist images, while validation contains 10000.

You can create your own custom model with your own custom parameters by importing the nn object from nn.java.
It's usage is described in docs for nn.

Each nn object has the parameter .train(), which has four parameters. The inputs (x), outputs (y), epochs (number
of times to iterate over both sets of data), batch_size (how many samples to iterate over at once), and savedir (directory to save trained models).

Both inputs and outputs have to be tensors.

Training the model can be done with nn.train(). Since the code I wrote is really inefficient, you can expect this to take a pretty long time. The model will be saved once for each epoch. 

We have several pre-trained models in the net/saved_models directory. 1, 2, and 3 are serialized nets after 1, 2, and 3 epochs respectively.

**driver.java shortcuts**

driver.java class makes this really simple with the train_mnist_net(String data_directory, String save_directory) function, which will load data from the data_directory, train the model, and save copies of the model in save_directory. 

I suggest that you use the validation set instead of the training set to train the model. I didn't have enough time to implement a class that has flow_from_directory functionality such as this one: https://keras.io/preprocessing/image/, so train_mnist_net loads all the images into memory as tensors first. Loading 10000 images from validation is doable, 60000 from train is a stretch. 

**NOTE THAT THIS WILL THROW AN ERROR IF YOU DON'T MODIFY THE PATHS IN THE DRIVER.JAVA SOURCE FILE!!!!**

Additionally driver.java comes with a predict() function. Since I already wrote the documentation for this in comments, I'll just repaste it here

    /**
     * Returns the label of an image given a filename
     * @param n a trained neural network
     * @param filepath path to a mnist image
     * @return the predicted label of the image
     */

    public static int predict(neural_net n, String filepath){

**predicting**

Each nn object comes with a .predict(tensor x) method which will predict the ys from a given tensor of xs. The input for this function must be a tensor made from samples, not just one sample. Luckily you can change an ArrayList of tensors into a tensor really easily as this functionality is supported in the constructor: tensor t = new tensor(ArrayList<tensor>). .predict() will output a tensor that corresponds to the y for each sample in x. These outputs will be one-hot encoded. To extract the class labels, use np.argmax(prediction, axis=1)
    
    
An easy way to check if the predictions are correct are the following. The filenames are named like C:\Users\Jason\IdeaProjects\mnist\training_sets\validation\0_7526.jpg for instance. The actual name is 0_7526.jpg, and the first character in that name corresponds to its label. So that file would show a 0.


**one-hot-encoding**
The ys that are outputted will be one-hot-encoded. This means that the network won't output integers like 1, 2, 3, 4 ... but instead will output a list of probabilities. For instance 1 will be encoded to [0, 1, 0, 0...], 0 will be encoded to [1, 0, 0, 0...], etc. The reason for this is because neural nets see bigger numbers correspond to being "greater". This is a problem when doing classification. For instance if you want a net to predict if something is a dog, cat, or bird, and you assign 0 to dog, 1 to cat, and 2 to bird, the network will infer that dog > cat > bird, which is not the case. This is why we use the one-hot-encoding.

You can extract the actual digit by finding the index of the maximum value in the output tensor (for each sample, the the tensor that contains everything).

**issues**

as of 6/10/18 9:10PM, the loss does not go below 5

user has to manually compile everything to resolve issues with circular importing

execute bash script to move picture after making it

windows users beware: remove the Thumbs.db from the training_set directories using a shell before running driver, or an error will be thrown.

**EDIT**

The neural network bug has been fixed, neural net predicts with 90-100% accuracy.

I was on the verge of giving up. I had already squashed several bugs, but the network still wasn't working. It was then that I prayed to my Lord and Savior Jesus Christ. It was not thirty seconds later when I spotted lr=1e-6: the learning rate was set too low for the model to learn anything in a reasonable amount of time.

I thank my Lord and Savior Jesus Christ for letting me finish this project.

And all things, whatsoever ye shall ask in prayer, believing, ye shall receive. - Matthew 21:22




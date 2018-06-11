# mnist
How to compile this mess:
```
./javac.sh
```

**Usage**:
np and py are helper packages. You can completely ignore them.

**Making your own validation set**:
located in the makePicture directory is the makePicture.pde file. click the pencil to draw, the eraser to erase, and the floppy disk to save the drawing in the window. then, run the bash script to move the picture into the mnist/training_sets/validation folder.

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


**driver.java shortcuts**
driver.java class makes this really simple with the train_mnist_net(String data_directory, String save_directory) function, which will load data from the data_directory, train the model, and save copies of the model in save_directory. 

I suggest that you use the validation set instead of the training set to train the model. I didn't have enough time to implement a class that has flow_from_directory functionality such as this one: https://keras.io/preprocessing/image/, so train_mnist_net loads all the images into memory as tensors first. Loading 10000 images from validation is doable, 60000 from train is a stretch. 

**predicting**

Each nn object comes with a .predict(tensor x) method which will predict the ys from a given tensor of xs. The input for this function must be a tensor made from samples, not just one sample. Luckily you can change an ArrayList of tensors into a tensor really easily as this functionality is supported in the constructor: tensor t = new tensor(ArrayList<tensor>). .predict() will output a tensor that corresponds to the y for each sample in x.


**one-hot-encoding**
The ys that are outputted will be one-hot-encoded. This means that the network won't output integers like 1, 2, 3, 4 ... but instead will output a list of probabilities. For instance 1 will be encoded to [0, 1, 0, 0...], 0 will be encoded to [1, 0, 0, 0...], etc. The reason for this is because neural nets see bigger numbers correspond to being "greater". This is a problem when doing classification. For instance if you want a net to predict if something is a dog, cat, or bird, and you assign 0 to dog, 1 to cat, and 2 to bird, the network will infer that dog > cat > bird, which is not the case. This is why we use the one-hot-encoding.

You can extract the actual digit by finding the index of the maximum value in the output tensor (for each sample, the the tensor that contains everything).

**issues**
as of 6/10/18 9:10PM, the loss does not go below 5
user has to manually compile everything to resolve issues with circular importing
execute bash script to move picture after making it







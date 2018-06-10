# mnist
How to compile this mess:
```
./javac.sh
```

**Usage**:

np and py are helper packages. You can completely ignore them.

**Training the model**:

There is a zip file in training_sets called training_sets.zip. Unzip that file and you should get two folders: train and validation

train contains 60000 mnist images, while validation contains 10000.

You can create your own custom model with your own custom parameters by importing the nn object from nn.java.
It's usage is described in docs for nn.

Each nn object has the parameter .train(), which has four parameters. The inputs (x), outputs (y), epochs (number
of times to iterate over both sets of data), and the batch_size (how many samples to iterate over at once).

Both inputs and outputs have to be tensors.

Preprocessing.java makes changing inputs and outputs into tensors easier. Preprocessing.parse(String fileName)
converts an image into a tensor. At the moment it only only converts black and white images, but you can 
have it convert all images with an easy fix to the source code.

Instead of normalize(black_white_imgToTensor(openFile(fileName))) use 
Instead of normalize(imgToTensor(openFile(fileName))), which will return RGB values


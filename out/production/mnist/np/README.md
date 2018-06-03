np docs:

This package provides access to tensors (n dimensional arrays) and matrix operations using tensors

Tensors:

You can create tensors with one of three constructors:

    - zeros: fills a tensor of specified shape with all zeros
    - ones: same as zeros but with ones
    - rand_normal: fills a tensor of specified shape by sampling from a normal distribution

Getting and setting:

You can get an element from a tensor by using .g([index1, index2, ...]). This will return either a tensor
if you are getting an entire row at a time, or a scalar, if you are only getting one value.

You can set the value of a cell in a tensor with .s([index1, index2, ...], newvalue). This will change
the value of one scalar inside of the tensor. You can only set one scalar at a time.

Getting values from tensors and scalars:

Both objects have toString() methods that will return a string representation. You can get the Double value of a
scalar with scalar.data, scalar.__val__(), or val(scalar).

Other operations of note:

You iterate through a tensor using the enhanced for loop: for(int[] i: tensor), which will iterate through its indices.
You can use these indices for getting and setting of your choice.

.T or .transpose returns the transpose of 2D and under tensors, we are currently trying to implement an nD transpose


Python methods:

This project seeks to be as similar to python as possible, so you can use python's underscore (__) api here as well.
Currently there are methods __str__, __val__, and __len__. This lets you use str(), val(), and len() on scalar
and tensor objects.

Additionally there is also a range() function which makes it easier to iterate through integers. It's usage is the
exact same as it was in python, except that you use the enhanced for loop instead of for i in ... syntax.

np:

Methods in np allow more operations on tensors.

add, multiply, divide, subtract perform their respective operations on two tensors. These methods return a new tensor,
and both tensors inputted must be the same shape.

equal lets you check if two tensors are equal.

vectorize lets you modify a tensor using a Callable(), for example define a Callable with a call() function that takes
in a double and multiplies it by two. vectorize(tensor, your_callable) returns a new tensor that's the old tensor but
each element has been multiplied by two.

log, exp, square, and sqrt do exactly what they say to each element in a tensor. They all return a new tensor. log and exp
default to E as the base and power respectively, but you can input your own base/power.

sum, max, min, and mean will either return a double, global sum, max, min, mean, or a tensor with each one of those operations
applied on one axis of the tensor. You can specify which axis in the parameters, but they default to globals.

argmax will return the indices of the maximum values in a tensor. This will also have an axis parameter with the same
use as the functions above.
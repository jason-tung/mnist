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

Python methods:

This project seeks to be as similar to python as possible, so you can use python's underscore (__) api here as well.
Currently there are methods __str__, __val__, and __len__. This lets you use str(), val(), and len() on scalar
and tensor objects.
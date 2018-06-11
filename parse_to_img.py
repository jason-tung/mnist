from keras.datasets import mnist
from PIL import Image
from multiprocessing import Pool

#This is the python script I used to process the mnist data into images
#See how nice this code looks compared to java?
#And how easy it is to use multiprocessing?
#And how awesome and concise everything looks?
#Yeah.


(x_train, y_train), (x_test, y_test) = mnist.load_data()

test_path = r"C:\Users\Jason\IdeaProjects\mnist\training_sets\validation"
train_path = r"C:\Users\Jason\IdeaProjects\mnist\training_sets\train"


def process_train(k):
    c, data, label = k
    print('Training set:', c, '/', len(x_train))
    img = Image.fromarray(data, mode='L')
    img.save("{}\\{}_{}.jpg".format(train_path, label, c))

def process_test(k):
    c, data, label = k
    print('Test set:', c, '/', len(x_test))
    img = Image.fromarray(data, mode='L')
    img.save("{}\\{}_{}.jpg".format(test_path, label, c))

if __name__ == '__main__':
    pool = Pool()

    pool.map(process_train, zip(range(len(x_train)), x_train, y_train))

    pool.map(process_test, zip(range(len(x_test)), x_test, y_test))




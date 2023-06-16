# Machine Learning Path Repository 
This repository used for Data Preprocessing and creating the model for EARS. We use android-based on gmaps API and content based filtering for determine the nearest path and recomendation hospital for user. At the end of the model , we will transform the model to tflite
# Work Flow 
1. Data Collecting 
2. Gmaps API Processing
3. Creating Model of Recommendation Hospital 
4. Convert to TF-Lite
5. Deploy to Android 
# Data Collecting
> We collect data on hospitals in Surabaya
# Creating Model 
_________ 100 epoch for model collaborative filtering nearest hospital
<div align="center" style="display:flex;">
<img style="width:45%" src="./../assets/loss.png">
<img style="width:45%" src="./../assets/accuracy.png">
<img style="width:45%" src="./../assets/mse.png">
</div>

```
Model: "sequential_5"
_________________________________________________________________
 Layer (type)                Output Shape              Param #   
=================================================================
 dense_20 (Dense)            (None, 64)                192       
                                                                 
 dense_21 (Dense)            (None, 64)                4160      
                                                                 
 dense_22 (Dense)            (None, 2)                 130       
                                                                 
=================================================================
Total params: 4,482
Trainable params: 4,482
Non-trainable params: 0
```

## The last 10 epoch Loss and Accuracy
```
Epoch 90/100
1/1 [==============================] - 0s 42ms/step - loss: 1.0630 - accuracy: 1.0000 - val_loss: 1.0488 - val_accuracy: 1.0000
Epoch 91/100
1/1 [==============================] - 0s 41ms/step - loss: 1.0477 - accuracy: 1.0000 - val_loss: 1.0095 - val_accuracy: 1.0000
Epoch 92/100
1/1 [==============================] - 0s 43ms/step - loss: 1.0085 - accuracy: 1.0000 - val_loss: 0.9499 - val_accuracy: 1.0000
Epoch 93/100
1/1 [==============================] - 0s 38ms/step - loss: 0.9490 - accuracy: 1.0000 - val_loss: 0.8740 - val_accuracy: 1.0000
Epoch 94/100
1/1 [==============================] - 0s 59ms/step - loss: 0.8732 - accuracy: 1.0000 - val_loss: 0.7863 - val_accuracy: 1.0000
Epoch 95/100
1/1 [==============================] - 0s 40ms/step - loss: 0.7856 - accuracy: 1.0000 - val_loss: 0.6910 - val_accuracy: 1.0000
Epoch 96/100
1/1 [==============================] - 0s 40ms/step - loss: 0.6904 - accuracy: 1.0000 - val_loss: 0.5926 - val_accuracy: 1.0000
Epoch 97/100
1/1 [==============================] - 0s 39ms/step - loss: 0.5921 - accuracy: 1.0000 - val_loss: 0.4949 - val_accuracy: 1.0000
Epoch 98/100
1/1 [==============================] - 0s 40ms/step - loss: 0.4945 - accuracy: 1.0000 - val_loss: 0.4014 - val_accuracy: 1.0000
Epoch 99/100
1/1 [==============================] - 0s 45ms/step - loss: 0.4011 - accuracy: 1.0000 - val_loss: 0.3149 - val_accuracy: 1.0000
Epoch 100/100
1/1 [==============================] - 0s 42ms/step - loss: 0.3147 - accuracy: 1.0000 - val_loss: 0.2376 - val_accuracy: 1.0000
```

## The last 10 epoch Loss and MSE
```
Epoch 90/100
1/1 [==============================] - 0s 40ms/step - loss: 0.1991 - mse: 0.1991 - val_loss: 0.3668 - val_mse: 0.3668
Epoch 91/100
1/1 [==============================] - 0s 39ms/step - loss: 0.3663 - mse: 0.3663 - val_loss: 0.5534 - val_mse: 0.5534
Epoch 92/100
1/1 [==============================] - 0s 41ms/step - loss: 0.5529 - mse: 0.5529 - val_loss: 0.7400 - val_mse: 0.7400
Epoch 93/100
1/1 [==============================] - 0s 43ms/step - loss: 0.7394 - mse: 0.7394 - val_loss: 0.9107 - val_mse: 0.9107
Epoch 94/100
1/1 [==============================] - 0s 40ms/step - loss: 0.9101 - mse: 0.9101 - val_loss: 1.0540 - val_mse: 1.0540
Epoch 95/100
1/1 [==============================] - 0s 42ms/step - loss: 1.0533 - mse: 1.0533 - val_loss: 1.1624 - val_mse: 1.1624
Epoch 96/100
1/1 [==============================] - 0s 41ms/step - loss: 1.1616 - mse: 1.1616 - val_loss: 1.2324 - val_mse: 1.2324
Epoch 97/100
1/1 [==============================] - 0s 38ms/step - loss: 1.2315 - mse: 1.2315 - val_loss: 1.2640 - val_mse: 1.2640
Epoch 98/100
1/1 [==============================] - 0s 55ms/step - loss: 1.2631 - mse: 1.2631 - val_loss: 1.2596 - val_mse: 1.2596
Epoch 99/100
1/1 [==============================] - 0s 60ms/step - loss: 1.2586 - mse: 1.2586 - val_loss: 1.2232 - val_mse: 1.2232
Epoch 100/100
1/1 [==============================] - 0s 57ms/step - loss: 1.2222 - mse: 1.2222 - val_loss: 1.1598 - val_mse: 1.1598
```



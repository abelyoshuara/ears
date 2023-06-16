# Machine Learning Path Repository 
This repository is designed for efficient Data Preprocessing and model creation for EARS (Emergency Assistance and Routing System). Our approach involves utilizing an android-based application built on the Google Maps API and employing content-based filtering techniques to determine the nearest path and recommend hospitals to the user. To ensure seamless integration, we plan to convert the model into tflite (TensorFlow Lite) format at the final stage. This enables the model to be deployed efficiently on resource-constrained devices while maintaining its functionality and accuracy.
# Work Flow 
1. Data Collecting 
2. Gmaps API Processing
3. Creating Model of Recommendation Hospital 
4. Convert to TF-Lite
5. Deploy to Android 
# Data Collecting 
We gather our data from :
- Hospital Address
   - Collect name of hospital in Surabaya 
- Ordinate Point Hospital
   - Use Latitude - Longtitude of Hospital and Distance from City Center
- Hospital Capacity
   - Cost of Service Price, Number of Hospitalization Room Services.
- Ratings 
   - Diseases that can be succesfully treated and user facility ratings 
# Creating Model 
We tried some hyperparameters in creating the model. These are some of the result.

- *First model in filtering hospital recomendation ____________ Epoch attempt 100*
![Presentasi Capstone (7)](https://github.com/abelyoshuara/ears-project/assets/128933031/4213d095-ea2c-488e-9de2-19a1ae25fd67)

After many attemp adjust the hyperparameters and adjusting the epochs size, finally we got the best result we can outcome.
![Presentasi Capstone (8)](https://github.com/abelyoshuara/ears-project/assets/128933031/30c3500e-253e-41bf-b948-c48a20ae874d)

- *Second model in nearest distance based on ordinate of Latitude and Longituted  Point of Hospital and Distance from City Center
_________ Epoch attempt 100*

![Presentasi Capstone (9)](https://github.com/abelyoshuara/ears-project/assets/128933031/50a194ff-b7ab-4081-9904-b85325b4e0da)

The hyperparameters and adjusting the epoch size for second model, this the last result we can outcome 
![Presentasi Capstone (10)](https://github.com/abelyoshuara/ears-project/assets/128933031/fce44b51-35a6-4bd2-a29b-5bbee65644f3)






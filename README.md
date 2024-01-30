# AgriDoctor

## All Cloud Deployed/Hosted URLs for this project by us:

Java Spring Boot backend API Url: https://agridoctorbackend-production.up.railway.app  
React frontend Url: https://therap-javafest-agridoctor.vercel.app  
Fast-API AI/ML backend API Url: https://flamoverse-crop-recommender-from-weather-npk.hf.space  

Status: Full Project is Cloud Deployed by Us.

## Presentation Video
Presentation Video: [Youtube Video Link](https://youtu.be/dVA5BGGk3Eo?feature=shared)

## AI and ML backend

Related codes and notebooks Folder: &nbsp; [AI_API_Docker_codes](https://github.com/Adith082/Therap-Java-Fest-AgriDoctor/tree/main/AI_API_Docker_codes)  
Hosted doc link: [AI API DOC](https://flamoverse-crop-recommender-from-weather-npk.hf.space/docs#/)   
AI/ML backend API Url: https://flamoverse-crop-recommender-from-weather-npk.hf.space  

### Technologies
Build tool : Docker  
 
Framework: Fast-API  

ASGI Server: Uvicorn[standard]  

AI libraries: torch, tensorflow, scikit-learn, pillow, torchvision  

Other Libraries: pydantic, python-multipart, starlette, numpy, requests  


### Crop Recommendation

Dataset used (kaggle): &nbsp; [Crop Recommendation Models Traning Dataset](https://www.kaggle.com/datasets/siddharthss/crop-recommendation-dataset)  
Training notebook (Containing all details): &nbsp; [Crop_Recommendation_Model.ipynb](https://github.com/Adith082/Therap-Java-Fest-AgriDoctor/blob/main/AI_API_Docker_codes/crop_recommendation/Crop_Recommendation_Model.ipynb)  
Total number of unique predictions: 22 unique different plants as follows: ['rice' 'maize' 'chickpea' 'kidneybeans' 'pigeonpeas' 'mothbeans'
 'mungbean' 'blackgram' 'lentil' 'pomegranate' 'banana' 'mango' 'grapes'
 'watermelon' 'muskmelon' 'apple' 'orange' 'papaya' 'coconut' 'cotton'
 'jute' 'coffee']  
 
Models trained: Random Forest, Naive Bayes, SVM Tree, Decision Tree.  
Best Performing Model: Random Forest (Accuracy: 99.777%)   

### Crop Disease Prediction
Trained after merging following Datasets (Kaggle): 
1. [New Bangladeshi Crop Disease](https://www.kaggle.com/datasets/nafishamoin/new-bangladeshi-crop-disease)
2. [New Plant Diseases Dataset](https://www.kaggle.com/datasets/vipoooool/new-plant-diseases-dataset)  

Model Architechture: Based on ResNet (CNN)  
Model Performance(Accuracy): 98.5%  
Total plants: 16  
Total Diseases: 35  
Total Classes: 45 as follows: ['Apple___Apple_scab', 'Grape___Black_rot', 'Rice___Brown_Spot', 'Orange___Haunglongbing_(Citrus_greening)', 'Tomato___Bacterial_spot', 'Potato___Late_blight', 'Apple___Black_rot', 'Corn_(maize)___Northern_Leaf_Blight', 'Potato___Early_blight', 'Corn_(maize)___Common_rust_', 'Peach___healthy', 'Grape___healthy', 'Cherry_(including_sour)___healthy', 'Cherry_(including_sour)___Powdery_mildew', 'Strawberry___Leaf_scorch', 'Tomato___Tomato_mosaic_virus', 'Rice___Neck_Blast', 'Corn_(maize)___healthy', 'Potato___healthy', 'Tomato___Leaf_Mold', 'Wheat___Yellow_Rust', 'Tomato___Early_blight', 'Wheat___Healthy', 'Grape___Leaf_blight_(Isariopsis_Leaf_Spot)', 'Tomato___Target_Spot', 'Tomato___Spider_mites Two-spotted_spider_mite', 'Apple___healthy', 'Squash___Powdery_mildew', 'Pepper,_bell___healthy', 'Tomato___healthy', 'Tomato___Late_blight', 'Soybean___healthy', 'Peach___Bacterial_spot', 'Rice___Healthy', 'Blueberry___healthy', 'Grape___Esca_(Black_Measles)', 'Corn_(maize)___Cercospora_leaf_spot Gray_leaf_spot', 'Apple___Cedar_apple_rust', 'Pepper,_bell___Bacterial_spot', 'Wheat___Brown_Rust', 'Strawberry___healthy', 'Tomato___Tomato_Yellow_Leaf_Curl_Virus', 'Tomato___Septoria_leaf_spot', 'Raspberry___healthy', 'Rice___Leaf_Blast']  

Training notebook (Containing all details): [AI_API_Docker_codes/crop_disease_detection/plant-disease-classification-resnet-data-merged.ipynb](https://github.com/Adith082/Therap-Java-Fest-AgriDoctor/blob/main/AI_API_Docker_codes/crop_disease_detection/plant-disease-classification-resnet-data-merged.ipynb)  

## React Frontend
Code Folder: [frontend](https://github.com/Adith082/Therap-Java-Fest-AgriDoctor/tree/main/frontend)  
React frontend cloud deployed Url: https://therap-javafest-agridoctor.vercel.app  

### Technologies
Build tool : create-react-app  
 
Framework: React  
 
Template Engine: JSX  

Styling: CSS  

Prebuilt Design Framework: react-bootstrap  

HTTPs Request Library: Axios  

Other libraries: react-toastify, react-router-dom  

### Picture Credits
Image and Icon Credits: Flaticon, Freepik  

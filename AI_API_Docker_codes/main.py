from fastapi import FastAPI, File, UploadFile, Form, Response, HTTPException
from pydantic import BaseModel
import pickle
import numpy as np
from starlette.responses import FileResponse
import shutil
import torch
from torchvision import transforms
from model import ResNet9
from PIL import Image
import io
from fastapi.middleware.cors import CORSMiddleware

# Create a new FastAPI app instance
app = FastAPI()

# Add this line to enable CORS
app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],  # You can change this to specific origins if needed
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

# loading the crop recommendation model
RandomForestModel = './crop_recommendation/models/RandomForest.pkl'
cropRecommendationModel = pickle.load(open(RandomForestModel, 'rb'))

#defining diseases which can be detected
disease_classes = ['Apple___Apple_scab',
                   'Apple___Black_rot',
                   'Apple___Cedar_apple_rust',
                   'Apple___healthy',
                   'Blueberry___healthy',
                   'Cherry_(including_sour)___Powdery_mildew',
                   'Cherry_(including_sour)___healthy',
                   'Corn_(maize)___Cercospora_leaf_spot Gray_leaf_spot',
                   'Corn_(maize)___Common_rust_',
                   'Corn_(maize)___Northern_Leaf_Blight',
                   'Corn_(maize)___healthy',
                   'Grape___Black_rot',
                   'Grape___Esca_(Black_Measles)',
                   'Grape___Leaf_blight_(Isariopsis_Leaf_Spot)',
                   'Grape___healthy',
                   'Orange___Haunglongbing_(Citrus_greening)',
                   'Peach___Bacterial_spot',
                   'Peach___healthy',
                   'Pepper,_bell___Bacterial_spot',
                   'Pepper,_bell___healthy',
                   'Potato___Early_blight',
                   'Potato___Late_blight',
                   'Potato___healthy',
                   'Raspberry___healthy',
                   'Soybean___healthy',
                   'Squash___Powdery_mildew',
                   'Strawberry___Leaf_scorch',
                   'Strawberry___healthy',
                   'Tomato___Bacterial_spot',
                   'Tomato___Early_blight',
                   'Tomato___Late_blight',
                   'Tomato___Leaf_Mold',
                   'Tomato___Septoria_leaf_spot',
                   'Tomato___Spider_mites Two-spotted_spider_mite',
                   'Tomato___Target_Spot',
                   'Tomato___Tomato_Yellow_Leaf_Curl_Virus',
                   'Tomato___Tomato_mosaic_virus',
                   'Tomato___healthy']

# loading the crop disease detection model
disease_model_path = './crop_disease_detection/models/plant_disease_model.pth'
disease_model = ResNet9(3, len(disease_classes))
disease_model.load_state_dict(torch.load(
    disease_model_path, map_location=torch.device('cpu')))
disease_model.eval()

# Define a Pydantic model to represent soil data for crop_recommendation
class SoilData(BaseModel):
    """
    Pydantic model to represent soil data required for crop recommendation.
    
    Attributes:
        nitrogen (int): Nitrogen content in the soil.
        phosphorous (int): Phosphorous content in the soil.
        pottasium (int): Potassium content in the soil.
        ph (float): pH level of the soil.
        rainfall (float): Amount of rainfall in the area.
        temperature (float): Temperature of the area.
        humidity (float): Humidity level of the area.
    """
    nitrogen: int
    phosphorous: int
    pottasium: int
    ph: float
    rainfall: float
    temperature: float
    humidity: float
    
@app.post("/crop-predict")
async def crop_prediction(input_data: SoilData):
    """
    Endpoint for predicting recommended crops based on provided soil data.
    
    Args:
        input_data (SoilData): Input data containing soil parameters.
        
    Returns:
        json: A json containing the recommended crop name.
    """
    N = input_data.nitrogen
    P = input_data.phosphorous
    K = input_data.pottasium
    temperature = input_data.temperature
    humidity = input_data.humidity
    ph = input_data.ph
    rainfall = input_data.rainfall
    
    data = np.array([[N, P, K, temperature, humidity, ph, rainfall]])
    predictedCrop = cropRecommendationModel.predict(data)
    
    return {'recommendation': predictedCrop[0]}

@app.post("/upload/")
async def upload_image(file: UploadFile = File(...)):
    contents = await file.read()
    with open(file.filename, "wb") as f:
        f.write(contents)
    
    # Return the uploaded file as a response
    return FileResponse(file.filename, media_type="image/png")

@app.post("/disease-predict")
async def disease_prediction(input_data: UploadFile = File(...)):
    """
    Endpoint to predict disease from an uploaded image.

    Args:
        input_data (UploadFile): The uploaded image file.

    Returns:
        dict: A JSON response with the predicted disease label.
    """
    try:
        img = Image.open(io.BytesIO(await input_data.read()))
        transform = transforms.Compose([
            transforms.Resize(256),
            transforms.ToTensor(),
        ])
        img_t = transform(img)
        img_u = torch.unsqueeze(img_t, 0)

        # Get predictions from model
        yb = disease_model(img_u)
        # Pick index with highest probability
        _, preds = torch.max(yb, dim=1)
        prediction = disease_classes[preds[0].item()]

        return {"prediction": prediction}
    except Exception as e:
        raise HTTPException(status_code=400, detail=str(e))

    return {"error": "An error occurred"}

@app.get("/")
async def read_root():
    """
    Root endpoint that responds with a welcome message.
    """
    return {"message": "Welcome to your FastAPI API! ITs working!"}


from fastapi import FastAPI
from pydantic import BaseModel
import pickle
import numpy as np

# Create a new FastAPI app instance
app = FastAPI()

# loading the crop recommendation model
RandomForestModel = './crop_recommendation/models/RandomForest.pkl'
cropRecommendationModel = pickle.load(open(RandomForestModel, 'rb'))

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

# Define a function to handle the GET request at '/'
@app.get("/")
async def read_root():
    """
    Root endpoint that responds with a welcome message.
    """
    return {"message": "Welcome to your FastAPI API! ITs working!"}


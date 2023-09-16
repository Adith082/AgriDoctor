from fastapi import FastAPI
from pydantic import BaseModel

# Create a new FastAPI app instance
app = FastAPI()

# Define a Pydantic model to represent the request body
class InputData(BaseModel):
    text: str

# Define a function to handle the GET request at '/'
@app.get("/")
async def read_root():
    """
    Root endpoint that responds with a welcome message.
    """
    return {"message": "Welcome to your FastAPI interface!"}

@app.get("/my")
def read_root():
    """
    Root endpoint that responds with a welcome message.
    """
    return {"message": "mymy"}

@app.post("/echo", response_model=InputData)
async def echo_text(input_data: InputData):
    """
    Endpoint that receives a string in the request body and returns it in a JSON response.
    """
    received_text = input_data.text
    return {"text": received_text}
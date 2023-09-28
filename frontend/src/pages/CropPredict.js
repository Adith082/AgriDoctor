import React, { useEffect, useState } from 'react'
import NavbarG from '../components/NavbarG'
import "./CropPredict.css"
import Button from 'react-bootstrap/Button';
import 'bootstrap/dist/css/bootstrap.min.css';
import { toast } from 'react-toastify';
import Collapse from 'react-bootstrap/Collapse';
import Form from 'react-bootstrap/Form';
import InputGroup from 'react-bootstrap/InputGroup';
import Card from 'react-bootstrap/Card';
import FeedbackG from '../components/FeedbackG';

function CropPredict() {
  const [walletBalance, setWalletBalance] = useState(0);

  const [nitrogen, setNitrogen] = useState(0)
  const [phosphorus, setPhosphorus] = useState(0)
  const [potassium, setPotassium] = useState(0)
  const [pH, setPH] = useState(0.0)
  const [cityName, setCityName] = useState("")

  const [nitrogenValid, setNitrogenValid] = useState(true)
  const [phosphorusValid, setPhosphorusValid] = useState(true)
  const [potassiumValid, setPotassiumValid] = useState(true)
  const [pHValid, setPHValid] = useState(true)
  const [cityNameValid, setCityNameValid] = useState(true)

  const [predictedCrop, setPredictedCrop] = useState("")

  useEffect(() => {
    // Define a function to fetch the wallet balance
    const fetchWalletBalance = async () => {
      try {
        const response = await fetch('API_ENDPOINT'); // Replace with your API endpoint
        const data = await response.json();
        setWalletBalance(data.walletBalance);
      } catch (error) {
        console.error('Error fetching wallet balance:', error);
      }
    };

      // Call the function to fetch wallet balance when the component mounts
      fetchWalletBalance();
  }, []);

  const handleCropPredClick = (e) => {
    if(nitrogen==="") setNitrogenValid(false);
    if(phosphorus==="") setPhosphorusValid(false);
    if(potassium==="") setPotassiumValid(false);
    if(pH==="") setPHValid(false);
    if(cityName==="") setCityNameValid(false);

    if(nitrogenValid && phosphorusValid && potassiumValid && pHValid && cityNameValid){
      toast.success("handle crop clicked");
    }
  }

  return (
    <div className='crop-predict-container'>
      <NavbarG currentPage={1} walletBalance={walletBalance}/>
      <div className='crop-duo-container'>
        
        <div className='input-container'>
          <div className="input-section">

            <h1 className='input-title'>Soil Data</h1>
            <InputGroup className="mb-3">
              <InputGroup.Text  className="input-group-text-dark">Nitrogen(ppm)&nbsp;&nbsp;&nbsp;&nbsp;</InputGroup.Text>
              <Form.Control
              type="number"
              placeholder="Nitogen(N) amount in soil(Unit: ppm)"
              value={nitrogen}
              onChange={(e)=>{setNitrogen(e.target.value);setPredictedCrop("");}}
              />
              <Collapse in={!nitrogenValid}>
              <div id="example-collapse-text">
                  <Card body className='warn-card custom-card'>
                    Field cannot be kept empty!
                  </Card>
              </div>
              </Collapse>
            </InputGroup>
            <InputGroup className="mb-3">
              <InputGroup.Text  className="input-group-text-dark">Phosphorus(ppm)</InputGroup.Text>
              <Form.Control
              type="number"
              placeholder="Phosphorus(P) amount in soil(Unit: ppm)"
              value={phosphorus}
              onChange={(e)=>{setPhosphorus(e.target.value);setPredictedCrop("");}}
              />
              <Collapse in={!phosphorusValid}>
              <div id="example-collapse-text">
                  <Card body className='warn-card custom-card'>
                    Field cannot be kept empty!
                  </Card>
              </div>
              </Collapse>
            </InputGroup>
            <InputGroup className="mb-3">
              <InputGroup.Text  className="input-group-text-dark">Potassium(ppm)&nbsp;&nbsp;</InputGroup.Text>
              <Form.Control
              type="number"
              placeholder="Potassium(K) amount in soil(Unit: ppm)"
              value={potassium}
              onChange={(e)=>{setPotassium(e.target.value);setPredictedCrop("");}}
              />
              <Collapse in={!potassiumValid}>
              <div id="example-collapse-text">
                  <Card body className='warn-card custom-card'>
                    Field cannot be kept empty!
                  </Card>
              </div>
              </Collapse>
            </InputGroup>
            <InputGroup className="mb-3">
              <InputGroup.Text  className="input-group-text-dark">pH</InputGroup.Text>
              <Form.Control
              type="number"
              placeholder="pH level of soil"
              value={pH}
              onChange={(e)=>{setPH(e.target.value);setPredictedCrop("");}}
              />
              <Collapse in={!pHValid}>
              <div id="example-collapse-text">
                  <Card body className='warn-card custom-card'>
                    Field cannot be kept empty!
                  </Card>
              </div>
              </Collapse>
            </InputGroup>
            <InputGroup className="mb-3">
              <InputGroup.Text  className="input-group-text-dark">City or District Name</InputGroup.Text>
              <Form.Control
              type="text"
              placeholder="City or District name where the soild data is collected"
              value={cityName}
              onChange={(e)=>{setPredictedCrop("");setCityName(e.target.value)}}
              />
              <Collapse in={!cityNameValid}>
              <div id="example-collapse-text">
                  <Card body className='warn-card custom-card'>
                    Field cannot be kept empty!
                  </Card>
              </div>
              </Collapse>
            </InputGroup>
            <Button className="prediction-button" onClick={handleCropPredClick}>Get Crop Recommendation</Button>

          </div>
        </div>

        <div className='prediction-container'>
          <div className="prediction-section">
            <h1 className='input-title'>Crop Recommendation</h1>
            {predictedCrop === "" ? (
              <h3>Click on the button left to get recommendation</h3>
            ) : (
              <h3>You should grow <strong>{predictedCrop}</strong> in your soil</h3>
            )}
          </div>
          {predictedCrop === "" ? (
              <div></div>
            ) : (
              <FeedbackG feedbackTitle={"Suggested Crop:"} predictionMessage={predictedCrop} show={true}/>
            )}
        </div>

      </div>
    </div>
  )
}

export default CropPredict
import React, { useEffect, useState } from 'react'
import NavbarG from '../components/NavbarG'
import "./FertilizerPredict.css"
import Button from 'react-bootstrap/Button';
import 'bootstrap/dist/css/bootstrap.min.css';
import { toast } from 'react-toastify';
import Collapse from 'react-bootstrap/Collapse';
import Form from 'react-bootstrap/Form';
import InputGroup from 'react-bootstrap/InputGroup';
import Card from 'react-bootstrap/Card';
import FeedbackG from '../components/FeedbackG';
import Dropdown from 'react-bootstrap/Dropdown';
import DropdownButton from 'react-bootstrap/DropdownButton';
import SupportedCrops from '../components/SupportedCrops';

function FertilizerPredict() {
  const [walletBalance, setWalletBalance] = useState(0);

  const [nitrogen, setNitrogen] = useState(0)
  const [phosphorus, setPhosphorus] = useState(0)
  const [potassium, setPotassium] = useState(0)
  const [crop, setCrop] = useState("")

  const [nitrogenValid, setNitrogenValid] = useState(true)
  const [phosphorusValid, setPhosphorusValid] = useState(true)
  const [potassiumValid, setPotassiumValid] = useState(true)

  const [predictedCrop, setPredictedCrop] = useState("")
  const [cropValid, setCropValid] = useState(true)

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
    else setNitrogenValid(true);
    if(phosphorus==="") setPhosphorusValid(false);
    else setPhosphorusValid(true);
    if(potassium==="") setPotassiumValid(false);
    else setPotassiumValid(true);
    if(crop==="") setCropValid(false);
    else setCropValid(true);

    if(nitrogenValid && phosphorusValid && potassiumValid &&setCropValid){
      toast.success("handle crop clicked");
    }
  }

  return (
    <div className='fertilizer-predict-container'>
      <NavbarG currentPage={2} walletBalance={walletBalance}/>
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
              <InputGroup.Text  className="input-group-text-dark">Crop Grown</InputGroup.Text>
              <Form.Control
              type="text"
              placeholder="Select the crop you are growing or will grow"
              value={crop}
              onChange={(e)=>{setPotassium(e.target.value);setPredictedCrop("");}}
              readOnly
              />
              <DropdownButton
                variant="outline-secondary"
                title="Select"
                id="input-group-dropdown-1"
              >
              {SupportedCrops.map((plant, index) => (
                <Dropdown.Item 
                  key={index} 
                  onClick={() => setCrop(plant)}
                >
                  {plant}
                </Dropdown.Item>
              ))}
              </DropdownButton>
              <Collapse in={!cropValid}>
              <div id="example-collapse-text">
                  <Card body className='warn-card custom-card'>
                    Field cannot be kept empty!
                  </Card>
              </div>
              </Collapse>
            </InputGroup>
            <Button className="prediction-button" onClick={handleCropPredClick}>Get Fertilizer Recommendation</Button>

          </div>
        </div>

        <div className='prediction-container'>
          <div className="prediction-section">
            <h1 className='input-title'>Fertilizer Recommendation</h1>
            {predictedCrop === "" ? (
              <h3>Click on the button left to get recommendation</h3>
            ) : (
              <h3>You should grow <strong>{predictedCrop}</strong> in your soil</h3>
            )}
          </div>
          {predictedCrop === "" ? (
              <div></div>
            ) : (
              <FeedbackG feedbackTitle={"Recommended Fertilizer:"} predictionMessage={predictedCrop} show={true}/>
            )}
        </div>

      </div>
    </div>
  )
}

export default FertilizerPredict
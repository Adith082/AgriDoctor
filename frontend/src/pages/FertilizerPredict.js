import React, { useContext, useEffect, useState } from 'react'
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
import {SupportedCrops, SupportedCropsBengali} from '../components/SupportedCrops';
import { LanguageContext } from '../contexts/LanguageContext';
import { LoginContext } from '../contexts/LoginContext';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import Spinner from 'react-bootstrap/Spinner';

function NewlineText({ text }) {
  const newText = text.split('\n').map((str, index) => (
    <React.Fragment key={index}>
      {str}
      <br />
    </React.Fragment>
  ));
  return <>{newText}</>;
}

function FertilizerPredict() {

  const {isEN} = useContext(LanguageContext);
  const {token, uid, setWalletBalance} = useContext(LoginContext);

  const [showSpinner, setShowSpinner] = useState(false);

  const navigate = useNavigate();

  const [nitrogen, setNitrogen] = useState(0)
  const [phosphorus, setPhosphorus] = useState(0)
  const [potassium, setPotassium] = useState(0)
  const [crop, setCrop] = useState("")

  const [nitrogenValid, setNitrogenValid] = useState(true)
  const [phosphorusValid, setPhosphorusValid] = useState(true)
  const [potassiumValid, setPotassiumValid] = useState(true)

  const [predictedCrop, setPredictedCrop] = useState("")
  const [predictedCropB, setPredictedCropB] = useState("")
  const [cropValid, setCropValid] = useState(true)

  useEffect(() => {
    //checking the existence of token
    const checkToken = () => {
      if(!token){
        navigate("/");
        toast.warn(isEN ? "Login First!" : "প্রথমে লগ-ইন করুন!");
      }
    }

    checkToken();
  }, [token, isEN, navigate]);

  function getFirstSentence(text) {
    const firstSentence = text.match(/^[^.!?]+[.!?]/);
    return firstSentence ? firstSentence[0] : '';
  }

  const handleCropPredClick = (e) => {
    if(nitrogen==="") setNitrogenValid(false);
    else setNitrogenValid(true);
    if(phosphorus==="") setPhosphorusValid(false);
    else setPhosphorusValid(true);
    if(potassium==="") setPotassiumValid(false);
    else setPotassiumValid(true);
    if(crop==="") setCropValid(false);
    else setCropValid(true);

    if(nitrogenValid && phosphorusValid && potassiumValid &&cropValid){
      setShowSpinner(true);
      if(token){
        const headers = {
            Authorization: "Bearer "+token
        }
        axios.post("https://agridoctorbackend-production.up.railway.app/api/services/crop-fertilizer-recommendation", {
          "nitrogen": parseInt(nitrogen),
          "phosphorous": parseInt(phosphorus),
          "pottasiam": parseInt(potassium),
          "cropName": crop,
          "uid": uid
        }, { headers })
        .then(response => {
            if(response.data.message==="No currency left. Cannot Provide Service."){
              toast.warn(isEN ? "Not enough balance. Please recharge your wallet balance!" : "যালেন্স পর্যাপ্ত নয়। দয়া করে আপনার ওয়ালেট ব্যালেন্স চার্জ করুন!");
              setShowSpinner(false);
              return;
            }
            console.log(response.data);
            setWalletBalance(response.data.wallet);
            setPredictedCrop(response.data.recommendationEnglish);
            setPredictedCropB(response.data.recommendationBengali);
            toast.success(isEN ? "Successful!" : "সফল!");
            setShowSpinner(false);
        })
        .catch(error => {
            console.error('Error:', error);
            toast.warning(isEN ? "Something went wrong! Try again later." : "কিছু ভুল হয়েছে! পরে আবার চেষ্টা করুন।");
            setShowSpinner(false)
        });
      }
    }else{
      toast.warn(isEN ? "Fields contain invalid inputs!" : "ক্ষেত্রগুলি অবৈধ ইনপুট ধারণ করে!");
      setShowSpinner(false)
    }
  }

  return (
    <div className='fertilizer-predict-container'>
      <NavbarG currentPage={2}/>
      <div className='crop-duo-container'>
        
        <div className='input-container'>
          <div className="input-section">

            <h1 className='input-title'>{isEN? "Soil Data": "মাটি তথ্য"}</h1>
            <InputGroup className="mb-3">
              <InputGroup.Text  className="input-group-text-dark">{isEN ? "Nitrogen (ppm)" : "নাইট্রোজেন (ppm)"}&nbsp;&nbsp;&nbsp;&nbsp;</InputGroup.Text>
              <Form.Control
              type="number"
              placeholder={isEN ? "Nitrogen(N) amount in soil (Unit: ppm)" : "মাটিতে নাইট্রোজেন(N) পরিমাণ (একক: ppm)"}
              value={nitrogen}
              onChange={(e)=>{setNitrogen(e.target.value);setPredictedCrop("");}}
              />
              <Collapse in={!nitrogenValid}>
              <div id="example-collapse-text">
                  <Card body className='warn-card custom-card'>
                  {isEN ? "Field cannot be kept empty!" : "ক্ষেত্র ফাঁকা রাখা যাবে না!"}
                  </Card>
              </div>
              </Collapse>
            </InputGroup>
            <InputGroup className="mb-3">
              <InputGroup.Text  className="input-group-text-dark">{isEN ? "Phosphorus(ppm)" : "ফসফরাস(ppm)"}</InputGroup.Text>
              <Form.Control
              type="number"
              placeholder={isEN ? "Phosphorus(P) amount in soil(Unit: ppm)" : "মাটির ফসফরাস(P) পরিমাণ (একক: ppm)"}
              value={phosphorus}
              onChange={(e)=>{setPhosphorus(e.target.value);setPredictedCrop("");}}
              />
              <Collapse in={!phosphorusValid}>
              <div id="example-collapse-text">
                  <Card body className='warn-card custom-card'>
                    {isEN ? "Field cannot be kept empty!" : "ক্ষেত্র ফাঁকা রাখা যাবে না!"}
                  </Card>
              </div>
              </Collapse>
            </InputGroup>
            <InputGroup className="mb-3">
              <InputGroup.Text  className="input-group-text-dark">{isEN ? "Potassium(ppm)" : "পটাশিয়াম(ppm)"}&nbsp;&nbsp;</InputGroup.Text>
              <Form.Control
              type="number"
              placeholder={isEN ? "Potassium(K) amount in soil (Unit: ppm)" : "মাটির পোটাশিয়াম(K) পরিমাণ (একক: ppm)"}
              value={potassium}
              onChange={(e)=>{setPotassium(e.target.value);setPredictedCrop("");}}
              />
              <Collapse in={!potassiumValid}>
              <div id="example-collapse-text">
                  <Card body className='warn-card custom-card'>
                  {isEN ? "Field cannot be kept empty!" : "ক্ষেত্র ফাঁকা রাখা যাবে না!"}
                  </Card>
              </div>
              </Collapse>
            </InputGroup>
            <InputGroup className="mb-3">
              <InputGroup.Text  className="input-group-text-dark">{isEN ? "Crop Grown" : "উৎপাদিত ফসল"}</InputGroup.Text>
              <Form.Control
              type="text"
              placeholder="Select the crop you are growing or will grow"
              value={crop}
              onChange={(e)=>{setPotassium(e.target.value);setPredictedCrop("");}}
              readOnly
              />
              <DropdownButton
                variant="outline-light"
                title="Select"
                id="input-group-dropdown-1"
              >
                <div style={{ maxHeight: '30vw', overflowY: 'auto' }}>
                {SupportedCrops.map((plant, index) => (
                  <Dropdown.Item 
                    key={index} 
                    onClick={() => setCrop(plant)}
                  >
                    {plant}{"  "+SupportedCropsBengali[index]}
                  </Dropdown.Item>
                ))}
                </div>
              </DropdownButton>
              <Collapse in={!cropValid}>
              <div id="example-collapse-text">
                  <Card body className='warn-card custom-card'>
                  {isEN ? "Field cannot be kept empty!" : "ক্ষেত্র ফাঁকা রাখা যাবে না!"}
                  </Card>
              </div>
              </Collapse>
            </InputGroup>
            <Button className="prediction-button" onClick={handleCropPredClick}><Spinner className={showSpinner?"":"no-display"} animation="border" variant="light" role='status' size="sm"/>{isEN?" Get Fertilizer Recommendation":" সার সুপারিশ পান"}</Button>

          </div>
        </div>

        <div className='prediction-container-fr'>
          <div className='empty-container'></div>
          <div className={predictedCrop===""?"prediction-section-ini":"prediction-section-fr"}>
            <h1 className='input-title'>{isEN?"Fertilizer Recommendation":"সার সুপারিশ"}</h1>
            {predictedCrop === "" ? (
              <h3>{isEN?"Click on the button left to get recommendation":"সুপারিশ পেতে বাম বোতামে ক্লিক করুন"}</h3>
            ) : (
              <h3>{isEN?<NewlineText text={predictedCrop}/>:<NewlineText text={predictedCropB}/>}</h3>
            )}
          </div>
          {predictedCrop === "" ? (
              <div></div>
            ) : (
              <FeedbackG feedbackTitle={"Recommended Fertilizer: "} predictionMessage={getFirstSentence(predictedCrop)} show={true}/>
            )}
        </div>

      </div>
    </div>
  )
}

export default FertilizerPredict
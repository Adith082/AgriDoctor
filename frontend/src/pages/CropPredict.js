import React, { useContext, useEffect, useState } from 'react'
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
import { LanguageContext } from '../contexts/LanguageContext';
import { LoginContext } from '../contexts/LoginContext';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import { SupportedCrops, SupportedCropsBengali } from '../components/SupportedCrops';
import Spinner from 'react-bootstrap/Spinner';
import Image from 'react-bootstrap/Image';

function CropPredict() {

  const {isEN} = useContext(LanguageContext);
  const {token, uid, setWalletBalance} = useContext(LoginContext);

  const navigate = useNavigate();

  const [nitrogen, setNitrogen] = useState(0)
  const [phosphorus, setPhosphorus] = useState(0)
  const [potassium, setPotassium] = useState(0)
  const [pH, setPH] = useState(0.0)
  const [cityName, setCityName] = useState("")
  const [rainfall, setRainfall] = useState(0)

  const [nitrogenValid, setNitrogenValid] = useState(true)
  const [phosphorusValid, setPhosphorusValid] = useState(true)
  const [potassiumValid, setPotassiumValid] = useState(true)
  const [pHValid, setPHValid] = useState(true)
  const [cityNameValid, setCityNameValid] = useState(true)
  const [rainfallValid, setRainfallValid] = useState(true)

  const [predictedCrop, setPredictedCrop] = useState("")

  
  const [showSpinner, setShowSpinner] = useState(false);

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

  const handleCropPredClick = (e) => {
    if(nitrogen==="") setNitrogenValid(false); else setNitrogenValid(true);
    if(phosphorus==="") setPhosphorusValid(false); else setPhosphorusValid(true);
    if(potassium==="") setPotassiumValid(false); else setPotassiumValid(true);
    if(pH==="") setPHValid(false); else setPHValid(true);
    if(cityName==="") setCityNameValid(false); else setCityNameValid(true);
    if(rainfall==="") setRainfallValid(false); else setRainfallValid(true);

    if(nitrogenValid && phosphorusValid && potassiumValid && pHValid && cityNameValid && rainfallValid){
      if(token){
        setShowSpinner(true);
        const headers = {
            Authorization: "Bearer "+token
        }
        axios.post("https://agridoctorbackend-production.up.railway.app/api/services/crop-recommendation", {
            "nitrogen": parseInt(nitrogen),
            "phosphorous": parseInt(phosphorus),
            "pottasium": parseInt(potassium),
            "ph": parseFloat(pH),
            "rainfall": parseFloat(rainfall),
            "city":cityName,
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
            setPredictedCrop(response.data.recommendation);
            toast.success(isEN ? "Successful!" : "সফল!");
            setShowSpinner(false);
        })
        .catch(error => {
            console.error('Error:', error);
            toast.warning(isEN ? "Something went wrong! Try again later." : "কিছু ভুল হয়েছে! পরে আবার চেষ্টা করুন।");
            setShowSpinner(false);
        });
      }
    }else{
      toast.warn(isEN ? "Fields contain invalid inputs!" : "ক্ষেত্রগুলি অবৈধ ইনপুট ধারণ করে!");
      setShowSpinner(false);
    }
  }

  return (
    <div className='crop-predict-container'>
      <NavbarG currentPage={1} />
      <div className='crop-duo-container'>
        
        <div className='input-container'>
          <div className="input-section">

            <h1 className='input-title'>
              <Image
                width={50}
                height={50}
                className="mr-3"
                src="https://cdn-icons-png.flaticon.com/512/12309/12309460.png" //Image Credit: FlatIcon
              />{'  '}{isEN? "Soil Data": "মাটি তথ্য"}
            </h1>
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
              <InputGroup.Text  className="input-group-text-dark">pH</InputGroup.Text>
              <Form.Control
              type="number"
              placeholder={isEN ? "pH value of the soil" : "মাটির pH মান"}
              value={pH}
              onChange={(e)=>{setPH(e.target.value);setPredictedCrop("");}}
              />
              <Collapse in={!pHValid}>
              <div id="example-collapse-text">
                  <Card body className='warn-card custom-card'>
                  {isEN ? "Field cannot be kept empty!" : "ক্ষেত্র ফাঁকা রাখা যাবে না!"}
                  </Card>
              </div>
              </Collapse>
            </InputGroup>
            <InputGroup className="mb-3">
              <InputGroup.Text  className="input-group-text-dark">{isEN ? "Rainfall(mm)" : "বৃষ্টি(mm)"}</InputGroup.Text>
              <Form.Control
              type="number"
              placeholder={isEN ? "Rainfall in the region. (Unit: mm)" : "এলাকায় বৃষ্টি। (একক: mm)"}
              value={rainfall}
              onChange={(e)=>{setRainfall(e.target.value);setPredictedCrop("");}}
              />
              <Collapse in={!rainfallValid}>
              <div id="example-collapse-text">
                  <Card body className='warn-card custom-card'>
                  {isEN ? "Field cannot be kept empty!" : "ক্ষেত্র ফাঁকা রাখা যাবে না!"}
                  </Card>
              </div>
              </Collapse>
            </InputGroup>
            <InputGroup className="mb-3">
              <InputGroup.Text  className="input-group-text-dark">{isEN ? "City or District Name" : "শহর অথবা জেলার নাম"}</InputGroup.Text>
              <Form.Control
              type="text"
              placeholder={isEN ? "City or District name where the soil data is collected" : "সেথায় তৈরি হয়েছে মাটি উপাদান ডেটা এই শহর বা জেলার নাম"}
              value={cityName}
              onChange={(e)=>{setPredictedCrop("");setCityName(e.target.value)}}
              />
              <Collapse in={!cityNameValid}>
              <div id="example-collapse-text">
                  <Card body className='warn-card custom-card'>
                  {isEN ? "Field cannot be kept empty!" : "ক্ষেত্র ফাঁকা রাখা যাবে না!"}
                  </Card>
              </div>
              </Collapse>
            </InputGroup>
            <Button className="prediction-button" onClick={handleCropPredClick}><Spinner className={showSpinner?"":"no-display"} animation="border" variant="light" role='status' size="sm"/>{isEN ? " Get Crop Recommendation" : " ফসলের সুপারিশ পান"}</Button>

          </div>
        </div>

        <div className='prediction-container'>
          <div className="prediction-section">
            <h1 className='input-title'>
              <Image
                width={50}
                height={50}
                className="mr-3"
                src="	https://cdn-icons-png.flaticon.com/512/8615/8615981.png" //Image Credit: FlatIcon
              />{'  '}
              {isEN ? "Crop Recommendation" : "ফসলের সুপারিশ"}</h1>
            {predictedCrop === "" ? (
              <h3>{isEN ? "Click on the button left to get recommendation" : "সুপারিশ পেতে বামে বাটনে ক্লিক করুন"}</h3>
            ) : (
              <h3>{isEN?"You should grow ":"আপনার মাটিতে "}<strong>{
                isEN?predictedCrop:
                SupportedCropsBengali[SupportedCrops.findIndex(crop => crop.toLowerCase() === predictedCrop.toLowerCase())]
              }</strong>{isEN?" in your soil":" চাষ করতে হবে"}</h3>
            )}
          </div>
          {predictedCrop === "" ? (
              <div></div>
            ) : (
              <FeedbackG feedbackTitle={"Suggested Crop: "} predictionMessage={predictedCrop} show={true}/>
            )}
        </div>

      </div>
    </div>
  )
}

export default CropPredict
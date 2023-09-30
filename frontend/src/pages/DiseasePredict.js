import React, { useContext, useEffect, useState } from 'react'
import NavbarG from '../components/NavbarG'
import "./DiseasePredict.css"
import Button from 'react-bootstrap/Button';
import 'bootstrap/dist/css/bootstrap.min.css';
import { toast } from 'react-toastify';
import Image from 'react-bootstrap/Image';
import Collapse from 'react-bootstrap/Collapse';
import Form from 'react-bootstrap/Form';
import InputGroup from 'react-bootstrap/InputGroup';
import Card from 'react-bootstrap/Card';
import { LanguageContext } from '../contexts/LanguageContext';
import { LoginContext } from '../contexts/LoginContext';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

function DiseasePredict() {

  const defaultDiseaseMessage = "Disease not yet identiied. Click the button below to get started."

  const {isEN} = useContext(LanguageContext);
  const {token, uid, setWalletBalance} = useContext(LoginContext);

  const navigate = useNavigate();

  const [image, setImage] = useState(null);
  const [isDraggedOver, setIsDraggedOver] = useState(false);
  const [diseaseMessage, setDiseaseMessage] = useState(defaultDiseaseMessage);

  const [feedbackMessage, setFeedbackMessage] = useState("")
  const [causeDisease, setCauseDisease] = useState("")
  const [preventionDisease, setPreventionDisease] = useState("")
  const [plant, setPlant] = useState("")

  const [diseaseMessageB, setDiseaseMessageB] = useState(defaultDiseaseMessage);
  const [causeDiseaseB, setCauseDiseaseB] = useState("")
  const [preventionDiseaseB, setPreventionDiseaseB] = useState("")
  const [plantB, setPlantB] = useState("")

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

  const handleImageUpload = (e) => {
    setDiseaseMessage(defaultDiseaseMessage);
    setImage(e.target.files[0]);
  }

  const handleDragOver = (e) => {
    e.preventDefault();
    setIsDraggedOver(true);
  }

  const handleDragEnter = (e) => {
    e.preventDefault();
    setIsDraggedOver(true);
  }

  const handleDragLeave = () => {
    setIsDraggedOver(false);
  }

  const handleDrop = (e) => {
    e.preventDefault();
    setIsDraggedOver(false);

    const droppedFile = e.dataTransfer.files[0];
    if (droppedFile && droppedFile.type.startsWith('image/')) {
      setDiseaseMessage(defaultDiseaseMessage);
      setImage(droppedFile);
    } else {
      toast.warn("Dropped file was invalid image file!");
    }
  }

  const handlePredictionClick = async (e) => {
    if(image){
      if(token){
        const headers = {
            Authorization: "Bearer "+token,
            'Content-Type': 'multipart/form-data'
        }

        const data = new FormData();
        data.append("input_data",image);
        data.append("uid",uid)

        axios.post("https://agridoctorbackend-production.up.railway.app/api/services/crop-disease-detection", data , { headers })
        .then(response => {
            if(response.data.message==="No currency left. Cannot Provide Service."){
              toast.warn(isEN ? "Not enough balance. Please recharge your wallet balance!" : "যালেন্স পর্যাপ্ত নয়। দয়া করে আপনার ওয়ালেট ব্যালেন্স চার্জ করুন!");
              return;
            }
            console.log(response.data);
            setWalletBalance(response.data.wallet);

            setPlant(response.data.cropNameEn);
            setDiseaseMessage(response.data.diseaseNameEn);
            setCauseDisease(response.data.causeOfDiseaseEn);
            setPreventionDisease(response.data.preventionOrCureEn);

            setPlantB(response.data.cropNameBn);
            setDiseaseMessageB(response.data.diseaseNameBn);
            setCauseDiseaseB(response.data.causeOfDiseaseBn);
            setPreventionDiseaseB(response.data.preventionOrCureBn);

            toast.success(isEN ? "Successful!" : "সফল!");
        })
        .catch(error => {
            console.error('Error:', error);
            toast.warning(isEN ? "Something went wrong! Try again later." : "কিছু ভুল হয়েছে! পরে আবার চেষ্টা করুন।");
        });

      }
    }else{
      toast.warn(isEN ? "Fields contain invalid inputs!" : "ক্ষেত্রগুলি অবৈধ ইনপুট ধারণ করে!");
    }
  }

  const handleFeedbackClick = (e) => {
    toast.success("Feedback Recieved");
  }

  return (
    <div className='disease-container'>
      <NavbarG currentPage={3}/>
      <div className='duo-container'>
        <div className='image-upload'>
          <h1 className='image-upload-title-ex'>Crop leaf Image</h1>
          {image ? (
            <img
              src={URL.createObjectURL(image)}
              alt="Uploaded Leaf"
              style={{ maxWidth: '40vw', maxHeight: '40vh', border: "2px solid white" }}
            />
          ) : (
            <div>
              <div className='upload-instruct'>Upload leaf image of the crop for which you want to identify the disease.</div>
              <div
                className={isDraggedOver ? 'dragged-over' : 'not-dragged-over'}
                onDragOver={handleDragOver}
                onDragEnter={handleDragEnter}
                onDragLeave={handleDragLeave}
                onDrop={handleDrop}
              >
                <Image
                  style={{ height: '15vh' }}
                  className="mr-3"
                  src="https://cdn-icons-png.flaticon.com/512/8191/8191568.png" //In=mage Credit: FlatIcon
                />
                <div>Drag and drop your image here</div>Or<p> Select the image with the button below</p>
              </div>
            </div>
          )}
          <div className="upload-btn">
            <div className="uploadimage">
                <label className='label-c' htmlFor="imgs">Select Image</label>
            </div>
            <input id="imgs" type="file" accept="image/*" onChange={handleImageUpload} />
          </div>
        </div>

        <div className='identify-disease'>
          <h1 className='image-upload-title-ex'>Identified Disease</h1>
          <div className='upload-instruct'><strong>Plant: </strong>{plant+"  "}<strong>Disease: </strong>{diseaseMessage}</div>
          <div className='upload-instruct'> <strong>Cause of Disease</strong> <br /> {causeDisease} </div>
          <div className='upload-instruct'> <strong>Prevention of Disease</strong> <br /> {preventionDisease} </div>
          <Button className="prediction-button" onClick={handlePredictionClick}>Identify Disease</Button>

          <div className={diseaseMessage===defaultDiseaseMessage? "no-display":"feedback-section"}>
            <h2 className='feedback-title'>Your feedback On Identified Disease</h2>
            <img
              src={image? URL.createObjectURL(image):""}
              alt="Uploaded Leaf"
              style={{ maxWidth: '15vw', maxHeight: '15vh', border: "2px solid white" }}
            />
            <div><strong>Plant: </strong>{plant+"  "}<strong>Disease: </strong>{diseaseMessage}</div>{/** Title of the feedback */}
            <InputGroup className="mb-3">
              <InputGroup.Text  className="input-group-text-dark">Your Feedback</InputGroup.Text>
              <Form.Control
              type="text"
              placeholder="Add your feedback or comment here"
              value={feedbackMessage}
              onChange={(e)=>{setFeedbackMessage(e.target.value)}}
              />
              <Collapse in={feedbackMessage===""}>
              <div id="example-collapse-text">
                  <Card body className='warn-card custom-card'>
                    Feedback cannot be empty!
                  </Card>
              </div>
              </Collapse>
          </InputGroup>
          
          <Button className="prediction-button" onClick={handleFeedbackClick}>Submit Feedback</Button>
          </div>
        </div>

      </div>
    </div>
  )
}

export default DiseasePredict
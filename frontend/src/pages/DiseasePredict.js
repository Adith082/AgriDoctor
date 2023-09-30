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
import Spinner from 'react-bootstrap/Spinner';

function removeBR(string){
  const stringWithLineBreaks = string.replace(/<br\/>/g, '\n');

  // Render in JSX
  return (
    <div>
      {stringWithLineBreaks.split('\n').map((line, index) => (
        <div key={index}>{line}</div>
      ))}
    </div>
  );
}

function DiseasePredict() {

  const defaultDiseaseMessage = "Disease not yet identiied. Click the button below to get started.";
  const defaultDiseaseMessageB = "রোগ এখনও সনাক্ত করা যায়নি। শুরু করতে নীচের বোতামে ক্লিক করুন।";

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

  const [diseaseMessageB, setDiseaseMessageB] = useState(defaultDiseaseMessageB);
  const [causeDiseaseB, setCauseDiseaseB] = useState("")
  const [preventionDiseaseB, setPreventionDiseaseB] = useState("")
  const [plantB, setPlantB] = useState("")

  const [showSuccess, setShowSuccress] = useState(false);
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

  const setAllDefault = () => {
    setDiseaseMessage(defaultDiseaseMessage);
    setCauseDiseaseB(defaultDiseaseMessageB);
    setCauseDisease("");
    setCauseDiseaseB("");
    setPreventionDisease("");
    setPreventionDiseaseB("");
  }

  const handleImageUpload = (e) => {
    setAllDefault();
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
      setAllDefault();
      setImage(droppedFile);
    } else {
      toast.warn("Dropped file was invalid image file!");
    }
  }

  const handlePredictionClick = async (e) => {
    if(image){
      setShowSpinner(true);
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
              setShowSpinner(false)
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
      setShowSpinner(false);
    }
  }

  const sendFeedbackImage = (feedbackId) => {
    if(feedbackMessage!==""){
      if(token){
        const headers = {
          Authorization: "Bearer "+token,
          'Content-Type': 'multipart/form-data'
      }

      setShowSpinner(true);

      const data = new FormData();
      data.append("image",image);

      axios.post("https://agridoctorbackend-production.up.railway.app/api/feedback/image/upload/"+feedbackId, data , { headers })
        .then(response => {
            console.log(response.data);
            setShowSuccress(true);
            toast.success(isEN ? "Successful!" : "সফল!");
            setShowSpinner(false)
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

  const handleFeedbackClick = (e) => {
    if(feedbackMessage!==""){
      if(token){
        setShowSpinner(true);
        const headers = {
            Authorization: "Bearer "+token
        }
        axios.post("https://agridoctorbackend-production.up.railway.app/api/user/"+uid+"/feedback", {
          "feedBackTitle":"{Plant and Disease: "+plant+" "+diseaseMessage+"}",
          "content":feedbackMessage
        }, { headers })
        .then(response => {
            console.log(response.data);
            sendFeedbackImage(response.data.feedBackId);
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
    <div className='disease-container'>
      <NavbarG currentPage={3}/>
      <div className='duo-container'>
        <div className='image-upload'>
          <h1 className='image-upload-title-ex'>{isEN?"Crop leaf Image(Clean Background)":"পাতার ছবি(পরিষ্কার পটভূমি)"}</h1>
          {image ? (
            <img
              src={URL.createObjectURL(image)}
              alt="Uploaded Leaf"
              style={{ maxWidth: '40vw', maxHeight: '40vh', border: "2px solid white" }}
            />
          ) : (
            <div>
              <div className='upload-instruct'>{isEN ? "Upload leaf image of the crop for which you want to identify the disease." : "আপনি যে ফসলের রোগ চিহ্নিত করতে চান তার পাতার ছবি আপলোড করুন।"}</div>
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
                <div>{isEN ? "Drag and drop your image here" : "এখানে আপনার ছবি টেনে নিয়ে ফেলুন"}</div>{isEN ? "Or" : "অথবা"}<p>{isEN ? "Select the image with the button below" : "নীচের বাটনে ক্লিক করে ছবি নির্বাচন করুন"}</p>
              </div>
            </div>
          )}
          <div className="upload-btn">
            <div className="uploadimage">
                <label className='label-c' htmlFor="imgs">{isEN ? "Select Image" : "ছবি নির্বাচন করুন"}</label>
            </div>
            <input id="imgs" type="file" accept="image/*" onChange={handleImageUpload} />
          </div>
        </div>

        <div className={diseaseMessage===defaultDiseaseMessage?'identify-disease-nopad':"identify-disease"}>
          <h1 className='image-upload-title-ex'>{isEN ? "Identified Disease" : "চিহ্নিত রোগ"}</h1>
          <div className='upload-instruct'><strong>{isEN ? "Plant: " : "উদ্ভিদ: "}</strong>{isEN?plant+"  ":plantB+"  "}<strong>{isEN?"Disease: ":"রোগ: "}</strong>{isEN?diseaseMessage:diseaseMessageB}</div>
          <div className='upload-instruct'> <strong>{isEN ? "Cause of Disease" : "রোগের কারণ"}</strong> <br /> {isEN?removeBR(causeDisease):removeBR(causeDiseaseB)} </div>
          <div className='upload-instruct'> <strong>{isEN ? "Prevention of Disease" : "রোগ প্রতিরোধ"}</strong> <br /> {isEN?removeBR(preventionDisease):removeBR(preventionDiseaseB)} </div>
          <Button className="prediction-button" onClick={handlePredictionClick}>
            <Spinner className={showSpinner?"":"no-display"} animation="border" variant="light" role='status' aria-hidden='true' size="sm"/>
            {isEN ? " Identify Disease" : " রোগ চিহ্নিত করুন"}
          </Button>

          <div className={diseaseMessage===defaultDiseaseMessage? "no-display":"feedback-section"}>
            <h2 className='feedback-title'>{isEN ? "Your feedback On Identified Disease" : "চিহ্নিত রোগের উপর আপনার মতামত"}</h2>
            <img
              src={image? URL.createObjectURL(image):""}
              alt="Uploaded Leaf"
              style={{ maxWidth: '15vw', maxHeight: '15vh', border: "2px solid white" }}
            />
            <div><strong>Plant: </strong>{plant+"  "}<strong>Disease: </strong>{diseaseMessage}</div>{/** Title of the feedback */}
            <InputGroup className="mb-3">
              <InputGroup.Text  className="input-group-text-dark">{isEN?"Your Feedback":"আপনার মতামত"}</InputGroup.Text>
              <Form.Control
              type="text"
              placeholder={isEN?"Add your feedback or comment here":"এখানে আপনার মন্তব্য অথবা মন্তব্য যোগ করুন"}
              value={feedbackMessage}
              onChange={(e)=>{setFeedbackMessage(e.target.value); setShowSuccress(false);}}
              />
              <Collapse in={feedbackMessage===""}>
              <div id="example-collapse-text">
                  <Card body className='warn-card custom-card'>
                  {isEN?"Feedback cannot be empty!":"মন্তব্য খালি রাখা যাবে না!"}
                  </Card>
              </div>
              </Collapse>
          </InputGroup>
          <div className={showSuccess?'bg-green':"no-display"}>{isEN ? "Feedback Sent Successfully!" : "মতামত সফলভাবে প্রেরিত হয়েছে!"}</div>
            <Button className="prediction-button" onClick={handleFeedbackClick}><Spinner className={showSpinner?"":"no-display"} animation="border" variant="light" role='status' size="sm"/>{isEN?" Submit Feedback":" মন্তব্য জমা দিন"}</Button>
          </div>
        </div>

      </div>
    </div>
  )
}

export default DiseasePredict
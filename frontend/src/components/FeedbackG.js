import React, { useContext, useState } from 'react'
import { toast } from 'react-toastify';
import Collapse from 'react-bootstrap/Collapse';
import Form from 'react-bootstrap/Form';
import InputGroup from 'react-bootstrap/InputGroup';
import Card from 'react-bootstrap/Card';
import Button from 'react-bootstrap/Button';
import 'bootstrap/dist/css/bootstrap.min.css';
import "./FeedbackG.css"
import { LanguageContext } from '../contexts/LanguageContext';
import { LoginContext } from '../contexts/LoginContext';
import axios from 'axios';

const FeedbackG = ({feedbackTitle, predictionMessage, show}) => {

  const {isEN} = useContext(LanguageContext);
  const {uid, token} = useContext(LoginContext);

  const [feedbackMessage, setFeedbackMessage] = useState("");
  const [showSuccess, setShowSuccress] = useState(false);

  const handleFeedbackClick = (e) => {
    if(feedbackMessage!==""){
      if(token){
        const headers = {
            Authorization: "Bearer "+token
        }
        axios.post("https://agridoctorbackend-production.up.railway.app/api/user/"+uid+"/feedback", {
          "feedBackTitle":"{"+feedbackTitle+predictionMessage+"}",
          "content":feedbackMessage
        }, { headers })
        .then(response => {
            console.log(response.data);
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
    
  return (
    <div className={show? "feedback-section":"no-display"}>
        <h2 className='feedback-title'>{isEN?"Your Feedback on the Results":"ফলাফল সম্পর্কিত আপনার মতামত"}</h2>
        <div><strong>{feedbackTitle}</strong> {predictionMessage}</div>{/** Title of the feedback */}
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
        
        <Button className="prediction-button" onClick={handleFeedbackClick}>{isEN?"Submit Feedback":"মন্তব্য জমা দিন"}</Button>
    </div>
  )
}

export default FeedbackG
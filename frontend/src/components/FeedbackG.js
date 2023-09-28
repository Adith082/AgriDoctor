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

const FeedbackG = ({feedbackTitle, predictionMessage, show}) => {

  const {isEN} = useContext(LanguageContext);

  const [feedbackMessage, setFeedbackMessage] = useState("");

  const handleFeedbackClick = (e) => {
    if(feedbackMessage!=="")
        toast.success("Feedback Sent.");
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
            onChange={(e)=>{setFeedbackMessage(e.target.value)}}
            />
            <Collapse in={feedbackMessage===""}>
            <div id="example-collapse-text">
                <Card body className='warn-card custom-card'>
                {isEN?"Feedback cannot be empty!":"মন্তব্য খালি রাখা যাবে না!"}
                </Card>
            </div>
            </Collapse>
        </InputGroup>
        
        <Button className="prediction-button" onClick={handleFeedbackClick}>{isEN?"Submit Feedback":"মন্তব্য জমা দিন"}</Button>
    </div>
  )
}

export default FeedbackG
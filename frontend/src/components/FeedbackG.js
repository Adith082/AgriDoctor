import React, { useState } from 'react'
import { toast } from 'react-toastify';
import Collapse from 'react-bootstrap/Collapse';
import Form from 'react-bootstrap/Form';
import InputGroup from 'react-bootstrap/InputGroup';
import Card from 'react-bootstrap/Card';
import Button from 'react-bootstrap/Button';
import 'bootstrap/dist/css/bootstrap.min.css';
import "./FeedbackG.css"

const FeedbackG = ({feedbackTitle, predictionMessage, show}) => {

  const [feedbackMessage, setFeedbackMessage] = useState("");

  const handleFeedbackClick = (e) => {
    if(feedbackMessage!=="")
        toast.success("Feedback Sent.");
  }
    
  return (
    <div className={show? "feedback-section":"no-display"}>
        <h2 className='feedback-title'>Your Feedback on the Results</h2>
        <div><strong>{feedbackTitle}</strong> {predictionMessage}</div>{/** Title of the feedback */}
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
  )
}

export default FeedbackG
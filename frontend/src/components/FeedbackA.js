import React from 'react'
import "./FeedbackA.css"
import Button from 'react-bootstrap/Button';

const FeedbackA = ({image, title, feedback, date}) => {
  return (
    <div className='admin-feedback-container'>
      <img className={image?"":"no-display"}
          src={image? URL.createObjectURL(image):""}
          alt="Uploaded Leaf"
          style={{ maxWidth: '10vw', maxHeight: '10vh', border: "2px solid white" , marginTop:"0.5rem", marginBottom:"0.5rem"}}
      />

      <div className='column-flex-green'>
        <div><strong>Title: </strong>{title}</div>
        <div><strong>User's Feedback: </strong>{feedback}</div>
      </div>

      <div className='column-flex'>
        <div className='bg-green'>{date}</div>
        <Button variant="outline-danger">Delete</Button>
      </div>
    </div>
  )
}

export default FeedbackA
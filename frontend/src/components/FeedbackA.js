import React, { useContext, useEffect, useState } from 'react'
import "./FeedbackA.css"
import Button from 'react-bootstrap/Button';
import axios from 'axios';
import { LanguageContext } from '../contexts/LanguageContext';
import { toast } from "react-toastify";
import { LoginContext } from '../contexts/LoginContext';

const FeedbackA = ({imageName, title, feedback, date, id, getAllFeedbacks}) => {

  const {isEN} = useContext(LanguageContext);
  const {token} = useContext(LoginContext);
  const [imgSrc, setImgSrc] = useState(null);

  const handleDelete = () =>{
    const headers = {
      Authorization: "Bearer "+token
    }
    axios.delete("https://agridoctorbackend-production.up.railway.app/api/feedbacks/"+id, { headers })
    .then(response => {
        if(response.data.message==="No currency left. Cannot Provide Service."){
          toast.warn(isEN ? "Not enough balance. Please recharge your wallet balance!" : "যালেন্স পর্যাপ্ত নয়। দয়া করে আপনার ওয়ালেট ব্যালেন্স চার্জ করুন!");
          return;
        }
        console.log(response.data);

        getAllFeedbacks();
        
        toast.error(isEN ? "Successful!" : "সফল!");
    })
    .catch(error => {
        console.error('Error:', error);
        toast.warning(isEN ? "Something went wrong! Try again later." : "কিছু ভুল হয়েছে! পরে আবার চেষ্টা করুন।");
    });
  }

  useEffect(() => {
    const getImage = () => {

      if(imageName==="default.png"){
        setImgSrc(null);
        return;
      }
    
      const headers = {
        Authorization: "Bearer " + token,
        'Content-Type': 'multipart/form-data',
      }
  
      axios.get("https://agridoctorbackend-production.up.railway.app/api/feedback/image/"+imageName, { headers:headers, 
      responseType: 'arraybuffer' })
      .then(response => {
        console.log(response);
        setImgSrc(URL.createObjectURL(new Blob([response.data])))
        return;
      })
      .catch(error => {
        console.error('Error:', error);
        setImgSrc(null);
      });
  
      setImgSrc(null);
  
    }
    getImage();
  }, [imageName, token]);

  return (
    <div className='admin-feedback-container'>
      <img className={imgSrc?"":"no-display"}
          src={imgSrc?imgSrc:null}
          alt="Uploaded Leaf"
          style={{ maxWidth: '10vw', maxHeight: '10vh', border: "2px solid white" , marginTop:"0.5rem", marginBottom:"0.5rem"}}
      />

      <div className='column-flex-green'>
        <div><strong>Title: </strong>{title}</div>
        <div><strong>User's Feedback: </strong>{feedback}</div>
      </div>

      <div className='column-flex'>
        <div className='bg-green'>{date}</div>
        <Button variant="outline-danger" onClick={handleDelete}>Delete</Button>
      </div>
    </div>
  )
}

export default FeedbackA
import React, { useContext, useEffect, useState } from 'react'
import "./AdminHome.css"
import NavbarA from '../components/NavbarA'
import FeedbackA from '../components/FeedbackA.js'
import Button from 'react-bootstrap/Button';
import { LanguageContext } from '../contexts/LanguageContext';
import { LoginContext } from '../contexts/LoginContext';
import { toast } from 'react-toastify';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

function AdminHome() {

  const {isEN} = useContext(LanguageContext);
  const {token, role} = useContext(LoginContext);

  const [allFeedbacks, setAllFeedbacks] = useState(null);
  const [image, setImage]=useState(null);

  const navigate = useNavigate();

  useEffect(() => {
    //checking the existence of token
    const checkToken = () => {
      if(!token){
        navigate("/");
        toast.warn(isEN ? "Login First!" : "প্রথমে লগ-ইন করুন!");
      }
    }

    const checkRole = () => {
      if(role!=="ROLE_ADMIN"){
        navigate("/");
        toast.warn(isEN ? "Unauthorized Access!" : "অননুমোদিত অ্যাক্সেস!");
      }
    }

    const getAllFeedbacks = () =>{
      if(token){

        const headers = {
          Authorization: "Bearer " + token,
        }

        axios.get("https://agridoctorbackend-production.up.railway.app/api/feedbacks", {headers})
        .then(response => {
            console.log(response.data)
            if(response.data.length===0){
              setAllFeedbacks(null);
            }else{
              setAllFeedbacks(response.data)
            }
            // toast.success(isEN ? "Wallet balance recharge successful!" : "ওয়ালেট ব্যালেন্স পুনরারম্ভ সফল!");
        })
        .catch(error => {
            console.error('Error:', error);
            toast.warning(isEN ? "Something went wrong! Try again later." : "কিছু ভুল হয়েছে! পরে আবার চেষ্টা করুন।");
        });
      }
    }

    checkToken();
    checkRole();
    getAllFeedbacks();
  }, [token, isEN, navigate, role]);

  const getImage = (index) => {
    
    const headers = {
      Authorization: "Bearer " + token,
    }

    axios.get("https://agridoctorbackend-production.up.railway.app/api/feedback/image/"+allFeedbacks[index].imageName, { headers })
    .then(response => {
      console.log(response);

      // Extract the file extension
      const fileExtension = allFeedbacks[index].imageName.split('.').pop().toLowerCase();
      
      const base64String = response.data; // Assuming the binary data is provided as a base64 encoded string

      // Convert the base64 string back to binary data
      const binaryData = atob(base64String);

      // Create a Uint8Array from the binary data
      const uint8Array = new Uint8Array(binaryData.length);
      for (let i = 0; i < binaryData.length; i++) {
       uint8Array[i] = binaryData.charCodeAt(i);
      }

      let contentType=""

      if (fileExtension === 'jpg' || fileExtension === 'jpeg') {
        contentType = 'image/jpeg';
      } else if (fileExtension === 'png') {
        contentType = 'image/png';
      } // Add more checks for other supported formats if needed

      console.log(contentType)

      const blob = new Blob([uint8Array.buffer], { type: contentType });
      const imageUrl = URL.createObjectURL(blob);

      // Now you can set the imageUrl in your component's state or directly in the JSX.
      // Assuming imageUrl is stored in state:
      setImage(imageUrl);
      return imageUrl;
    })
    .catch(error => {
      console.error('Error:', error);
      return null;
    });

    return null;

  }

  const getAllFeedbacks = () =>{
    if(token){

      const headers = {
        Authorization: "Bearer " + token,
      }

      axios.get("https://agridoctorbackend-production.up.railway.app/api/feedbacks", {headers})
      .then(response => {
          console.log(response.data)
          if(response.data.length===0){
            setAllFeedbacks(null);
          }else{
            setAllFeedbacks(response.data)
          }
          // toast.success(isEN ? "Wallet balance recharge successful!" : "ওয়ালেট ব্যালেন্স পুনরারম্ভ সফল!");
      })
      .catch(error => {
          console.error('Error:', error);
          toast.warning(isEN ? "Something went wrong! Try again later." : "কিছু ভুল হয়েছে! পরে আবার চেষ্টা করুন।");
      });
    }
  }

  return (
    <div className='admin-home-container'>
        <NavbarA/>
        {allFeedbacks?
        <div className='list-container'>
          <h1 className='image-upload-title-ex'>User Feedbacks</h1>

          {allFeedbacks.map((feedback, index)=> (
          <FeedbackA image={feedback.imageName!=='default.png'?getImage(index):null} title={feedback.feedBackTitle} feedback={feedback.content} date={feedback.addedDate} id={feedback.feedBackId} getAllFeedbacks={getAllFeedbacks}/>
          ))}
          <FeedbackA image={image} title={"this is the tilte section"} feedback={"This is the user's feedback"} date={"08/sd/5454"}/>
          <img src={image} alt="Fetched Image" />

          <div className='row-flex'>
            <div className='page-font'><strong>4</strong>/23</div>
            <Button variant="outline-light">Next</Button>
          </div>

        </div>:<h1 className='image-upload-title-ex'>All User Feedbacks Handled!</h1>}
    </div>
  )
}

export default AdminHome
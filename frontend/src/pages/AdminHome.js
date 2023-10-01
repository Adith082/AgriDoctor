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
import Image from 'react-bootstrap/Image';

function AdminHome() {

  const itemsPerPage = 3; // Display 3 items per page

  const {isEN} = useContext(LanguageContext);
  const {token, role} = useContext(LoginContext);

  const [allFeedbacks, setAllFeedbacks] = useState(null);

  const [startIndex, setStartIndex] = useState(0);
  const [endIndex, setEndIndex] = useState(4);
  const [pageNumber, setPageNumber] = useState(1);

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
  
  function returnEnd(end){
    if(end<=allFeedbacks.length) return end;
    return allFeedbacks.length;
  }

  function setStartEnd(pn){
    const st = (pn - 1) * itemsPerPage;
    setStartIndex(st);
    setEndIndex(returnEnd(st+itemsPerPage));
  }

  const handlePreviousClick = (e) => {
    if(pageNumber<=1){
      setPageNumber(1);
      setStartEnd(1);
    }else{
      const newPageNumber = pageNumber-1;
      setPageNumber(newPageNumber);
      setStartEnd(newPageNumber);
    }
  }

  const handleNextClick = (e) => {
    if(pageNumber>=Math.ceil(allFeedbacks.length/itemsPerPage)){
      setPageNumber(Math.ceil(allFeedbacks.length/itemsPerPage));
      setStartEnd(Math.ceil(allFeedbacks.length/itemsPerPage));
    }else{
      const newPageNumber = pageNumber+1;
      setPageNumber(newPageNumber);
      setStartEnd(newPageNumber);
    }
  }

  return (
    <div className='admin-home-container'>
        <NavbarA/>
        {allFeedbacks?
        <div className='list-container'>
          <h1 className='image-upload-title-ex'>
            <Image
              width={50}
              height={50}
              className="mr-3"
              src="https://cdn-icons-png.flaticon.com/512/7732/7732720.png" //Image Credit: FlatIcon
            />{'  '}
          User Feedbacks</h1>

          {allFeedbacks.slice(startIndex, endIndex).map((feedback, index)=> (
          <FeedbackA key={index} imageName={feedback.imageName} title={feedback.feedBackTitle} feedback={feedback.content} date={feedback.addedDate} id={feedback.feedBackId} getAllFeedbacks={getAllFeedbacks}/>
          ))}

          <div className='row-flex'>
            <Button variant="outline-light" onClick={handlePreviousClick}>Previous</Button>
            <div className='page-font'><strong>{pageNumber}</strong>/{Math.ceil(allFeedbacks.length/itemsPerPage)}</div>
            <Button variant="outline-light" onClick={handleNextClick}>Next</Button>
          </div>

        </div>:<h1 className='image-upload-title-ex-center'>{'   '}All User Feedbacks Handled!</h1>}
    </div>
  )
}

export default AdminHome
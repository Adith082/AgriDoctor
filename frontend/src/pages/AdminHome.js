import React, { useState } from 'react'
import "./AdminHome.css"
import NavbarA from '../components/NavbarA'
import FeedbackA from '../components/FeedbackA.js'
import Button from 'react-bootstrap/Button';

function AdminHome() {

  const [image,setImage] = useState(null);

  const handleImageUpload = (e) => {
    // setDiseaseMessage(defaultDiseaseMessage);
    setImage(e.target.files[0]);
  }


  return (
    <div className='admin-home-container'>
        <NavbarA/>
        <div className='list-container'>
          <h1 className='image-upload-title-ex'>User Feedbacks</h1>
          <FeedbackA image={image} title={"this is the tilte section"} feedback={"This is the user's feedback"} date={"08/sd/5454"}/>
          <FeedbackA image={image} title={"this is the tilte section"} feedback={"This is the user's feedback"} date={"08/sd/5454"}/>

          <div className='row-flex'>
            <Button variant="outline-light" onClick={handleImageUpload}>Previous</Button>
            <div className='page-font'><strong>4</strong>/23</div>
            <Button variant="outline-light">Next</Button>
          </div>

        </div>
    </div>
  )
}

export default AdminHome
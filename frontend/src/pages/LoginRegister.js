import React, { useContext, useState } from 'react'
import NavbarLR from '../components/NavbarLR.js'
import './LoginRegister.css'
import Form from 'react-bootstrap/Form';
import InputGroup from 'react-bootstrap/InputGroup';
import 'bootstrap/dist/css/bootstrap.min.css';
import Card from 'react-bootstrap/Card';
import Collapse from 'react-bootstrap/Collapse';
import Button from 'react-bootstrap/Button';
import { toast } from "react-toastify";
import { LanguageContext } from '../contexts/LanguageContext.js';
import axios from 'axios';
import { LoginContext } from '../contexts/LoginContext.js';
import { useNavigate } from 'react-router-dom';
import Modal from 'react-bootstrap/Modal';
import Image from 'react-bootstrap/Image';

function LoginRegister() {

  const navigate = useNavigate();

  const {isEN} = useContext(LanguageContext);
  const {setToken, setWalletBalance, setUid, setRole} = useContext(LoginContext);

  const [firstName, setFirstName] = useState(""); // State for First Name
  const [lastName, setLastName] = useState(""); // State for Last Name
  const [email, setEmail] = useState("")
  const [password, setPassword] = useState("")
  const [rePassword, setRePassword] = useState("")

  const [firstNameValid, setFirstNameValid] = useState(false);
  const [lastNameValid, setLastNameValid] = useState(false)
  const [emailValid, setEmailValid] = useState(false);
  const [passwordValid, setPasswordValid] = useState(false);
  const [rePasswordValid, setRePasswordValid] = useState(false);

  const [firstNameOpen, setFirstNameOpen] = useState(false)
  const [emailOpen, setEmailOpen] = useState(false)
  const [passOpen, setPassOpen] = useState(false)
  const [rePassOpen, setRePassOpen] = useState(false)

  const [nameWarnMessage, setNameWarnMessage] = useState("")
  const [emailWarnMessage, setEmailWarnMessage] = useState("")
  const [passWarnMessage, setPassWarnMessage] = useState("")
  const [rePassWarnMessage, setRePassWarnMessage] = useState("")

  const [showSignup, setShowSignup] = useState(false)
  const [adminShow, setAdminShow] = useState(false);

  const [showModal, setShowModal] = useState(false);
  const [code, setCode] = useState("");
  const [enterdCode, setEnteredCode] = useState("");

  const handleFirstNameChange = (e) => {
    const value = e.target.value;
    setFirstName(value);

    // Perform validation here
    if (value.length < 3) {
      setFirstNameOpen(true);
      setFirstNameValid(false);
      setNameWarnMessage(isEN?"First Name must be at least 3 characters long.":"নামের প্রথম অংশ অন্তত ৩ টি অক্ষর হতে হবে।");
    } else {
      setFirstNameOpen(false);
      setFirstNameValid(true);
      setNameWarnMessage("");
    }
  }

  const handleLastNameChange = (e) => {
    const value = e.target.value;
    setLastName(value);

    // Perform validation here
    if (value.length < 3) {
      setFirstNameOpen(true);
      setLastNameValid(false)
      setNameWarnMessage(isEN?"Last Name must be at least 3 characters long.":"নামের শেষ অংশ অন্তত ৩ টি অক্ষর হতে হবে।");
    } else {
      setFirstNameOpen(false);
      setLastNameValid(true)
      setNameWarnMessage("");
    }
  }

  const handleEmailChange = (e) => {
    const value = e.target.value;
    setEmail(value);

    // Perform email validation here
    const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailPattern.test(value)) {
      setEmailOpen(true);
      setEmailValid(false)
      setEmailWarnMessage(isEN?"Invalid email address.":"অবৈধ ইমেইল ঠিকানা।");
    } else {
      setEmailOpen(false);
      setEmailValid(true)
      setEmailWarnMessage("");
    }
  }

  const handlePasswordChange = (e) => {
    const value = e.target.value;
    setPassword(value);

    // Perform password validation here
    if (value.length < 5 && (value!==rePassword)) {
      setPassOpen(true);
      setPasswordValid(false)
      setPassWarnMessage(isEN?"Password must be at least 5 characters long and be same as retyped password":"পাসওয়ার্ডটি অন্তত ৫ টি অক্ষরের হতে হবে এবং পুনরায় লিখিত পাসওয়ার্ডের সাথে মিলতে হবে");
    }else if (value.length < 5) {
      setRePassOpen(true);
      setRePasswordValid(false)
      setRePassWarnMessage(isEN?"Password must be at least 5 characters long and be same as retyped password":"পাসওয়ার্ডটি অন্তত ৫ টি অক্ষরের হতে হবে এবং পুনরায় লিখিত পাসওয়ার্ডের সাথে মিলতে হবে");
      setPassOpen(false);
      setPasswordValid(true)
      setPassWarnMessage("");
    }
    else {
      setPassOpen(false);
      setPasswordValid(true)
      setPassWarnMessage("");
      setRePassOpen(false);
      setRePasswordValid(true);
      setRePassWarnMessage("");
    }
  }

  const handleRePasswordChange = (e) => {
    const value = e.target.value;
    setRePassword(value);

    // Perform retype password validation here
    if (value !== password) {
      setRePassOpen(true);
      setRePasswordValid(false);
      setRePassWarnMessage(isEN?"Password and retyped passwords do not match.":"পাসওয়ার্ড এবং পুনরায় লিখিত পাসওয়ার্ড মিলে না।");
    } else {
      setRePassOpen(false);
      setRePasswordValid(true);
      setRePassWarnMessage("");
      setPassOpen(false);
      setPasswordValid(true)
      setPassWarnMessage("");
    }
  }

  const handleRegistrationClick = () => {

    if(firstNameValid && lastNameValid && emailValid && passwordValid && rePasswordValid){
      const headers = {};

      axios.post("https://agridoctorbackend-production.up.railway.app/api/verifyemail", {email:email}, { headers })
      .then(response => {
        setCode(response.data.verificationCode);
        setShowModal(true);
      })
      .catch(error => {
        console.error('Error:', error);
        toast.warning(isEN ? "Something went wrong! Try again later." : "কিছু ভুল হয়েছে! পরে আবার চেষ্টা করুন।");
      });
    }else{
      toast.warn(isEN?"Some of the iputs are invalid!":"কিছু ইনপুট অবৈধ!");
    }
  }

  const performRegistration = () => {
    if(enterdCode===code){
      // const headers = {};

      const username = firstName+" "+lastName;

      axios.post("https://agridoctorbackend-production.up.railway.app/api/auth/register", {
        "name": username,
        "email": email,
        "password": password
      })
      .then(response => {
        setShowModal(false);
        toast.success(isEN?"Registration Success!":"নিবন্ধন সফল!");
        navigate("/");
      })
      .catch(error => {
        console.error('Error:', error);
        toast.warning(isEN ? "Wrong e-mail or password" : "ভুল ইমেইল অথবা পাসওয়ার্ড")
      });
    }else{
      toast.warn(isEN?"Entered wrong code!":"আপনি ভুল কোড দিয়েছেন!");
    }
  }

  const handleLoginClick = () => {

    if(emailValid && passwordValid){

      const headers = {};

      axios.post("https://agridoctorbackend-production.up.railway.app/api/auth/login", {username:email, password:password}, { headers })
      .then(response => {
        if(!response.data.token){
          toast.warn(isEN ? "Wrong e-mail or password" : "ভুল ইমেইল অথবা পাসওয়ার্ড");
          return;
        }
        setToken(response.data.token);
        setWalletBalance(response.data.user.wallet);
        setUid(response.data.user.id);
        navigate("/home");
        toast.success(isEN ? "Login Success!" : "লগইন সফল!");
      })
      .catch(error => {
        console.error('Error:', error);
        toast.warning(isEN ? "Wrong e-mail or password" : "ভুল ইমেইল অথবা পাসওয়ার্ড")
      });

    }else{
      toast.warn(isEN ? "Incorrect format of E-mail/password!" : "ইমেইল/পাসওয়ার্ডের সঠিক ফরম্যাট নয়!")
    }
  }

  const handleAdminLoginClick = () => {
    if(emailValid && passwordValid){

      const headers = {};

      axios.post("https://agridoctorbackend-production.up.railway.app/api/auth/login", {username:email, password:password}, { headers })
      .then(response => {
        if(!response.data.token){
          toast.warn(isEN ? "Wrong e-mail or password" : "ভুল ইমেইল অথবা পাসওয়ার্ড");
          return;
        }
        setToken(response.data.token);
        setWalletBalance(response.data.user.wallet);
        setUid(response.data.user.id);
        setRole(response.data.user.roles[0].name);
        navigate("/admin-home");
        toast.success(isEN ? "Login Success!" : "লগইন সফল!");
      })
      .catch(error => {
        console.error('Error:', error);
        toast.warning(isEN ? "Wrong e-mail or password" : "ভুল ইমেইল অথবা পাসওয়ার্ড")
      });

    }else{
      toast.warn(isEN ? "Incorrect format of E-mail/password!" : "ইমেইল/পাসওয়ার্ডের সঠিক ফরম্যাট নয়!")
    }
  }

  const resetForm = () => {
    setFirstName("");
    setLastName("");
    setEmail("");
    setPassword("");
    setRePassword("");
    
    setFirstNameValid(false);
    setLastNameValid(false);
    setEmailValid(false);
    setPasswordValid(false);
    setRePasswordValid(false);
    
    setFirstNameOpen(false);
    setEmailOpen(false);
    setPassOpen(false);
    setRePassOpen(false);
    
    setNameWarnMessage("");
    setEmailWarnMessage("");
    setPassWarnMessage("");
    setRePassWarnMessage("");
  }
  

  return (
    <div className='main-container'>
      <NavbarLR setShowSignup={setShowSignup} resetForm={resetForm} setAdminShow={setAdminShow}/>
      <div className={`signup-section ${showSignup ? '' : 'hidden'}`}>
        <h2 className="text-center mb-4 heading">
        <Image
          width={50}
          height={50}
          className="mr-3"
          src="https://cdn-icons-png.flaticon.com/128/10969/10969109.png" //Image Credit: FlatIcon
        />  
        {isEN?"Sign up":"সাইন আপ"}</h2>
        <InputGroup className="mb-3">
          <InputGroup.Text className="input-group-text-dark">{isEN?"Name":"নাম"}</InputGroup.Text>
          <Form.Control
            type="text"
            placeholder={isEN?"First Name":"নামের প্রথম অংশ"}
            value={firstName}
            onChange={handleFirstNameChange}
          />
          <Form.Control
            type="text"
            placeholder={isEN?"Last Name":"নামের শেষ অংশ"}
            value={lastName}
            onChange={handleLastNameChange}
          />
          <Collapse in={firstNameOpen}>
            <div id="example-collapse-text">
              <Card body className='warn-card custom-card'>
                {nameWarnMessage}
              </Card>
            </div>
          </Collapse>
        </InputGroup>
        <InputGroup className="mb-3">
          <InputGroup.Text  className="input-group-text-dark">{isEN?"E-mail":"ই-মেইল"}</InputGroup.Text>
          <Form.Control
            type="E-mail"
            placeholder={isEN?"E-mail address":"ই-মেইল ঠিকানা"}
            value={email}
            onChange={handleEmailChange}
          />
          <Collapse in={emailOpen}>
            <div id="example-collapse-text">
              <Card body className='warn-card custom-card'>
                {emailWarnMessage}
              </Card>
            </div>
          </Collapse>
        </InputGroup>
        <InputGroup className="mb-3">
          <InputGroup.Text  className="input-group-text-dark">{isEN?"Password":"পাসওয়ার্ড"}</InputGroup.Text>
          <Form.Control
            type="password"
            placeholder={isEN?"Enter password":"পাসওয়ার্ড লিখুন"}
            value={password}
            onChange={handlePasswordChange}
          />
          <Collapse in={passOpen}>
            <div id="example-collapse-text">
              <Card body className='warn-card custom-card'>
                {passWarnMessage}
              </Card>
            </div>
          </Collapse>
        </InputGroup>
        <InputGroup className="mb-3">
          <InputGroup.Text  className="input-group-text-dark">{isEN?"Confirm Password":"পাসওয়ার্ড নিশ্চিত করুন"}</InputGroup.Text>
          <Form.Control
            type="password"
            placeholder={isEN?"Retype password":"পাসওয়ার্ড পুনরায় লিখুন"}
            value={rePassword}
            onChange={handleRePasswordChange}
          />
          <Collapse in={rePassOpen}>
            <div id="example-collapse-text">
              <Card body className='warn-card custom-card'>
                {rePassWarnMessage}
              </Card>
            </div>
          </Collapse>
        </InputGroup>
        <Button className="custom-button" onClick={handleRegistrationClick}>{isEN?"Confirm Registration":"নিবন্ধন নিশ্চিত করুন"}</Button>
        <span className="login-text">{isEN ? "Already registered? " : "ইত:পূর্বে নিবন্ধিত? "}<span className="highlighted-link" onClick={() => setShowSignup(false)}>
          {isEN ? "Login now here!" : "এখন লগ-ইন করুন!"}
        </span></span>
      </div>
      <div className={`login-section ${showSignup || (adminShow) ? 'hidden' : ''}`}>
        <h2 className="text-center mb-4 heading">
        <Image
          width={50}
          height={50}
          className="mr-3"
          src="https://cdn-icons-png.flaticon.com/512/5582/5582872.png" //Image Credit: FlatIcon
        />
        {isEN ? "Login" : "লগ-ইন"}</h2>
        <InputGroup className="mb-3">
          <InputGroup.Text  className="input-group-text-dark">{isEN ? "E-mail" : "ই-মেইল"}</InputGroup.Text>
          <Form.Control
            type="E-mail"
            placeholder={isEN ? "e-mail address" : "ই-মেইল ঠিকানা"}
            value={email}
            onChange={handleEmailChange}
          />
          <Collapse in={emailOpen}>
            <div id="example-collapse-text">
              <Card body className='warn-card custom-card'>
                {emailWarnMessage}
              </Card>
            </div>
          </Collapse>
        </InputGroup>
        <InputGroup className="mb-3">
          <InputGroup.Text  className="input-group-text-dark">{isEN?"Password":"পাসওয়ার্ড"}</InputGroup.Text>
          <Form.Control
            type="password"
            placeholder={isEN?"Enter password":"পাসওয়ার্ড লিখুন"}
            value={password}
            onChange={handlePasswordChange}
          />
          <Collapse in={passOpen}>
            <div id="example-collapse-text">
              <Card body className='warn-card custom-card'>
                {passWarnMessage}
              </Card>
            </div>
          </Collapse>
        </InputGroup>
        <Button className="custom-button" onClick={handleLoginClick}>{isEN ? "Login" : "লগ-ইন"}</Button>
        <span className="login-text">
          {isEN
          ? "Not yet registered? "
          : "এখনো নিবন্ধিত নন? "}
            <span className="highlighted-link" onClick={() => setShowSignup(true)}>{isEN ? "Register now here!" : "এখানে নিবন্ধন করুন!"}</span>
        </span>
      </div>
      <div className={`login-section ${adminShow ? '' : 'hidden'}`}>
        <h2 className="text-center mb-4 heading">
          <Image
            width={50}
            height={50}
            className="mr-3"
            src="https://cdn-icons-png.flaticon.com/512/5516/5516903.png" //Image Credit: FlatIcon
          />
          {isEN ? "Admin Login" : "অ্যাডমিন লগইন"}
        </h2>
        <InputGroup className="mb-3">
          <InputGroup.Text  className="input-group-text-dark">{isEN ? "E-mail" : "ই-মেইল"}</InputGroup.Text>
          <Form.Control
            type="E-mail"
            placeholder={isEN ? "e-mail address" : "ই-মেইল ঠিকানা"}
            value={email}
            onChange={handleEmailChange}
          />
          <Collapse in={emailOpen}>
            <div id="example-collapse-text">
              <Card body className='warn-card custom-card'>
                {emailWarnMessage}
              </Card>
            </div>
          </Collapse>
        </InputGroup>
        <InputGroup className="mb-3">
          <InputGroup.Text  className="input-group-text-dark">{isEN?"Password":"পাসওয়ার্ড"}</InputGroup.Text>
          <Form.Control
            type="password"
            placeholder={isEN?"Enter password":"পাসওয়ার্ড লিখুন"}
            value={password}
            onChange={handlePasswordChange}
          />
          <Collapse in={passOpen}>
            <div id="example-collapse-text">
              <Card body className='warn-card custom-card'>
                {passWarnMessage}
              </Card>
            </div>
          </Collapse>
        </InputGroup>
        <Button className="custom-button" onClick={handleAdminLoginClick}>Login</Button>
        <span className="login-text">{isEN ? "Cannot access even after being Admin? " : "অ্যাডমিন হলেও অ্যাক্সেস করতে পারছেননি? "}<span className="highlighted-link" onClick={() => {}}>{isEN ? "Contact the System Admins!" : "সিস্টেম অ্যাডমিনদের সাথে যোগাযোগ করুন!"}</span></span>
      </div>

      
      <Modal show={showModal} onHide={(e)=>{setShowModal(false)}} centered>
        <Modal.Header closeButton>
            <Image
                width={64}
                height={64}
                className="mr-3"
                src="https://cdn-icons-png.flaticon.com/512/5679/5679639.png" //Image Credit: FlatIcon
            />
            <Modal.Title>&nbsp;&nbsp;{isEN?"Enter the verification code sent to    your e-mail below":"নীচে আপনার ই-মেইলে পাঠানো    যাচাইকরণ কোডটি লিখুন"}</Modal.Title>
        </Modal.Header>
        <Modal.Body>
            <InputGroup className="mb-3">
                <InputGroup.Text  className="input-group-text-dark">{isEN?"Verification Code":"যাচাইকরণ কোড"}</InputGroup.Text>
                <Form.Control
                type="text"
                placeholder={isEN?"E-mail Verification Code":"ই-মেইল যাচাইকরণ কোড"}
                value={enterdCode}
                onChange={(e)=>{setEnteredCode(e.target.value)}}
                />
            </InputGroup>
        </Modal.Body>
        <Modal.Footer>
            <Button variant="outline-success" onClick={performRegistration}>
                {isEN?"Verify E-mail":"ইমেল যাচাই করুন"}
            </Button>
        </Modal.Footer>
    </Modal>



    </div>
  )
}

export default LoginRegister
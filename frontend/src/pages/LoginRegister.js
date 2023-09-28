import React, { useState } from 'react'
import NavbarLR from '../components/NavbarLR.js'
import './LoginRegister.css'
import Form from 'react-bootstrap/Form';
import InputGroup from 'react-bootstrap/InputGroup';
import 'bootstrap/dist/css/bootstrap.min.css';
import Card from 'react-bootstrap/Card';
import Collapse from 'react-bootstrap/Collapse';
import Button from 'react-bootstrap/Button';
import { toast } from "react-toastify";

function LoginRegister() {

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
  const [adminShow, setAdminShow] = useState(false)

  const handleFirstNameChange = (e) => {
    const value = e.target.value;
    setFirstName(value);

    // Perform validation here
    if (value.length < 3) {
      setFirstNameOpen(true);
      setFirstNameValid(true);
      setNameWarnMessage("First Name must be at least 3 characters long.");
    } else {
      setFirstNameOpen(false);
      setLastNameValid(false);
      setNameWarnMessage("");
    }
  }

  const handleLastNameChange = (e) => {
    const value = e.target.value;
    setLastName(value);

    // Perform validation here
    if (value.length < 3) {
      setFirstNameOpen(true);
      setLastNameValid(true)
      setNameWarnMessage("Last Name must be at least 3 characters long.");
    } else {
      setFirstNameOpen(false);
      setLastNameValid(false)
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
      setEmailValid(true)
      setEmailWarnMessage("Invalid email address.");
    } else {
      setEmailOpen(false);
      setEmailValid(false)
      setEmailWarnMessage("");
    }
  }

  const handlePasswordChange = (e) => {
    const value = e.target.value;
    setPassword(value);

    // Perform password validation here
    if (value.length < 8 && (value!==rePassword)) {
      setPassOpen(true);
      setPasswordValid(true)
      setPassWarnMessage("Password must be at least 8 characters long and be same as retyped password");
    } else {
      setPassOpen(false);
      setPasswordValid(false)
      setPassWarnMessage("");
    }
  }

  const handleRePasswordChange = (e) => {
    const value = e.target.value;
    setRePassword(value);

    // Perform retype password validation here
    if (value !== password) {
      setRePassOpen(true);
      setRePasswordValid(true);
      setRePassWarnMessage("Passwords do not match.");
    } else {
      setRePassOpen(false);
      setRePasswordValid(false);
      setRePassWarnMessage("");
    }
  }

  const handleRegistrationClick = () => {
    if(firstNameValid && lastNameValid && emailValid && passwordValid && rePasswordValid){
      toast.success("Ho Ho Ho! Register Success!")
    }else{
      toast.warn("RIP!")
    }
  }

  const handleLoginClick = () => {
    if(emailValid && passwordValid){
      toast.success("Ho Ho Ho! Login Success!")
    }else{
      toast.warn("RIP!")
    }
  }

  const handleAdminLoginClick = () => {
    if(emailValid && passwordValid){
      toast.success("Ho Ho Ho! Login Success!")
    }else{
      toast.warn("RIP!")
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
        <h2 className="text-center mb-4 heading">Sign Up</h2>
        <InputGroup className="mb-3">
          <InputGroup.Text className="input-group-text-dark">Name</InputGroup.Text>
          <Form.Control
            type="text"
            placeholder="First Name"
            value={firstName}
            onChange={handleFirstNameChange}
          />
          <Form.Control
            type="text"
            placeholder="Last Name"
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
          <InputGroup.Text  className="input-group-text-dark">E-mail</InputGroup.Text>
          <Form.Control
            type="E-mail"
            placeholder="e-mail address"
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
          <InputGroup.Text  className="input-group-text-dark">Password</InputGroup.Text>
          <Form.Control
            type="password"
            placeholder="enter password"
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
          <InputGroup.Text  className="input-group-text-dark">Confirm Password</InputGroup.Text>
          <Form.Control
            type="password"
            placeholder="retype password"
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
        <Button className="custom-button" onClick={handleRegistrationClick}>Confirm Registration</Button>
        <span className="login-text">Already registered? <span className="highlighted-link" onClick={() => setShowSignup(true)}>Login now here!</span></span>
      </div>
      <div className={`login-section ${showSignup || (adminShow) ? 'hidden' : ''}`}>
        <h2 className="text-center mb-4 heading">Login</h2>
        <InputGroup className="mb-3">
          <InputGroup.Text  className="input-group-text-dark">email</InputGroup.Text>
          <Form.Control
            type="E-mail"
            placeholder="e-mail address"
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
          <InputGroup.Text  className="input-group-text-dark">Password</InputGroup.Text>
          <Form.Control
            type="password"
            placeholder="enter password"
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
        <Button className="custom-button" onClick={handleLoginClick}>Login</Button>
        <span className="login-text">Not yet registered? <span className="highlighted-link" onClick={() => setShowSignup(true)}>Register now here!</span></span>
      </div>
      <div className={`login-section ${adminShow ? '' : 'hidden'}`}>
        <h2 className="text-center mb-4 heading">Admin Login</h2>
        <InputGroup className="mb-3">
          <InputGroup.Text  className="input-group-text-dark">email</InputGroup.Text>
          <Form.Control
            type="E-mail"
            placeholder="e-mail address"
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
          <InputGroup.Text  className="input-group-text-dark">Password</InputGroup.Text>
          <Form.Control
            type="password"
            placeholder="enter password"
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
        <span className="login-text">Cannot access even after being Admin? <span className="highlighted-link" onClick={() => {}}>Contact the System Admins!</span></span>
      </div>
    </div>
  )
}

export default LoginRegister
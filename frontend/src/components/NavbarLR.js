import React, { useContext } from 'react'
import Container from 'react-bootstrap/Container';
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';
import Button from 'react-bootstrap/Button';
import 'bootstrap/dist/css/bootstrap.min.css';
import { LanguageContext } from '../contexts/LanguageContext';
import LanguageSelector from './LanguageSelector';
import Image from 'react-bootstrap/Image';

const NavbarLR =  ({setShowSignup, setAdminShow}) => {

  const {isEN} = useContext(LanguageContext);

  return (
    <div className='p-3'>
      <Navbar collapseOnSelect expand="lg" className="bg-dark navbar-dark rounded">
        <Container fluid style={{paddingLeft: '4vw', paddingRight: '4vw'}}>
          <Navbar.Brand href="/">
            <img
              src={require('../images/agridoctor-logo1.png')}//credit flaticon
              width="35"
              height="35"
              className="d-inline-block align-top"
              alt="React Bootstrap logo"
              />{'  '}
              <img
              src={require('../images/agridoctor-wordart.png')}//credit flaticon
              width="200"
              height="35"
              className="d-inline-block align-top"
              alt="React Bootstrap logo"
              />
          </Navbar.Brand>
          <Navbar.Toggle aria-controls="responsive-navbar-nav" />
          <Navbar.Collapse id="responsive-navbar-nav">
            <Nav className="me-auto">
            </Nav>
            <Nav>
              <Nav.Link onClick={(e)=> {setAdminShow(true);setShowSignup(false);}}>
                <Image
                  width={25}
                  height={25}
                  className="mr-3"
                  src="https://cdn-icons-png.flaticon.com/512/5516/5516903.png " //Image Credit: FlatIcon
                />  
                {isEN?" Admin Login":" অ্যাডমিন লগ-ইন"}
              </Nav.Link>
            </Nav>
            "  "
            <Nav>
              <Button variant="outline-success" onClick={(e)=>{setShowSignup(false); setAdminShow(false);}}>
                <Image
                width={25}
                height={25}
                className="mr-3"
                src="https://cdn-icons-png.flaticon.com/512/5582/5582872.png" //Image Credit: FlatIcon
                />
              {isEN?"Login":"লগ-ইন"}</Button>
              "  "
              <Button variant="outline-success" onClick={(e)=>{setShowSignup(true); setAdminShow(false);}}>
              <Image
                width={25}
                height={25}
                className="mr-3"
                src="https://cdn-icons-png.flaticon.com/128/10969/10969109.png" //Image Credit: FlatIcon
              />  
              {isEN?" Sign up":" সাইন আপ"}</Button>
            </Nav>
            "  "
            <Nav>
              <LanguageSelector/>
            </Nav>
          </Navbar.Collapse>
        </Container>
      </Navbar>
    </div>
  )
}

export default NavbarLR
import React from 'react'
import Container from 'react-bootstrap/Container';
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';
import Button from 'react-bootstrap/Button';
import 'bootstrap/dist/css/bootstrap.min.css';

const NavbarLR =  ({setShowSignup, setAdminShow}) => {

  return (
    <div className='p-3'>
      <Navbar collapseOnSelect expand="lg" className="bg-dark navbar-dark rounded">
        <Container>
          <Navbar.Brand href="/">
              <img
                src="https://cdn-icons-png.flaticon.com/512/188/188333.png"//credit: "https://www.flaticon.com/free-icons/leaf" Leaf icons created by Roundicons - Flaticon
                width="30"
                height="30"
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
                Admin Login
              </Nav.Link>
            </Nav>
            "  "
            <Nav>
              <Button variant="outline-success" onClick={(e)=>{setShowSignup(false); setAdminShow(false);}}>Login</Button>
              "  "
              <Button variant="outline-success" onClick={(e)=>{setShowSignup(true); setAdminShow(false);}}>Sign up</Button>
            </Nav>
          </Navbar.Collapse>
        </Container>
      </Navbar>
    </div>
  )
}

export default NavbarLR
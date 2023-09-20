import React from 'react'
import Container from 'react-bootstrap/Container';
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';
import Button from 'react-bootstrap/Button';
import 'bootstrap/dist/css/bootstrap.min.css';

function Navbar() {
    const handleLogout = () => {
    // This is where you put your custom logout logic
    alert("Logout function executed!");
    };

    const handleDankMemesClick = () => {
    // This is where you put your custom logic for Dank memes
    alert("Dank memes function executed!");
    };

    return (
        <div className='p-3'>
            <Navbar collapseOnSelect expand="lg" className="bg-dark navbar-dark rounded">
            <Container>
                <Navbar.Brand href="/">
                    <img
                    src="https://cdn-icons-png.flaticon.com/512/188/188333.png"
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
                    <Nav.Link href="#deets">More deets</Nav.Link>
                    <Nav.Link eventKey={2} onClick={handleDankMemesClick}>
                    Dank memes
                    </Nav.Link>
                </Nav>
                <Nav>" "</Nav>
                <Nav>
                    <Button variant="outline-success" onClick={handleLogout}>Logout</Button>
                </Nav>
                </Navbar.Collapse>
            </Container>
            </Navbar>
        </div>
    )
}

export default Navbar
import React from 'react'
import "./NavbarG.css"
import Container from 'react-bootstrap/Container';
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';
import Button from 'react-bootstrap/Button';
import 'bootstrap/dist/css/bootstrap.min.css';
import { toast } from "react-toastify";
import { useNavigate } from 'react-router-dom';

const NavbarA = () => {

    const currentPage=0;

    const navigate = useNavigate();

    const handleLogout = () => {
        // This is where you put your custom logout logic
        navigate('/admin-home');
        toast.warn("Successfully Logged Out!");
    };

    const handleCropPredClick = () => {
        navigate("/crop-predict");
        // toast.success("Successfully logged out!")
    };

    const handleFertilizerPredClick = () => {
        navigate("/fertilizer-predict");
        // toast.success("Successfully logged out!")
    };

    const handleDiseasePredClick = () => {
        navigate("/disease-predict");
        // toast.success("Successfully logged out!")
    };

    return (
        <div className='p-3'>
            <Navbar collapseOnSelect expand="lg" className="bg-dark navbar-dark rounded">
            <Container>
                <Navbar.Brand href="/home">
                    <img
                    src="https://cdn-icons-png.flaticon.com/512/188/188333.png"//credit flaticon
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
                    <Nav.Link href='/home' className={currentPage === 0 ? 'bold' : ''}>
                        Home
                    </Nav.Link>
                    <Nav.Link onClick={handleCropPredClick} className={currentPage === 1 ? 'bold' : ''}>
                        Crop Recommendation
                    </Nav.Link>
                    <Nav.Link onClick={handleFertilizerPredClick} className={currentPage === 2 ? 'bold' : ''}>
                        Fertilizer Recommendation
                    </Nav.Link>
                    <Nav.Link onClick={handleDiseasePredClick} className={currentPage === 3 ? 'bold' : ''}>
                        Disease Prediction
                    </Nav.Link>
                </Nav>
                "  "
                <Nav>
                    <Button variant="outline-success" onClick={handleLogout}>Logout</Button>
                </Nav>
                </Navbar.Collapse>
            </Container>
            </Navbar>
        </div>
    )
}

export default NavbarA
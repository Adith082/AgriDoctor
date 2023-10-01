import React, { useContext } from 'react'
import "./NavbarG.css"
import Container from 'react-bootstrap/Container';
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';
import Button from 'react-bootstrap/Button';
import 'bootstrap/dist/css/bootstrap.min.css';
import { toast } from "react-toastify";
import { useNavigate } from 'react-router-dom';
import LanguageSelector from './LanguageSelector';
import { LanguageContext } from '../contexts/LanguageContext';
import { LoginContext } from '../contexts/LoginContext';
import StandardImg from './StandardImg';
import Image from 'react-bootstrap/Image';

const NavbarA = () => {

    const currentPage=0;

    const navigate = useNavigate();

    const {isEN} = useContext(LanguageContext);
    const {setToken} = useContext(LoginContext);

    const handleLogout = () => {
        //custom logout logic
        navigate('/');
        setToken(null);
        toast.warn(isEN ? "Successfully Logged Out!" : "সফলভাবে লগ আউট হয়েছে!");
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
            <Container fluid style={{paddingLeft: '4vw', paddingRight: '4vw'}}>
                <Navbar.Brand href="/home">
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
                    <Nav.Link href='/home' className={currentPage === 0 ? 'bold' : ''}>
                        <StandardImg url={"https://cdn-icons-png.flaticon.com/512/9492/9492029.png"}/>
                        {isEN?" Home":" হোম"}
                    </Nav.Link>
                    <Nav.Link onClick={handleCropPredClick} className={currentPage === 1 ? 'bold' : ''}>
                        <StandardImg url={"https://cdn-icons-png.flaticon.com/512/1574/1574250.png"}/>
                        {isEN?" Crop Recommendation":" ফসল সুপারিশ"}
                    </Nav.Link>
                    <Nav.Link onClick={handleFertilizerPredClick} className={currentPage === 2 ? 'bold' : ''}>
                        <StandardImg url={"https://cdn-icons-png.flaticon.com/512/6049/6049858.png"}/>
                        {isEN?" Fertilizer Recommendation":" সার/উর্বরক সুপারিশ"}
                    </Nav.Link>
                    <Nav.Link onClick={handleDiseasePredClick} className={currentPage === 3 ? 'bold' : ''}>
                        <StandardImg url={"https://cdn-icons-png.flaticon.com/512/3587/3587375.png"}/>
                        {isEN?" Disease Identification":" ফসল রোগ চিহ্নিতকরণ"}
                    </Nav.Link>
                </Nav>
                "  "
                <Nav>
                    <Button variant="outline-success" onClick={handleLogout}><Image
                        width={25}
                        height={25}
                        className="mr-3"
                        src="https://cdn-icons-png.flaticon.com/512/5243/5243281.png" //Image Credit: FlatIcon
                       />{isEN?"Logout":"লগ-আউট"}
                    </Button>
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

export default NavbarA
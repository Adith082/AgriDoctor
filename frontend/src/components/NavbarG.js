import React from 'react'
import Container from 'react-bootstrap/Container';
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';
import Button from 'react-bootstrap/Button';
import 'bootstrap/dist/css/bootstrap.min.css';
import { toast } from "react-toastify";
import "./NavbarG.css"
import { useNavigate } from 'react-router-dom';

const NavbarG = ({currentPage, walletBalance}) => {

    const navigate = useNavigate();

    const handleLogout = () => {
        // This is where you put your custom logout logic
        navigate('/');
        alert("Logout function executed!");
    };

    const handleDankMemesClick = () => {
    // This is where you put your custom logic for Dank memes
        toast.success("Successfully logged out!")
    };

    const handleAddWallet = () => {
        toast.success('Wallet Balance Add')
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
                    <Nav.Link onClick={handleDankMemesClick} className={currentPage === 1 ? 'bold' : ''}>
                        Crop Recommendation
                    </Nav.Link>
                    <Nav.Link onClick={handleDankMemesClick} className={currentPage === 2 ? 'bold' : ''}>
                        Fertilizer Recommendation
                    </Nav.Link>
                    <Nav.Link onClick={handleDankMemesClick} className={currentPage === 3 ? 'bold' : ''}>
                        Disease Prediction
                    </Nav.Link>
                    <Navbar.Brand onClick={handleAddWallet}>
                        <img
                        src="https://cdn-icons-png.flaticon.com/512/4315/4315609.png"//credit flaticon
                        width="30"
                        height="30"
                        className="d-inline-block align-top"
                        alt="React Bootstrap logo"
                        />
                    </Navbar.Brand>
                    <Navbar.Brand>
                        <img
                        src="https://cdn-icons-png.flaticon.com/512/9548/9548542.png"//credit flaticon
                        width="30"
                        height="30"
                        className="d-inline-block align-top"
                        alt="React Bootstrap logo"
                        />
                    </Navbar.Brand>
                    <Nav.Link>
                        Tk. {walletBalance}
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

export default NavbarG
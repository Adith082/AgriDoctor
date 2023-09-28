import React, { useContext, useState } from 'react'
import Container from 'react-bootstrap/Container';
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';
import Button from 'react-bootstrap/Button';
import 'bootstrap/dist/css/bootstrap.min.css';
import { toast } from "react-toastify";
import "./NavbarG.css"
import { useNavigate } from 'react-router-dom';
import Modal from 'react-bootstrap/Modal';
import Collapse from 'react-bootstrap/Collapse';
import Form from 'react-bootstrap/Form';
import InputGroup from 'react-bootstrap/InputGroup';
import Card from 'react-bootstrap/Card';
import Image from 'react-bootstrap/Image';
import LanguageSelector from './LanguageSelector';
import { LanguageContext } from '../contexts/LanguageContext';

const NavbarG = ({currentPage, walletBalance}) => {

    const navigate = useNavigate();

    const {isEN} = useContext(LanguageContext)

    const [showModal, setShowModal] = useState(false);
    const [accountNo, setAccountNo] = useState("");
    const [cvc, setCvc] = useState("");
    const [date, setDate] = useState("");
    const [amount, setAmount] = useState(0);

    const [accountNoValid, setAccountNoValid] = useState(true);
    const [cvcValid, setCvcValid] = useState(true);
    const [dateValid, setDateValid] = useState(true);
    const [amountValid, setAmountValid] = useState(true);

    const [accountNoWarn, setAccountNoWarn] = useState("Field cannot be empty!");
    const [cvcWarn, setCvcWarn] = useState("Field cannot be empty!");
    const [dateWarn, setDateWarn] = useState("Field cannot be empty!");
    const [amountWarn, setAmountWarn] = useState("Field cannot be empty!");

    const handleLogout = () => {
        // This is where you put your custom logout logic
        navigate('/');
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

    const handleAddWallet = () => {
        setShowModal(!showModal);
        setAccountNoValid(true);
        setCvcValid(true);
        setDateValid(true);
        setAmountValid(true);
        setAccountNoWarn("Field cannot be empty!");
        setCvcWarn("Field cannot be empty!");
        setDateWarn("Field cannot be empty!");
        setAmountWarn("Field cannot be empty!");
    };

    const handleAddBalance = (e) => {

        if (accountNo === "") {
            setAccountNoValid(false);
            setAccountNoWarn("Field cannot be empty!");
        }

        if (cvc === "") {
            setCvcValid(false);
            setCvcWarn("Field cannot be empty!");
        }

        if (date === "") {
            setDateValid(false);
            setDateWarn("Field cannot be empty!");
        }

        if (amount === "") {
            setAmountValid(false);
            setAmountWarn("Field cannot be empty!");
        }

        if(accountNoValid&&cvcValid&&dateValid&&amountValid)
            toast.success("Wallet balance recharge successful!");
    }

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
                    />{' '}
                    {isEN?"AgriDoctor":"অ্যাগ্রি-ডক্টর"}
                </Navbar.Brand>
                <Navbar.Toggle aria-controls="responsive-navbar-nav" />
                <Navbar.Collapse id="responsive-navbar-nav">
                <Nav className="me-auto">
                </Nav>
                <Nav>
                    <Nav.Link href='/home' className={currentPage === 0 ? 'bold' : ''}>
                        {isEN?"Home":"হোম"}
                    </Nav.Link>
                    <Nav.Link onClick={handleCropPredClick} className={currentPage === 1 ? 'bold' : ''}>
                        {isEN?"Crop Recommendation":"ফসল সুপারিশ"}
                    </Nav.Link>
                    <Nav.Link onClick={handleFertilizerPredClick} className={currentPage === 2 ? 'bold' : ''}>
                        {isEN?"Fertilizer Recommendation":"সার/উর্বরক সুপারিশ"}
                    </Nav.Link>
                    <Nav.Link onClick={handleDiseasePredClick} className={currentPage === 3 ? 'bold' : ''}>
                        {isEN?"Disease Identification":"ফসল রোগ চিহ্নিতকরণ"}
                    </Nav.Link>
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
                        {isEN?"Tk.":"ট"}{' '}{isEN?walletBalance:walletBalance.toLocaleString('bn-BD')}
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
                </Nav>
                "  "
                <Nav>
                    <Button variant="outline-success" onClick={handleLogout}>{isEN?"Logout":"লগ-আউট"}</Button>
                </Nav>
                "  "
                <Nav>
                    <LanguageSelector/>
                </Nav>
                </Navbar.Collapse>
            </Container>
            </Navbar>


            <Modal show={showModal} onHide={handleAddWallet} centered>
                <Modal.Header closeButton>
                    <Image
                        width={64}
                        height={64}
                        className="mr-3"
                        src="https://cdn-icons-png.flaticon.com/512/349/349221.png" //Image Credit: FlatIcon
                    />
                    <Modal.Title>&nbsp;&nbsp;{isEN?"VISA Card Information":"ভিসা(VISA) কার্ড তথ্য"}</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <InputGroup className="mb-3">
                        <InputGroup.Text  className="input-group-text-dark">{isEN?"Account No.":"অ্যাকাউন্ট নম্বর"}</InputGroup.Text>
                        <Form.Control
                        type="number"
                        placeholder="Card Number"
                        value={accountNo}
                        onChange={(e)=>{setAccountNo(e.target.value)}}
                        />
                        <Collapse in={!accountNoValid}>
                        <div id="example-collapse-text">
                            <Card body className='warn-card custom-card'>
                            {accountNoWarn}
                            </Card>
                        </div>
                        </Collapse>
                    </InputGroup>
                    <InputGroup className="mb-3">
                        <InputGroup.Text  className="input-group-text-dark">CVC</InputGroup.Text>
                        <Form.Control
                        type="number"
                        placeholder="CVC"
                        value={cvc}
                        onChange={(e)=>{setCvc(e.target.value)}}
                        />
                        <Collapse in={!cvcValid}>
                        <div id="example-collapse-text">
                            <Card body className='warn-card custom-card'>
                            {cvcWarn}
                            </Card>
                        </div>
                        </Collapse>
                    </InputGroup>
                    <InputGroup className="mb-3">
                        <InputGroup.Text  className="input-group-text-dark">{isEN?"Expiry Date":"উত্তীর্ণের তারিখ"}</InputGroup.Text>
                        <Form.Control
                        type="date"
                        placeholder="Card Expiry Date"
                        value={date}
                        onChange={(e)=>{setDate(e.target.value)}}
                        />
                        <Collapse in={!dateValid}>
                        <div id="example-collapse-text">
                            <Card body className='warn-card custom-card'>
                            {dateWarn}
                            </Card>
                        </div>
                        </Collapse>
                    </InputGroup>
                    <InputGroup className="mb-3">
                        <InputGroup.Text  className="input-group-text-dark">{isEN?"Recharge Amount":"রিচার্জ পরিমাণ"}</InputGroup.Text>
                        <Form.Control
                        type="number"
                        placeholder="Amount to be added"
                        value={amount}
                        onChange={(e)=>{setAmount(e.target.value)}}
                        />
                        <Collapse in={!amountValid}>
                        <div id="example-collapse-text">
                            <Card body className='warn-card custom-card'>
                            {amountWarn}
                            </Card>
                        </div>
                        </Collapse>
                    </InputGroup>
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="outline-success" onClick={handleAddBalance}>
                        {isEN?"Recharge Balance":"রিচার্জ টাকা"}
                    </Button>
                </Modal.Footer>
            </Modal>
        </div>
    )
}

export default NavbarG
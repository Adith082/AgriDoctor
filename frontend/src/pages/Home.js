import React, { useContext, useEffect, useState } from 'react'
import "./Home.css"
import NavbarG from '../components/NavbarG'
import Card from 'react-bootstrap/Card';
import Container from 'react-bootstrap/Container';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';
import 'bootstrap/dist/css/bootstrap.min.css';
import { useNavigate } from 'react-router-dom';
import Button from 'react-bootstrap/Button';
import { LanguageContext } from '../contexts/LanguageContext';
import { toast } from 'react-toastify';
import { LoginContext } from '../contexts/LoginContext';

function Home() {

  const [walletBalance, setWalletBalance] = useState(0);

  const navigate = useNavigate();

  const {token} = useContext(LoginContext);
  const {isEN} = useContext(LanguageContext)

  useEffect(() => {
    // Define a function to fetch the wallet balance
    const fetchWalletBalance = async () => {
      try {
        const response = await fetch('API_ENDPOINT'); // Replace with your API endpoint
        const data = await response.json();
        setWalletBalance(data.walletBalance);
      } catch (error) {
        console.error('Error fetching wallet balance:', error);
      }
    };

    //checking the existence of token
    const checkToken = () => {
      if(!token){
        navigate("/");
        toast.warn(isEN ? "Login First!" : "প্রথমে লগ-ইন করুন!");
      }
    }

    checkToken();
    // Call the function to fetch wallet balance when the component mounts
    fetchWalletBalance();
  }, [token, isEN, navigate]);

  const handleCropPredict = (e) => {
    navigate('/crop-predict');
  }

  const handleFertPredict = (e) => {
    navigate('/fertilizer-predict');
  }

  const handleDiseasePredict = (e) => {
    navigate('/disease-predict');
  }

  return (
    <div>
    <div className='home-container'>
        <NavbarG currentPage={0} walletBalance={walletBalance}/>
        <Container className="mt-4 d-flex justify-content-center align-items-center vh-80">
            <Row xs={1} md={2} lg={3} className="g-4">
                <Col>
                <Card className="h-100 shadow">
                    <Card.Img variant="top" className='card-image' src={require('../images/green-sprouts-dark-soil-against-blurred-background-symbolizing-concept-growth-potential.jpg')}/> {/*Image Credit: Image By svstudioarton Freepik*/}
                    <Card.Body>
                    <Card.Title>Crop Planting Recommendations</Card.Title>
                    <Card.Text>
                        Gives suggestion on which crop to grow based on provided soil-data like: Nitrogen, Phosphorus, Potassium level of the soil and Humidity, Rainfall, and Temperature level of the weather.
                    </Card.Text>
                    <Button className="custom-button" onClick={handleCropPredict}>Take There!</Button>
                    </Card.Body>
                </Card>
                </Col>
                <Col>
                <Card className="h-100 shadow">
                    <Card.Img variant="top" className='card-image' src={require('../images/granulated-fertilize.jpg')}/> {/*Image Credit: Image by Zbynek Pospisil*/}
                    <Card.Body>
                    <Card.Title>Fertilizer Recommendation</Card.Title>
                    <Card.Text>
                        Based on provided soil nutrient levels and based on the plant you are growing, get recommendation on the level of nutrient of the soil and recommended fertilizers can be used to get best crop growth.
                    </Card.Text>
                    <Button className="custom-button" onClick={handleFertPredict}>Take There!</Button>
                    </Card.Body>
                </Card>
                </Col>
                <Col>
                <Card className="h-100 shadow">
                    <Card.Img variant="top" src={require('../images/flat-lay-dry-leaves.jpg')} />{/*Image Credit: Image by Freepik*/}
                    <Card.Body>
                    <Card.Title>Crop Disease Detection</Card.Title>
                    <Card.Text>
                        Detects the the disease a plant is affected with provided the image of the leaf of the crop. It also provides the cause of the disease with necessary description.
                    </Card.Text>
                    <Button className="custom-button" onClick={handleDiseasePredict}>Take There!</Button>
                    </Card.Body>
                </Card>
                </Col>
            </Row>
        </Container>
    </div>
    </div>
  )
}

export default Home
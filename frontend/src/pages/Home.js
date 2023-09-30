import React, { useContext, useEffect } from 'react'
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

  const navigate = useNavigate();

  const {token} = useContext(LoginContext);
  const {isEN} = useContext(LanguageContext)

  useEffect(() => {

    //checking the existence of token
    const checkToken = () => {
      if(!token){
        navigate("/");
        toast.warn(isEN ? "Login First!" : "প্রথমে লগ-ইন করুন!");
      }
    }

    checkToken();
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
        <NavbarG currentPage={0}/>
        <Container className="mt-4 d-flex justify-content-center align-items-center vh-80">
            <Row xs={1} md={2} lg={3} className="g-4">
                <Col>
                <Card className="h-100 shadow">
                    <Card.Img variant="top" className='card-image' src={require('../images/green-sprouts-dark-soil-against-blurred-background-symbolizing-concept-growth-potential.jpg')}/> {/*Image Credit: Image By svstudioarton Freepik*/}
                    <Card.Body>
                    <Card.Title>{isEN ? "Crop Planting Recommendations" : "ফসল চাষের প্রস্তাবনা"}</Card.Title>
                    <Card.Text>
                    {isEN ? "Get suggestions on which crop to grow based on provided soil data such as Nitrogen, Phosphorus, and Potassium levels, as well as weather conditions including Humidity, Rainfall, and Temperature."
                    :"নাইট্রোজেন, ফসফরাস, এবং পটাসিয়ামের স্তরের পাশাপাশি আর্দ্রতা, বৃষ্টিপাত এবং তাপমাত্রা সহ আবহাওয়ার অবস্থার উপর ভিত্তি করে কোন ফসল রোপণ করতে হবে তার পরামর্শ পান।"}
                    </Card.Text>
                    <Button className="custom-button" onClick={handleCropPredict}>{isEN ? "Take Me There!" : "সেখানে নিয়ে চলুন!"}</Button>
                    </Card.Body>
                </Card>
                </Col>
                <Col>
                <Card className="h-100 shadow">
                    <Card.Img variant="top" className='card-image' src={require('../images/granulated-fertilize.jpg')}/> {/*Image Credit: Image by Zbynek Pospisil*/}
                    <Card.Body>
                    <Card.Title>{isEN ? "Fertilizer Recommendation" : "উর্বরক পরামর্শ"}</Card.Title>
                    <Card.Text>
                    {isEN
                      ? "Based on the provided soil nutrient levels and the type of plant you are growing, you can receive recommendations for adjusting the soil nutrient levels and using specific fertilizers to achieve optimal crop growth."
                      : "প্রদত্ত মাটির পুষ্টির স্তরের উপর ভিত্তি করে এবং আপনি যে উদ্ভিদটি বৃদ্ধি করছেন তার উপর ভিত্তি করে, মাটির পুষ্টির স্তর সমর্থন পান এবং সর্বোত্তম ফসল বৃদ্ধির জন্য সার ব্যবহারের সুপারিশ পান।"
                    }
                    </Card.Text>
                    <Button className="custom-button" onClick={handleFertPredict}>{isEN ? "Take Me There!" : "সেখানে নিয়ে চলুন!"}</Button>
                    </Card.Body>
                </Card>
                </Col>
                <Col>
                <Card className="h-100 shadow">
                    <Card.Img variant="top" src={require('../images/flat-lay-dry-leaves.jpg')} />{/*Image Credit: Image by Freepik*/}
                    <Card.Body>
                    <Card.Title>{isEN? "Crop Disease Detection": "ফসলে রোগ পরিচিতি"}</Card.Title>
                    <Card.Text>
                      {isEN
                      ? "Detects the disease a plant is affected with provided the image of the leaf of the crop. It also provides the cause of the disease with necessary description."
                      : "ফসলের পাতার ছবি দেওয়া হলে এটি পাতার উপর আসন্ন রোগটি চিহ্নিত করে। এছাড়া, রোগের কারণ এবং প্রয়োজনীয় বর্ণনা সরবরাহ করে।"
                      }
                    </Card.Text>
                    <Button className="custom-button" onClick={handleDiseasePredict}>{isEN ? "Take Me There!" : "সেখানে নিয়ে চলুন!"}</Button>
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
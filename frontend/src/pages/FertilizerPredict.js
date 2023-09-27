import React, { useEffect, useState } from 'react'
import NavbarG from '../components/NavbarG'
import "./FertilizerPredict.css"
import Button from 'react-bootstrap/Button';
import 'bootstrap/dist/css/bootstrap.min.css';
import { toast } from 'react-toastify';
import Image from 'react-bootstrap/Image';
import Collapse from 'react-bootstrap/Collapse';
import Form from 'react-bootstrap/Form';
import InputGroup from 'react-bootstrap/InputGroup';
import Card from 'react-bootstrap/Card';

function FertilizerPredict() {

  const [walletBalance, setWalletBalance] = useState(0);

  const [nitrogen, setNitrogen] = useState(0);
  const [phosphorus, setPhosphorus] = useState(0);
  const [potassium, setPotassium] = useState(0);

  const [nitrogenValid, setNitrogenValid] = useState(0);
  const [phosphorusValid, setPhosphorusValid] = useState(0);
  const [potassiumValid, setPotassiumValid] = useState(0);

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

      // Call the function to fetch wallet balance when the component mounts
      fetchWalletBalance();
  }, []);

  return (
    <div className='fertilizer-container'>
      <NavbarG currentPage={2} walletBalance={walletBalance}/>
      <div className='fertilizer-duo-container'>
        <div className='right-container'>
          <div className='fertilizer-input-section'>
            <h1 className='fertilizer-input-title'>Crop leaf Image</h1>
            <InputGroup className="mb-3">
              <InputGroup.Text  className="input-group-text-dark">Nitrogen(ppm)</InputGroup.Text>
              <Form.Control
              type="number"
              placeholder="Nitrogen(N) amount in soil"
              value={nitrogen}
              onChange={(e)=>{setNitrogen(e.target.value)}}
              />
              <Collapse in={nitrogen===""}>
              <div id="example-collapse-text">
                  <Card body className='warn-card custom-card'>
                    Field cannot be kept empty!
                  </Card>
              </div>
              </Collapse>
          </InputGroup>
          
          </div>
        </div>
        <div className='right-container'>
          <div className='fertilizer-input-section'>
            <h1 className='fertilizer-input-title'>Crop leaf Image</h1>
          </div>
        </div>
      </div>
    </div>
  )
}

export default FertilizerPredict
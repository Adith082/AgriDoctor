import React, { useEffect, useState } from 'react'
import NavbarG from '../components/NavbarG'

function DiseasePredict() {
  const [walletBalance, setWalletBalance] = useState(0);

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
    <div>
      <NavbarG currentPage={0} walletBalance={walletBalance}/>
    </div>
  )
}

export default DiseasePredict
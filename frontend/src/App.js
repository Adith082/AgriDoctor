import { Routes, Route } from "react-router-dom";
import LoginRegister from "./pages/LoginRegister";
import Home from "./pages/Home";
import CropPredict from "./pages/CropPredict"
import FertilizerPredict from "./pages/FertilizerPredict"
import DiseasePredict from "./pages/DiseasePredict"
import AdminHome from "./pages/AdminHome";
import { useState } from "react";
import { LanguageContext } from "./contexts/LanguageContext";
import { LoginContext } from "./contexts/LoginContext";

function App() {

  const [isEN, setIsEN] = useState(true);
  const [token, setToken] = useState(null);
  const [walletBalance, setWalletBalance] = useState(null);
  const [uid, setUid] = useState(null);

  return (
    <div>
      <LanguageContext.Provider value={{isEN, setIsEN}}>
      <LoginContext.Provider value={{token, setToken, walletBalance, setWalletBalance, uid, setUid}}>
        <Routes>
          <Route
            path="/"
            element={
              <LoginRegister/>
            }
          />

          <Route
            path="/home"
            element={
              <Home/>
            }
          />

          <Route
            path="/crop-predict"
            element={
              <CropPredict/>
            }
          />

          <Route
            path="/fertilizer-predict"
            element={
              <FertilizerPredict/>
            }
          />

          <Route
            path="/disease-predict"
            element={
              <DiseasePredict/>
            }
          />

          <Route
            path="/admin-home"
            element={
              <AdminHome/>
            }
          />
        </Routes>
      </LoginContext.Provider>
      </LanguageContext.Provider>
    </div>
  );
}

export default App;

import { Routes, Route } from "react-router-dom";
import LoginRegister from "./pages/LoginRegister";
import Home from "./pages/Home";
import CropPredict from "./pages/CropPredict"
import FertilizerPredict from "./pages/FertilizerPredict"
import DiseasePredict from "./pages/DiseasePredict"
import AdminHome from "./pages/AdminHome";
import { useState } from "react";
import { LanguageContext } from "./contexts/LanguageContext";

function App() {

  const [isEN, setIsEN] = useState(true);

  return (
    <div>
      <LanguageContext.Provider value={{isEN, setIsEN}}>
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
      </LanguageContext.Provider>
    </div>
  );
}

export default App;

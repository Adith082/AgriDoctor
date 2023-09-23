import { Routes, Route } from "react-router-dom";
import LoginRegister from "./pages/LoginRegister";
import Home from "./pages/Home";
import CropPredict from "./pages/CropPredict"
import FertilizerPredict from "./pages/FertilizerPredict"
import DiseasePredict from "./pages/FertilizerPredict"

function App() {
  return (
    <div>
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
      </Routes>
    </div>
  );
}

export default App;

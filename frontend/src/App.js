import { Routes, Route } from "react-router-dom";
import LoginRegister from "./pages/LoginRegister";

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
      </Routes>
    </div>
  );
}

export default App;

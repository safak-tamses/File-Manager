import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";
import LoginRegister from "./Component/LoginRegister";
import Page from "./Component/Page";
function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Navigate to={"/index"} />} />
        <Route path="/index" element={<Page />} />
        <Route path="/login" element={<LoginRegister />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;

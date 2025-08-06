import { HashRouter, Routes, Route, useLocation } from "react-router-dom";
import { useState } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap/dist/js/bootstrap.bundle.min.js';

import Header from './modules/app/Header';
import Footer from './modules/app/Footer';
import Login from './modules/user/components/Login';
import Register from './modules/user/components/Register';
import UserProfile from './modules/user/components/Profile';

const Content = () => {
  const location = useLocation();

  const hideHeaderPath = ["/login", "/register"];
  const hideHeader = hideHeaderPath.includes(location.pathname);

  return (
    <>
      {!hideHeader && <Header />}
      <Routes>
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />
        <Route path="/profile/:id" element={<UserProfile />} />
        
      </Routes>
    </>
  );
}

function App() {
  const [search, setSearch] = useState("");

  return (
    <div>
      <Content search={search} setSearch={setSearch} />
      <Footer />
    </div>
  );
}

export default App;

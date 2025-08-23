import { Routes, Route, useLocation } from "react-router-dom";
import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap/dist/js/bootstrap.bundle.min.js';

import Header from './modules/app/Header';
import Footer from './modules/app/Footer';
import Login from './modules/user/components/Login';
import Register from './modules/user/components/Register';
import UserProfile from './modules/user/components/Profile';
import CardDetails from "./modules/cards/components/CardDetails";
import CardSearch from './modules/cards/components/CardSearch';
import AddCardStock from "./modules/cardStock/components/AddCardStock.jsx";
import CartPage from "./modules/cart/components/CartPage.jsx";
import OrderSummaryPage from "./modules/cart/components/OrderSummary.jsx";
import OrdersBuyer from "./modules/orders/components/OrdersBuyer.jsx";


const Content = () => {
  const location = useLocation();

  const hideHeaderPath = ["/login", "/register"];
  const hideHeader = hideHeaderPath.includes(location.pathname);

  return (
    <main className="flex-grow-1">
      {!hideHeader && <Header />}
      <Routes>
        <Route path="/" element={<CardSearch />} />
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />
        <Route path="/profile/:id" element={<UserProfile />} />
        <Route path="/cards/:id" element={<CardDetails />} />
        <Route path="/sell/:cardId" element={<AddCardStock />} />
        <Route path="/cart" element={<CartPage />} />      
        <Route path="/orderSummary" element={<OrderSummaryPage />} />      
        <Route path="/ordersBuyer" element={<OrdersBuyer />} />      
      </Routes>
    </main>
  );
};

function App() {

  return (
    <div className="d-flex flex-column min-vh-100">
      <Content/>
      <Footer />
    </div>
  );
}

export default App;

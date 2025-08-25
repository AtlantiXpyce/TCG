import { Link, useNavigate } from "react-router-dom";

function Header() {
  const username = localStorage.getItem("username");
  const isStore = localStorage.getItem("isStore");
  const navigate = useNavigate();
  const userId = localStorage.getItem("userId");

  const handleLogout = () => {
    localStorage.removeItem("token");
    localStorage.removeItem("username");
    localStorage.removeItem("role");
    localStorage.removeItem("userId");
    localStorage.removeItem("isStore");
    navigate("/login");
  };

  const goBack = () => {
    navigate("/");
  }
  
  const goCart = () => {
    navigate("/cart");
  }

  const goOrders = () => {
    navigate("/ordersBuyer");
  }

  const goStock = () => {
    navigate("/myStock");
  }

  return (
    <header className="bg-light p-3">
      <div className="container d-flex justify-content-between align-items-center">

        {username ? (
          <div className="d-flex align-items-center gap-3">
            <Link to={`/profile/${userId}`} className="text-decoration-none fw-bold">
              {username}
            </Link>
            <button onClick={handleLogout} className="btn btn-outline-danger btn-sm">
              Cerrar sesión
            </button>
          </div>
        ) : (
          <Link to="/login" className="btn btn-primary btn-sm">
            Iniciar sesión
          </Link>
        )}
        <div className="d-flex gap-2 ms-auto">
          {isStore && (
            <button className="btn btn-outline-primary btn-sm" onClick={goStock}>
              Mis ofertas
            </button>
          )}
          {username && (
            <button className="btn btn-outline-primary btn-sm" onClick={goCart}>
              Carrito
            </button>
          )}
          {username && (
            <button className="btn btn-outline-primary btn-sm" onClick={goOrders}>
              Mis pedidos
            </button>
          )}
          <button className="btn btn-outline-primary btn-sm" onClick={goBack}>
            Home
          </button>
        </div>
      </div>
    </header>
  );
}

export default Header;

import { Link, useNavigate } from "react-router-dom";

function Header({ search, setSearch }) {
  const username = localStorage.getItem("username");
  const navigate = useNavigate();

  const handleLogout = () => {
    localStorage.removeItem("token");
    localStorage.removeItem("username");
    navigate("/login");
  };

  return (
    <header className="bg-light p-3">
      <div className="container d-flex justify-content-between align-items-center">
        <input
          type="text"
          className="form-control me-3"
          placeholder="Buscar..."
          value={search}
          onChange={(e) => setSearch(e.target.value)}
          style={{ maxWidth: "300px" }}
        />

        {username ? (
          <div className="d-flex align-items-center gap-3">
            <Link to="/profile" className="text-decoration-none fw-bold">
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
      </div>
    </header>
  );
}

export default Header;

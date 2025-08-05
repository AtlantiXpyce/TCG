import React, { useState } from "react";
import { Link, useNavigate } from "react-router-dom";

const Login = () => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState(null);
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError(null);

    try {
      const response = await fetch("http://localhost:8080/user/login", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({ username, password }),
      });

      if (!response.ok) {
        const errorMsg = await response.text();
        setError(errorMsg || "Error al iniciar sesión");
        return;
      }

      const data = await response.json();
      localStorage.setItem("token", data.token);
      navigate("/");
    } catch (err) {
      setError("Error de conexión");
      console.error(err);
    }
  };

  return (
    <div className="d-flex vh-100 align-items-center justify-content-center bg-light">
      <div className="card shadow-sm" style={{ maxWidth: 400, width: "100%" }}>
        <div className="card-body p-4">
          <h3 className="card-title text-center mb-4">Iniciar sesión</h3>
          <form onSubmit={handleSubmit} noValidate>
            <div className="mb-3">
              <input
                id="username"
                type="text"
                className="form-control"
                value={username}
                onChange={e => setUsername(e.target.value)}
                placeholder="Usuario"
                autoComplete="username"
                required
              />
            </div>
            <div className="mb-3">
              <input
                id="password"
                type="password"
                className="form-control"
                value={password}
                onChange={e => setPassword(e.target.value)}
                placeholder="Contraseña"
                autoComplete="current-password"
                required
              />
            </div>
            {error && (
              <div className="alert alert-danger" role="alert">
                {error}
              </div>
            )}
            <button type="submit" className="btn btn-primary w-100">
              Entrar
            </button>
          </form>
          <div className="text-center mt-3">
            <small>
              ¿No tienes cuenta?{" "}
              <Link to="/register" className="link-primary">
                Regístrate
              </Link>
            </small>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Login;

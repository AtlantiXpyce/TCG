import React,{ useState } from "react";
import { Link } from "react-router-dom";

const Login = () => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");

  return (
    <div className="d-flex vh-100 align-items-center justify-content-center bg-light">
      <div className="card shadow-sm" style={{ maxWidth: 400, width: "100%" }}>
        <div className="card-body p-4">
          <h3 className="card-title text-center mb-4">Iniciar sesión</h3>
          <form onSubmit={e => e.preventDefault()} noValidate>
            <div className="mb-3">
              <input
                id="username"
                type="text"
                className="form-control"
                value={username}
                onChange={e => setUsername(e.target.value)}
                placeholder="Usuario"
                autoComplete="username"
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
              />
            </div>
            <button type="submit" className="btn btn-primary w-100">
              Entrar
            </button>
          </form>
          <div className="text-center mt-3">
            <small>
              ¿No tienes cuenta?{" "}
              <Link to="/register" className="link-primary">Regístrate</Link>
            </small>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Login;

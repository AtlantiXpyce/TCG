import React, { useState } from "react";
import { Link } from "react-router-dom";

const Register = () => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [email, setEmail] = useState("");
  const [firstName, setFirstName] = useState("");
  const [lastName, setLastName] = useState("");

  const [errors, setErrors] = useState({});

  const validate = () => {
    const newErrors = {};

    if (!username.trim()) {
      newErrors.username = "El nombre de usuario es obligatorio";
    } else if (username.length < 3 || username.length > 50) {
      newErrors.username = "El nombre de usuario debe tener entre 3 y 50 caracteres";
    }

    if (!password) {
      newErrors.password = "La contraseña es obligatoria";
    } else if (password.length < 6) {
      newErrors.password = "La contraseña debe tener al menos 6 caracteres";
    }

    if (!email.trim()) {
      newErrors.email = "El correo es obligatorio";
    } else if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email)) {
      newErrors.email = "El correo no es válido";
    }

    if (!firstName.trim()) {
      newErrors.firstName = "El nombre es obligatorio";
    }

    if (!lastName.trim()) {
      newErrors.lastName = "Los apellidos son obligatorio";
    }

    setErrors(newErrors);

    return Object.keys(newErrors).length === 0;
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    if (validate()) {
      console.log({ username, password, email, firstName, lastName });
    }
  };

  return (
    <div className="d-flex vh-100 align-items-center justify-content-center bg-light">
      <div className="card shadow-sm" style={{ maxWidth: 400, width: "100%" }}>
        <div className="card-body p-4">
          <h3 className="card-title text-center mb-4">Registrarse</h3>
          <form onSubmit={handleSubmit} noValidate>
            
            <div className="mb-3">
              <input
                id="firstName"
                type="text"
                className={`form-control ${errors.firstName ? "is-invalid" : ""}`}
                value={firstName}
                onChange={e => setFirstName(e.target.value)}
                placeholder="Nombre"
                autoComplete="given-name"
              />
              {errors.firstName && <div className="invalid-feedback">{errors.firstName}</div>}
            </div>

            <div className="mb-3">
              <input
                id="lastName"
                type="text"
                className={`form-control ${errors.lastName ? "is-invalid" : ""}`}
                value={lastName}
                onChange={e => setLastName(e.target.value)}
                placeholder="Apellidos"
                autoComplete="family-name"
              />
              {errors.lastName && <div className="invalid-feedback">{errors.lastName}</div>}
            </div>
            
            <div className="mb-3">
              <input
                id="username"
                type="text"
                className={`form-control ${errors.username ? "is-invalid" : ""}`}
                value={username}
                onChange={e => setUsername(e.target.value)}
                placeholder="Nombre de usuario"
                autoComplete="username"
              />
              {errors.username && <div className="invalid-feedback">{errors.username}</div>}
            </div>

            <div className="mb-3">
              <input
                id="password"
                type="password"
                className={`form-control ${errors.password ? "is-invalid" : ""}`}
                value={password}
                onChange={e => setPassword(e.target.value)}
                placeholder="Contraseña"
                autoComplete="new-password"
              />
              {errors.password && <div className="invalid-feedback">{errors.password}</div>}
            </div>

            <div className="mb-3">
              <input
                id="email"
                type="email"
                className={`form-control ${errors.email ? "is-invalid" : ""}`}
                value={email}
                onChange={e => setEmail(e.target.value)}
                placeholder="Correo electrónico"
                autoComplete="email"
              />
              {errors.email && <div className="invalid-feedback">{errors.email}</div>}
            </div>

            <button type="submit" className="btn btn-primary w-100">
              Crear cuenta
            </button>
          </form>
          <div className="text-center mt-3">
            <small>
              ¿Ya tienes cuenta?{" "}
              <Link to="/login" className="link-primary">Iniciar sesión</Link>
            </small>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Register;

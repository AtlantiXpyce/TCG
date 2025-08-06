import { useEffect, useState } from "react";
import { useParams, Link } from "react-router-dom";

const UserProfile = () => {
  const { id } = useParams();
  const [profile, setProfile] = useState(null);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchProfile = async () => {
      try {
        const token = localStorage.getItem("token");

        const response = await fetch(`http://localhost:8080/user/${id}`, {
          headers: {
            "Authorization": token ? `Bearer ${token}` : "",
          },
        });

        if (!response.ok) {
          const msg = await response.text();
          setError(msg || "No se pudo cargar el perfil");
          return;
        }

        const data = await response.json();
        setProfile(data);
      } catch (err) {
        setError("Error de conexión con el servidor");
        console.error(err);
      }
    };

    fetchProfile();
  }, [id]);

  return (
    <div className="d-flex vh-100 align-items-center justify-content-center bg-light">
      <div className="card shadow-sm" style={{ maxWidth: 400, width: "100%" }}>
        <div className="card-body p-4">
          <h3 className="card-title text-center mb-4">Perfil de usuario</h3>

          {error && (
            <div className="alert alert-danger" role="alert">
              {error}
            </div>
          )}
          
          {profile && (
            <>
              <div className="mb-3">
                <strong>Usuario:</strong> {profile.username}
              </div>
              <div className="mb-3">
                <strong>Nombre:</strong> {profile.firstName}
              </div>
              <div className="mb-3">
                <strong>¿Es tienda?:</strong> {profile.store ? "Sí" : "No"}
              </div>
            </>
          )}

          <div className="text-center mt-3">
            <Link to="/" className="btn btn-secondary w-100">
              Volver
            </Link>
          </div>
        </div>
      </div>
    </div>
  );
};

export default UserProfile;

import { useState, useEffect } from "react";
import { useNavigate, Link } from "react-router-dom";

const AdminUserList = () => {
  const [users, setUsers] = useState([]);
  const [pageNum, setPageNum] = useState(0);
  const [totalPages, setTotalPages] = useState(0);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  const navigate = useNavigate();
  const token = localStorage.getItem("token");

  const fetchUsers = async (page = 0) => {
    try {
      setLoading(true);
      const response = await fetch(`http://localhost:8080/admin/getUsers?pageNum=${page}`, {
        headers: { Authorization: `Bearer ${token}` },
      });

      if (!response.ok) {
        setError("Error al cargar los usuarios");
        setLoading(false);
        return;
      }

      const data = await response.json();
      setUsers(data.content);
      setTotalPages(data.totalPages);
      setPageNum(data.number);
    } catch (err) {
      setError("Error de conexión");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    if (token) {
      fetchUsers(pageNum);
    } else {
      setError("Debes iniciar sesión como admin para ver usuarios");
      setLoading(false);
    }
  }, [token, pageNum]);

  const handleDelete = async (userId) => {
    try {
      const response = await fetch(`http://localhost:8080/admin/deleteUser?userId=${userId}`, {
        method: "POST",
        headers: { Authorization: `Bearer ${token}` },
      });

      if (!response.ok) {
        alert("Error al eliminar usuario");
        return;
      }

      fetchUsers(pageNum);
    } catch (err) {
      console.error("Error eliminando usuario", err);
    }
  };

  if (loading) {
    return (
      <div className="d-flex vh-100 align-items-center justify-content-center bg-light">
        <div>Cargando usuarios...</div>
      </div>
    );
  }

  if (error) {
    return (
      <div className="d-flex flex-column vh-100 align-items-center justify-content-center bg-light">
        <div className="alert alert-danger">{error}</div>
        <button className="btn btn-secondary mt-3" onClick={() => navigate("/")}>
          Volver al inicio
        </button>
      </div>
    );
  }

  return (
    <div style={{ paddingTop: "60px", paddingBottom: "50px" }}>
      <div className="container">
        <h2 className="mb-4 text-center">Gestión de Usuarios</h2>
        {users.length === 0 ? (
          <div className="alert alert-info text-center">No hay usuarios registrados</div>
        ) : (
          <>
            <div className="table-responsive">
              <table className="table table-striped table-hover">
                <thead>
                  <tr>
                    <th>ID</th>
                    <th>Username</th>
                    <th>Email</th>
                    <th></th>
                  </tr>
                </thead>
                <tbody>
                  {users.map((user) => (
                    <tr key={user.id}>
                      <td>{user.id}</td>
                      <td>
                        <Link to={`/profile/${user.id}`} className="text-decoration-none">
                          {user.username}
                        </Link>
                      </td>
                      <td>{user.email}</td>
                      <td>
                        <button
                          className="btn btn-sm btn-danger"
                          onClick={() => handleDelete(user.id)}
                        >
                          Eliminar
                        </button>
                      </td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>

            <div className="d-flex justify-content-between align-items-center mt-4">
              <button
                className="btn btn-secondary"
                disabled={pageNum === 0}
                onClick={() => setPageNum(pageNum - 1)}
              >
                Anterior
              </button>
              <span>
                Página {pageNum + 1} de {totalPages}
              </span>
              <button
                className="btn btn-secondary"
                disabled={pageNum + 1 >= totalPages}
                onClick={() => setPageNum(pageNum + 1)}
              >
                Siguiente
              </button>
            </div>
          </>
        )}
      </div>
    </div>
  );
};

export default AdminUserList;
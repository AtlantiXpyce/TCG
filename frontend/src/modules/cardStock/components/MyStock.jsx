import { useState, useEffect } from "react";
import { Link } from "react-router-dom";

const MyStock = () => {
  const [stocks, setStocks] = useState([]);
  const [page, setPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  const token = localStorage.getItem("token");
  const sellerId = localStorage.getItem("userId");

  const fetchStocks = async (pageNum = 0) => {
    try {
      setLoading(true);
      const response = await fetch(
        `http://localhost:8080/cardStock/seller?sellerId=${sellerId}&page=${pageNum}`,
        {
          headers: { Authorization: `Bearer ${token}` },
        }
      );

      if (!response.ok) {
        setError("Error al cargar tus ofertas");
        setLoading(false);
        return;
      }

      const data = await response.json();
      setStocks(data.content || []);
      setTotalPages(data.totalPages || 0);
      setPage(data.number || 0);
    } catch (err) {
      setError("Error de conexión");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    if (token && sellerId) {
      fetchStocks(0);
    } else {
      setError("Debes iniciar sesión para ver tus ofertas");
      setLoading(false);
    }
  }, [token, sellerId]);

  const handleDelete = async (stockId) => {
    if (!window.confirm("¿Seguro que quieres eliminar esta oferta?")) return;

    try {
      const response = await fetch(
        `http://localhost:8080/cardStock/delete?sellerId=${sellerId}&stockId=${stockId}`,
        {
          method: "POST",
          headers: { Authorization: `Bearer ${token}` },
        }
      );

      if (response.ok) {
        fetchStocks(page);
      } else {
        alert("Error al eliminar la oferta");
      }
    } catch (err) {
      alert("Error de conexión");
    }
  };

  if (loading) {
    return <div className="text-center mt-5">Cargando...</div>;
  }

  if (error) {
    return <div className="alert alert-danger mt-5">{error}</div>;
  }

  return (
    <div className="container mt-4">
      <h2>Mis ofertas</h2>

      {stocks.length === 0 ? (
        <p>No tienes cartas en venta.</p>
      ) : (
        <>
          <div className="table-responsive mt-3">
            <table className="table table-striped table-hover">
              <thead>
                <tr>
                  <th>ID</th>
                  <th>Carta</th>
                  <th>Idioma</th>
                  <th>Condición</th>
                  <th>Cantidad</th>
                  <th>Precio</th>
                  <th>Comentario</th>
                  <th>Acciones</th>
                </tr>
              </thead>
              <tbody>
                {stocks.map((stock) => (
                  <tr key={stock.id}>
                    <td>{stock.id}</td>
                    <td>
                      <Link
                        to={`/cards/${stock.cardId}`}
                        className="text-decoration-none"
                      >
                        {stock.cardId}
                      </Link>
                    </td>
                    <td>{stock.language}</td>
                    <td>{stock.condition}</td>
                    <td>{stock.quantity}</td>
                    <td>${stock.price}</td>
                    <td>{stock.comment}</td>
                    <td>
                      <button
                        className="btn btn-danger btn-sm"
                        onClick={() => handleDelete(stock.id)}
                      >
                        Eliminar
                      </button>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>

          <div className="d-flex justify-content-between mt-3">
            <button
              className="btn btn-outline-secondary"
              onClick={() => fetchStocks(page - 1)}
              disabled={page === 0}
            >
              Anterior
            </button>
            <span>
              Página {page + 1} de {totalPages}
            </span>
            <button
              className="btn btn-outline-secondary"
              onClick={() => fetchStocks(page + 1)}
              disabled={page + 1 >= totalPages}
            >
              Siguiente
            </button>
          </div>
        </>
      )}
    </div>
  );
};

export default MyStock;

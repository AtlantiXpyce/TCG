import { useState, useEffect } from "react";
import { Link, useParams, useNavigate, useLocation } from "react-router-dom";
import ReviewForm from "./ReviewForm";

const OrderDetails = () => {
  const { id } = useParams();
  const navigate = useNavigate();
  const location = useLocation();
  const { rating, review } = location.state || {};
  const [orderLines, setOrderLines] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  const token = localStorage.getItem("token");

  useEffect(() => {
    const fetchOrderLines = async () => {
      try {
        const response = await fetch(
          `http://localhost:8080/orders/lines?orderId=${id}`,
          {
            headers: { Authorization: `Bearer ${token}` },
          }
        );

        if (!response.ok) {
          if (response.status === 404) {
            setError("Orden no encontrada");
          } else {
            setError("Error al cargar la orden");
          }
          setLoading(false);
          return;
        }

        const data = await response.json();
        setOrderLines(data);
      } catch (err) {
        setError("Error de conexión");
      } finally {
        setLoading(false);
      }
    };

    if (token) {
      fetchOrderLines();
    } else {
      setError("Debes iniciar sesión para ver esta orden");
      setLoading(false);
    }
  }, [id, token]);

  if (loading) {
    return <div className="text-center mt-5">Cargando...</div>;
  }

  if (error) {
    return <div className="alert alert-danger mt-5">{error}</div>;
  }

  const total = orderLines.reduce(
    (sum, line) => sum + line.price * line.quantity,
    0
  );

  return (
    <div className="container mt-4">
      <h2>Detalles del pedido #{id}</h2>

      <div className="table-responsive mt-3">
        <table className="table table-striped table-hover">
          <thead>
            <tr>
              <th>Carta</th>
              <th>Set</th>
              <th>Idioma</th>
              <th>Condición</th>
              <th>Vendedor</th>
              <th>Cantidad</th>
              <th>Precio unitario</th>
              <th>Subtotal</th>
            </tr>
          </thead>
          <tbody>
            {orderLines.map((line) => (
              <tr key={line.id}>
                <td>
                  <Link to={`/cards/${line.cardId}`} className="text-decoration-none">
                    {line.card}
                  </Link>
                </td>
                <td>{line.cardSetName}</td>
                <td>{line.language}</td>
                <td>{line.condition}</td>
                <td>
                  <Link to={`/profile/${line.sellerId}`} className="text-decoration-none">
                    {line.sellerName}
                  </Link>
                </td>
                <td>{line.quantity}</td>
                <td>${line.price}</td>
                <td>${(line.price * line.quantity).toFixed(2)}</td>
              </tr>
            ))}
          </tbody>
          <tfoot>
            <tr>
              <th colSpan="7" className="text-end">
                Total
              </th>
              <th>${total.toFixed(2)}</th>
            </tr>
          </tfoot>
        </table>
      </div>
      {rating !== null ? (
        <div className="card p-4 mt-4 shadow-sm">
          <h4 className="mb-3 text-center">Tu valoración</h4>
          <p><strong>Puntuación:</strong> {rating} / 5</p>
          <p><strong>Comentario:</strong> {review || "Sin comentario"}</p>
        </div>
      ) : (
        <ReviewForm orderTableId={id} buyerId={localStorage.getItem("userId")} />
      )}
      <button className="btn btn-secondary mt-3" onClick={() => navigate(-1)}>
        Volver
      </button>
    </div>
  );
};

export default OrderDetails;

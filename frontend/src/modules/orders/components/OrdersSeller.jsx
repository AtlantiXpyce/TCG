import { useState, useEffect } from "react";
import { Link, useNavigate } from "react-router-dom";

const OrdersSeller = () => {
  const [orders, setOrders] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const navigate = useNavigate();
  const token = localStorage.getItem("token");

  const fetchOrders = async () => {
    try {
      setLoading(true);
      const response = await fetch("http://localhost:8080/orders/seller", {
        headers: { Authorization: `Bearer ${token}` },
      });

      if (!response.ok) {
        setError("Error al cargar los pedidos de tus ventas");
        setLoading(false);
        return;
      }

      const data = await response.json();
      setOrders(data);
    } catch (err) {
      setError("Error de conexión");
    } finally {
      setLoading(false);
    }
  };

  const handleMarkAsSent = async (orderId) => {
    const confirm = window.confirm("¿Seguro que quieres marcar este pedido como enviado?");
    if (!confirm) return;

    try {
      const response = await fetch(`http://localhost:8080/orders/sent?orderId=${orderId}`, {
        method: "POST",
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });

      if (response.ok) {
        const message = await response.text();
        alert(message);
        fetchOrders();
      } else {
        const errorMsg = await response.text();
        alert("Error al enviar pedido: " + errorMsg);
      }
    } catch (err) {
      alert("Error de conexión");
    }
  };

  useEffect(() => {
    if (token) {
      fetchOrders();
    } else {
      setError("Debes iniciar sesión para ver tus ventas");
      setLoading(false);
    }
  }, [token]);

  if (loading) {
    return (
      <div className="d-flex vh-100 align-items-center justify-content-center bg-light">
        <div>Cargando pedidos...</div>
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
        <h2 className="mb-4 text-center">Pedidos de mis ventas</h2>
        {orders.length === 0 ? (
          <div className="alert alert-info text-center">No tienes pedidos como vendedor</div>
        ) : (
          <div className="table-responsive">
            <table className="table table-striped table-hover">
              <thead>
                <tr>
                  <th>ID Pedido</th>
                  <th>Fecha de pedido</th>
                  <th>Fecha de llegada</th>
                  <th>Valoración</th>
                  <th>Reseña</th>
                  <th>Enviado?</th>
                  <th>Comprador</th>
                  <th>Acciones</th>
                </tr>
              </thead>
              <tbody>
                {orders.map((order) => (
                  <tr key={order.id}>
                    <td>
                      <Link
                        to={`/order/${order.id}`}
                        state={{ rating: order.rating, review: order.review }}
                        className="text-decoration-none"
                      >
                        {order.id}
                      </Link>
                    </td>
                    <td>{order.orderDate ? new Date(order.orderDate).toLocaleString() : "-"}</td>
                    <td>{order.arrivalDate ? new Date(order.arrivalDate).toLocaleDateString() : "-"}</td>
                    <td>{order.rating ?? "-"}</td>
                    <td>{order.review || "-"}</td>
                    <td>{order.isSent ? "Sí" : "No"}</td>
                    <td>
                      <Link
                        to={`/profile/${order.buyerId}`}
                        className="text-decoration-none"
                      >
                        {order.buyerName}
                      </Link>
                    </td>
                    <td>
                      {!order.isSent && (
                        <button
                          className="btn btn-sm btn-success"
                          onClick={() => handleMarkAsSent(order.id)}
                        >
                          Marcar como enviado
                        </button>
                      )}
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        )}
      </div>
    </div>
  );
};

export default OrdersSeller;

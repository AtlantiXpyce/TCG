import { useState, useEffect } from "react";
import { Link, useNavigate } from "react-router-dom";

const OrdersBuyer = () => {
  const [orders, setOrders] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const navigate = useNavigate();
  const token = localStorage.getItem("token");

  const fetchOrders = async () => {
    try {
      setLoading(true);
      const response = await fetch("http://localhost:8080/orders", {
        headers: { Authorization: `Bearer ${token}` },
      });

      if (!response.ok) {
        setError("Error al cargar los pedidos");
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

  useEffect(() => {
    if (token) {
      fetchOrders();
    } else {
      setError("Debes iniciar sesión para ver tus pedidos");
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
        <h2 className="mb-4 text-center">Mis pedidos</h2>
        {orders.length === 0 ? (
          <div className="alert alert-info text-center">No tienes pedidos realizados</div>
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
                  <th>Vendedor</th>
                </tr>
              </thead>
              <tbody>
                {orders.map((order) => (
                  <tr key={order.id}>
                    <td>
                        <Link to={`/order/${order.id}`} state={{rating: order.rating, review: order.review}} className="text-decoration-none">
                            {order.id}
                        </Link>
                    </td>
                    <td>{order.orderDate ? new Date(order.orderDate).toLocaleString() : "-"}</td>
                    <td>{order.arrivalDate ? new Date(order.arrivalDate).toLocaleDateString() : "-"}</td>
                    <td>{order.rating ?? "-"}</td>
                    <td>{order.review || "-"}</td>
                    <td>{order.isSent ? "Sí" : "No"}</td>
                    <td>
                      <Link to={`/profile/${order.sellerId}`} state={{rating: order.rating, review: order.review, isSeller: order.isSeller}} className="text-decoration-none">
                            {order.sellerName}
                        </Link>
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

export default OrdersBuyer;

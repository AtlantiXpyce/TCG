import { useState, useEffect } from "react";
import { useNavigate, Link } from "react-router-dom";

const CartPage = () => {
  const [cart, setCart] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const navigate = useNavigate();
  const token = localStorage.getItem("token");

  const fetchCart = async () => {
    try {
      setLoading(true);
      const response = await fetch("http://localhost:8080/cart", {
        headers: { Authorization: `Bearer ${token}` },
      });

      if (!response.ok) {
        setError("Error al cargar el carrito");
        setLoading(false);
        return;
      }

      const data = await response.json();
      setCart(data);
    } catch (err) {
      setError("Error de conexión");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    if (token) {
      fetchCart();
    } else {
      setError("Debes iniciar sesión para ver el carrito");
      setLoading(false);
    }
  }, [token]);

  const handleDelete = async (cartId) => {
    try {
      await fetch("http://localhost:8080/cart/delete", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify(cartId),
      });
      fetchCart();
    } catch (err) {
      console.error("Error eliminando del carrito", err);
    }
  };

  const handleBuy = async () => {
    try {
      const response = await fetch("http://localhost:8080/cart/buy", {
        method: "POST",
        headers: { Authorization: `Bearer ${token}` },
      });

      if (!response.ok) {
        alert("Error al comprar el carrito");
        return;
      }

      alert("Compra efectuada correctamente");
      const orderLinesDto = await response.json();
      setCart([]);
      navigate("/orderSummary", { state: { orderLines: orderLinesDto } });

    } catch (err) {
      console.error("Error en la compra", err);
    }
  };

  if (loading) {
    return (
      <div className="d-flex vh-100 align-items-center justify-content-center bg-light">
        <div>Cargando carrito...</div>
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
        <h2 className="mb-4 text-center">Carrito de compra</h2>
        {cart.length === 0 ? (
          <div className="alert alert-info text-center">Tu carrito está vacío</div>
        ) : (
          <>
            <div className="table-responsive">
              <table className="table table-striped table-hover">
                <thead>
                  <tr>
                    <th>Nombre</th>
                    <th>Set</th>
                    <th>TCG</th>
                    <th>Idioma</th>
                    <th>Condición</th>
                    <th>Cantidad</th>
                    <th>Precio</th>
                    <th>Vendedor</th>
                    <th></th>
                  </tr>
                </thead>
                <tbody>
                  {cart.map((item) => (
                    <tr key={item.cartId}>
                      <td>{item.cardName}</td>
                      <td>{item.cardSetName}</td>
                      <td>{item.tcgName}</td>
                      <td>{item.language}</td>
                      <td>{item.condition}</td>
                      <td>{item.quantity}</td>
                      <td>${item.price}</td>
                      <td>
                        <Link to={`/profile/${item.sellerId}`} className="text-decoration-none">
                            {item.sellerName}
                        </Link>
                      </td>
                      <td>
                        <button
                          className="btn btn-sm btn-danger"
                          onClick={() => handleDelete(item.cartId)}
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
              <h5>
                Total: $
                {cart
                  .reduce(
                    (acc, item) =>
                      acc + parseFloat(item.price) * item.quantity,
                    0
                  )
                  .toFixed(2)}
              </h5>
              <button className="btn btn-success" onClick={handleBuy}>
                Comprar carrito
              </button>
            </div>
          </>
        )}
      </div>
    </div>
  );
};

export default CartPage;

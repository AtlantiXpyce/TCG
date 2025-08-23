import { useState } from "react";

const AddToCartButton = ({ cardStockId, quantity }) => {
  const [loading, setLoading] = useState(false);
  const token = localStorage.getItem("token");

  const handleAdd = async () => {
    if (!token) {
      alert("Debes iniciar sesión para añadir al carrito");
      return;
    }

    try {
      setLoading(true);
      const response = await fetch("http://localhost:8080/cart", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify({ cardStockId, quantity }),
      });

      if (!response.ok) {
        if (response.status === 400) {
          alert("Cantidad no disponible");
        } else {
          alert("Error al añadir al carrito");
        }
        return;
      }

      alert("Carta añadida al carrito");
      window.location.reload();

    } catch (err) {
      console.error("Error añadiendo al carrito", err);
      alert("Error de conexión");
    } finally {
      setLoading(false);
    }
  };

  return (
    <button
      className="btn btn-sm btn-primary"
      onClick={handleAdd}
      disabled={loading}
    >
      {loading ? "Añadiendo..." : "Añadir al carrito"}
    </button>
  );
};

export default AddToCartButton;

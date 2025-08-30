import { useState } from "react";
import AddToCartButton from "../../cart/components/AddToCartButton";
import { Link } from "react-router-dom";

const StockTable = ({ stock, setStock }) => {
  const [quantities, setQuantities] = useState({});
  const role = localStorage.getItem("role");
  const token = localStorage.getItem("token");

  const handleChange = (stockId, value) => {
    setQuantities((prev) => ({
      ...prev,
      [stockId]: value,
    }));
  };

  const handleDelete = async (stockId) => {
  if (!token) {
    alert("Debes iniciar sesión para eliminar stock");
    return;
  }

  if (!window.confirm("¿Seguro que quieres eliminar este stock?")) return;

  try {
    const response = await fetch(
      `http://localhost:8080/admin/deleteStock?stockId=${stockId}`,
      {
        method: "POST",
        headers: {
          "Authorization": `Bearer ${token}`,
        },
      }
    );

    if (response.status >= 200 && response.status < 300) {
      setStock((prev) => prev.filter((s) => s.id !== stockId));
    } else if (response.status === 403) {
      alert("No tienes permisos para eliminar este stock");
    } else if (response.status === 404) {
      alert("Stock no encontrado");
    } else {
      alert(`Error al eliminar stock (HTTP ${response.status})`);
    }
  } catch (err) {
    console.error("Error en la petición deleteStock:", err);
    alert("Error de red al eliminar stock");
  }
};


  return (
    <div className="table-responsive">
      <table className="table table-striped table-hover w-100">
        <thead>
          <tr>
            <th>Idioma</th>
            <th>Condición</th>
            <th>Cantidad</th>
            <th>Precio</th>
            <th>Vendedor</th>
            <th className="text-center" style={{ minWidth: "130px" }}>
              Comprar
            </th>
            {(role === "ADMIN" || role === "STORE") && (
              <th className="text-center" style={{ minWidth: "120px" }}>
                Acciones
              </th>
            )}
          </tr>
        </thead>
        <tbody>
          {stock.map((s) => {
            const selectedQty = quantities[s.id] || 1;
            return (
              <tr key={s.id}>
                <td>{s.language}</td>
                <td>{s.condition}</td>
                <td>{s.quantity}</td>
                <td>€{s.price}</td>
                <td>
                  <Link
                    to={`/profile/${s.sellerId}`}
                    className="text-decoration-none"
                  >
                    {s.sellerUsername}
                  </Link>
                </td>
                <td className="d-flex align-items-center justify-content-center gap-2">
                  <select
                    className="form-select form-select-sm"
                    style={{ width: "70px" }}
                    value={selectedQty}
                    onChange={(e) =>
                      handleChange(s.id, Number(e.target.value))
                    }
                  >
                    {Array.from({ length: s.quantity }, (_, i) => i + 1).map(
                      (n) => (
                        <option key={n} value={n}>
                          {n}
                        </option>
                      )
                    )}
                  </select>
                  <AddToCartButton cardStockId={s.id} quantity={selectedQty} />
                </td>
                {(role === "ADMIN" || role === "STORE") && (
                  <td className="text-center">
                    <button
                      className="btn btn-sm btn-danger"
                      onClick={() => handleDelete(s.id)}
                    >
                      Eliminar
                    </button>
                  </td>
                )}
              </tr>
            );
          })}
        </tbody>
      </table>
    </div>

  );
};

export default StockTable;
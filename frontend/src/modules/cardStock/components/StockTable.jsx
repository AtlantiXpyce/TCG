import { useState } from "react";
import AddToCartButton from "../../cart/components/AddToCartButton";

const StockTable = ({ stock }) => {
  const [quantities, setQuantities] = useState({});

  const handleChange = (stockId, value) => {
    setQuantities((prev) => ({
      ...prev,
      [stockId]: value,
    }));
  };

  return (
    <div className="table-responsive">
      <table className="table table-striped table-hover">
        <thead>
          <tr>
            <th>Idioma</th>
            <th>Condición</th>
            <th>Cantidad</th>
            <th>Precio</th>
            <th></th>
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
                <td>${s.price}</td>
                <td className="d-flex align-items-center gap-2">
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
              </tr>
            );
          })}
        </tbody>
      </table>
    </div>
  );
};

export default StockTable;

import { useLocation, useNavigate, Link } from "react-router-dom";

const OrderSummaryPage = () => {
  const location = useLocation();
  const navigate = useNavigate();
  const orderLines = location.state?.orderLines || [];

  const total = orderLines.reduce(
    (acc, item) => acc + parseFloat(item.price) * item.quantity,
    0
  );

  return (
    <div style={{ paddingTop: "60px", paddingBottom: "50px" }}>
      <div className="container">
        <h2 className="mb-4 text-center">Resumen del pedido</h2>

        {orderLines.length === 0 ? (
          <div className="alert alert-info text-center">No hay líneas de pedido</div>
        ) : (
          <>
            <div className="table-responsive">
              <table className="table table-striped table-hover">
                <thead>
                  <tr>
                    <th>Nombre</th>
                    <th>TCG</th>
                    <th>Set</th>
                    <th>Idioma</th>
                    <th>Condición</th>
                    <th>Cantidad</th>
                    <th>Precio</th>
                  </tr>
                </thead>
                <tbody>
                  {orderLines.map((item) => (
                    <tr key={item.id}>
                      <td>
                        <Link to={`/cards/${item.cardId}`} className="text-decoration-none">
                            {item.card}
                        </Link>
                      </td>
                      <td>{item.tcgName}</td>
                      <td>{item.cardSetName}</td>
                      <td>{item.language}</td>
                      <td>{item.condition}</td>
                      <td>{item.quantity}</td>
                      <td>€{parseFloat(item.price).toFixed(2)}</td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>

            <div className="d-flex justify-content-end align-items-center mt-4">
              <h5>Total: ${total.toFixed(2)}</h5>
            </div>

            <div className="text-center mt-4">
              <button className="btn btn-primary" onClick={() => navigate("/")}>
                Volver al inicio
              </button>
            </div>
          </>
        )}
      </div>
    </div>
  );
};

export default OrderSummaryPage;

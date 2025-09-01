import { useState } from "react";
import { useNavigate } from "react-router-dom";

const ReviewForm = ({ orderTableId, buyerId }) => {
  const [rating, setRating] = useState("");
  const [review, setReview] = useState("");
  const [message, setMessage] = useState(null);
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setMessage(null);

    try {
      const token = localStorage.getItem("token");

      const response = await fetch("http://localhost:8080/orders/review", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify({
          orderTableId,
          rating: Number(rating),
          review,
          buyerId,
        }),
      });

      if (response.ok) {
        setMessage({ type: "success", text: "Pedido confirmado y evaluado" });
        setRating("");
        setReview("");
        navigate("/ordersBuyer");

      } else {
        const err = await response.text();
        setMessage({ type: "danger", text: `Error: ${err}` });
      }
    } catch (error) {
      setMessage({ type: "danger", text: "Error de conexión con el servidor" });
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="mt-5">
      <h3 className="mb-3 text-center">Confirmar y valorar pedido</h3>

      {message && (
        <div className={`alert alert-${message.type} text-center`}>
          {message.text}
        </div>
      )}

      <form onSubmit={handleSubmit} className="card p-4 shadow-sm">
        <div className="mb-3">
          <label className="form-label">Puntuación (0 - 5)</label>
          <select
            className="form-select"
            value={rating}
            onChange={(e) => setRating(e.target.value)}
            required
          >
            <option value="">Selecciona...</option>
            {[0, 1, 2, 3, 4, 5].map((r) => (
              <option key={r} value={r}>
                {r}
              </option>
            ))}
          </select>
        </div>

        <div className="mb-3">
          <label className="form-label">Comentario (opcional)</label>
          <textarea
            className="form-control"
            rows="3"
            maxLength="100"
            value={review}
            onChange={(e) => setReview(e.target.value)}
          />
          <div className="form-text">{review.length}/100 caracteres</div>
        </div>

        <div className="text-center">
          <button type="submit" className="btn btn-success" disabled={loading}>
            {loading ? "Enviando..." : "Enviar valoración"}
          </button>
        </div>
      </form>
    </div>
  );
};

export default ReviewForm;

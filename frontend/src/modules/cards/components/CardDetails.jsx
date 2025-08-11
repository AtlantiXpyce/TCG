import React, { useState, useEffect } from "react";
import { useParams, useNavigate } from "react-router-dom";

const CardDetails = () => {
  const { id } = useParams();
  const navigate = useNavigate();

  const [card, setCard] = useState(null);
  const [error, setError] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchCard = async () => {
      try {
        setLoading(true);
        const response = await fetch(`http://localhost:8080/cards/${id}`);

        if (!response.ok) {
          if (response.status === 404) {
            setError("Carta no encontrada");
          } else {
            setError("Error al cargar la carta");
          }
          setLoading(false);
          return;
        }

        const data = await response.json();
        setCard(data);
      } catch (err) {
        setError("Error de conexión");
      } finally {
        setLoading(false);
      }
    };

    fetchCard();
  }, [id]);

  if (loading) {
    return (
      <div className="d-flex vh-100 align-items-center justify-content-center bg-light">
        <div>Cargando...</div>
      </div>
    );
  }

  if (error) {
    return (
      <div className="d-flex flex-column vh-100 align-items-center justify-content-center bg-light">
        <div className="alert alert-danger">{error}</div>
        <button className="btn btn-secondary mt-3" onClick={() => navigate(-1)}>
          Volver
        </button>
      </div>
    );
  }

  if (!card) {
    return null;
  }

  const imageSrc = `data:image/png;base64,${card.imageBase64}`;

  return (
    <div className="d-flex vh-100 align-items-center justify-content-center bg-light p-3">
      <div className="card shadow-sm" style={{ maxWidth: 600, width: "100%" }}>
        <div className="card-body p-4">
          <h3 className="card-title text-center mb-4">{card.name}</h3>
          <div className="d-flex flex-column align-items-center">
            <img
              src={imageSrc}
              alt={card.name}
              style={{ maxWidth: "100%", borderRadius: 8 }}
              className="mb-4"
            />
            <div className="w-100">
              <p><strong>Nombre TCG:</strong> {card.tcgName}</p>
              <p><strong>Número de carta:</strong> {card.cardNumber}</p>
              <p><strong>Variante de arte:</strong> {card.artVariant}</p>
              <p><strong>Nombre del set:</strong> {card.setName}</p>
              <p><strong>Rareza:</strong> {card.rarityName}</p>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default CardDetails;

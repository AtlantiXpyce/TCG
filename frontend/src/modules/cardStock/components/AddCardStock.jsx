import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";

const AddCardStock = () => {
  const { cardId } = useParams();
  const [languageId, setLanguageId] = useState("");
  const [price, setPrice] = useState("");
  const [quantity, setQuantity] = useState(1);
  const [conditionId, setConditionId] = useState("");
  const [comment, setComment] = useState("");

  const [languages, setLanguages] = useState([]);
  const [conditions, setConditions] = useState([]);
  const [message, setMessage] = useState("");

  useEffect(() => {
    fetch("http://localhost:8080/cardStock/languages")
      .then(res => res.json())
      .then(data => setLanguages(data))
      .catch(err => console.error(err));

    fetch("http://localhost:8080/cardStock/conditions")
      .then(res => res.json())
      .then(data => setConditions(data))
      .catch(err => console.error(err));
  }, []);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setMessage("");

    const token = localStorage.getItem("token");
    if (!token) {
      setMessage("No estás autenticado.");
      return;
    }

    const dto = {
      cardId,
      languageId,
      price,
      quantity,
      conditionId,
      comment,
    };

    try {
      const res = await fetch("http://localhost:8080/cardStock/add", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          "Authorization": `Bearer ${token}`,
        },
        body: JSON.stringify(dto),
      });

      if (res.ok) {
        setMessage("Carta añadida al stock correctamente.");
        setLanguageId("");
        setPrice("");
        setQuantity(1);
        setConditionId("");
        setComment("");
      } else {
        const error = await res.text();
        setMessage(`Error: ${error}`);
      }
    } catch (err) {
      setMessage("Error con el servidor.");
    }
  };

  return (
    <div className="container py-4">
      <div className="card shadow-sm">
        <div className="card-body">
          <h3 className="card-title mb-4">Añadir carta al stock</h3>
          <form onSubmit={handleSubmit} className="row g-3">
            <div className="col-md-4">
              <label className="form-label">Idioma</label>
              <select
                className="form-select"
                value={languageId}
                onChange={(e) => setLanguageId(e.target.value)}
                required
              >
                <option value="">Seleccione un idioma</option>
                {languages.map(l => (
                  <option key={l.id} value={l.id}>{l.name}</option>
                ))}
              </select>
            </div>

            <div className="col-md-4">
              <label className="form-label">Condición</label>
              <select
                className="form-select"
                value={conditionId}
                onChange={(e) => setConditionId(e.target.value)}
                required
              >
                <option value="">Seleccione una condición</option>
                {conditions.map(cond => (
                  <option key={cond.id} value={cond.id}>{cond.name}</option>
                ))}
              </select>
            </div>

            <div className="col-md-4">
              <label className="form-label">Precio (€)</label>
              <input
                type="number"
                step="0.01"
                className="form-control"
                value={price}
                onChange={(e) => setPrice(e.target.value)}
                required
              />
            </div>

            <div className="col-md-4">
              <label className="form-label">Cantidad</label>
              <input
                type="number"
                className="form-control"
                value={quantity}
                onChange={(e) => setQuantity(e.target.value)}
                min="1"
                required
              />
            </div>

            <div className="col-md-8">
              <label className="form-label">Comentario</label>
              <input
                type="text"
                className="form-control"
                value={comment}
                onChange={(e) => setComment(e.target.value)}
                placeholder="Opcional"
              />
            </div>

            <div className="col-12">
              <button type="submit" className="btn btn-primary w-100">
                Guardar en stock
              </button>
            </div>
            {message && <div className="alert alert-info">{message}</div>}
          </form>
        </div>
      </div>
    </div>
  );
};

export default AddCardStock;
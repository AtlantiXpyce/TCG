import { useState, useEffect } from "react";
import { useParams, useNavigate } from "react-router-dom";
import StockTable from "../../cardStock/components/StockTable"; 

const CardDetails = () => {
  const { id } = useParams();
  const navigate = useNavigate();

  const [card, setCard] = useState(null);
  const [error, setError] = useState(null);
  const [loading, setLoading] = useState(true);

  const [stock, setStock] = useState([]);
  const [languages, setLanguages] = useState([]);
  const [selectedLanguage, setSelectedLanguage] = useState("");
  const [page, setPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);

  const token = localStorage.getItem("token");

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

  useEffect(() => {
    const fetchLanguages = async () => {
      try {
        const response = await fetch(`http://localhost:8080/cardStock/languages`);
        const data = await response.json();
        setLanguages(data);
      } catch (err) {
        console.error("Error cargando idiomas", err);
      }
    };
    fetchLanguages();
  }, []);

  useEffect(() => {
    if (token) fetchStock();
  }, [id, selectedLanguage, page, token]);

  const fetchStock = async () => {
    try {
      const url = new URL(`http://localhost:8080/cardStock/find`);
      url.searchParams.append("id", id);
      url.searchParams.append("page", page);
      if (selectedLanguage) url.searchParams.append("language", selectedLanguage);

      const response = await fetch(url, {
        headers: { Authorization: `Bearer ${token}` },
      });

      if (!response.ok) {
        console.error("Error al cargar el stock");
        return;
      }

      const data = await response.json();
      setStock(data.content);
      setTotalPages(data.totalPages);
    } catch (err) {
      console.error("Error al cargar stock", err);
    }
  };

  const handleLanguageChange = (e) => {
    setSelectedLanguage(e.target.value);
    setPage(0);
  };

  const handlePageChange = (newPage) => {
    setPage(newPage);
  };

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
  <div style={{paddingTop: '60px' ,paddingBottom: '50px' }}>
      <div className="d-flex align-items-center justify-content-center w-100">
      <div className="d-flex flex-column" style={{ maxWidth: 800, width: "100%" }}>
          <div className="card shadow-sm mb-4" style={{ maxWidth: 800, width: "100%" }}>
            <div className="card-body p-4">
              <h3 className="card-title text-center mb-4">{card.name}</h3>
              <div className="d-flex flex-column align-items-center">
              <img src={imageSrc} alt={card.name} style={{ maxWidth: "100%", borderRadius: 8 }} className="mb-4" />
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
          {token && (
            <div className="card shadow-sm p-4" style={{ maxWidth: 800, width: "100%" }}>
              <h5>Stock disponible</h5>

              <div className="mb-3">
              <label htmlFor="languageSelect" className="form-label">Filtrar por idioma:</label>
                <select
                  id="languageSelect"
                  className="form-select"
                  value={selectedLanguage}
                  onChange={handleLanguageChange}
                >
                  <option value="">Todos</option>
                {languages.map(lang => (
                  <option key={lang.id} value={lang.name}>{lang.name}</option>
                  ))}
                </select>
              </div>

              <StockTable stock={stock} setStock={setStock} />

            <div className="d-flex justify-content-between">
                <button
                  className="btn btn-outline-secondary"
                  onClick={() => handlePageChange(page - 1)}
                  disabled={page === 0}
                >
                  Anterior
                </button>
              <span>Página {page + 1} de {totalPages}</span>
                <button
                  className="btn btn-outline-secondary"
                  onClick={() => handlePageChange(page + 1)}
                  disabled={page + 1 >= totalPages}
                >
                  Siguiente
                </button>
              </div>
            </div>
          )}
        </div>
      </div>
    </div>
  );
};

export default CardDetails;

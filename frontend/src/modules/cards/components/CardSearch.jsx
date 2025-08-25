import { useEffect, useState } from "react";
import { Link } from "react-router-dom";

const CardSearch = () => {
  const [name, setName] = useState("");
  const [setId, setSetId] = useState("");
  const [tcgId, setTcgId] = useState("");
  const [tcgs, setTcgs] = useState([]);
  const [sets, setSets] = useState([]);
  const [cards, setCards] = useState([]);
  const [page, setPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);
  const isStore = localStorage.getItem("isStore");

  useEffect(() => {

    fetch("http://localhost:8080/cards/tcgs")
      .then(res => res.json())
      .then(data => setTcgs(data))
      .catch(err => console.error(err));

    fetch("http://localhost:8080/cards/sets")
      .then(res => res.json())
      .then(data => setSets(data))
      .catch(err => console.error(err));
  }, []);

  const searchCards = (pageNum = 0) => {
    const params = new URLSearchParams();
    if (name) params.append("name", name);
    if (setId) params.append("setId", setId);
    if (tcgId) params.append("tcgId", tcgId);
    params.append("page", pageNum);

    fetch(`http://localhost:8080/cards/search?${params.toString()}`)
      .then(res => res.json())
      .then(data => {
        setCards(data.content || []);
        setTotalPages(data.totalPages || 0);
        setPage(data.number || 0);
      })
      .catch(err => console.error(err));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    searchCards(0);
  };

  return (
    <div className="container py-4">
        <div className="card shadow-sm">
            <div className="card-body">
                <h3 className="card-title mb-4">Buscar cartas</h3>
                <form onSubmit={handleSubmit} className="row g-3 align-items-end">
                    <div className="col-md-4">
                        <label className="form-label">Nombre</label>
                        <input
                            type="text"
                            className="form-control"
                            value={name}
                            onChange={(e) => setName(e.target.value)}
                            placeholder="Ej: Charizard"
                        />
                    </div>
                    <div className="col-md-3">
                        <label className="form-label">TCG</label>
                        <select
                            className="form-select"
                            value={tcgId}
                            onChange={(e) => setTcgId(e.target.value)}
                        >
                            <option value="">Todos</option>
                            {tcgs.map(tcg => (
                            <option key={tcg.id} value={tcg.id}>{tcg.name}</option>
                            ))}
                        </select>
                    </div>
                    <div className="col-md-3">
                        <label className="form-label">Set</label>
                        <select
                            className="form-select"
                            value={setId}
                            onChange={(e) => setSetId(e.target.value)}
                        >
                        <option value="">Todos</option>
                        {sets.map(set => (
                        <option key={set.id} value={set.id}>{set.name}</option>
                        ))}
                        </select>
                    </div>
                    <div className="col-md-2">
                        <button type="submit" className="btn btn-primary w-100">Buscar</button>
                    </div>
                </form>
            </div>
      </div>

      <div className="mt-4">
        {cards.length > 0 ? (
          <>
            <table className="table table-striped">
              <thead>
                <tr>
                  <th>Nombre</th>
                  <th>Set</th>
                  <th>Numero de carta</th>
                  <th>Rareza</th>
                  <th>TCG</th>
                  <th>Estilo</th>
                  {isStore && <th>Acciones</th>}
                </tr>
              </thead>
              <tbody>
                {cards.map(card => (
                  <tr key={card.id}>
                    <td>
                        <Link to={`/cards/${card.id}`} className="text-decoration-none">
                            {card.name}
                        </Link>
                    </td>
                    <td>{card.setName}</td>
                    <td>{card.cardNumber}</td>
                    <td>{card.rarityName}</td>
                    <td>{card.tcgName}</td>
                    <td>{card.artVariant}</td>
                    {isStore && (
                      <td>
                        <Link to={`/sell/${card.id}`} className="btn btn-sm btn-success">
                          Vender
                        </Link>
                      </td>
                    )}
                  </tr>
                ))}
              </tbody>
            </table>

            <div className="d-flex justify-content-between">
              <button
                className="btn btn-outline-secondary"
                onClick={() => searchCards(page - 1)}
                disabled={page === 0}
              >
                Anterior
              </button>
              <span>Página {page + 1} de {totalPages}</span>
              <button
                className="btn btn-outline-secondary"
                onClick={() => searchCards(page + 1)}
                disabled={page + 1 >= totalPages}
              >
                Siguiente
              </button>
            </div>
          </>
        ) : (
          <p className="text-muted">No hay resultados</p>
        )}
      </div>
    </div>
  );
};

export default CardSearch;

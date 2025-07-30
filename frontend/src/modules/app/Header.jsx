function Header({ search, setSearch }) {
  return (
    <header className="bg-light p-3">
      <div className="container">
        <input
          type="text"
          className="form-control"
          placeholder="Buscar..."
          value={search}
          onChange={(e) => setSearch(e.target.value)}
        />
      </div>
    </header>
  );
}

export default Header;

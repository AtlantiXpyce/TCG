import { useState } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap/dist/js/bootstrap.bundle.min.js';

import Header from './modules/app/Header';
import Body from './modules/app/Body';
import Footer from './modules/app/Footer';

function App() {
  const [search, setSearch] = useState("");

  return (
    <>
      <Header search={search} setSearch={setSearch} />
      <Body />
      <Footer />
    </>
  );
}

export default App;

DROP TABLE IF EXISTS orderLine CASCADE;
DROP TABLE IF EXISTS orderTable CASCADE;
DROP TABLE IF EXISTS condition CASCADE;
DROP TABLE IF EXISTS cardLanguage CASCADE;
DROP TABLE IF EXISTS cart CASCADE;
DROP TABLE IF EXISTS cardStock CASCADE;
DROP TABLE IF EXISTS card CASCADE;
DROP TABLE IF EXISTS cardSet CASCADE;
DROP TABLE IF EXISTS rarity CASCADE;
DROP TABLE IF EXISTS tcg CASCADE;
DROP TABLE IF EXISTS locations CASCADE;
DROP TABLE IF EXISTS users CASCADE;

CREATE TABLE locations (
    id SERIAL PRIMARY KEY,
    latitude DOUBLE PRECISION NOT NULL,
    longitude DOUBLE PRECISION NOT NULL
);

CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    firstName VARCHAR(100) NOT NULL,
    lastName VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    isStore BOOLEAN NOT NULL DEFAULT FALSE,
    role VARCHAR(50) NOT NULL,
    locationid INT,
    FOREIGN KEY (locationid) REFERENCES locations(id)
);

CREATE TABLE tcg (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

CREATE TABLE cardSet (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    tcgId INT NOT NULL,
    amount INT NOT NULL,
    FOREIGN KEY (tcgId) REFERENCES tcg(id)
);

CREATE TABLE rarity (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    tcgId INT NOT NULL,
    FOREIGN KEY (tcgId) REFERENCES tcg(id)
);

CREATE TABLE condition (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    initial VARCHAR(5) NOT NULL
);

CREATE TABLE cardLanguage (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    code VARCHAR(5) NOT NULL
);

CREATE TABLE card (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    tcgid INT NOT NULL,
    setid INT NOT NULL,
    rarityid INT NOT NULL,
    cardNumber VARCHAR(20) NOT NULL,
    artVariant VARCHAR(100),
    imagen BYTEA,
    FOREIGN KEY (tcgid) REFERENCES tcg(id),
    FOREIGN KEY (setid) REFERENCES cardSet(id),
    FOREIGN KEY (rarityid) REFERENCES rarity(id)
);

CREATE TABLE cardStock (
    id SERIAL PRIMARY KEY,
    cardid INT NOT NULL,
    sellerid INT NOT NULL,
    price NUMERIC(10,2) NOT NULL,
    quantity INT NOT NULL,
    cartQuantity INT NOT NULL,
    conditionid INT NOT NULL,
    comment VARCHAR(100),
    lang INT NOT NULL,
    FOREIGN KEY (conditionid) REFERENCES condition(id),
    FOREIGN KEY (lang) REFERENCES cardLanguage(id),
    FOREIGN KEY (cardid) REFERENCES card(id),
    FOREIGN KEY (sellerid) REFERENCES users(id)
);

CREATE TABLE cart (
    id SERIAL PRIMARY KEY,
    cardStockid INT NOT NULL,
    quantity INT NOT NULL,
    userid INT NOT NULL,
    FOREIGN KEY (cardStockid) REFERENCES cardStock(id),
    FOREIGN KEY (userid) REFERENCES users(id)
);

CREATE TABLE orderTable (
    id SERIAL PRIMARY KEY,
    orderDate TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    rating INT,
    review VARCHAR(100),
    arrivalDate TIMESTAMP,
    buyerid INT NOT NULL,
    FOREIGN KEY (buyerid) REFERENCES users(id)
);

CREATE TABLE orderLine (
    id SERIAL PRIMARY KEY,
    orderid INT NOT NULL,
    cardid INT NOT NULL,
    language VARCHAR(50) NOT NULL,
    price NUMERIC(10,2) NOT NULL,
    quantity INT NOT NULL,
    condition VARCHAR(50) NOT NULL,
    sellerid INT NOT NULL,
    FOREIGN KEY (sellerid) REFERENCES users(id),
    FOREIGN KEY (orderid) REFERENCES orderTable(id),
    FOREIGN KEY (cardid) REFERENCES card(id)
);

INSERT INTO condition (name, initial) VALUES
('Perfecta','PF'),
('Buena','GD'),
('Usada','US'),
('Muy usada','VU'),
('Mala','BD');

INSERT INTO cardLanguage (name, code) VALUES
('Español', 'es'),
('Inglés', 'en'),
('Francés', 'fr');

INSERT INTO tcg (name) VALUES
('Pokémon'),
('Magic: The Gathering');

INSERT INTO cardSet (name, tcgid, amount) VALUES
('Base Set', 1, 102),
('Jungle', 1, 64),
('Alpha', 2, 295),
('Aetherdrift', 2, 291);

INSERT INTO rarity (name, tcgid) VALUES
('Common', 1),
('Uncommon', 1),
('Holo Rare', 1),
('Common', 2),
('Uncommon', 2),
('Rare', 2),
('Mythic Rare', 2);

INSERT INTO card (name, tcgid, setid, rarityid, cardNumber, artVariant, imagen) VALUES
('Pikachu', 1, 1, 1, '58', 'Base', null),
('Charizard', 1, 1, 3, '4', 'Base', null),
('Blastoise', 1, 1, 3, '2', 'Base', null),
('Venusaur', 1, 1, 3, '15', 'Base', null),
('Raichu', 1, 1, 2, '14', 'Base', null),
('Machamp', 1, 1, 2, '8', 'Base', null),
('Gyarados', 1, 1, 3, '6', 'Base', null),
('Vaporeon', 1, 2, 1, '12', 'Base', null),
('Snorlax', 1, 2, 3, '11', 'Base', null),
('Flareon', 1, 2, 3, '3', 'Base', null),
('Scyther', 1, 2, 2, '10', 'Base', null),
('Pidgeot', 1, 2, 3, '8', 'Base', null),
('Black lotus', 2, 3, 6, '50', 'Base', null),
('Ancestral Recall', 2, 3, 6, '23', 'Base', null),
('Cursed Land', 2, 3, 5, '84', 'Base', null),
('The Aetherspark', 2, 4, 7, '231', 'Base', null),
('Chandra, Spark Hunter', 2, 4, 7, '411', 'Fracture Foil', null),
('Chandra, Spark Hunter ', 2, 4, 7, '401', 'Foil', null);

INSERT INTO locations (latitude, longitude) VALUES
(40.4168, -3.7038),
(41.3874, 2.1686);

INSERT INTO users (username, firstName, lastName, email, password, isStore, role, locationid) VALUES
('adminS', 'Laura', 'Gómez', 'admin_store@example.com',
'$2b$12$hoQ6RI8v8JtFWTOkWMXu7uaZuOTQpmUMT5j/rY5zjDgC2xG.Prs4m', TRUE, 'ADMIN', 1),
('adminU', 'Carlos', 'Pérez', 'admin_user@example.com',
'$2b$12$hoQ6RI8v8JtFWTOkWMXu7uaZuOTQpmUMT5j/rY5zjDgC2xG.Prs4m', FALSE, 'ADMIN', NULL),
('userS', 'María', 'López', 'store_user@example.com',
'$2b$12$hoQ6RI8v8JtFWTOkWMXu7uaZuOTQpmUMT5j/rY5zjDgC2xG.Prs4m', TRUE, 'USER', 2),
('userU', 'José', 'Martínez', 'normal_user@example.com',
'$2b$12$hoQ6RI8v8JtFWTOkWMXu7uaZuOTQpmUMT5j/rY5zjDgC2xG.Prs4m', FALSE, 'USER', NULL);

INSERT INTO cardStock (cardid, sellerid, price, quantity, conditionid, comment, lang, cartQuantity) VALUES
(14, (1), 5.01, 10, 1, 'Como nueva', 1, 0),
(14, (1), 4.50, 8, 2, 'Ligero desgaste', 1, 0),
(14, (1), 3.20, 15, 3, 'Usada pero jugable', 2, 0),
(14, (1), 6.00, 5, 1, 'Edición en inglés impecable', 2, 0),
(14, (1), 2.50, 12, 4, 'Muy jugada', 3, 0),
(14, (1), 7.00, 4, 1, 'Rara de conseguir', 3, 0),
(14, (1), 3.80, 6, 2, 'Buena calidad general', 1, 0),
(14, (1), 1.50, 20, 5, 'Estado malo', 2, 0),
(14, (3), 5.20, 9, 1, 'Perfecta en español', 1, 0),
(14, (3), 4.80, 7, 2, 'Buena, poco uso', 1, 0),
(14, (3), 3.10, 10, 3, 'Bastante jugada', 2, 0),
(14, (3), 6.50, 3, 1, 'Inglés casi perfecta', 2, 0),
(14, (3), 2.80, 11, 4, 'Muy usada', 3, 0),
(14, (3), 7.20, 2, 1, 'Edición difícil', 3, 0),
(14, (3), 3.60, 5, 2, 'Estado decente', 1, 0);


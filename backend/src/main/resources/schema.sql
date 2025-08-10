DROP TABLE IF EXISTS orderLine CASCADE;
DROP TABLE IF EXISTS orderTable CASCADE;
DROP TABLE IF EXISTS cart CASCADE;
DROP TABLE IF EXISTS cardStock CASCADE;
DROP TABLE IF EXISTS card CASCADE;
DROP TABLE IF EXISTS cardSet CASCADE;
DROP TABLE IF EXISTS rarity CASCADE;
DROP TABLE IF EXISTS tcg CASCADE;
DROP TABLE IF EXISTS location CASCADE;
DROP TABLE IF EXISTS users CASCADE;

CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    firstName VARCHAR(100) NOT NULL,
    lastName VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    isStore BOOLEAN NOT NULL DEFAULT FALSE,
    role VARCHAR(50) NOT NULL
);

CREATE TABLE location (
    id SERIAL PRIMARY KEY,
    latitude DOUBLE PRECISION NOT NULL,
    longitude DOUBLE PRECISION NOT NULL
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

CREATE TABLE card (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    tcgId INT NOT NULL,
    setId INT NOT NULL,
    rarityId INT NOT NULL,
    cardNumber VARCHAR(20) NOT NULL,
    artVariant VARCHAR(100),
    imagen BYTEA,
    FOREIGN KEY (tcgId) REFERENCES tcg(id),
    FOREIGN KEY (setId) REFERENCES cardSet(id),
    FOREIGN KEY (rarityId) REFERENCES rarity(id)
);

CREATE TABLE cardStock (
    id SERIAL PRIMARY KEY,
    cardId INT NOT NULL,
    sellerId INT NOT NULL,
    language VARCHAR(50) NOT NULL,
    price NUMERIC(10,2) NOT NULL,
    quantity INT NOT NULL,
    condition VARCHAR(50) NOT NULL,
    comment VARCHAR(100),
    FOREIGN KEY (cardId) REFERENCES card(id),
    FOREIGN KEY (sellerId) REFERENCES users(id)
);

CREATE TABLE cart (
    id SERIAL PRIMARY KEY,
    cardStockId INT NOT NULL,
    quantity INT NOT NULL,
    userId INT NOT NULL,
    FOREIGN KEY (cardStockId) REFERENCES cardStock(id),
    FOREIGN KEY (userId) REFERENCES users(id)
);

CREATE TABLE orderTable (
    id SERIAL PRIMARY KEY,
    orderDate TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    rating INT,
    review VARCHAR(100),
    arrivalDate TIMESTAMP
);

CREATE TABLE orderLine (
    id SERIAL PRIMARY KEY,
    orderId INT NOT NULL,
    cardId INT NOT NULL,
    language VARCHAR(50) NOT NULL,
    price NUMERIC(10,2) NOT NULL,
    quantity INT NOT NULL,
    condition VARCHAR(50) NOT NULL,
    FOREIGN KEY (orderId) REFERENCES orderTable(id),
    FOREIGN KEY (cardId) REFERENCES card(id)
);

INSERT INTO tcg (name) VALUES
('Pokémon'),
('Magic: The Gathering');

INSERT INTO cardSet (name, tcgId, amount) VALUES
('Base Set', 1, 102),
('Jungle', 1, 64),
('Alpha', 2, 295),
('Aetherdrift', 2, 291);

INSERT INTO rarity (name, tcgId) VALUES
('Common', 1),
('Uncommon', 1),
('Holo Rare', 1),
('Common', 2),
('Uncommon', 2),
('Rare', 2),
('Mythic Rare', 2);

INSERT INTO card (name, tcgId, setId, rarityId, cardNumber, artVariant, imagen) VALUES
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
('Pidgeot', 1, 2, 3, '8', 'Base', null);
DROP TABLE IF EXISTS orderLine;
DROP TABLE IF EXISTS orderTable;
DROP TABLE IF EXISTS cart;
DROP TABLE IF EXISTS cardStock;
DROP TABLE IF EXISTS card;
DROP TABLE IF EXISTS cardSet;
DROP TABLE IF EXISTS rarity;
DROP TABLE IF EXISTS tcg;
DROP TABLE IF EXISTS location;
DROP TABLE IF EXISTS "user";

CREATE TABLE "user" (
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
    FOREIGN KEY (sellerId) REFERENCES "user"(id)
);

CREATE TABLE cart (
    id SERIAL PRIMARY KEY,
    cardStockId INT NOT NULL,
    quantity INT NOT NULL,
    userId INT NOT NULL,
    FOREIGN KEY (cardStockId) REFERENCES cardStock(id),
    FOREIGN KEY (userId) REFERENCES "user"(id)
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

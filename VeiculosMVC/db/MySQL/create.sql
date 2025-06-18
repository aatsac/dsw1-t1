-- 1) Cria o banco e o seleciona
CREATE DATABASE IF NOT EXISTS Veiculos;
USE Veiculos;

-- 2) Usuário base
CREATE TABLE Usuario (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  email VARCHAR(255) NOT NULL UNIQUE,
  password VARCHAR(255) NOT NULL,
  nome VARCHAR(255) NOT NULL,
  papel VARCHAR(50) NOT NULL
);

-- 3) Cliente “herda” Usuário (1–1 via FK em id)
CREATE TABLE Cliente (
  id BIGINT PRIMARY KEY,
  cpf   CHAR(14)      NOT NULL UNIQUE,
  telefone VARCHAR(20),
  sexo ENUM('M','F','O') DEFAULT 'O',
  dataNascimento DATE,
  FOREIGN KEY (id) REFERENCES Usuario(id)
);

-- 4) Loja “herda” Usuário (1–1 via FK em id)
CREATE TABLE Loja (
  id BIGINT PRIMARY KEY,
  cnpj CHAR(18)      NOT NULL UNIQUE,
  descricao TEXT,
  FOREIGN KEY (id) REFERENCES Usuario(id)
);

-- 5) Veículo cadastrado pela Loja
CREATE TABLE Veiculo (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  idLoja BIGINT NOT NULL,
  placa    VARCHAR(10) NOT NULL UNIQUE,
  modelo   VARCHAR(100) NOT NULL,
  chassi   VARCHAR(17)  NOT NULL UNIQUE,
  ano      INT,
  quilometragem INT,
  descricao TEXT,
  valor    DECIMAL(15,2),
  fotos    JSON,
  FOREIGN KEY (idLoja)    REFERENCES Loja(id)
);

-- 6) Proposta cadastrada pelo Cliente
CREATE TABLE Proposta (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  valor         DECIMAL(15,2) NOT NULL,
  condicoesPgto TEXT,
  dataCompra    DATE NOT NULL,
  idVeiculo     BIGINT NOT NULL,
  idCliente     BIGINT NOT NULL,
  FOREIGN KEY (idVeiculo) REFERENCES Veiculo(id),
  FOREIGN KEY (idCliente) REFERENCES Cliente(id)
);

-- Insert CLIENTE user
INSERT INTO Usuario (email, password, nome, papel) VALUES
('fulano@email.com', '123456', 'Fulano de Tal', 'CLIENTE');
INSERT INTO Cliente (id, cpf, telefone, sexo, dataNascimento) VALUES
(LAST_INSERT_ID(), '123.456.789-01', '(11)98765-4321', 'M', '1990-01-01');

-- Insert LOJA user
INSERT INTO Usuario (email, password, nome, papel) VALUES
('ciclano@email.com', '123456', 'Ciclano de Tal', 'LOJA');
INSERT INTO Loja (id, cnpj, descricao) VALUES
(LAST_INSERT_ID(), '12.345.678/0001-95', 'Loja de Veículos Exemplo');

INSERT INTO Veiculo (idLoja, placa, modelo, chassi, ano, quilometragem, descricao, valor, fotos)
VALUES (LAST_INSERT_ID(), 'ABC1234', 'Fusca', '9BWZZZ377VT004251', 1980, 100000, 'Fusca em bom estado, motor revisado.', 12000.00, '["fotos/fusca1.jpg", "fotos/fusca2.jpg"]');

INSERT INTO Proposta (valor, condicoesPgto, dataCompra, idVeiculo, idCliente) VALUES
(14000.00, 'À vista', '2023-10-01', LAST_INSERT_ID(), LAST_INSERT_ID());

SELECT * FROM Usuario;
SELECT * FROM Cliente;
SELECT * FROM Loja;
SELECT * FROM Veiculo;
SELECT * FROM Proposta;

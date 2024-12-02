USE Farmacia;

-- Criar tabelas
CREATE TABLE Medicamentos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    fabricante VARCHAR(255),
    preco DECIMAL(10, 2) NOT NULL,
    estoque INT NOT NULL
);

CREATE TABLE Clientes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    cpf VARCHAR(11) NOT NULL UNIQUE,
    telefone VARCHAR(15),
    endereco TEXT
);

CREATE TABLE Vendas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    data_venda DATE NOT NULL,
    cliente_id INT,
    total DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (cliente_id) REFERENCES Clientes(id)
);

CREATE TABLE ItensVenda (
    id INT AUTO_INCREMENT PRIMARY KEY,
    venda_id INT,
    medicamento_id INT,
    quantidade INT NOT NULL,
    preco_unitario DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (venda_id) REFERENCES Vendas(id),
    FOREIGN KEY (medicamento_id) REFERENCES Medicamentos(id)
);

-- Inserir exemplos
INSERT INTO Medicamentos (nome, fabricante, preco, estoque) VALUES 
('Paracetamol', 'EMS', 5.50, 100),
('Dipirona', 'Medley', 3.75, 200),
('Amoxicilina', 'Neo Química', 15.90, 50),
('Ibuprofeno', 'Ache', 12.00, 150),
('Omeprazol', 'EMS', 8.00, 80);

INSERT INTO Clientes (nome, cpf, telefone, endereco) VALUES
('João Silva', '12345678901', '(11) 99999-9999', 'Rua A, 123'),
('Maria Oliveira', '98765432100', '(21) 98888-8888', 'Avenida B, 456');

INSERT INTO Vendas (data_venda, cliente_id, total) VALUES
('2024-12-01', 1, 50.75),
('2024-12-02', 2, 30.00);

INSERT INTO ItensVenda (venda_id, medicamento_id, quantidade, preco_unitario) VALUES
(1, 1, 5, 5.50),
(1, 2, 2, 3.75),
(2, 3, 1, 15.90);

-- Atualizar dados
UPDATE Medicamentos SET estoque = estoque - 5 WHERE id = 1;
UPDATE Clientes SET telefone = '(11) 99999-8888' WHERE id = 1;

-- Alterar tabelas
ALTER TABLE Medicamentos ADD COLUMN categoria VARCHAR(100);
ALTER TABLE Clientes MODIFY COLUMN endereco VARCHAR(500);

-- Deletar dados
DELETE FROM ItensVenda WHERE id = 1;
DELETE FROM Medicamentos WHERE id = 5;

-- Consultas
-- 1. Medicamentos com estoque baixo
SELECT * FROM Medicamentos WHERE estoque < 100;

-- 2. Vendas acima de um valor
SELECT * FROM Vendas WHERE total > 40 ORDER BY total DESC;

-- 3. Clientes e suas vendas
SELECT c.nome, v.data_venda, v.total
FROM Clientes c
JOIN Vendas v ON c.id = v.cliente_id
GROUP BY c.nome;

-- 4. Quantidade total de itens vendidos por medicamento
SELECT m.nome, SUM(iv.quantidade) AS total_vendido
FROM Medicamentos m
JOIN ItensVenda iv ON m.id = iv.medicamento_id
GROUP BY m.nome
HAVING total_vendido > 10;

-- 5. Venda com maior valor
SELECT MAX(total) AS maior_venda FROM Vendas;

-- 6. Média de preço dos medicamentos
SELECT AVG(preco) AS preco_medio FROM Medicamentos;

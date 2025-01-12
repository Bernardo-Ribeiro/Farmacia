CREATE DATABASE IF NOT EXISTS Farmacia;
USE Farmacia;


-- Criar tabelas
CREATE TABLE Clientes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    dni VARCHAR(20) NOT NULL UNIQUE,
    telefone VARCHAR(15),
    endereco TEXT,
    email VARCHAR(255)
);

CREATE TABLE ReceitasMedicas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    cliente_id INT NOT NULL,
    medico_nome VARCHAR(255) NOT NULL,
	data_emissao DATE NOT NULL,
    FOREIGN KEY (cliente_id) REFERENCES Clientes(id)
);
CREATE TABLE Medicamentos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    categoria ENUM('Analgesico', 'Antibiotico', 'Anti-inflamatorio', 'Antidepressivo') NOT NULL,
    estoque INT NOT NULL,
    preco DECIMAL(10, 2) NOT NULL,
    data_vencimento DATE NOT NULL,
    receita_obrigatoria BOOLEAN NOT NULL
);

CREATE TABLE MedicamentosReceitados (
    id INT AUTO_INCREMENT PRIMARY KEY,
    receita_id INT NOT NULL,
    medicamento_id INT NOT NULL,
    quantidade INT NOT NULL,
    FOREIGN KEY (receita_id) REFERENCES ReceitasMedicas(id),
    FOREIGN KEY (medicamento_id) REFERENCES Medicamentos(id)
);

CREATE TABLE Funcionarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    dni VARCHAR(20) NOT NULL UNIQUE,
    cargo ENUM('Farmaceutico', 'Caixa', 'Repositor') NOT NULL,
    salario DECIMAL(10, 2) NOT NULL
);

CREATE TABLE Fornecedores (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    contato VARCHAR(255),
    endereco TEXT
);

CREATE TABLE ProdutosFornecidos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    fornecedor_id INT NOT NULL,
    medicamento_id INT NOT NULL,
    preco DECIMAL(10, 2) NOT NULL,
    data_entrega DATE NOT NULL,
    FOREIGN KEY (fornecedor_id) REFERENCES Fornecedores(id),
    FOREIGN KEY (medicamento_id) REFERENCES Medicamentos(id)
);

CREATE TABLE OrdensDeCompra (
    id INT AUTO_INCREMENT PRIMARY KEY,
    fornecedor_id INT NOT NULL,
    medicamento_id INT NOT NULL,
    quantidade INT NOT NULL,
    data_prevista_entrega DATE NOT NULL,
    FOREIGN KEY (fornecedor_id) REFERENCES Fornecedores(id),
    FOREIGN KEY (medicamento_id) REFERENCES Medicamentos(id)
);
CREATE TABLE Vendas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    data_hora DATETIME NOT NULL,
    cliente_id INT,
    funcionario_id INT,
    total DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (cliente_id) REFERENCES Clientes(id),
    FOREIGN KEY (funcionario_id) REFERENCES Funcionarios(id)
);

CREATE TABLE ItensVenda (
    id INT AUTO_INCREMENT PRIMARY KEY,
    venda_id INT,
    medicamento_id INT,
    quantidade INT NOT NULL,
    preco_unitario DECIMAL(10, 2) NOT NULL,
    receita_id INT,
    FOREIGN KEY (venda_id) REFERENCES Vendas(id),
    FOREIGN KEY (medicamento_id) REFERENCES Medicamentos(id),
    FOREIGN KEY (receita_id) REFERENCES ReceitasMedicas(id)
);

-- Clientes
INSERT INTO Clientes (nome, dni, telefone, endereco, email) VALUES
('Cliente 1', 'DNI123456', '123456789', 'Rua Exemplo 1', 'cliente1@exemplo.com'),
('Cliente 2', 'DNI654321', '987654321', 'Rua Exemplo 2', 'cliente2@exemplo.com'),
('Cliente 3', 'DNI111111', '555555555', 'Rua Exemplo 3', 'cliente3@exemplo.com'),
('Cliente 4', 'DNI222222', '666666666', 'Rua Exemplo 4', 'cliente4@exemplo.com'),
('Cliente 5', 'DNI333333', '777777777', 'Rua Exemplo 5', 'cliente5@exemplo.com');

-- Inserindo dados na tabela ReceitasMedicas
INSERT INTO ReceitasMedicas (cliente_id, medico_nome, data_emissao) VALUES
(1, 'Dr. Bernardo Ribeiro', '2024-01-15'),
(2, 'Dra. Felipe de Avila', '2024-02-10'),
(3, 'Dr. Jorge Santos', '2024-03-05'),
(4, 'Dra. Beatriz Souza', '2024-04-12'),
(5, 'Dr. Ricardo Alves', '2024-05-20');

-- Inserindo dados na tabela Medicamentos
INSERT INTO Medicamentos (nome, categoria, estoque, preco, data_vencimento, receita_obrigatoria) VALUES
('Paracetamol', 'Analgesico', 100, 5.50, '2025-01-01', FALSE),
('Amoxicilina', 'Antibiotico', 50, 12.90, '2025-03-15', TRUE),
('Ibuprofeno', 'Anti-inflamatorio', 80, 8.30, '2025-06-10', FALSE),
('Fluoxetina', 'Antidepressivo', 30, 20.00, '2024-12-20', TRUE),
('Dipirona', 'Analgesico', 120, 4.75, '2025-02-05', FALSE);

-- Inserindo dados na tabela MedicamentosReceitados
INSERT INTO MedicamentosReceitados (receita_id, medicamento_id, quantidade) VALUES
(1, 1, 2),
(1, 2, 1),
(2, 3, 2),
(3, 4, 1),
(4, 5, 3);

-- Inserindo dados na tabela Funcionarios
INSERT INTO Funcionarios (nome, dni, cargo, salario) VALUES
('Paulo Silva', '78901234567', 'Farmaceutico', 4500.00),
('Ana Costa', '89012345678', 'Caixa', 2200.00),
('Lucas Pereira', '90123456789', 'Repositor', 1800.00),
('Juliana Oliveira', '12309845678', 'Farmaceutico', 4800.00),
('Roberta Lima', '23409856789', 'Caixa', 2300.00);

-- Inserindo dados na tabela Fornecedores
INSERT INTO Fornecedores (nome, contato, endereco) VALUES
('Farmaceutica ABC', 'contato@abc.com', 'Rua das Indústrias, 123'),
('Saúde Mais', 'suporte@saudecorp.com', 'Av. Central, 456'),
('Distribuidora Vida', 'atendimento@vida.com', 'Estrada do Progresso, 789'),
('Pharma Global', 'global@pharma.com', 'Rodovia Saúde, 321'),
('Medic Plus', 'plus@medicplus.com', 'Rua do Comércio, 654');

-- Inserindo dados na tabela ProdutosFornecidos
INSERT INTO ProdutosFornecidos (fornecedor_id, medicamento_id, preco, data_entrega) VALUES
(1, 1, 4.50, '2024-11-01'),
(1, 2, 11.00, '2024-11-15'),
(2, 3, 7.50, '2024-12-01'),
(3, 4, 18.00, '2024-10-25'),
(4, 5, 3.90, '2024-09-30');

-- Inserindo dados na tabela OrdensDeCompra
INSERT INTO OrdensDeCompra (fornecedor_id, medicamento_id, quantidade, data_prevista_entrega) VALUES
(1, 1, 200, '2024-12-15'),
(1, 2, 100, '2025-01-05'),
(2, 3, 150, '2025-02-10'),
(3, 4, 80, '2024-11-20'),
(4, 5, 300, '2025-01-30');

-- Inserindo dados na tabela Vendas
INSERT INTO Vendas (data_hora, cliente_id, funcionario_id, total) VALUES
('2024-01-20 10:30:00', 1, 1, 18.50),
('2024-02-05 15:45:00', 2, 2, 25.80),
('2024-03-10 11:15:00', 3, 1, 14.30),
('2024-04-22 16:00:00', 4, 3, 30.00),
('2024-05-12 09:00:00', 5, 2, 50.20);

-- Inserindo dados na tabela ItensVenda
INSERT INTO ItensVenda (venda_id, medicamento_id, quantidade, preco_unitario, receita_id) VALUES
(1, 1, 2, 5.50, 1),
(1, 2, 1, 12.90, 1),
(2, 3, 2, 8.30, 2),
(3, 4, 1, 20.00, 3),
(4, 5, 3, 4.75, NULL);


-- Tabela Clientes
UPDATE Clientes 
SET telefone = '55512345678' 
WHERE id = 1;

UPDATE Clientes 
SET email = 'novoemail@gmail.com' 
WHERE id = 2;

-- Tabela ReceitasMedicas
UPDATE ReceitasMedicas 
SET medico_nome = 'Dr. Carlos Eduardo' 
WHERE id = 1;

UPDATE ReceitasMedicas 
SET data_emissao = '2024-06-01' 
WHERE id = 2;

-- Tabela Medicamentos
UPDATE Medicamentos 
SET estoque = estoque - 10 
WHERE id = 1;

UPDATE Medicamentos 
SET preco = 19.50 
WHERE id = 4;

-- Tabela MedicamentosReceitados
UPDATE MedicamentosReceitados 
SET quantidade = 5 
WHERE id = 1;

UPDATE MedicamentosReceitados 
SET medicamento_id = 3 
WHERE id = 2;

-- Tabela Funcionarios
UPDATE Funcionarios 
SET salario = salario + 500 
WHERE id = 1;

UPDATE Funcionarios 
SET cargo = 'Caixa' 
WHERE id = 3;

-- Tabela Fornecedores
UPDATE Fornecedores 
SET contato = 'novoemail@fornecedor.com' 
WHERE id = 1;

UPDATE Fornecedores 
SET endereco = 'Av. Reformulada, 456' 
WHERE id = 2;

-- Tabela ProdutosFornecidos
UPDATE ProdutosFornecidos 
SET preco = 5.00 
WHERE id = 1;

UPDATE ProdutosFornecidos 
SET data_entrega = '2024-11-20' 
WHERE id = 2;

-- Tabela OrdensDeCompra
UPDATE OrdensDeCompra 
SET quantidade = 500 
WHERE id = 1;

UPDATE OrdensDeCompra 
SET data_prevista_entrega = '2025-02-15' 
WHERE id = 2;

-- Tabela Vendas
UPDATE Vendas 
SET total = 20.00 
WHERE id = 1;

UPDATE Vendas 
SET funcionario_id = 3 
WHERE id = 2;

-- Tabela ItensVenda
UPDATE ItensVenda 
SET quantidade = 4 
WHERE id = 1;

UPDATE ItensVenda 
SET preco_unitario = 6.00 
WHERE id = 2;


-- Tabela Clientes
ALTER TABLE Clientes ADD COLUMN data_nascimento DATE;

ALTER TABLE Clientes MODIFY COLUMN telefone VARCHAR(20);

-- Tabela ReceitasMedicas
ALTER TABLE ReceitasMedicas ADD COLUMN observacoes TEXT;

ALTER TABLE ReceitasMedicas CHANGE COLUMN data_emissao data_prescricao DATE;

-- Tabela Medicamentos
ALTER TABLE Medicamentos ADD COLUMN fornecedor_principal INT;

ALTER TABLE Medicamentos ADD FOREIGN KEY (fornecedor_principal) REFERENCES Fornecedores(id);

-- Tabela MedicamentosReceitados
ALTER TABLE MedicamentosReceitados ADD COLUMN dosagem VARCHAR(50);

ALTER TABLE MedicamentosReceitados DROP COLUMN dosagem;

-- Tabela Funcionarios
ALTER TABLE Funcionarios ADD COLUMN data_admissao DATE;

ALTER TABLE Funcionarios MODIFY COLUMN salario DECIMAL(12, 2);

-- Tabela Fornecedores
ALTER TABLE Fornecedores ADD COLUMN telefone VARCHAR(15);

ALTER TABLE Fornecedores ADD COLUMN email VARCHAR(255);

-- Tabela ProdutosFornecidos
ALTER TABLE ProdutosFornecidos ADD COLUMN lote VARCHAR(50);

ALTER TABLE ProdutosFornecidos MODIFY COLUMN preco DECIMAL(12, 2);

-- Tabela OrdensDeCompra
ALTER TABLE OrdensDeCompra ADD COLUMN status ENUM('Pendente', 'Concluída', 'Cancelada');

ALTER TABLE OrdensDeCompra CHANGE COLUMN data_prevista_entrega data_entrega_prevista DATE;

-- Tabela Vendas
ALTER TABLE Vendas ADD COLUMN metodo_pagamento ENUM('Cartão', 'Dinheiro', 'Pix');

ALTER TABLE Vendas MODIFY COLUMN total DECIMAL(12, 2);

-- Tabela ItensVenda
ALTER TABLE ItensVenda ADD COLUMN desconto DECIMAL(5, 2);

ALTER TABLE itensvenda DROP FOREIGN KEY itensvenda_ibfk_2;

ALTER TABLE itensvenda MODIFY medicamento_id INT NULL;

-- Tabela ReceitasMedicas
DELETE FROM ReceitasMedicas 
WHERE id = 5;

ALTER TABLE vendas DROP FOREIGN KEY vendas_ibfk_1;


-- Tabela Clientes
DELETE FROM Clientes WHERE id = 5;

DELETE FROM itensvenda WHERE medicamento_id = 3;

-- Tabela MedicamentosReceitados
DELETE FROM MedicamentosReceitados 
WHERE id = 3;

-- Tabela Medicamentos
DELETE FROM Medicamentos 
WHERE id = 6;


-- Tabela Funcionarios
DELETE FROM Funcionarios 
WHERE id = 6;

-- Tabela ProdutosFornecidos
DELETE FROM ProdutosFornecidos 
WHERE id = 5;

-- Tabela OrdensDeCompra
DELETE FROM OrdensDeCompra 
WHERE id = 3;

-- Tabela Vendas
DELETE FROM Vendas 
WHERE id = 6;

-- Tabela ItensVenda
DELETE FROM ItensVenda 
WHERE id = 4;


-- Selecionar todos os clientes cujo nome começa com 'A' (LIKE)
SELECT * 
FROM Clientes 
WHERE nome LIKE 'A%';

-- Selecionar todos os clientes ordenados por nome (ORDER BY)
SELECT id, nome, telefone 
FROM Clientes 
ORDER BY nome;

-- Contar quantos clientes existem no banco (COUNT)
SELECT COUNT(*) AS total_clientes 
FROM Clientes;

-- Selecionar todas as receitas de um cliente específico (JOIN com Clientes)
SELECT r.id AS receita_id, c.nome AS cliente_nome, r.medico_nome 
FROM ReceitasMedicas r
JOIN Clientes c ON r.cliente_id = c.id
WHERE c.id = 1;

-- Selecionar as receitas médicas ordenadas pela data de emissão (ORDER BY)
SELECT id, medico_nome 
FROM ReceitasMedicas 
ORDER BY medico_nome DESC;

-- Contar quantas receitas foram emitidas por cada médico (GROUP BY e COUNT)
SELECT medico_nome, COUNT(*) AS total_receitas 
FROM ReceitasMedicas 
GROUP BY medico_nome;

-- Selecionar medicamentos com estoque menor que 50 (WHERE e funções agregadas)
SELECT nome, estoque 
FROM Medicamentos 
WHERE estoque < 50;

-- Obter o medicamento mais caro (MAX)
SELECT nome, preco 
FROM Medicamentos 
WHERE preco = (SELECT MAX(preco) FROM Medicamentos);

-- Consultar o estoque médio de medicamentos por categoria (GROUP BY e AVG)
SELECT categoria, AVG(estoque) AS estoque_medio 
FROM Medicamentos 
GROUP BY categoria;

-- Consultar medicamentos prescritos em uma receita específica (JOIN com Medicamentos)
SELECT mr.id, m.nome AS medicamento, mr.quantidade 
FROM MedicamentosReceitados mr
JOIN Medicamentos m ON mr.medicamento_id = m.id
WHERE mr.receita_id = 1;

-- Consultar a quantidade total de medicamentos prescritos (SUM)
SELECT SUM(quantidade) AS total_medicamentos 
FROM MedicamentosReceitados;

-- Agrupar medicamentos prescritos por receita e contar o número de itens (GROUP BY, COUNT)
SELECT receita_id, COUNT(*) AS total_itens 
FROM MedicamentosReceitados 
GROUP BY receita_id;

-- Selecionar funcionários cujo cargo seja "Farmacêutico" (LIKE)
SELECT * 
FROM Funcionarios 
WHERE cargo LIKE 'Farmaceutico';

-- Obter o maior e menor salário entre os funcionários (MAX e MIN)
SELECT MAX(salario) AS maior_salario, MIN(salario) AS menor_salario 
FROM Funcionarios;

-- Listar a média salarial por cargo (GROUP BY e AVG)
SELECT cargo, AVG(salario) AS salario_medio 
FROM Funcionarios 
GROUP BY cargo;

-- Consultar todos os fornecedores cujo nome contenha "Pharma" (LIKE)
SELECT * 
FROM Fornecedores 
WHERE nome LIKE '%Pharma%';

-- Consultar os fornecedores ordenados por nome (ORDER BY)
SELECT nome, contato, endereco 
FROM Fornecedores 
ORDER BY nome;

-- Contar quantos fornecedores existem (COUNT)
SELECT COUNT(*) AS total_fornecedores 
FROM Fornecedores;

-- Consultar os medicamentos fornecidos por um fornecedor específico (JOIN com Medicamentos)
SELECT pf.id, f.nome AS fornecedor, m.nome AS medicamento, pf.preco 
FROM ProdutosFornecidos pf
JOIN Fornecedores f ON pf.fornecedor_id = f.id
JOIN Medicamentos m ON pf.medicamento_id = m.id
WHERE f.id = 1;

-- Calcular o preço médio dos produtos fornecidos (AVG)
SELECT AVG(preco) AS preco_medio 
FROM ProdutosFornecidos;

-- Contar quantos produtos cada fornecedor fornece (GROUP BY e COUNT)
SELECT fornecedor_id, COUNT(*) AS total_produtos 
FROM ProdutosFornecidos 
GROUP BY fornecedor_id;

-- Consultar todas as ordens de compra de um fornecedor específico (JOIN com Fornecedores)
SELECT oc.id, f.nome AS fornecedor, oc.quantidade
FROM OrdensDeCompra oc
JOIN Fornecedores f ON oc.fornecedor_id = f.id
WHERE f.id = 2;

-- Consultar a quantidade total comprada por fornecedor (SUM e GROUP BY)
SELECT fornecedor_id, SUM(quantidade) AS total_quantidade 
FROM OrdensDeCompra 
GROUP BY fornecedor_id;

-- Selecionar as ordens de compra com mais de 200 itens e agrupadas por fornecedor (HAVING)
SELECT fornecedor_id, SUM(quantidade) AS total_quantidade 
FROM OrdensDeCompra 
GROUP BY fornecedor_id 
HAVING total_quantidade > 200;

-- Consultar todas as vendas realizadas por um funcionário específico (JOIN com Funcionarios)
SELECT v.id AS venda_id, f.nome AS funcionario, v.data_hora, v.total 
FROM Vendas v
JOIN Funcionarios f ON v.funcionario_id = f.id
WHERE f.id = 1;

-- Consultar o total de vendas por cliente (GROUP BY e SUM)
SELECT cliente_id, SUM(total) AS total_compras 
FROM Vendas 
GROUP BY cliente_id;

-- Selecionar as vendas acima de R$30,00 e ordenar por valor (ORDER BY e WHERE)
SELECT id, data_hora, total 
FROM Vendas 
WHERE total > 30 
ORDER BY total DESC;

-- Consultar os itens de venda com medicamentos de uma venda específica (JOIN com Medicamentos)
SELECT iv.id, iv.venda_id, m.nome AS medicamento, iv.quantidade, iv.preco_unitario 
FROM ItensVenda iv
JOIN Medicamentos m ON iv.medicamento_id = m.id
WHERE iv.venda_id = 1;

-- Calcular o total arrecadado por venda (GROUP BY e SUM)
SELECT venda_id, SUM(quantidade * preco_unitario) AS total_venda 
FROM ItensVenda 
GROUP BY venda_id;

-- Selecionar vendas que possuem mais de 5 itens (HAVING e COUNT)
SELECT venda_id, COUNT(*) AS total_itens 
FROM ItensVenda 
GROUP BY venda_id 
HAVING total_itens > 5;
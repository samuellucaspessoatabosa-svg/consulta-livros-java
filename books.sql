-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Tempo de geração: 08/06/2026 às 20:55
-- Versão do servidor: 10.4.32-MariaDB
-- Versão do PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Banco de dados: `books`
--

-- --------------------------------------------------------

--
-- Estrutura para tabela `authorisbn`
--

CREATE TABLE `authorisbn` (
  `authorID` int(11) DEFAULT NULL,
  `isbn` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `authorisbn`
--

INSERT INTO `authorisbn` (`authorID`, `isbn`) VALUES
(1, '0132151001'),
(2, '0132151001'),
(1, '0131869000'),
(2, '0131869000'),
(1, '0132151002'),
(2, '0132151002');

-- --------------------------------------------------------

--
-- Estrutura para tabela `authors`
--

CREATE TABLE `authors` (
  `authorID` int(11) NOT NULL,
  `firstName` varchar(20) DEFAULT NULL,
  `lastName` varchar(30) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `authors`
--

INSERT INTO `authors` (`authorID`, `firstName`, `lastName`) VALUES
(1, 'Paul', 'Deitel'),
(2, 'Harvey', 'Deitel'),
(3, 'Abbey', 'Deitel');

-- --------------------------------------------------------

--
-- Estrutura para tabela `titles`
--

CREATE TABLE `titles` (
  `isbn` varchar(20) NOT NULL,
  `title` varchar(100) DEFAULT NULL,
  `editionNumber` int(11) DEFAULT NULL,
  `copyright` varchar(4) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `titles`
--

INSERT INTO `titles` (`isbn`, `title`, `editionNumber`, `copyright`) VALUES
('0131869000', 'C++ Como Programar', 8, '2012'),
('0132151001', 'Java Como Programar', 9, '2012'),
('0132151002', 'C Como Programar', 7, '2013');

--
-- Índices para tabelas despejadas
--

--
-- Índices de tabela `authorisbn`
--
ALTER TABLE `authorisbn`
  ADD KEY `authorID` (`authorID`),
  ADD KEY `isbn` (`isbn`);

--
-- Índices de tabela `authors`
--
ALTER TABLE `authors`
  ADD PRIMARY KEY (`authorID`);

--
-- Índices de tabela `titles`
--
ALTER TABLE `titles`
  ADD PRIMARY KEY (`isbn`);

--
-- Restrições para tabelas despejadas
--

--
-- Restrições para tabelas `authorisbn`
--
ALTER TABLE `authorisbn`
  ADD CONSTRAINT `authorisbn_ibfk_1` FOREIGN KEY (`authorID`) REFERENCES `authors` (`authorID`),
  ADD CONSTRAINT `authorisbn_ibfk_2` FOREIGN KEY (`isbn`) REFERENCES `titles` (`isbn`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

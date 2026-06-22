-- V1__create_schema.sql
-- Cria o esquema inicial do banco de dados taskDois.
-- Tabelas: usuario, receita.

-- ─────────────────────────────────────────────────────────
-- Tabela: usuario
-- ─────────────────────────────────────────────────────────
CREATE TABLE usuario (
    id       BIGSERIAL PRIMARY KEY,
    nome     VARCHAR(150) NOT NULL,
    login    VARCHAR(80)  NOT NULL,
    senha    VARCHAR(255) NOT NULL,
    situacao VARCHAR(20)  NOT NULL
);

-- Login deve ser único para permitir autenticação correta
ALTER TABLE usuario
    ADD CONSTRAINT uq_usuario_login UNIQUE (login);

-- ─────────────────────────────────────────────────────────
-- Tabela: receita
-- ─────────────────────────────────────────────────────────
CREATE TABLE receita (
    id            BIGSERIAL PRIMARY KEY,
    nome          VARCHAR(150)   NOT NULL,
    descricao     VARCHAR(1000),
    data_registro DATE           NOT NULL,
    custo         NUMERIC(10, 2) NOT NULL,
    tipo_receita  VARCHAR(20)    NOT NULL,
    status        VARCHAR(20)    NOT NULL
);

-- Índices para consultas frequentes (filtros por tipo/status são comuns em listagens)
CREATE INDEX idx_receita_tipo_receita ON receita (tipo_receita);
CREATE INDEX idx_receita_status ON receita (status);

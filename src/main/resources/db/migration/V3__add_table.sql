-- V3__add_table.sql
-- Adiciona tabela de categoria de cozinha ao esquema.

CREATE TABLE categoria (
    id          BIGSERIAL PRIMARY KEY,
    nome        VARCHAR(150) NOT NULL,
    descricao   VARCHAR(500),
    tipo        VARCHAR(50)  NOT NULL,
    status      VARCHAR(20)  NOT NULL DEFAULT 'ativo'
);

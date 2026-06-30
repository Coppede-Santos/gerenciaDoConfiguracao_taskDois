-- V3__add_table_instrumento_cozinhav4__add_table_cat.sql
-- Adiciona tabela de instrumentos de cozinha ao esquema.
-- Aplicada automaticamente pelo Flyway ao atualizar o ambiente.

CREATE TABLE Categora (
                                     id          BIGSERIAL PRIMARY KEY,
                                     nome        VARCHAR(150) NOT NULL,
                                     descricao   VARCHAR(500),
                                     tipo        VARCHAR(50)  NOT NULL,
                                     status      VARCHAR(20)  NOT NULL DEFAULT 'ativo'
);

-- V2__insert_initial_datav4__add_table_cat.sql
-- Dados iniciais para demonstração dos ambientes de Homologação e Produção.
-- Executado automaticamente pelo Flyway na primeira inicialização do ambiente.

-- Usuário administrador (senha em texto plano para fins acadêmicos)
INSERT INTO usuario (nome, login, senha, situacao) VALUES
    ('Administrador', 'messi@gmail.com', 'melhorquepele', 'ativo');

-- Receitas de exemplo
INSERT INTO receita (nome, descricao, data_registro, custo, tipo_receita, ativo) VALUES
                                                                                     ('Bolo de Cenoura',  'Bolo delicioso com cobertura de chocolate',  '2023-10-01', 15.50, 'doce',    true),
                                                                                     ('Coxinha',          'Coxinha de frango com catupiry',              '2023-10-02',  3.50, 'salgada', true),
                                                                                     ('Pudim',            'Pudim de leite condensado',                   '2023-10-03', 20.00, 'doce',    true),
                                                                                     ('Rissole',          'Rissole de queijo com presunto',              '2023-10-04',  3.00, 'salgada', true),
                                                                                     ('Brigadeiro',       'Brigadeiro tradicional de chocolate',         '2023-10-05',  1.50, 'doce',    true),
                                                                                     ('Esfiha',           'Esfiha de carne moída',                      '2023-10-06',  4.00, 'salgada', true),
                                                                                     ('Torta de Limão',   'Torta doce com creme de limão e merengue',   '2023-10-07', 35.00, 'doce',    true),
                                                                                     ('Empada',           'Empadinha de palmito',                        '2023-10-08',  4.50, 'salgada', true),
                                                                                     ('Beijinho',         'Doce de coco',                                '2023-10-09',  1.50, 'doce',    true),
                                                                                     ('Kibe',             'Kibe frito de carne',                         '2023-10-10',  4.00, 'salgada', true);
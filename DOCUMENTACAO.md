# Documentação Completa - Sistema Gerenciador de Receitas

## 📋 Índice
1. [Documentação da Aplicação](#documentação-da-aplicação)
2. [Documentação da Publicação](#documentação-da-publicação)
3. [Tempos de Desenvolvimento e Implantação](#tempos-de-desenvolvimento-e-implantação)

---

## 📱 Documentação da Aplicação

### 1. Modelagem do Banco de Dados

O sistema utiliza PostgreSQL como banco de dados relacional com as seguintes entidades:

#### Entidade: USUARIO
| Campo     | Tipo         | Descrição                          |
|-----------|--------------|-------------------------------------|
| id        | BIGSERIAL    | Chave primária (auto-incremento)   |
| nome      | VARCHAR(255) | Nome completo do usuário           |
| login     | VARCHAR(255) | Login único do usuário             |
| senha     | VARCHAR(255) | Senha em texto plano               |
| situacao  | VARCHAR(255) | Status do usuário (ativo/inativo)  |

#### Entidade: RECEITA
| Campo          | Tipo         | Descrição                              |
|----------------|--------------|----------------------------------------|
| id             | BIGSERIAL    | Chave primária (auto-incremento)       |
| nome           | VARCHAR(255) | Nome da receita                        |
| descricao      | VARCHAR(255) | Descrição detalhada                    |
| data_registro  | DATE         | Data de cadastro da receita            |
| custo          | DECIMAL      | Custo da receita em reais              |
| tipo_receita   | VARCHAR(255) | Tipo da receita (doce/salgada)         |

#### Diagrama de Relacionamento

```
┌─────────────────┐
│     USUARIO     │
├─────────────────┤
│ id (PK)         │
│ nome            │
│ login           │
│ senha           │
│ situacao        │
└─────────────────┘

┌─────────────────┐
│     RECEITA     │
├─────────────────┤
│ id (PK)         │
│ nome            │
│ descricao       │
│ data_registro   │
│ custo           │
│ tipo_receita    │
└─────────────────┘
```

**Observações:**
- As entidades não possuem relacionamento direto entre si
- `data_registro` é preenchida automaticamente na criação (usando `@PrePersist`)
- O sistema usa Hibernate com estratégia `create-drop` para recriação das tabelas a cada inicialização

---

### 2. Interface Desenvolvida

A aplicação possui três interfaces principais desenvolvidas com Thymeleaf:

#### 2.1 Tela de Login (`login.html`)

**Funcionalidades:**
- Formulário com campos de login e senha
- Validação de credenciais
- Verificação de status do usuário (ativo/inativo)
- Exibição de mensagens de erro

**Campos:**
- Login (campo de texto)
- Senha (campo password)
- Botão "Entrar"

**Screenshot/Descrição:**
- Design centralizado com fundo cinza claro (#f4f4f4)
- Card branco com sombra para destaque
- Mensagens de erro em vermelho
- Responsivo e mobile-friendly

**Fluxo:**
1. Usuário insere login e senha
2. Sistema valida credenciais no banco
3. Se válido e ativo → redireciona para `/receitas`
4. Se inválido ou inativo → exibe mensagem de erro

---

#### 2.2 Tela de Listagem de Receitas (`receitas/lista.html`)

**Funcionalidades:**
- Exibição de todas as receitas cadastradas em formato de tabela
- Botão para adicionar nova receita
- Ações de editar e excluir para cada receita
- Botão de logout
- Formatação de valores monetários (R$)

**Estrutura da Tabela:**
| ID | Nome | Descrição | Custo | Tipo | Data Registro | Ações |
|----|------|-----------|-------|------|---------------|-------|
| Dados dinâmicos das receitas ||||||| Editar / Excluir |

**Elementos da Interface:**
- Header com título "Receitas Cadastradas" e botão "Sair"
- Botão verde "Nova Receita"
- Tabela responsiva com linhas alternadas
- Links de ação (Editar em azul, Excluir com confirmação)
- Mensagem quando não há receitas cadastradas

**Fluxo de Navegação:**
- `/receitas` → Lista todas as receitas
- `/receitas/nova` → Formulário de cadastro
- `/receitas/editar/{id}` → Formulário de edição
- `/receitas/excluir/{id}` → Exclusão com confirmação JavaScript
- `/logout` → Encerra sessão

---

#### 2.3 Tela de Formulário de Receita (`receitas/form.html`)

**Funcionalidades:**
- Cadastro de novas receitas
- Edição de receitas existentes
- Validação de campos obrigatórios
- Formatação de valores monetários

**Campos do Formulário:**
- Nome (texto, obrigatório)
- Descrição (textarea, obrigatório)
- Custo (number com decimais, obrigatório)
- Tipo de Receita (select: doce/salgada, obrigatório)
- Data de Registro (date, preenchida automaticamente)

**Botões:**
- "Salvar" → Persiste os dados no banco
- "Cancelar/Voltar" → Retorna à listagem

**Comportamento:**
- Modo cadastro: campos vazios
- Modo edição: campos preenchidos com dados existentes
- Validação HTML5 nativa
- Submit via POST para `/receitas/salvar`

---

### 3. Tecnologias Utilizadas na Interface

- **Thymeleaf**: Engine de templates server-side
- **HTML5**: Estrutura semântica
- **CSS3**: Estilização inline (sem frameworks externos)
- **JavaScript**: Confirmações de exclusão (minimal)
- **Spring MVC**: Controle de rotas e sessões

---

## 🚀 Documentação da Publicação

### 1. Como Acessar a VM

#### 1.1 Informações de Acesso

**Credenciais da VM:**
- **IP Público:** 177.44.248.32
- **Usuário:** univates
- **Protocolo:** SSH (porta 22)

#### 1.2 Conectar via SSH

**No Linux/macOS:**
```bash
ssh univates@177.44.248.32
```

**No Windows (PowerShell):**
```powershell
ssh univates@177.44.248.32
```

**No Windows (PuTTY):**
1. Abra o PuTTY
2. Host Name: `177.44.248.32`
3. Port: `22`
4. Connection type: `SSH`
5. Click "Open"
6. Login: `univates`

---

### 2. Instalação de Ferramentas

#### 2.1 Atualizar Sistema Operacional

```bash
sudo apt update
sudo apt upgrade -y
```

**Tempo estimado:** 3-5 minutos

---

#### 2.2 Instalar Java 17 (JDK)

**Comandos:**
```bash
sudo apt install openjdk-17-jdk -y
```

**Verificar instalação:**
```bash
java -version
```

**Saída esperada:**
```
openjdk version "17.0.x"
OpenJDK Runtime Environment (build ...)
OpenJDK 64-Bit Server VM (build ...)
```

**Tempo estimado:** 2-3 minutos

---

#### 2.3 Instalar PostgreSQL

**Comandos:**
```bash
sudo apt install postgresql postgresql-contrib -y
sudo systemctl start postgresql
sudo systemctl enable postgresql
```

**Verificar instalação:**
```bash
sudo systemctl status postgresql
```

**Saída esperada:**
```
● postgresql.service - PostgreSQL RDBMS
   Loaded: loaded
   Active: active (exited)
```

**Tempo estimado:** 3-4 minutos

---

#### 2.4 Configurar Banco de Dados

**Criar banco de dados e usuário:**
```bash
sudo -u postgres psql
```

**Dentro do PostgreSQL:**
```sql
CREATE DATABASE taskdois;
CREATE USER app_user WITH PASSWORD '123456789';
GRANT ALL PRIVILEGES ON DATABASE taskdois TO app_user;
\q
```

**Conceder permissões ao schema public:**
```bash
sudo -u postgres psql -d taskdois
```

```sql
GRANT ALL ON SCHEMA public TO app_user;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO app_user;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO app_user;
ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT ALL ON TABLES TO app_user;
ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT ALL ON SEQUENCES TO app_user;
\q
```

**Tempo estimado:** 2 minutos

---

#### 2.5 Configurar Firewall

**Permitir tráfego HTTP/HTTPS e porta 8080:**
```bash
sudo ufw allow OpenSSH
sudo ufw allow 80/tcp
sudo ufw allow 443/tcp
sudo ufw allow 8080/tcp
sudo ufw enable
```

**Verificar regras:**
```bash
sudo ufw status
```

**Tempo estimado:** 1 minuto

---

### 3. Implantação da Aplicação

#### 3.1 Clonar o Repositório

```bash
cd ~
git clone https://github.com/Coppede-Santos/gerenciaDoConfiguracao_taskDois
cd gerenciaDoConfiguracao_taskDois
```

**Tempo estimado:** 1-2 minutos

---

#### 3.2 Dar Permissões ao Gradle Wrapper

```bash
chmod +x gradlew
```

**Tempo estimado:** < 1 minuto

---

#### 3.3 Configurar Application Properties

**Editar arquivo de configuração:**
```bash
nano src/main/resources/application.properties
```

**Adicionar ao final do arquivo:**
```properties
server.address=0.0.0.0
```

**Salvar:** `Ctrl+O`, `Enter`, `Ctrl+X`

**Conteúdo completo esperado:**
```properties
spring.application.name=taskDois
spring.datasource.url=jdbc:postgresql://localhost:5432/taskdois
spring.datasource.username=app_user
spring.datasource.password=123456789
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
server.error.include-message=always
server.error.include-stacktrace=always
logging.level.org.springframework.web=DEBUG
server.address=0.0.0.0
```

**Tempo estimado:** 1 minuto

---

#### 3.4 Executar a Aplicação

**Iniciar aplicação:**
```bash
./gradlew bootRun
```

**Aguardar mensagem:**
```
Started TaskDoisApplication in X.XXX seconds
```

**Tempo estimado:**
- Primeira execução: 5-10 minutos (download de dependências)
- Execuções subsequentes: 10-15 segundos

---

#### 3.5 Popular Tabela de Receitas (Opcional)

**Em outra sessão SSH, criar script SQL:**
```bash
cd ~/gerenciaDoConfiguracao_taskDois
nano insert_receitasv4__add_table_cat.sql
```

**Conteúdo do script:**
```sql
INSERT INTO receita (nome, descricao, data_registro, custo, tipo_receita) VALUES
('Bolo de Cenoura', 'Bolo delicioso com cobertura de chocolate', '2023-10-01', 15.50, 'doce'),
('Coxinha', 'Coxinha de frango com catupiry', '2023-10-02', 3.50, 'salgada'),
('Pudim', 'Pudim de leite condensado', '2023-10-03', 20.00, 'doce'),
('Rissole', 'Rissole de queijo com presunto', '2023-10-04', 3.00, 'salgada'),
('Brigadeiro', 'Brigadeiro tradicional de chocolate', '2023-10-05', 1.50, 'doce'),
('Esfiha', 'Esfiha de carne moída', '2023-10-06', 4.00, 'salgada'),
('Torta de Limão', 'Torta doce com creme de limão e merengue', '2023-10-07', 35.00, 'doce'),
('Empada', 'Empadinha de palmito', '2023-10-08', 4.50, 'salgada'),
('Beijinho', 'Doce de coco', '2023-10-09', 1.50, 'doce'),
('Kibe', 'Kibe frito de carne', '2023-10-10', 4.00, 'salgada');
```

**Salvar e executar:**
```bash
psql -h localhost -U app_user -d taskdois -f insert_receitasv4__add_table_cat.sql
```
Senha: `123456789`

**Tempo estimado:** 1 minuto

---

### 4. URL de Acesso

#### 4.1 Acessar a Aplicação

**URL Pública:**
```
http://177.44.248.32:8080
```

**Credenciais de Login:**
- **Usuário:** messi
- **Senha:** melhorquepele

#### 4.2 Rotas Disponíveis

| Rota | Método | Descrição |
|------|--------|-----------|
| `/login` | GET | Exibe formulário de login |
| `/login` | POST | Processa autenticação |
| `/logout` | GET | Encerra sessão |
| `/` ou `/receitas` | GET | Lista todas as receitas |
| `/receitas/nova` | GET | Formulário de nova receita |
| `/receitas/salvar` | POST | Salva receita (nova ou editada) |
| `/receitas/editar/{id}` | GET | Formulário de edição |
| `/receitas/excluir/{id}` | GET | Exclui receita |

#### 4.3 Verificar Status da Aplicação

**Verificar se está rodando:**
```bash
curl http://localhost:8080/login
```

**Verificar logs em tempo real:**
```bash
# Na terminal onde executou ./gradlew bootRun
# Os logs aparecem automaticamente
```

---

## ⏱️ Tempos de Desenvolvimento e Implantação

### Resumo Geral

| Etapa | Tempo Estimado | Tempo Real |
|-------|----------------|------------|
| **Desenvolvimento da Aplicação** | 120 min | 150 min |
| **Criação do Ambiente na VM** | 15 min | 20 min |
| **Publicação da Aplicação** | 10 min | 25 min |
| **Resolução de Problemas** | - | 30 min |
| **TOTAL** | 145 min | 225 min |

---

### Detalhamento por Fase

#### 1. Desenvolvimento da Aplicação (150 minutos)

| Tarefa | Tempo |
|--------|-------|
| Configuração inicial do projeto (Spring Initializr, dependências) | 10 min |
| Criação das entidades (Usuario, Receita) | 15 min |
| Desenvolvimento dos repositórios JPA | 10 min |
| Implementação dos controllers (Login, Receita) | 30 min |
| Desenvolvimento das views Thymeleaf (login, lista, form) | 40 min |
| Configuração do application.properties | 10 min |
| Criação do CommandLineRunner para dados iniciais | 15 min |
| Testes locais e ajustes | 20 min |

**Ferramentas utilizadas:**
- IntelliJ IDEA / VS Code
- PostgreSQL local
- Navegador para testes
- Git para versionamento

---

#### 2. Criação do Ambiente na VM (20 minutos)

| Tarefa | Tempo |
|--------|-------|
| Acesso SSH à VM | 1 min |
| Atualização do sistema (`apt update/upgrade`) | 5 min |
| Instalação do Java 17 | 3 min |
| Instalação do PostgreSQL | 4 min |
| Configuração do banco de dados | 3 min |
| Configuração do firewall (UFW) | 2 min |
| Testes de conectividade | 2 min |

**Observações:**
- Primeira execução pode demorar mais devido a downloads
- Conexão de internet impacta diretamente no tempo

---

#### 3. Publicação da Aplicação (25 minutos)

| Tarefa | Tempo |
|--------|-------|
| Clone do repositório Git | 2 min |
| Configuração de permissões (chmod gradlew) | 1 min |
| Edição do application.properties (server.address) | 2 min |
| Primeira execução (download de dependências Gradle) | 12 min |
| Criação e execução do script SQL de dados | 3 min |
| Testes de acesso externo | 3 min |
| Verificação de rotas e funcionalidades | 2 min |

---

#### 4. Resolução de Problemas (30 minutos)

| Problema | Tempo | Solução |
|----------|-------|---------|
| Erro de permissão no Gradle (`./gradlew: Permission denied`) | 2 min | `chmod +x gradlew` |
| Erro "permission denied for schema public" | 10 min | Comandos GRANT no PostgreSQL |
| Tabelas não criadas automaticamente | 5 min | Verificar `ddl-auto=create-drop` |
| Aplicação não acessível externamente | 8 min | Adicionar `server.address=0.0.0.0` |
| Erro de autenticação PostgreSQL (Peer authentication) | 3 min | Usar `-h localhost` no psql |
| Arquivo insert_receitasv4__add_table_cat.sql não encontrado | 2 min | Navegar para diretório correto |

---

### Observações sobre Tempos

**Fatores que impactam o tempo:**
1. **Velocidade da conexão de internet:** Download de dependências Gradle
2. **Especificações da VM:** CPU e RAM disponíveis
3. **Experiência prévia:** Familiaridade com Spring Boot e PostgreSQL
4. **Primeira execução vs. subsequentes:** Gradle faz cache de dependências

**Otimizações possíveis:**
- Pré-configurar ambiente com Java e PostgreSQL instalados
- Usar imagem Docker com dependências prontas
- Executar `./gradlew build` antes do deploy
- Automatizar com scripts de deploy

---

## 📊 Conclusão

A aplicação foi desenvolvida e implantada com sucesso em ambiente de produção (VM), seguindo as melhores práticas de desenvolvimento web com Spring Boot. O sistema está funcional, acessível publicamente e pronto para uso.

**URL de Acesso:** http://177.44.248.32:8080
**Credenciais:** messi / melhorquepele

---

**Desenvolvido como atividade prática de Gerência de Configuração (Task Dois)**
**Data:** Março/2026

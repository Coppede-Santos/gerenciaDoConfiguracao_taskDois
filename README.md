# Gerenciador de Receitas

Sistema web para gerenciamento de receitas (doces e salgadas) desenvolvido em Java com Spring Boot. Oferece autenticação de usuários e CRUD completo para cadastro, edição e exclusão de receitas.

---

## Tecnologias Utilizadas

- **Java 17**
- **Spring Boot 3.2.4**
- **Spring Data JPA** (Hibernate)
- **Thymeleaf** (Template engine)
- **PostgreSQL** (Banco de dados)
- **Lombok** (Redução de boilerplate)
- **Gradle** (Build e gerenciamento de dependências)

---

##  Pré-requisitos

Antes de iniciar, certifique-se de ter instalado em sua máquina:

- **Java 17** ou superior 
- **PostgreSQL 12+** rodando na porta `5432` 
- **Git** (para clonar o repositório)

---

## Configuração do Banco de Dados

### 1. Criar banco de dados e usuário

Acesse o PostgreSQL:
```bash
sudo -u postgres psql
```

Execute os comandos:
```sql
CREATE DATABASE taskdois;
CREATE USER app_user WITH PASSWORD '123456789';
GRANT ALL PRIVILEGES ON DATABASE taskdois TO app_user;
\q
```

### 2. Conceder permissões ao schema

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

---

## Como Executar a Aplicação

### 1. Clonar o repositório

```bash
git clone https://github.com/Coppede-Santos/gerenciaDoConfiguracao_taskDois
cd gerenciaDoConfiguracao_taskDois
```

### 2. Executar a aplicação

**No Windows:**
```bash
.\gradlew.bat bootRun
```

**No Linux/macOS:**
```bash
chmod +x gradlew  # Apenas na primeira vez
./gradlew bootRun
```

**Aguarde a mensagem:**
```
Started TaskDoisApplication in X.XXX seconds
```

> **Primeira execução:** Pode demorar alguns minutos devido ao download de dependências.

---

## Acesso ao Sistema

Abra seu navegador e acesse:

**URL Local:** `http://localhost:8080`

### Credenciais Padrão

O sistema cria automaticamente um usuário administrador na primeira execução:

- **Login:** `messi`
- **Senha:** `melhorquepele`

---

## Popular Banco com Dados de Exemplo

Para inserir 10 receitas de exemplo no banco de dados:

### 1. Certifique-se que a aplicação está rodando

A aplicação precisa estar em execução para que as tabelas sejam criadas.

### 2. Executar o script SQL

Em outra janela de terminal, execute:

```bash
cd gerenciaDoConfiguracao_taskDois
psql -h localhost -U app_user -d taskdois -f insert_receitasv4__add_table_cat.sql
```

Digite a senha quando solicitado: `123456789`

O script `insert_receitasv4__add_table_cat.sql` já está incluído no repositório e irá inserir 10 receitas de exemplo.

---

## Funcionalidades

-  **Autenticação de usuários** com validação de status (ativo/inativo)
-  **Listagem de receitas** com todos os detalhes
-  **Cadastro de novas receitas** com formulário validado
-  **Edição de receitas** existentes
-  **Exclusão de receitas** com confirmação
-  **Controle de sessão** com logout seguro
-  **Persistência de dados** entre reinicializações

---

## Implantação em Servidor/VM

### Acessar a VM


```bash
ssh univates@177.44.248.32
```

---

###  Instalar Ferramentas Necessárias

#### Java 17
```bash
sudo apt update
sudo apt install openjdk-17-jdk -y
java -version
```

#### PostgreSQL
```bash
sudo apt install postgresql postgresql-contrib -y
sudo systemctl start postgresql
sudo systemctl enable postgresql
```

#### Configuração do PostgreSQL
```bash
sudo -u postgres psql
```

Dentro do PostgreSQL, execute:
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

---

###  Implantar o Projeto

#### Clonar repositório
```bash
git clone https://github.com/Coppede-Santos/gerenciaDoConfiguracao_taskDois
cd gerenciaDoConfiguracao_taskDois
chmod +x gradlew
```

#### Executar aplicação

```bash
./gradlew bootRun
```

Aguarde a mensagem `Started TaskDoisApplication`.

---

### Popular Receitas

Com a aplicação rodando, em outra sessão SSH, execute o script SQL incluído no repositório:

```bash
cd ~/gerenciaDoConfiguracao_taskDois
psql -h localhost -U app_user -d taskdois -f insert_receitasv4__add_table_cat.sql
```

Senha: `123456789`

Isso irá inserir 10 receitas de exemplo no banco de dados.

---

### Acessar Aplicação


**Acessar pelo navegador:**
```
http://177.44.248.32:8080
```

**Credenciais:**
- Login: `messi`
- Senha: `melhorquepele`

---

## Estrutura do Projeto

```
taskDois/
├── src/
│   ├── main/
│   │   ├── java/com/gerencia/taskdois/
│   │   │   ├── controller/         # Controllers (Login, Receita)
│   │   │   ├── model/               # Entidades JPA (Usuario, Receita)
│   │   │   ├── repository/          # Repositórios JPA
│   │   │   └── TaskDoisApplication.java
│   │   └── resources/
│   │       ├── application.properties
│   │       ├── templates/           # Views Thymeleaf
│   │       │   ├── login.html
│   │       │   └── receitas/
│   │       │       ├── lista.html
│   │       │       └── form.html
│   │       └── static/              # CSS/JS (se houver)
│   └── test/                        # Testes unitários
├── build.gradle                     # Configuração Gradle
├── gradlew                          # Gradle Wrapper (Linux/Mac)
├── gradlew.bat                      # Gradle Wrapper (Windows)
├── insert_receitasv4__add_table_cat.sql              # Script de dados exemplo
├── README.md                        # Este arquivo
└── DOCUMENTACAO.md                  # Documentação completa

```

---
# Gerenciador de Receitas

Esta é uma aplicação web desenvolvida em Java com Spring Boot para o gerenciamento de receitas (doces e salgadas). O sistema permite a autenticação de usuários e possui um CRUD completo para gerenciar as receitas cadastradas.

## 🛠 Tecnologias Utilizadas

- **Java 17**
- **Spring Boot 3.2.4**
- **Spring Data JPA**
- **Thymeleaf** (para as views em HTML)
- **PostgreSQL** (Banco de dados relacional)
- **Lombok** (para reduzir código boilerplate)
- **Gradle** (Gerenciador de dependências e build)

## ⚙️ Pré-requisitos

Antes de iniciar, certifique-se de ter instalado em sua máquina:

1. [Java Development Kit (JDK) 17](https://adoptium.net/) (O Gradle fará o download automático caso não encontre, através das Toolchains configuradas).
2. [PostgreSQL](https://www.postgresql.org/download/) rodando localmente na porta padrão (`5432`).

## 🗄️ Configuração do Banco de Dados

1. Abra seu SGBD (ex: pgAdmin) ou o terminal do PostgreSQL.
2. Crie um banco de dados vazio com o nome `taskdois`:
   ```sql
   CREATE DATABASE taskdois;
   ```
3. Crie o usuário `app_user` com a senha configurada:
   ```sql
   CREATE USER app_user WITH PASSWORD '123456789';
   GRANT ALL PRIVILEGES ON DATABASE taskdois TO app_user;
   ```
4. **(Opcional)** Para popular a tabela `receita` com 10 registros de exemplo, execute o script SQL:
   ```shell
   psql -U app_user -d taskdois -f insert_receitas.sql
   ```
   Ou conecte-se ao banco e execute manualmente os inserts do arquivo `insert_receitas.sql`.

## 🚀 Como Executar a Aplicação

A aplicação utiliza o Gradle Wrapper, então você não precisa ter o Gradle instalado na sua máquina. 

Para rodar a aplicação, abra seu terminal na raiz do projeto e execute o comando abaixo:

### No Windows:
```shell
.\gradlew.bat bootRun
```

### No Linux / macOS:
```shell
./gradlew bootRun
```

*Nota: Na primeira vez que for executado, o Gradle fará o download de todas as dependências e do JDK 17 (se necessário), o que pode demorar alguns minutos.*

## 🧑‍💻 Acesso ao Sistema

Quando a aplicação estiver rodando (você verá a mensagem `Started TaskDoisApplication` no console), abra o seu navegador e acesse:

**URL:** `http://localhost:8080`

### Credenciais de Login

O sistema cria automaticamente um usuário administrador na primeira execução. Utilize as seguintes credenciais para acessar:

- **Login:** `messi`
- **Senha:** `melhorquepele`

## ✨ Funcionalidades

- **Login:** Autenticação de usuário com validação de status ativo/inativo.
- **Listagem de Receitas:** Exibe todas as receitas com seus detalhes (nome, descrição, custo, data de registro, tipo doce/salgada).
- **Cadastro:** Tela para criar novas receitas.
- **Edição:** Permite alterar dados de uma receita existente.
- **Exclusão:** Remove uma receita do banco de dados (com confirmação na tela).
- **Logout:** Encerra a sessão do usuário de forma segura.

## 🌐 Implantação em Servidor/VM

### 1️⃣ Acessar a VM

Conecte-se à sua máquina virtual via SSH:

```bash
ssh usuario@ip-da-vm
```

### 2️⃣ Instalar as Ferramentas Necessárias

#### Instalar Java 17
```bash
sudo apt update
sudo apt install openjdk-17-jdk -y
java -version
```

#### Instalar PostgreSQL
```bash
sudo apt install postgresql postgresql-contrib -y
sudo systemctl start postgresql
sudo systemctl enable postgresql
```

#### Configurar PostgreSQL
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

#### Configurar acesso remoto ao PostgreSQL (opcional)
Edite o arquivo de configuração:
```bash
sudo nano /etc/postgresql/*/main/postgresql.conf
```
Altere a linha:
```
listen_addresses = '*'
```

Edite o arquivo de autenticação:
```bash
sudo nano /etc/postgresql/*/main/pg_hba.conf
```
Adicione:
```
host    all             all             0.0.0.0/0               md5
```

Reinicie o PostgreSQL:
```bash
sudo systemctl restart postgresql
```

### 3️⃣ Implantar o Projeto

#### Clonar o repositório (ou transferir os arquivos)
```bash
git clone <url-do-repositorio>
cd taskDois
```

Ou transfira os arquivos via SCP:
```bash
scp -r /caminho/local/taskDois usuario@ip-da-vm:/home/usuario/
```

#### Executar a aplicação

**Modo desenvolvimento:**
```bash
./gradlew bootRun
```

**Gerar JAR e executar em produção:**
```bash
./gradlew build
java -jar build/libs/taskDois-0.0.1-SNAPSHOT.jar
```

#### Executar como serviço (systemd)

Crie o arquivo de serviço:
```bash
sudo nano /etc/systemd/system/taskdois.service
```

Conteúdo:
```ini
[Unit]
Description=Task Dois Spring Boot Application
After=syslog.target network.target

[Service]
User=usuario
ExecStart=/usr/bin/java -jar /home/usuario/taskDois/build/libs/taskDois-0.0.1-SNAPSHOT.jar
SuccessExitStatus=143
Restart=always
RestartSec=10

[Install]
WantedBy=multi-user.target
```

Ative e inicie o serviço:
```bash
sudo systemctl daemon-reload
sudo systemctl enable taskdois
sudo systemctl start taskdois
sudo systemctl status taskdois
```

#### Configurar Firewall
```bash
sudo ufw allow 8080/tcp
sudo ufw enable
```

### 4️⃣ Acessar a Aplicação

Acesse pelo navegador:
```
http://ip-da-vm:8080
```

### 🔒 Considerações de Segurança para Produção

- Altere as senhas padrão do banco de dados
- Configure HTTPS com certificado SSL
- Use um proxy reverso (Nginx/Apache) na porta 80/443
- Configure variáveis de ambiente para credenciais sensíveis
- Implemente backup automático do banco de dados

---
Desenvolvido como atividade prática ("Task Dois").
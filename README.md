# FinTrack

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![JavaFX](https://img.shields.io/badge/JavaFX-107C41?style=for-the-badge&logo=java&logoColor=white)
![SQLite](https://img.shields.io/badge/SQLite-07405E?style=for-the-badge&logo=sqlite&logoColor=white)
![Apache Maven](https://img.shields.io/badge/Apache_Maven-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white)
![JUnit 5](https://img.shields.io/badge/JUnit_5-25A162?style=for-the-badge&logo=junit5&logoColor=white)
![Windows](https://img.shields.io/badge/Windows-0078D6?style=for-the-badge&logo=windows&logoColor=white)

FinTrack é uma aplicação desktop para gerenciamento financeiro pessoal desenvolvida em Java e JavaFX, utilizando SQLite como mecanismo de persistência local. O projeto foi projetado com foco em simplicidade operacional, organização arquitetural em camadas e uma interface gráfica moderna e responsiva.


## Sumário

- [Visão Geral](#visão-geral)
- [Funcionalidades](#funcionalidades)
- [Arquitetura do Projeto](#arquitetura-do-projeto)
- [Tecnologias Utilizadas](#tecnologias-utilizadas)
- [Estrutura de Diretórios](#estrutura-de-diretórios)
- [Pré-requisitos](#pré-requisitos)
- [Como Executar a Aplicação](#como-executar-a-aplicação)
  - [Opção 1: Executável Autossuficiente (Windows)](#opção-1-executável-autossuficiente-windows)
  - [Opção 2: A Partir do Código-Fonte (Maven)](#opção-2-a-partir-do-código-fonte-maven)
  - [Opção 3: Pacote JAR Sombreado (Fat JAR)](#opção-3-pacote-jar-sombreado-fat-jar)
- [Testes Automatizados](#testes-automatizados)
- [Geração de Pacotes de Distribuição](#geração-de-pacotes-de-distribuição)
- [Licença](#licença)


## Visão Geral

O FinTrack tem como objetivo auxiliar no controle diário de receitas e despesas. A aplicação funciona totalmente offline, armazenando as informações em um banco de dados relacional embarcado (`fintrack.db`), garantindo a privacidade dos dados financeiros do usuário.

A interface gráfica foi concebida utilizando FXML e JavaFX CSS, adotando uma paleta visual baseada em tons de vinho imperial (`#8A151B`), grafite escuro e superfícies limpas, com indicadores visuais dedicados para cada tipo de movimentação financeira.



## Funcionalidades

- **Registro de Movimentações:** Cadastro de transações financeiras com descrição, notas complementares opcionais, valor monetário, data e categorização (Receita ou Despesa).
- **Histórico e Listagem:** Tabela interativa com redimensionamento de colunas, exibindo data, descrição, valor formatado e tipo da operação.
- **Edição de Transações:** Permite alterar dados de qualquer lançamento previamente registrado através do botão dedicado ou por meio de duplo clique diretamente na linha da tabela.
- **Exclusão de Registros:** Remoção segura de registros selecionados da base de dados.
- **Dashboard e Indicadores:** Painel consolidado contendo:
  - Total acumulado de receitas.
  - Total acumulado de despesas.
  - Saldo líquido atual em conta, com destaque visual de acordo com o saldo (positivo ou negativo).
  - Contagem total de transações registradas.
- **Interface Gráfica e Modo Console:** Disponibilidade de interface gráfica via JavaFX e também de uma interface alternativa em linha de comando (CLI) para fins didáticos e testes de fluxo.


## Arquitetura do Projeto

A solução segue princípios de separação de responsabilidades (SoC) organizados no padrão MVC (Model-View-Controller) aliado a padrões de acesso a dados:

- **Model (`fintrack.model`):** Representa as entidades do domínio financeiro (`Transacao` e `TransacaoMensal`).
- **Data Access Object & Repository (`fintrack.dao`, `fintrack.repository`):**
  - `RepositorioGenerico<T>`: Interface genérica com operações CRUD (`adicionar`, `atualizar`, `remover`, `listarTodos`, `adicionarTodos`).
  - `TransacaoDAO`: Implementação concreta que executa comandos SQL preparados (`PreparedStatement`) sobre o SQLite.
  - `Conexao`: Gerenciador da conexão JDBC e responsável pela criação automática do esquema relacional (`CREATE TABLE IF NOT EXISTS`).
- **Controllers (`fintrack.controller`):**
  - `MainController`: Gerencia o ciclo de vida do painel principal e a sincronização entre abas.
  - `ListaController`: Controla a exibição, paginação, exclusão e modal de edição da tabela.
  - `FormController`: Validação de entradas e submissão de novos lançamentos.
  - `RelatorioController`: Agregação e apresentação dos indicadores financeiros.
  - `FinTracker`: Controlador com regras de negócio e cálculos de saldo.
- **Views & Estilos (`resources/views`, `resources/styles`):** Arquivos FXML declarativos associados a uma folha de estilos CSS modular.


## Tecnologias Utilizadas

- **Linguagem:** Java 17+ (testado e compatível com OpenJDK / Oracle JDK 21).
- **Interface Gráfica:** JavaFX (Controles, Gráficos e FXML).
- **Banco de Dados:** SQLite 3 via driver JDBC (`org.xerial:sqlite-jdbc`).
- **Gerenciador de Dependências e Build:** Apache Maven.
- **Framework de Testes:** JUnit 5 (Jupiter API e Jupiter Engine).
- **Empacotamento:** Maven Shade Plugin e JDK `jpackage`.


## Estrutura de Diretórios

```text
fintrack/
|-- .gitignore                               # Regras de exclusão do Git para a raiz
|-- Criar-Atalho-Area-de-Trabalho.ps1        # Script para criar atalho na Área de Trabalho
|-- iniciar-fintrack.bat                     # Inicializador rápido para ambiente Windows
|-- app-icon.ico                             # Ícone de alta resolução da aplicação
|-- README.md                                # Documentação técnica do projeto
`-- fintrack/
    |-- pom.xml                              # Descritor Maven e plugins de build
    |-- iniciar-fintrack.vbs                 # Inicializador silencioso sem janela de console
    `-- src/
        |-- main/
        |   |-- java/
        |   |   `-- fintrack/
        |   |       |-- app/                 # FinApp, AppLauncher e Main (CLI)
        |   |       |-- controller/          # Controladores da UI e de fluxo
        |   |       |-- dao/                 # Camada DAO e conexão com o banco
        |   |       |-- exceptions/          # Exceções de negócio
        |   |       |-- model/               # Entidades de domínio
        |   |       |-- repository/          # Interfaces de repositório genéricas
        |   |       `-- utils/               # Formatadores de moeda e data
        |   `-- resources/
        |       |-- images/                  # Logotipos e ícones do sistema
        |       |-- styles/                  # style.css (Design System da aplicação)
        |       `-- views/                   # Telas FXML (main, lista, form, relatorio)
        `-- test/
            `-- java/
                `-- fintrack/                # Testes unitários (TransacaoDAOTest)
```

---

## Pré-requisitos

Para executar a partir do código-fonte ou compilar o projeto:
- Java Development Kit (JDK) 17 ou superior instalado e configurado no `PATH`.
- Apache Maven 3.8+ instalado e configurado no `PATH`.

*Nota: Para usuários finais que utilizarem a versão empacotada com runtime embutido (distribuição Windows), não é necessária a instalação prévia de Java ou Maven.*

---

## Como Executar a Aplicação

### Opção 1: Executável Autossuficiente (Windows)

1. Baixe o pacote compactado na aba de **Releases** do repositório (ex: `FinTrack-Windows-v1.0.zip`).
2. Extraia o conteúdo em uma pasta de sua preferência.
3. Execute o arquivo `FinTrack.exe` com dois cliques.
4. Opcionalmente, execute o script `Criar-Atalho-Area-de-Trabalho.ps1` para fixar o atalho com o ícone oficial na Área de Trabalho.

### Opção 2: A Partir do Código-Fonte (Maven)

Clone o repositório e navegue até a pasta do módulo:

```bash
git clone https://github.com/SEU-USUARIO/fintrack.git
cd fintrack/fintrack
```

Execute a aplicação através do plugin oficial do JavaFX:

```bash
mvn clean javafx:run
```

Para executar a versão em linha de comando (CLI interativo):

```bash
mvn compile exec:java -Dexec.mainClass="fintrack.app.Main"
```

### Opção 3: Pacote JAR Sombreado (Fat JAR)

Gere o arquivo `.jar` contendo todas as dependências incluídas:

```bash
mvn clean package -DskipTests
```

O arquivo executável será gerado em `target/fintrack.jar`. Para executá-lo:

```bash
java -jar target/fintrack.jar
```

---

## Testes Automatizados

O projeto conta com suíte de testes unitários automatizados cobrindo as operações de persistência e regras de cálculo do DAO utilizando SQLite em memória:

Para executar os testes:

```bash
cd fintrack
mvn test
```

Os relatórios detalhados da execução do Surefire são gravados em `target/surefire-reports/`.

---

## Geração de Pacotes de Distribuição

Para gerar uma imagem nativa da aplicação com o runtime Java embarcado utilizando o utilitário `jpackage` do JDK:

```bash
# 1. Empacotar o JAR sombreado
mvn clean package -DskipTests

# 2. Gerar a imagem de distribuição Windows
jpackage --name FinTrack \
  --input target \
  --main-jar fintrack.jar \
  --main-class fintrack.app.AppLauncher \
  --type app-image \
  --icon app-icon.ico \
  --dest dist
```

O diretório `dist/FinTrack` resultante conterá o executável `FinTrack.exe` pronto para distribuição, sem exigir máquina virtual Java na máquina de destino.


## Licença

Este projeto é disponibilizado sob a licença MIT. Consulte o arquivo de licença correspondente para mais informações.

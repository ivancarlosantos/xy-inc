<p align="center">
 <img src="https://img.shields.io/static/v1?label=Dev&message=Ivan Santos&color=8257E5&labelColor=000000" alt="@ivan_santos" />
 <img src="https://img.shields.io/static/v1?label=Tipo&message=Desafio&color=867E12&labelColor=00A654" alt="Desafio" />
 <img src="https://img.shields.io/static/v1?label=Status&message=Em Desenvolvimento&color=&labelColor=90876I" alt="Status" />
</p>

> # API de Serviço para Localização de Pontos de Interesses (POI's)

* **Desafio por** [@Luizalabs](https://www.linkedin.com/company/luizalabs/)

> ## Descrição do Projeto
O projeto consiste na construção de uma API para auxiliar na localização de pontos de interesses
(POI - Points Of Interests).

O intuito é, cadastrar locais e estabelecimentos como pontos de interesse, simulando um GPS localizador

Sendo assim, foram criados os serviços:
* Cadastro de Ponto de Interesse
* Listar Pontos de Interesse
* Encontrar Local de Ponto de Interesse por Filtro (por letra/terminação inicial e/ou final)  
* Listar Pontos por proximidade: onde relaciona as coordenadas X e Y bem como para uma distância de um ponto máximo (max), onde esse serviço mostrará estabelecimentos a partir de seu ponto de referência


> ## Tecnologias Utilizadas
As seguintes ferramentas foram utilizadas na construção do projeto
- [Java - v17](https://www.oracle.com/br/java/technologies/downloads/)
- [Maven](https://maven.apache.org/)
- [Spring Boot](https://spring.io/)
- [Postgres](https://www.postgresql.org/)
- [Adminer](https://www.adminer.org/)
- [Docker](https://www.docker.com/get-started/)
- [JUnit](https://junit.org/junit5/)
- [Pitest](https://pitest.org/)
- [Mockito](https://site.mockito.org/)
- [MockMVC](https://docs.spring.io/spring-framework/reference/testing/spring-mvc-test-framework.html)
- [Testcontainers](https://testcontainers.com/getting-started/)
- [Swagger Docs](https://swagger.io/)

> ## Práticas adotadas

- API REST
- Containerização
- Injeção de Dependências
- Testes de Unidade, Integração e cobertura
- Testcontainers
- Tratamento de respostas de erro
- Implementação de esteira CI/CD Actions

> ## Instalação
Tenha instalado em seu sistema operacional:
> - [JAVA NA VERSÃO 17 OU SUPERIOR]()
>
> - [MAVEN]()
>
> - [DOCKER]()

> ## Como Executar

- Clonar repositório `GIT`
```bash
$ git clone https://github.com/ivancarlosantos/xy-inc.git
```
- Ir até o diretório/pasta localizando a aplicação
```bash
$ cd [caminho onde realizou o clone]
```
- Construir o projeto

_Caso desejar usar container Docker do Postgres para persistência e o Adminer SGBD, na raíz do projeto, executar:_
```bash
$ docker-compose up -d
```
> ## Adminer
- _Adminer é um serviço de SGBD para acesso e gerência de dados desenvolvido em PHP_ 

Para acesso aos dados persistidos, via ` Adminer `, acesse:
````bash
$ http://localhost:15432
````
Com o container do _**Adminer SGBD**_ ativo, preencha os seguintes campos:
> [**System**]() - [_**PostgreSQL**_]()
>
> [**Server**]() - [_**db**_]()
> 
> [**Usuário**]() - [_**postgres**_]()
> 
> [**Senha**]() - [_**12345**_]()

> ## DEMO

- Caso desejar testar a aplicação em ambiente `dev`, com o Docker ativo, execute:
````bash
$ docker run --name=poi -p 8080:8080 -d devmenorzera/poi
````
- Para acessar a aplicação, via [Swagger-UI](http://localhost:8080/swagger-ui/index.html#/):
````bash
$ http://localhost:8080/swagger-ui/index.html#/
````
_``PS: Em caso de execução da aplicação em ambiente dev, os dados serão salvos em memória (H2)``_

Com a base de dados ativa, inicializar o projeto:

- Na raíz do projeto, executar a aplicação (caso tenha o maven instalado em sua máquina)
```bash
$ mvn spring-boot:run
```
- ou use o wrapper run
```bash
$ .\mvnw spring-boot:run
```
# API Endpoints
API poderá ser acessada em [http://localhost:8080/poi/test](http://localhost:8080/poi/test)

Ou se preferir, você pode usar o [SWAGGER](http://localhost:8080/swagger-ui/index.html) como client HTTP

Em caso de sucesso a API retornará a seguinte resposta
```bash
$ http GET :8080/poi/test

  {
    "address": "ip address",
    "date": "Current Date"
  }
```

Para fazer as requisições HTTP abaixo, foi utilizada a ferramenta [Postman](https://www.postman.com/):

- Cadastrar um Ponto de Interesse [[POST]]()
```bash
$ http POST :8080/poi/save localPoi="LOCAL" coordX=10.0 coordY=20.0

  {
    "localPoi": "LOCAL",
    "coordX": 10.0,
    "coordY": 20.0
  }
```

- Listar Pontos de Interesse Cadastrados [[GET]]()
```bash
$ http GET :8080/poi/list

[
 {
    "localPoi": "LOCAL A",
    "coordX": 10.0,
    "coordY": 20.0
  },
   {
    "localPoi": "LOCAL B",
    "coordX": 20.0,
    "coordY": 30.0
  }
]
```
- Busca Ponto de Interesse por Local [[GET]]()

`PS: Você pode encontrar/filtrar por inicio, meio ou final do nome local`
```bash
$ http GET :8080/poi/find?local=LOCAL

 {
    "localPoi": "LOCAL",
    "coordX": 10.0,
    "coordY": 20.0
 }
```
- Procura Ponto de Interesses por referência [[GET]]()
```bash
$ http GET :8080/poi/search?x=20&y=10&max=10
[
  {
    "localPoi": "LOCAL",
    "coordX": 10.0,
    "coordY": 20.0
  }
] 
```

- Atualizar Ponto de Interesse [[PUT]]()
```bash
$ http PUT 8080:poi/update/1 localPoi="LOCAL" coordX=10.0 coordY=20.0

 {
    "localPoi": "LOCAL",
    "coordX": 10.0,
    "coordY": 20.0
 }
```
> ## Teste de Unidade
_Os teste de unidade podem ser executados para verificação de cobertura da aplicação_

Para execução dos teste de unidade e integração, realize o seguinte comando:
```bash
$ mvn clean test 
```

Para execução dos testes para verificação de cobertura, realize o seguinte comando:
```bash
$ mvn test-compile org.pitest:pitest-maven:mutationCoverage
```

O link de cobertura pode ser encontrado no diretório

```bash
$ cd target/pit-reports/index.html
```

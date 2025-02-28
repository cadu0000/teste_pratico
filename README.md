### Para executar é necessário criar um arquivo .env na pasta raiz do projeto com as configurações do banco de dados seguindo o padrão:
> DB_URL=
> 
> DB_DRIVER=
> 
> USER_LOGIN=
> 
> USER_PASSWORD=
> 
> DATABASE_NAME=
> 
> ROOT_PASSWORD=

### Posteriormente utilizar o comando a seguir para rodar o bando de dados via container docker:

> *docker-compose up --build -d*

### Explicação sobre possíveis questionamentos em relação à lógica do código:
O código foi feito para fins de estudo e para preencher os requisitos de um teste, no teste não foi solicitada a conexão com um banco de dados, portanto,
há métodos (essencialmente os de consulta) que não estão o acessando para retornar os valores, mas pegando diretamente da lista ou map criado.

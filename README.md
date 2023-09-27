# Housekeeping

## Sobre

Decidi realizar este projeto como parte do meu portfólio, uma oportunidade para demonstrar minhas habilidades. 
Ele foi desenvolvido utilizando o Framework Spring, persistido com PostgreSQL, e a interface web foi construída com o uso do Angular.

## Ideia/Problemática

A ideia surgiu quando eu frequentava meu antigo emprego como auxiliar de recepção em um Resort.
Todos os dias pela manhã e durante o dia, a Governanta era obrigada a vir à recepção para receber atualizações sobre os check-ins e check-outs.
Imprimíamos um relatório que não era muito claro e de fácil leitura, e entregávamos a ela. Isso precisava ocorrer várias vezes ao dia, pois qualquer mudança nas entradas do dia precisava ser repassada à Governanta.

Minha solução foi pensada como uma interface amigável para o controle e monitoramento das rotinas de limpeza das acomodações. As acomodações disponíveis no hotel seriam adicionadas previamente pelo administrador do sistema, e as reservas chegariam por meio de uma conexão com o software de reservas utilizado pelo hotel.

Em cada card que representa uma acomodação, é possível ver o status de ocupação do quarto, baseado em um Enum (Ocupado, Desocupado e Manutenção). Também estão disponíveis as datas de entrada e saída do hóspede, a quantidade de hóspedes dividida em adultos/crianças com +5 anos/crianças com -5 anos, e o status de limpeza do quarto, também baseado em um Enum (Sujo, Limpo).

Ao clicar no card, um modal é aberto com dados extras sobre a acomodação, como necessidade de cama extra, berço ou solicitação de limpeza, além de um campo de texto para observações relevantes.

## Pontos Interessantes

Alguns aspectos interessantes deste projeto incluem:

- Utilizei um Enum `isActive` para permitir apenas uma reserva por vez em cada acomodação.
- Organizei uma rotina utilizando o `Scheduled` para alterar esse status.
- Criei um componente no Angular para consulta de registros, no caso de reservas passadas.
- Diagrama dos endpoints utilizados, bem como das classes e pacotes, estão disponíveis na pasta [Documents](https://github.com/Klaus-Edu/HousekeepingProject/tree/main/documents).

## Tecnologias

- Java 17
- Spring Boot (3.1.3)

  Bibliotecas:
  - Spring Data JPA
  - Spring Validator
  - Spring Starter Web
  - PostgreSQL Driver
  - Lombok
  - Spring Doc OpenAPI (Swagger)

- PostgreSQL
- Angular (16.2.1)

  Módulos:
  - BrowserModule
  - HttpClientModule
  - RouterModule
  - NgbModule
  - FormsModule

## Próximos Passos

Os próximos passos para este projeto incluem:

- Implementar autenticação de usuários para ter maior controle sobre alterações de informações.
- Melhorar a lógica de disponibilização de uma reserva por acomodação.
- Tornar o frontend mais agradável e amigável.

## Como rodar o Projeto localmente

- Certifique-se de ter instalado em sua máquina o Docker e Docker-Compose, e que estejam atualizados;
- Abra um prompt de comando na pasta raiz do repositorio e rode o comando "docker-compose up";
- Acesse a aplicação pela url "localhost:8082"
- Para finalizar a aplicação utilize o comando "docker-compose down";

# 6. Requisitos
Requisitos e especificações do Sistema Coffee Cake.

## 6.1 Requisitos Funcionais

<br>

|Identificador|Descrição|
|-|-|
|R1|Cadastro/login para funcionários e gerentes.|
|R2|Cadastro de produtos e combos.|
|R3|Realização, edição e cancelamento de vendas.|
|R4|Controle de estoque de ingredientes.|
|R5|Relatórios de vendas por período, produto e funcionário.|

<br>

## 6.2 Requisitos Não Funcionais

<br>

|Identificador|Descrição|Requisitos Relacionados|
|-|-|-|
|RN1|Tela otimizada para balcão/atendimento rápido.|R1, R2|
|RN2|Suporte a impressora de cupom.||
|RN3|Sistema disponível durante o funcionamento.|R4|
|RN4|Backup automático diário.|R5|

<br>

## 6.3 Regras de Negócios

<br>

|Identificador|Descrição|Requisitos Relacionados|
|-|-|-|
|RN1|Comanda obrigatória para pedidos no salão.|R3|
|RN2|Os produtos ofertados variam de acordo com o horário (ex: café da manhã, tarde).|R4|
|RN3|Estoque de ingredientes é atualizado em tempo real a cada venda.|R3, R4|
|RN4|Cancelamento de venda apenas por gerente/supervisor.|R3|

<br>


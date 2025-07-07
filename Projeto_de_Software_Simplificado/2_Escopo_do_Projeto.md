# 2. Escopo do Projeto

<br>

<p align="justify">O sistema “Coffee Cake” tem como objetivo principal oferecer uma solução digital para o gerenciamento de informações de uma cafeteria, com foco na organização eficiente de dados relacionados a comidas, bebidas e outros elementos operacionais como cadastro/login de funcionários e gerentes. Por meio de um banco de dados, o sistema permitirá o armazenamento, a consulta e a atualização dessas informações de forma estruturada e segura.

<br>
<br>

### 2.1 Problema a ser solucionado
<br>

<p align="justify">Atualmente, muitas cafeterias utilizam métodos manuais ou sistemas fragmentados para gerenciar a mercadoria e acompanhar os pedidos dos clientes. Isso pode resultar principalmente em perda de dados e retrabalho desnecessário. O “Coffee Cake” busca resolver esse problema centralizando todas as informações em um único sistema com base em banco de dados, promovendo maior organização, agilidade e confiabilidade nas operações do dia a dia.

<br>
<br>

## 2.2 Objetivos do Sistema

<br>

- Organizar dados de produtos e ingredientes disponíveis de forma simples.
- Armazenar informações dos funcionários e gerentes para fins de cadastro/login.
- Permitir o acesso a dois tipos de usuários: os funcionários (irão cadastrar clientes, categorias, alimentos, fazer consultas, inclusão e exclusão de dados) e o administrador (poderá cadastrar funcionários além de ter acesso a todas as funcionalidades do sistema).

<br>
	
## 2.3 Itens Incluídos no Projeto

<br>

O escopo do sistema Coffee Cake possui:
- Modelagem e implementação de um banco de dados utilizando modelagem conceitual, lógica e física.
- Funcionalidades de cadastro, login, listagem, edição e exclusão para produtos, clientes e funcionários, ou seja, criar, ler, atualizar e deletar (CRUD).
- Documentação do modelo de dados e funcionalidades do sistema.
- Interface para inserção, consulta e atualização de dados. (simples).

<br>

## 2.4 Itens Excluídos do Projeto

<br>

O projeto não inclui:
- Processos de pagamentos online ou integração com sistemas de pagamento.
- Funcionalidades avançadas de relatórios ou inteligência de dados
- Integração com sistemas externos.
- Perfil de cliente, onde o mesmo poderia acessar o banco de dados.

<br>

## 2.5 Fronteiras e Interfaces

<br>

<p align="justify">O sistema será desenvolvido  como um aplicativo simples e funcionará de forma independente, sem necessidade de integração com outros sistemas externos. Os dados serão armazenados exclusivamente no banco de dados local (SQLite). A interação com o sistema se dará por meio de páginas de listagem, voltadas apenas ao uso interno da cafeteria.
<br>
<br>

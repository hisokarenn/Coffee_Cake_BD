# 2. Escopo do Projeto
O sistema “Coffee Cake” tem como objetivo principal oferecer uma solução digital para o gerenciamento de informações de uma cafeteria, com foco na organização eficiente de dados relacionados a comidas, bebidas e outros elementos operacionais como cadastro/login de funcionários e gerentes. Por meio de um banco de dados, o sistema permitirá o armazenamento, a consulta e a atualização dessas informações de forma estruturada e segura.

### 2.1 Problema a ser solucionado
Atualmente, muitas cafeterias utilizam métodos manuais ou sistemas fragmentados para gerenciar a mercadoria e acompanhar os pedidos dos clientes. Isso pode resultar principalmente em perda de dados e retrabalho desnecessário. O “Coffee Cake” busca resolver esse problema centralizando todas as informações em um único sistema com base em banco de dados, promovendo maior organização, agilidade e confiabilidade nas operações do dia a dia.

## 2.2 Objetivos do Sistema
- Organizar dados de comida e bebidas disponíveis no cardápio.
- Armazenar informações dos funcionários e gerentes para fins de cadastro/login.
- Permitir o gerenciamento de categorias de produtos (ex: doces, cafés, salgados, sucos).
- Permitir o acesso a dois tipos de usuários: os funcionários (irão cadastrar clientes, categorias, alimentos, fazer consultas, inclusão e exclusão de dados) e o administrador (poderá cadastrar funcionários além de ter acesso a todas as funcionalidades do sistema).
	
## 2.3 Itens Incluídos no Projeto
O escopo do sistema Coffee Cake possui:
- Modelagem e implementação de um banco de dados utilizando modelagem conceitual, lógica e física.
- Interface web para inserção, consulta e atualização de dados. (simples).
- Funcionalidades de cadastro, login, listagem, edição e exclusão para produtos, categorias, clientes e funcionários, ou seja, criar, ler, atualizar e deletar (CRUD).
- Validação de dados na inserção e atualização de registros.
- Documentação do modelo de dados e funcionalidades do sistema.

## 2.4 Itens Excluídos do Projeto
O projeto não inclui:
- Processos de pagamentos online ou integração com sistemas de pagamento.
- Funcionalidades avançadas de relatórios ou inteligência de dados
- Integração com sistemas externos.
- Perfil de cliente, onde o mesmo poderia acessar o banco de dados.

## 2.5 Fronteiras e Interfaces
O sistema será desenvolvido  como um site simples via navegador e funcionará de forma independente, sem necessidade de integração com outros sistemas externos. Os dados serão armazenados exclusivamente no banco de dados local ou em um servidor de banco de dados definido durante a implementação. A interação com o sistema se dará por meio de páginas de listagem, voltadas apenas ao uso interno da cafeteria.

# sistemadeenviodelote

### Sistema de Envio de Lote Contabilidade para Nasajon

 um sistema em Java para envio de lotes contábeis para o sistema Nasajon. Este aplicativo desktop terá interfaces para gerenciar o envio de dados contábeis em lote.

## Explicação do Sistema

Este sistema de envio de lotes contábeis para o Nasajon foi desenvolvido em Java utilizando a biblioteca Swing para a interface gráfica. O sistema permite:

1. **Autenticação de usuários** - Tela de login para acesso ao sistema
2. **Dashboard** - Visão geral dos lotes pendentes, enviados e com erro
3. **Criação de lotes** - Interface para criar e enviar novos lotes contábeis
4. **Consulta de lotes** - Visualização e gerenciamento dos lotes enviados
5. **Configurações** - Parametrização da conexão com o sistema Nasajon


### Principais Funcionalidades

- **Envio de lotes contábeis**: Permite enviar arquivos de lançamentos contábeis, notas fiscais e folha de pagamento para o sistema Nasajon
- **Monitoramento de status**: Acompanhamento do status de processamento dos lotes
- **Reenvio de lotes com erro**: Possibilidade de reenviar lotes que apresentaram falhas
- **Configuração de conexão**: Interface para configurar os parâmetros de conexão com o Nasajon


### Estrutura do Projeto

- **Pacote `com.contabilidade.ui`**: Contém todas as classes de interface gráfica
- **Pacote `com.contabilidade.model`**: Classes de modelo de dados
- **Pacote `com.contabilidade.service`**: Classes de serviço que implementam a lógica de negócio


### Observações

Este é um protótipo funcional que simula a integração com o sistema Nasajon. Em um ambiente de produção, seria necessário implementar a integração real com a API do Nasajon, além de adicionar persistência de dados em um banco de dados.

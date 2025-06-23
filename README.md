Aluno: Adriano Tavares  
RA: 811389  
---

# Sistema de Gerenciamento de Veículos

Um sistema web para cadastro e consulta de veículos, lojas, clientes e propostas de compra, implementado com **Spring Boot**, **Spring Data JPA**, **Thymeleaf** e **Spring Security**.

## Objetivos e Requisitos

1. **R1: CRUD de Clientes**  
   - Operações: Criar, Ler, Atualizar e Excluir clientes.  
   - Acesso restrito a usuários com papel **ADMIN**.

2. **R2: CRUD de Lojas**  
   - Operações: Criar, Ler, Atualizar e Excluir lojas.  
   - Acesso restrito a usuários com papel **ADMIN**.

3. **R3: Cadastro de Veículo para Venda**  
   - Após login como **LOJA**, cadastrar veículo com:  
     - **CNPJ** da loja  
     - **Placa**  
     - **Modelo**  
     - **Chassi**  
     - **Ano**  
     - **Quilometragem**  
     - **Descrição**  
     - **Valor**  
     - **Fotos** (até 10 imagens)

4. **R4: Listagem Pública de Veículos**  
   - Exibição de todos os veículos em página única (acesso público).  
   - Filtro por modelo.

5. **R5: Proposta de Compra de Veículo**  
   - Após login como **CLIENTE**, ao visualizar um veículo (R4):  
     - Informar **valor** e **condições de pagamento**  
     - Registrar a **data** da proposta  
   - Restringe **uma única proposta “ABERTO” por veículo e por cliente**.

6. **R6: Listagem de Veículos da Loja**  
   - Após login como **LOJA**, exibir todos os veículos cadastrados pela loja.

7. **R7: Listagem de Propostas do Cliente**  
   - Após login como **CLIENTE**, listar todas as suas propostas com status:  
     - **ABERTO** (em análise pela loja)  
     - **NÃO ACEITO**  
     - **ACEITO**

8. **R8: Análise e Decisão de Propostas pela Loja**  
   - Após login como **LOJA**, para cada proposta:  
     - Atualizar status para **NÃO ACEITO** ou **ACEITO**  
     - Notificar o cliente **por e-mail** sobre a decisão  
     - Se **NÃO ACEITO**, opcionalmente incluir **contra-proposta** (valor + condições)  
     - Se **ACEITO**, agendar reunião (videoconferência) e incluir **link** no e-mail

9. **R9: Internacionalização**  
   - Suporte a, no mínimo, **dois idiomas** (Português + outro à escolha).

10. **R10: Validação e Tratamento de Erros**  
    - Validação de campos (bean validation) e mensagens amigáveis.

---


## 📚 Fluxos Principais

1. **Administrador**  
   - Faz login → Gerencia (CRUD) **Clientes** e **Lojas**.

2. **Loja**  
   - Faz login → Cadastra veículos → Visualiza suas propostas recebidas → Atualiza status.

3. **Cliente**  
   - Faz login → Visualiza catálogo público de veículos → Envia proposta (ABERTO) → Acompanha status no painel de propostas.

---

## 🌐 Internacionalização

- Mensagens externas em `messages.properties`  
- Suporte a **pt_BR** e **en_US** (exemplo)

---

> **Aluno:** Adriano Tavares  
> **RA:** 811389  

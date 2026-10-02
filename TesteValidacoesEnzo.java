public class TesteValidacoesEnzo {

    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("   INICIANDO BATERIA DE TESTES DE VALIDAÇÃO");
        System.out.println("==================================================\n");

        testarPedidoFechado();
        System.out.println("\n--------------------------------------------------\n");

        testarLimiteOrcamentario();
        System.out.println("\n--------------------------------------------------\n");

        testarAcessoNegado();
        System.out.println("\n--------------------------------------------------\n");

        testarCenarioSucesso();

        System.out.println("\n==================================================");
        System.out.println("   BATERIA DE TESTES FINALIZADA COM SUCESSO");
        System.out.println("==================================================");
    }

    private static void testarPedidoFechado() {
        System.out.println("[TESTE 1] Simulando alteração em pedido fechado...");
        Long idPedido = 1054L;
        boolean estaFechado = true;

        try {
            ValidadorSeguranca.validarEstadoPedido(idPedido, estaFechado);
            System.out.println("FALHA: O pedido deveria ter sido bloqueado.");
        } catch (PedidoFechadoException e) {
            System.out.println("SUCESSO: Exceção capturada com precisão!");
            System.out.println(" Mensagem: " + e.getMessage());
            System.out.println(" ID do Pedido Afetado: " + e.getIdPedido());
        } catch (DominioException e) {
            System.out.println(" Capturada pela classe base de domínio: " + e.getMessage());
        }
    }

    private static void testarLimiteOrcamentario() {
        System.out.println("[TESTE 2] Simulando estouro de orçamento de departamento...");
        String departamento = "Tecnologia da Informação";
        double valorSolicitado = 45000.00;
        double limiteMaximo = 30000.00;

        try {
            ValidadorSeguranca.validarLimiteOrcamentario(departamento, valorSolicitado, limiteMaximo);
            System.out.println("FALHA: O valor deveria ter excedido o limite.");
        } catch (LimiteDepartamentoExcedidoException e) {
            System.out.println("SUCESSO: Exceção orçamental capturada!");
            System.out.println(" Mensagem: " + e.getMessage());
            System.out.println(" Departamento: " + e.getNomeDepartamento());
            System.out.println(" Valor Solicitado: R$ " + String.format("%.2f", e.getValorSolicitado()));
            System.out.println(" Limite Permitido: R$ " + String.format("%.2f", e.getLimiteMaximo()));
            System.out.println(" Excesso Calculado: R$ " + String.format("%.2f", e.getExcessoCalculado()));
        } catch (DominioException e) {
            System.out.println(" Capturada pela classe base de domínio: " + e.getMessage());
        }
    }

    private static void testarAcessoNegado() {
        System.out.println("[TESTE 3] Simulando acesso sem privilégios suficientes...");
        String usuario = "operador_comum";
        String perfilAtual = "OPERADOR";
        String perfilRequerido = "ADMINISTRADOR";
        String operacao = "APROVAR_PEDIDO_AQUISICAO";

        try {
            ValidadorSeguranca.validarPermissaoUsuario(usuario, perfilAtual, perfilRequerido, operacao);
            System.out.println("FALHA: O utilizador não deveria ter acesso.");
        } catch (AcessoNegadoException e) {
            System.out.println("SUCESSO: Exceção de segurança capturada!");
            System.out.println(" Mensagem: " + e.getMessage());
            System.out.println(" Utilizador: " + e.getUsuario());
            System.out.println(" Operação Bloqueada: " + e.getOperacaoIntentada());
            System.out.println(" Nível Exigido: " + e.getNivelRequerido());
            System.out.println(" Data/Hora da Ocorrência: " + e.getDataHoraFormatada());
        } catch (DominioException e) {
            System.out.println(" Capturada pela classe base de domínio: " + e.getMessage());
        }
    }

    private static void testarCenarioSucesso() {
        System.out.println("[TESTE 4] Simulando validação com dados válidos...");
        try {
            ValidadorSeguranca.validarEstadoPedido(2001L, false);
            ValidadorSeguranca.validarLimiteOrcamentario("RH", 5000.00, 10000.00);
            ValidadorSeguranca.validarPermissaoUsuario("admin", "ADMINISTRADOR", "ADMINISTRADOR", "APROVAR_PEDIDO");
            System.out.println("SUCESSO: Todas as validações passaram sem lançar exceções.");
        } catch (DominioException e) {
            System.out.println("FALHA: Não deveria ter lançado exceção neste cenário: " + e.getMessage());
        }
    }
}
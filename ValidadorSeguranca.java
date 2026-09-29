public class ValidadorSeguranca {

    private static final double LIMITE_PADRAO_SISTEMA = 50000.00;

    public static void validarEstadoPedido(Long idPedido, boolean estaFechado) throws PedidoFechadoException {
        if (idPedido == null || idPedido <= 0) {
            throw new IllegalArgumentException("O identificador do pedido deve ser um número válido e maior que zero.");
        }
        if (estaFechado) {
            throw new PedidoFechadoException(idPedido);
        }
    }

    public static void validarEstadoPedido(Long idPedido, boolean estaFechado, String detalheMotivo) throws PedidoFechadoException {
        if (estaFechado) {
            throw new PedidoFechadoException(idPedido, detalheMotivo);
        }
    }

    public static void validarLimiteOrcamentario(String departamento, double valorSolicitado, double limiteMaximo)
            throws LimiteDepartamentoExcedidoException {
        if (valorSolicitado <= 0) {
            throw new IllegalArgumentException("O valor solicitado para a aquisição deve ser estritamente positivo.");
        }
        if (limiteMaximo <= 0) {
            limiteMaximo = LIMITE_PADRAO_SISTEMA;
        }
        if (valorSolicitado > limiteMaximo) {
            String nomeDept = (departamento != null && !departamento.isBlank()) ? departamento : "Geral";
            throw new LimiteDepartamentoExcedidoException(nomeDept, valorSolicitado, limiteMaximo);
        }
    }

    public static void validarPermissaoUsuario(String usuario, String perfilUsuario, String perfilRequerido, String operacao)
            throws AcessoNegadoException {
        if (usuario == null || usuario.isBlank()) {
            throw new AcessoNegadoException("Anónimo", operacao, perfilRequerido);
        }
        if (perfilUsuario == null || perfilUsuario.isBlank()) {
            throw new AcessoNegadoException(usuario, operacao, perfilRequerido);
        }
        if (!perfilUsuario.equalsIgnoreCase(perfilRequerido) && !"ADMINISTRADOR".equalsIgnoreCase(perfilUsuario)) {
            throw new AcessoNegadoException(usuario, operacao, perfilRequerido);
        }
    }

    public static boolean ehOperacaoPermitida(String perfilUsuario, String perfilRequerido) {
        if (perfilUsuario == null || perfilRequerido == null) {
            return false;
        }
        return perfilUsuario.equalsIgnoreCase(perfilRequerido) || "ADMINISTRADOR".equalsIgnoreCase(perfilUsuario);
    }
}
/**
 * Exceção de domínio lançada quando uma tentativa de cadastrar ou aprovar
 * um pedido de aquisição excede o limite financeiro teto estabelecido
 * para o departamento solicitante.
 *
 * @author EnzoTesta
 * @version 1.0
 */
public class LimiteDepartamentoExcedidoException extends DominioException {

    private static final long serialVersionUID = 1L;

    private final String nomeDepartamento;
    private final double valorSolicitado;
    private final double limiteMaximo;
    private final double excessoCalculado;

    /**
     * Construtor completo para registrar os detalhes orçamentários do departamento.
     *
     * @param nomeDepartamento Nome do departamento associado ao pedido.
     * @param valorSolicitado   Valor monetário total da requisição.
     * @param limiteMaximo      Limite orçamentário teto configurado no sistema.
     */
    public LimiteDepartamentoExcedidoException(String nomeDepartamento, double valorSolicitado, double limiteMaximo) {
        super(montarMensagemDetalhada(nomeDepartamento, valorSolicitado, limiteMaximo));

        this.nomeDepartamento = nomeDepartamento != null ? nomeDepartamento : "Não especificado";
        this.valorSolicitado = valorSolicitado;
        this.limiteMaximo = limiteMaximo;
        this.excessoCalculado = calcularExcesso(valorSolicitado, limiteMaximo);
    }

    /**
     * Monta uma mensagem amigável e explicativa sobre o estouro de orçamento.
     */
    private static String montarMensagemDetalhada(String departamento, double solicitado, double limite) {
        double diferenca = solicitado - limite;
        return String.format(
                "Operação negada: O valor solicitado (R$ %.2f) excede o limite do departamento '%s' (R$ %.2f) em R$ %.2f.",
                solicitado,
                departamento,
                limite,
                diferenca
        );
    }

    /**
     * Calcula a diferença monetária acima do limite permitido.
     */
    private static double calcularExcesso(double valor, double limite) {
        if (valor > limite) {
            return valor - limite;
        }
        return 0.0;
    }

    /**
     * Obtém o nome do departamento envolvido na exceção.
     *
     * @return String com o nome do departamento.
     */
    public String getNomeDepartamento() {
        return nomeDepartamento;
    }

    /**
     * Obtém o valor total que foi solicitado.
     *
     * @return double do valor solicitado.
     */
    public double getValorSolicitado() {
        return valorSolicitado;
    }

    /**
     * Obtém o teto máximo orçamentário do departamento.
     *
     * @return double do limite configurado.
     */
    public double getLimiteMaximo() {
        return limiteMaximo;
    }

    public double getExcessoCalculado() {
        return excessoCalculado;
    }
}
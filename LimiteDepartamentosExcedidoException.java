
public class LimiteDepartamentoExcedidoException extends DominioException {

    private static final long serialVersionUID = 1L;

    private final String nomeDepartamento;
    private final double valorSolicitado;
    private final double limiteMaximo;
    private final double excessoCalculado;

    public LimiteDepartamentoExcedidoException(String nomeDepartamento, double valorSolicitado, double limiteMaximo) {
        super(montarMensagemDetalhada(nomeDepartamento, valorSolicitado, limiteMaximo));

        this.nomeDepartamento = nomeDepartamento != null ? nomeDepartamento : "Não especificado";
        this.valorSolicitado = valorSolicitado;
        this.limiteMaximo = limiteMaximo;
        this.excessoCalculado = calcularExcesso(valorSolicitado, limiteMaximo);
    }


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

    private static double calcularExcesso(double valor, double limite) {
        if (valor > limite) {
            return valor - limite;
        }
        return 0.0;
    }


    public String getNomeDepartamento() {
        return nomeDepartamento;
    }


    public double getValorSolicitado() {
        return valorSolicitado;
    }


    public double getLimiteMaximo() {
        return limiteMaximo;
    }

    public double getExcessoCalculado() {
        return excessoCalculado;
    }
}
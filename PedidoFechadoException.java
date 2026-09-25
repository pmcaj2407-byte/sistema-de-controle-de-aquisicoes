public class PedidoFechadoException extends DominioException {
    private static final long serialVersionUID = 1L;

    private final Long idPedido;

    public PedidoFechadoException(Long idPedido) {
        super("Não é possível alterar ou processar o pedido ID " + idPedido + " pois ele já se encontra fechado.");
        this.idPedido = idPedido;
    }

    public PedidoFechadoException(Long idPedido, String detalheAdicional) {
        super("Não é possível alterar o pedido ID " + idPedido + ": " + detalheAdicional);
        this.idPedido = idPedido;
    }

    public Long getIdPedido() {
        return idPedido;
    }
}
import java.time.LocalDate;

public class ServicoAprovacao {

    public void aprovar(PedidoAvaliavel pedido, Usuario operador) {
        validarAdministrador(operador);
        validarPedidoAberto(pedido);
        pedido.aprovar();
    }

    public void reprovar(PedidoAvaliavel pedido, Usuario operador) {
        validarAdministrador(operador);
        validarPedidoAberto(pedido);
        pedido.reprovar();
    }

    public void registrarConclusao(PedidoAvaliavel pedido, Usuario operador, LocalDate dataConclusao) {
        validarAdministrador(operador);
        if (pedido == null) {
            throw new IllegalArgumentException("O pedido e obrigatorio");
        }
        if (!pedido.estaAprovado()) {
            throw new IllegalStateException("Somente pedidos aprovados podem ser concluidos");
        }
        if (pedido.possuiDataConclusao()) {
            throw new IllegalStateException("O pedido ja possui data de conclusao");
        }
        if (dataConclusao == null) {
            throw new IllegalArgumentException("A data de conclusao e obrigatoria");
        }
        pedido.concluir(dataConclusao);
    }

    private void validarAdministrador(Usuario operador) {
        if (operador == null || operador.getTipo() != TipoUsuario.ADMINISTRADOR) {
            throw new SecurityException("Somente administradores podem avaliar pedidos");
        }
    }

    private void validarPedidoAberto(PedidoAvaliavel pedido) {
        if (pedido == null) {
            throw new IllegalArgumentException("O pedido e obrigatorio");
        }
        if (!pedido.estaAberto()) {
            throw new IllegalStateException("Somente pedidos abertos podem ser alterados");
        }
    }
}
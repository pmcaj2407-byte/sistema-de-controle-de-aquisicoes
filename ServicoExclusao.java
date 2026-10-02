import java.util.List;

public class ServicoExclusao {

    public void excluirPedido(Pedido pedido, Usuario funcionario, List<Pedido> pedidos) {
        if (pedido == null) {
            throw new IllegalArgumentException("O pedido e obrigatorio");
        }
        if (funcionario == null) {
            throw new IllegalArgumentException("O funcionario e obrigatorio");
        }
        if (pedidos == null) {
            throw new IllegalArgumentException("A lista de pedidos e obrigatoria");
        }
        if (funcionario.getTipo() != TipoUsuario.FUNCIONARIO) {
            throw new SecurityException("Somente funcionarios podem excluir pedidos");
        }
        if (pedido.getFunc() == null || pedido.getFunc().getId() != funcionario.getId()) {
            throw new SecurityException("Funcionarios so podem excluir seus proprios pedidos");
        }
        if (!pedido.estaAberto()) {
            throw new IllegalStateException("Somente pedidos abertos podem ser excluidos");
        }
        if (!pedidos.remove(pedido)) {
            throw new IllegalArgumentException("O pedido nao consta na lista de pedidos");
        }
    }
}

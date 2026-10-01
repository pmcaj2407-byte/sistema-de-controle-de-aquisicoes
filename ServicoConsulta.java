import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ServicoConsulta {

    private final List<Pedido> pedidos;

    public ServicoConsulta(List<Pedido> pedidos) {
        if (pedidos == null) {
            throw new IllegalArgumentException("A lista de pedidos e obrigatoria");
        }
        this.pedidos = pedidos;
    }

    public List<Pedido> listarPedidosEntreDatas(LocalDate dataInicio, LocalDate dataFim) {
        if (dataInicio == null || dataFim == null) {
            throw new IllegalArgumentException("As datas inicial e final sao obrigatorias");
        }
        if (dataInicio.isAfter(dataFim)) {
            throw new IllegalArgumentException("A data inicial nao pode ser posterior a data final");
        }

        List<Pedido> resultado = new ArrayList<>();
        for (Pedido pedido : pedidos) {
            if (pedido != null
                    && pedido.getDataI() != null
                    && !pedido.getDataI().isBefore(dataInicio)
                    && !pedido.getDataI().isAfter(dataFim)) {
                resultado.add(pedido);
            }
        }
        return resultado;
    }

    public List<Pedido> buscarPorFuncionario(Usuario funcionario) {
        if (funcionario == null) {
            throw new IllegalArgumentException("O funcionario solicitante e obrigatorio");
        }

        List<Pedido> resultado = new ArrayList<>();
        for (Pedido pedido : pedidos) {
            if (pedido != null
                    && pedido.getFunc() != null
                    && pedido.getFunc().getId() == funcionario.getId()) {
                resultado.add(pedido);
            }
        }
        return resultado;
    }

    public List<Pedido> buscarPorDescricaoItem(String descricao) {
        if (descricao == null || descricao.isBlank()) {
            throw new IllegalArgumentException("A descricao do item e obrigatoria");
        }

        String busca = descricao.trim().toLowerCase(Locale.ROOT);
        List<Pedido> resultado = new ArrayList<>();
        for (Pedido pedido : pedidos) {
            if (pedido != null && pedido.getItens() != null) {
                boolean encontrou = pedido.getItens().stream()
                        .anyMatch(item -> item != null
                                && item.getDescricao() != null
                                && item.getDescricao().toLowerCase(Locale.ROOT).contains(busca));
                if (encontrou) {
                    resultado.add(pedido);
                }
            }
        }
        return resultado;
    }
}
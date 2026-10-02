import java.util.List;

public class ServicoEstatisticas {

    private List<Pedido> pedidos;

    public ServicoEstatisticas(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

    public long[] totalPorStatus() {

        long[] totais = new long[Pedido.StatusPedido.values().length];

        for (Pedido p : pedidos) {
            totais[p.getStatusP().ordinal()]++;
        }

        return totais;
    }

    public double[] percentualPorStatus() {

        double[] percentuais = new double[Pedido.StatusPedido.values().length];
        long[] totais = totalPorStatus();

        int total = pedidos.size();

        for (int i = 0; i < totais.length; i++) {

            if (total == 0) {
                percentuais[i] = 0;
            } else {
                percentuais[i] = (totais[i] * 100.0) / total;
            }

        }

        return percentuais;
    }

    public int quantidadeUltimos30Dias() {

        int cont = 0;

        for (Pedido p : pedidos) {

            long diferenca =
                    java.time.temporal.ChronoUnit.DAYS.between(
                            p.getDataI(),
                            java.time.LocalDate.now()
                    );

            if (diferenca <= 30) {
                cont++;
            }
        }

        return cont;
    }

    public double valorMedioUltimos30Dias() {

        double soma = 0;
        int cont = 0;

        for (Pedido p : pedidos) {

            long diferenca =
                    java.time.temporal.ChronoUnit.DAYS.between(
                            p.getDataI(),
                            java.time.LocalDate.now()
                    );

            if (diferenca <= 30) {
                soma += p.getValorTotal();
                cont++;
            }
        }

        if (cont == 0) {
            return 0;
        }

        return soma / cont;
    }

    public Pedido pedidoAbertoMaiorValor() {

        Pedido maior = null;

        for (Pedido p : pedidos) {

            if (p.estaAberto()) {

                if (maior == null ||
                        p.getValorTotal() > maior.getValorTotal()) {

                    maior = p;
                }
            }
        }

        return maior;
    }
}
import java.time.LocalDate;

public class TesteThiago {

    public static void main(String[] args) {
        testarDadosIniciais();
        testarFluxoDeAprovacao();
        System.out.println("Testes do modulo do Thiago aprovados.");
    }

    private static void testarDadosIniciais() {
        if (DadosIniciais.carregarDepartamentos().size() < 5) {
            throw new AssertionError("Devem existir pelo menos 5 departamentos");
        }
        long funcionarios = DadosIniciais.carregarUsuarios().stream()
                .filter(usuario -> usuario.getTipo() == Tipo.FUNCIONARIO)
                .count();
        if (funcionarios < 15) {
            throw new AssertionError("Devem existir pelo menos 15 funcionarios");
        }
    }

    private static void testarFluxoDeAprovacao() {
        DadosIniciais.UsuarioInicial admin = DadosIniciais.carregarUsuarios().get(0);
        PedidoDeTeste pedido = new PedidoDeTeste();
        ServicoAprovacao servico = new ServicoAprovacao();

        servico.aprovar(pedido, admin);
        servico.registrarConclusao(pedido, admin, LocalDate.now());

        try {
            servico.aprovar(pedido, admin);
            throw new AssertionError("Um pedido concluido nao pode ser reavaliado");
        } catch (IllegalStateException esperado) {
            // Comportamento esperado.
        }
    }

    private static final class PedidoDeTeste implements PedidoAvaliavel {
        private boolean aberto = true;
        private boolean aprovado;
        private LocalDate dataConclusao;

        @Override
        public Usuario getSolicitante() {
            return null;
        }

        @Override
        public boolean estaAberto() {
            return aberto;
        }

        @Override
        public boolean estaAprovado() {
            return aprovado;
        }

        @Override
        public boolean possuiDataConclusao() {
            return dataConclusao != null;
        }

        @Override
        public void aprovar() {
            aberto = false;
            aprovado = true;
        }

        @Override
        public void reprovar() {
            aberto = false;
        }

        @Override
        public void concluir(LocalDate dataConclusao) {
            this.dataConclusao = dataConclusao;
        }
    }
}
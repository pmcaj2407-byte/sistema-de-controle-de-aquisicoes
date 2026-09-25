import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class Pedido implements PedidoAvaliavel {

    public enum StatusPedido { ABERTO, APROVADO, REPROVADO }

    private StatusPedido statusP;
    private Usuario func;
    private Departamento dep;
    
    // Substituímos String por LocalDate
    private LocalDate dataI;
    private LocalDate dataF;
    
    private List<Item> itens;

    // Construtor: a dataI pode ser informada ou pegar automaticamente a data atual (LocalDate.now())
    public Pedido(Usuario func, LocalDate dataI) {
        this.func = func;
        this.dep = func.getDep(); // Pega direto do usuário
        this.dataI = dataI;
        this.dataF = null; // Inicia sem data final
        this.statusP = StatusPedido.ABERTO;
        this.itens = new ArrayList<>();
    }

    // Método para finalizar/fechar o pedido e registrar a data final
    public void finalizarPedido(StatusPedido novoStatus, LocalDate dataF) {
        this.statusP = novoStatus;
        this.dataF = dataF;
    }

    // Método para calcular a quantidade de dias que o pedido levou para ser concluído
    public long getTempoConclusaoEmDias() {
        if (this.dataF == null) {
            // Se ainda não foi finalizado, calcula a diferença em relação à data de hoje
            return ChronoUnit.DAYS.between(this.dataI, LocalDate.now());
        }
        return ChronoUnit.DAYS.between(this.dataI, this.dataF);
    }

    // Getters e Setters
    public StatusPedido getStatusP() {
        return statusP;
    }

    public Usuario getFunc() {
        return func;
    }

    public Departamento getDep() {
        return dep;
    }

    public LocalDate getDataI() {
        return dataI;
    }

    public LocalDate getDataF() {
        return dataF;
    }

    public List<Item> getItens() {
        return itens;
    }

    public void adicionarItem(Item item) {
        itens.add(item);
    }

    public double getValorTotal() {
        double valor = 0;
        for (Item item : itens) {
            valor += item.getValorTotal();
        }
        return valor;
    }

    @Override
    public Usuario getSolicitante() {
        return func;
    }

    @Override
    public boolean estaAberto() {
        return statusP == StatusPedido.ABERTO;
    }

    @Override
    public boolean estaAprovado() {
        return statusP == StatusPedido.APROVADO;
    }

    @Override
    public boolean possuiDataConclusao() {
        return dataF != null;
    }

    @Override
    public void aprovar() {
        finalizarPedido(StatusPedido.APROVADO, null);
    }

    @Override
    public void reprovar() {
        finalizarPedido(StatusPedido.REPROVADO, null);
    }

    @Override
    public void concluir(LocalDate dataConclusao) {
        if (!estaAprovado()) {
            throw new IllegalStateException("Somente pedidos aprovados podem ser concluidos");
        }
        finalizarPedido(StatusPedido.APROVADO, dataConclusao);
    }
}
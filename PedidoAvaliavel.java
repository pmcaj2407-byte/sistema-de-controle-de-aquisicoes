import java.time.LocalDate;

public interface PedidoAvaliavel {

    Usuario getSolicitante();

    boolean estaAberto();

    boolean estaAprovado();

    boolean possuiDataConclusao();

    void aprovar();

    void reprovar();

    void concluir(LocalDate dataConclusao);
}
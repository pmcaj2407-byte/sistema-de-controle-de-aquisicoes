import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AcessoNegadoException extends DominioException {

    private static final long serialVersionUID = 1L;

    private final String usuario;
    private final String operacaoIntentada;
    private final String nivelRequerido;
    private final LocalDateTime dataHoraTentativa;

    public AcessoNegadoException(String usuario, String operacaoIntentada) {
        this(usuario, operacaoIntentada, "ADMINISTRADOR");
    }

    public AcessoNegadoException(String usuario, String operacaoIntentada, String nivelRequerido) {
        super(montarMensagemAcesso(usuario, operacaoIntentada, nivelRequerido));

        this.usuario = (usuario != null && !usuario.isBlank()) ? usuario : "Anónimo/Desconhecido";
        this.operacaoIntentada = (operacaoIntentada != null && !operacaoIntentada.isBlank()) ? operacaoIntentada : "Operação Não Especificada";
        this.nivelRequerido = (nivelRequerido != null && !nivelRequerido.isBlank()) ? nivelRequerido : "Restrito";
        this.dataHoraTentativa = LocalDateTime.now();
    }

    private static String montarMensagemAcesso(String us, String op, String nivel) {
        return String.format(
                "Acesso Negado: O utilizador '%s' tentou executar '%s' sem possuir o nível de acesso '%s'.",
                (us != null ? us : "Anónimo"),
                (op != null ? op : "Operação Não Especificada"),
                (nivel != null ? nivel : "Restrito")
        );
    }

    public String getUsuario() {
        return usuario;
    }

    public String getOperacaoIntentada() {
        return operacaoIntentada;
    }

    public String getNivelRequerido() {
        return nivelRequerido;
    }

    public LocalDateTime getDataHoraTentativa() {
        return dataHoraTentativa;
    }

    public String getDataHoraFormatada() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return dataHoraTentativa.format(formatter);
    }
}
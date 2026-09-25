import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class DadosIniciais {

    private DadosIniciais() {
    }

    public static List<Departamento> carregarDepartamentos() {
        List<Departamento> departamentos = new ArrayList<>();
        Collections.addAll(departamentos, Departamento.values());
        return departamentos;
    }

    public static List<UsuarioInicial> carregarUsuarios() {
        List<UsuarioInicial> usuarios = new ArrayList<>();
        usuarios.add(new UsuarioInicial(1, "Ana Paula Martins", "APM", Tipo.ADMINISTRADOR, Departamento.COMPRAS));
        usuarios.add(new UsuarioInicial(2, "Carlos Eduardo Lima", "CEL", Tipo.ADMINISTRADOR, Departamento.FINANCEIRO));

        usuarios.add(new UsuarioInicial(101, "Beatriz Alves", "BA", Tipo.FUNCIONARIO, Departamento.RH));
        usuarios.add(new UsuarioInicial(102, "Bruno Cardoso", "BC", Tipo.FUNCIONARIO, Departamento.TI));
        usuarios.add(new UsuarioInicial(103, "Camila Ferreira", "CF", Tipo.FUNCIONARIO, Departamento.FINANCEIRO));
        usuarios.add(new UsuarioInicial(104, "Daniel Rocha", "DR", Tipo.FUNCIONARIO, Departamento.COMPRAS));
        usuarios.add(new UsuarioInicial(105, "Eduardo Mendes", "EM", Tipo.FUNCIONARIO, Departamento.LOGISTICA));
        usuarios.add(new UsuarioInicial(106, "Fernanda Souza", "FS", Tipo.FUNCIONARIO, Departamento.RH));
        usuarios.add(new UsuarioInicial(107, "Gabriel Santos", "GS", Tipo.FUNCIONARIO, Departamento.TI));
        usuarios.add(new UsuarioInicial(108, "Helena Costa", "HC", Tipo.FUNCIONARIO, Departamento.FINANCEIRO));
        usuarios.add(new UsuarioInicial(109, "Igor Oliveira", "IO", Tipo.FUNCIONARIO, Departamento.COMPRAS));
        usuarios.add(new UsuarioInicial(110, "Julia Nascimento", "JN", Tipo.FUNCIONARIO, Departamento.LOGISTICA));
        usuarios.add(new UsuarioInicial(111, "Lucas Ribeiro", "LR", Tipo.FUNCIONARIO, Departamento.RH));
        usuarios.add(new UsuarioInicial(112, "Mariana Gomes", "MG", Tipo.FUNCIONARIO, Departamento.TI));
        usuarios.add(new UsuarioInicial(113, "Nicolas Martins", "NM", Tipo.FUNCIONARIO, Departamento.FINANCEIRO));
        usuarios.add(new UsuarioInicial(114, "Olivia Barros", "OB", Tipo.FUNCIONARIO, Departamento.COMPRAS));
        usuarios.add(new UsuarioInicial(115, "Paulo Teixeira", "PT", Tipo.FUNCIONARIO, Departamento.LOGISTICA));
        return usuarios;
    }

    public static final class UsuarioInicial extends Usuario {
        private final String iniciais;
        private final Departamento departamento;

        public UsuarioInicial(int id, String nome, String iniciais, Tipo tipo, Departamento departamento) {
            super(id, nome, tipo);
            if (iniciais == null || iniciais.isBlank()) {
                throw new IllegalArgumentException("As iniciais do usuario sao obrigatorias");
            }
            if (departamento == null) {
                throw new IllegalArgumentException("O departamento do usuario e obrigatorio");
            }
            this.iniciais = iniciais;
            this.departamento = departamento;
        }

        public String getIniciais() {
            return iniciais;
        }

        public Departamento getDepartamento() {
            return departamento;
        }
    }
}
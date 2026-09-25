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
        usuarios.add(new UsuarioInicial(1, "Ana Paula Martins", "APM", TipoUsuario.ADMINISTRADOR, Departamento.COMPRAS));
        usuarios.add(new UsuarioInicial(2, "Carlos Eduardo Lima", "CEL", TipoUsuario.ADMINISTRADOR, Departamento.FINANCEIRO));

        usuarios.add(new UsuarioInicial(101, "Beatriz Alves", "BA", TipoUsuario.FUNCIONARIO, Departamento.RH));
        usuarios.add(new UsuarioInicial(102, "Bruno Cardoso", "BC", TipoUsuario.FUNCIONARIO, Departamento.TI));
        usuarios.add(new UsuarioInicial(103, "Camila Ferreira", "CF", TipoUsuario.FUNCIONARIO, Departamento.FINANCEIRO));
        usuarios.add(new UsuarioInicial(104, "Daniel Rocha", "DR", TipoUsuario.FUNCIONARIO, Departamento.COMPRAS));
        usuarios.add(new UsuarioInicial(105, "Eduardo Mendes", "EM", TipoUsuario.FUNCIONARIO, Departamento.LOGISTICA));
        usuarios.add(new UsuarioInicial(106, "Fernanda Souza", "FS", TipoUsuario.FUNCIONARIO, Departamento.RH));
        usuarios.add(new UsuarioInicial(107, "Gabriel Santos", "GS", TipoUsuario.FUNCIONARIO, Departamento.TI));
        usuarios.add(new UsuarioInicial(108, "Helena Costa", "HC", TipoUsuario.FUNCIONARIO, Departamento.FINANCEIRO));
        usuarios.add(new UsuarioInicial(109, "Igor Oliveira", "IO", TipoUsuario.FUNCIONARIO, Departamento.COMPRAS));
        usuarios.add(new UsuarioInicial(110, "Julia Nascimento", "JN", TipoUsuario.FUNCIONARIO, Departamento.LOGISTICA));
        usuarios.add(new UsuarioInicial(111, "Lucas Ribeiro", "LR", TipoUsuario.FUNCIONARIO, Departamento.RH));
        usuarios.add(new UsuarioInicial(112, "Mariana Gomes", "MG", TipoUsuario.FUNCIONARIO, Departamento.TI));
        usuarios.add(new UsuarioInicial(113, "Nicolas Martins", "NM", TipoUsuario.FUNCIONARIO, Departamento.FINANCEIRO));
        usuarios.add(new UsuarioInicial(114, "Olivia Barros", "OB", TipoUsuario.FUNCIONARIO, Departamento.COMPRAS));
        usuarios.add(new UsuarioInicial(115, "Paulo Teixeira", "PT", TipoUsuario.FUNCIONARIO, Departamento.LOGISTICA));
        return usuarios;
    }

    public static final class UsuarioInicial extends Usuario {
        private final String iniciais;
        private final Departamento departamento;

        public UsuarioInicial(int id, String nome, String iniciais, TipoUsuario tipo, Departamento departamento) {
            super(id, nome, tipo, departamento);
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
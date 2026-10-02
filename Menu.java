import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Menu {

    private static final Scanner entrada = new Scanner(System.in);
    private static final SessaoUsuario sessao = SessaoUsuario.getInstancia();

    private static final List<Usuario> usuarios = new ArrayList<>(DadosIniciais.carregarUsuarios());
    private static final List<Pedido> pedidos = new ArrayList<>();

    private static final ServicoAprovacao servicoAprovacao = new ServicoAprovacao();
    private static final ServicoConsulta servicoConsulta = new ServicoConsulta(pedidos);
    private static final ServicoEstatisticas servicoEstatisticas = new ServicoEstatisticas(pedidos);
    private static final ServicoExclusao servicoExclusao = new ServicoExclusao();

    private static boolean executando = true;

    public static void main(String[] args) {
        System.out.println("---SISTEMA DE PEDIDOS DE AQUISICAO---");

        while (executando) {
            Usuario atual = sessao.getUsuarioAtual();
            if (atual == null) {
                telaLogin();
            } else if (atual.getTipo() == TipoUsuario.ADMINISTRADOR) {
                menuAdministrador();
            } else {
                menuFuncionario();
            }
        }
        System.out.println("Sistema finalizado ");
    }

    private static void telaLogin() {
        System.out.println("\n--------------- LOGIN ---------------");
        for (Usuario u : usuarios) {
            String iniciais = (u instanceof DadosIniciais.UsuarioInicial)
                    ? ((DadosIniciais.UsuarioInicial) u).getIniciais() : "";
            System.out.printf("%4d - %-22s [%s] %-14s %s%n",
                    u.getId(), u.getNome(), iniciais, u.getTipo(), u.getDep().getNome());
        }
        System.out.println("   0 - Sair do sistema");

        int id = lerInt("Informe o ID do usuario: ");
        if (id == 0) {
            executando = false;
            return;
        }
        sessao.trocarUsuarioPorId(id, usuarios);
    }

    private static void menuFuncionario() {
        Usuario usuario = sessao.getUsuarioAtual();
        System.out.println("\n========== MENU FUNCIONARIO ==========");
        System.out.println("Usuario: " + usuario.getNome() + " | Depto: " + usuario.getDep().getNome());
        System.out.println("1 - Criar novo pedido");
        System.out.println("2 - Listar meus pedidos");
        System.out.println("3 - Excluir um pedido meu (somente abertos)");
        System.out.println("4 - Buscar meus pedidos por descricao de item");
        System.out.println("5 - Meus pedidos por periodo");
        System.out.println("6 - Ver departamentos e limites");
        System.out.println("7 - Trocar de usuario");
        System.out.println("0 - Sair");

        int opcao = lerInt("Opcao: ");
        try {
            switch (opcao) {
                case 1:
                    criarPedido(usuario);
                    break;
                case 2:
                    listar(servicoConsulta.buscarPorFuncionario(usuario));
                    break;
                case 3:
                    excluirPedido(usuario);
                    break;
                case 4:
                    listar(filtrarDoUsuario(
                            servicoConsulta.buscarPorDescricaoItem(lerTexto("Descricao do item: ")), usuario));
                    break;
                case 5:
                    listar(filtrarDoUsuario(consultarPorPeriodo(), usuario));
                    break;
                case 6:
                    mostrarDepartamentos();
                    break;
                case 7:
                    sessao.setUsuarioAtual(null);
                    break;
                case 0:
                    executando = false;
                    break;
                default:
                    System.out.println("Digite outra coisa, assim não vai rodar :( ");
            }
        } catch (DominioException e) {
            System.out.println("[REGRA DE NEGOCIO] " + e.getMessage());
        } catch (RuntimeException e) {
            System.out.println("[ERRO] " + e.getMessage());
        }
    }

    private static void criarPedido(Usuario usuario) {
        Pedido pedido = new Pedido(usuario, LocalDate.now());
        System.out.println("\n--- Novo pedido (" + usuario.getDep().getNome() + ") ---");
        System.out.println("Informe os itens. Deixe a descricao em branco para finalizar pfvr ");

        while (true) {
            String descricao = lerTexto("Descricao do item: ");
            if (descricao.isBlank()) {
                break;
            }
            double valor = lerDouble("Valor unitario (R$): ");
            int qnt = lerInt("Quantidade: ");
            if (valor <= 0 || qnt <= 0) {
                System.out.println("Valor e quantidade devem ser maiores que zero. Item descartado ");
                continue;
            }
            pedido.adicionarItem(new Item(descricao, valor, qnt));
            System.out.printf("Item adicionado. Total parcial: R$ %.2f%n", pedido.getValorTotal());
        }

        if (pedido.getItens().isEmpty()) {
            System.out.println("Pedido cancelado: nenhum item informado.");
            return;
        }

        ValidadorSeguranca.validarLimiteOrcamentario(
                usuario.getDep().getNome(),
                pedido.getValorTotal(),
                usuario.getDep().getLimiteAprovacao());

        pedidos.add(pedido);
        System.out.printf("Pedido #%d criado com sucesso! Total: R$ %.2f%n", pedidos.size(), pedido.getValorTotal());
    }

    private static void excluirPedido(Usuario usuario) {
        List<Pedido> meus = servicoConsulta.buscarPorFuncionario(usuario);
        listar(meus);
        if (meus.isEmpty()) {
            return;
        }
        Pedido pedido = obterPedido(lerInt("Numero do pedido a excluir: "));
        servicoExclusao.excluirPedido(pedido, usuario, pedidos);
        System.out.println("Pedido excluido com sucesso.");
    }

    private static void menuAdministrador() {
        Usuario usuario = sessao.getUsuarioAtual();
        System.out.println("\n========== MENU ADMINISTRADOR ==========");
        System.out.println("Usuario: " + usuario.getNome() + " | Depto: " + usuario.getDep().getNome());
        System.out.println(" 1 - Listar todos os pedidos");
        System.out.println(" 2 - Listar pedidos abertos");
        System.out.println(" 3 - Aprovar pedido");
        System.out.println(" 4 - Reprovar pedido");
        System.out.println(" 5 - Registrar conclusao de pedido aprovado");
        System.out.println(" 6 - Consultar pedidos por periodo");
        System.out.println(" 7 - Consultar pedidos por funcionario");
        System.out.println(" 8 - Buscar pedidos por descricao de item");
        System.out.println(" 9 - Estatisticas");
        System.out.println("10 - Ver departamentos e limites");
        System.out.println("11 - Trocar de usuario");
        System.out.println(" 0 - Sair");

        int opcao = lerInt("Opcao: ");
        try {
            switch (opcao) {
                case 1:
                    listar(pedidos);
                    break;
                case 2:
                    listar(abertos());
                    break;
                case 3:
                    avaliarPedido(usuario, true);
                    break;
                case 4:
                    avaliarPedido(usuario, false);
                    break;
                case 5:
                    registrarConclusao(usuario);
                    break;
                case 6:
                    listar(consultarPorPeriodo());
                    break;
                case 7:
                    consultarPorFuncionario();
                    break;
                case 8:
                    listar(servicoConsulta.buscarPorDescricaoItem(lerTexto("Descricao do item: ")));
                    break;
                case 9:
                    mostrarEstatisticas();
                    break;
                case 10:
                    mostrarDepartamentos();
                    break;
                case 11:
                    sessao.setUsuarioAtual(null);
                    break;
                case 0:
                    executando = false;
                    break;
                default:
                    System.out.println("Opcao errada :( ");
            }
        } catch (DominioException e) {
            System.out.println("[REGRA DE NEGOCIO] " + e.getMessage());
        } catch (RuntimeException e) {
            System.out.println("[ERRO] " + e.getMessage());
        }
    }

    private static void avaliarPedido(Usuario operador, boolean aprovar) {
        String operacao = aprovar ? "APROVAR_PEDIDO" : "REPROVAR_PEDIDO";
        ValidadorSeguranca.validarPermissaoUsuario(
                operador.getNome(), operador.getTipo().name(), "ADMINISTRADOR", operacao);

        List<Pedido> abertos = abertos();
        listar(abertos);
        if (abertos.isEmpty()) {
            return;
        }

        int numero = lerInt("Numero do pedido: ");
        Pedido pedido = obterPedido(numero);

        ValidadorSeguranca.validarEstadoPedido(Long.valueOf(numero), !pedido.estaAberto());

        if (aprovar) {
            servicoAprovacao.aprovar(pedido, operador);
            System.out.println("Pedido #" + numero + " APROVADO");
        } else {
            servicoAprovacao.reprovar(pedido, operador);
            System.out.println("Pedido #" + numero + " NEGADO");
        }
    }

    private static void registrarConclusao(Usuario operador) {
        List<Pedido> pendentes = new ArrayList<>();
        for (Pedido p : pedidos) {
            if (p.estaAprovado() && !p.possuiDataConclusao()) {
                pendentes.add(p);
            }
        }
        System.out.println("Pedidos aprovados aguardando conclusao: ");
        listar(pendentes);
        if (pendentes.isEmpty()) {
            return;
        }

        Pedido pedido = obterPedido(lerInt("Numero do pedido: "));
        String texto = lerTexto("Data de conclusao (dd/MM/yyyy) ou ENTER para hoje: ");
        LocalDate data = texto.isBlank() ? LocalDate.now() : converterData(texto);

        if (data.isBefore(pedido.getDataI())) {
            throw new IllegalArgumentException("A data de conclusao nao pode ser anterior a data de abertura");
        }

        servicoAprovacao.registrarConclusao(pedido, operador, data);
        System.out.println("Conclusao registrada. Tempo total: " + pedido.getTempoConclusaoEmDias() + " dia(s)");
    }

    private static void consultarPorFuncionario() {
        int id = lerInt("ID do funcionario: ");
        Usuario alvo = null;
        for (Usuario u : usuarios) {
            if (u.getId() == id) {
                alvo = u;
            }
        }
        if (alvo == null) {
            throw new IllegalArgumentException("Usuario com ID " + id + " nao encontrado ");
        }
        listar(servicoConsulta.buscarPorFuncionario(alvo));
    }

    private static void mostrarEstatisticas() {
        System.out.println("\n--- ESTATISTICAS ---");
        System.out.println("Total de pedidos: " + pedidos.size());

        Pedido.StatusPedido[] status = Pedido.StatusPedido.values();
        long[] totais = servicoEstatisticas.totalPorStatus();
        double[] percentuais = servicoEstatisticas.percentualPorStatus();
        for (int i = 0; i < status.length; i++) {
            System.out.printf("%-10s: %d pedido(s) (%.1f%%)%n", status[i], totais[i], percentuais[i]);
        }

        System.out.println("Pedidos nos ultimos 30 dias: " + servicoEstatisticas.quantidadeUltimos30Dias());
        System.out.printf("Valor medio (ultimos 30 dias): R$ %.2f%n", servicoEstatisticas.valorMedioUltimos30Dias());

        Pedido maior = servicoEstatisticas.pedidoAbertoMaiorValor();
        if (maior == null) {
            System.out.println("Pedido aberto de maior valor: nenhum ");
        } else {
            System.out.println("Pedido aberto de maior valor: ");
            imprimirPedido(maior);
        }
    }

    private static List<Pedido> consultarPorPeriodo() {
        LocalDate inicio = lerData("Data inicial (dd/MM/yyyy): ");
        LocalDate fim = lerData("Data final (dd/MM/yyyy): ");
        return servicoConsulta.listarPedidosEntreDatas(inicio, fim);
    }

    private static void mostrarDepartamentos() {
        System.out.println("\n--- DEPARTAMENTOS ---");
        for (Departamento d : DadosIniciais.carregarDepartamentos()) {
            System.out.printf("%-26s limite de aprovacao: R$ %.2f%n", d.getNome(), d.getLimiteAprovacao());
        }
    }

    private static List<Pedido> abertos() {
        List<Pedido> resultado = new ArrayList<>();
        for (Pedido p : pedidos) {
            if (p.estaAberto()) {
                resultado.add(p);
            }
        }
        return resultado;
    }

    private static List<Pedido> filtrarDoUsuario(List<Pedido> lista, Usuario usuario) {
        List<Pedido> resultado = new ArrayList<>();
        for (Pedido p : lista) {
            if (p.getFunc().getId() == usuario.getId()) {
                resultado.add(p);
            }
        }
        return resultado;
    }

    private static Pedido obterPedido(int numero) {
        if (numero < 1 || numero > pedidos.size()) {
            throw new IllegalArgumentException("Pedido #" + numero + " nao existe ");
        }
        return pedidos.get(numero - 1);
    }

    private static void listar(List<Pedido> lista) {
        if (lista.isEmpty()) {
            System.out.println("Nenhum pedido encontrado ");
            return;
        }
        System.out.println();
        for (Pedido p : lista) {
            imprimirPedido(p);
        }
    }

    private static void imprimirPedido(Pedido p) {
        System.out.printf("#%d | %s (%s) | %s | Inicio: %s | Fim: %s | Total: R$ %.2f%n",
                pedidos.indexOf(p) + 1,
                p.getFunc().getNome(),
                p.getDep().getNome(),
                p.getStatusP(),
                formatarData(p.getDataI()),
                formatarData(p.getDataF()),
                p.getValorTotal());
        for (Item i : p.getItens()) {
            System.out.printf("      - %s | %d x R$ %.2f = R$ %.2f%n",
                    i.getDescricao(), i.getQnt(), i.getValorUnitario(), i.getValorTotal());
        }
    }

    private static String lerTexto(String mensagem) {
        System.out.print(mensagem);
        return entrada.nextLine().trim();
    }

    private static int lerInt(String mensagem) {
        while (true) {
            try {
                return Integer.parseInt(lerTexto(mensagem));
            } catch (NumberFormatException e) {
                System.out.println("Digite um numero inteiro valido ");
            }
        }
    }

    private static double lerDouble(String mensagem) {
        while (true) {
            try {
                return Double.parseDouble(lerTexto(mensagem).replace(',', '.'));
            } catch (NumberFormatException e) {
                System.out.println("Digite um valor numerico valido ");
            }
        }
    }

    private static LocalDate lerData(String mensagem) {
        while (true) {
            try {
                return converterData(lerTexto(mensagem));
            } catch (RuntimeException e) {
                System.out.println("Data invalida. Use o formato dd/MM/yyyy.");
            }
        }
    }

    private static LocalDate converterData(String texto) {
        String[] partes = texto.split("/");
        if (partes.length != 3) {
            throw new IllegalArgumentException("Data invalida. Use o formato dd/MM/yyyy");
        }
        try {
            return LocalDate.of(
                    Integer.parseInt(partes[2].trim()),
                    Integer.parseInt(partes[1].trim()),
                    Integer.parseInt(partes[0].trim()));
        } catch (RuntimeException e) {
            throw new IllegalArgumentException("Data invalida. Use o formato dd/MM/yyyy");
        }
    }

    private static String formatarData(LocalDate data) {
        if (data == null) {
            return "-";
        }
        return String.format("%02d/%02d/%04d", data.getDayOfMonth(), data.getMonthValue(), data.getYear());
    }
}

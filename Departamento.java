public enum Departamento {
    FINANCEIRO("Financeiro", 10000.00),
    RH("Recursos Humanos", 2000.00),
    TI("Tecnologia da Informação", 5000.00),
    COMPRAS("Compras", 15000.00),
    LOGISTICA("Logística", 3000.00);

    private final String nome;
    private final double limiteAprovacao;

    // Construtor do Enum
    Departamento(String nome, double limiteAprovacao) {
        this.nome = nome;
        this.limiteAprovacao = limiteAprovacao;
    }

    public String getNome() {
        return nome;
    }

    public double getLimiteAprovacao() {
        return limiteAprovacao;
    }
}
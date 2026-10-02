public class Teste {
    public static void main(String[] args) {
        System.out.println("=== TESTANDO O ENUM DEPARTAMENTO ===");

        // 1. Acessando um departamento específico e seus métodos
        Departamento depTI = Departamento.TI;
        System.out.println("Nome do Setor: " + depTI.getNome());
        System.out.println("Limite de Aprovação: R$ " + depTI.getLimiteAprovacao());

        System.out.println("\n=== LISTA DE TODOS OS DEPARTAMENTOS ===");
        
        // 2. Percorrendo todos os departamentos cadastrados
        for (Departamento dep : Departamento.values()) {
            System.out.println("Setor: " + dep.getNome() + " | Limite: R$ " + dep.getLimiteAprovacao());
        }
    }
}
public abstract class Usuario {

    private int id; 
    private String nome; 
    private Tipo tipo;
    private Departamento dep;

    public Usuario(int id, String nome, Tipo tipo, Departamento dep) {
        this.id=id;
        this.nome = nome; 
        this.tipo = tipo;
        this.dep=dep;

    }
    public Departamento getDep() {
        return dep;
    }
    public void setDep(Departamento dep) {
        this.dep = dep;
    }
    
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }
}
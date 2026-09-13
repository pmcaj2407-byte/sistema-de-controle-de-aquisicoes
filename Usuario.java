public abstract class Usuario {

    private int id; 
    private String nome; 
    private Tipo tipo;


    public Usuario(int id, String nome, Tipo tipo) {
        this.id=id;
        this.nome = nome; 
        this.tipo = tipo;

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
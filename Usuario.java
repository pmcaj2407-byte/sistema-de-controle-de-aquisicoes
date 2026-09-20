public abstract class Usuario {

    private int id; 
    private String nome; 
<<<<<<< HEAD
    private Tipo tipo;
    private Departamento departamento;


    public Usuario(int id, String nome, Tipo tipo, Departamento departamento) {
        this.id=id;
        this.nome = nome; 
        this.tipo = tipo;
        this.departamento = departamento;
=======
    private TipoUsuario tipo;
    private Departamento dep;

    public Usuario(int id, String nome, TipoUsuario tipo, Departamento dep) {
        this.id=id;
        this.nome = nome; 
        this.tipo = tipo;
        this.dep = dep;
>>>>>>> eef789cddc921551b840dafc844462581fcb5124

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

    public TipoUsuario getTipo() {
        return tipo;
    }

    public void setTipo(TipoUsuario tipo) {
        this.tipo = tipo;
    }

    public Departamento getDepartamento() {
        return departamento;
    }
    public void setDepartamento(Departamento departamento){
        this.departamento = departamento;
    }

    public boolean Administrador(){
        return tipo == tipo.ADMINISTRADOR;
    }

    @Override
    public String toString(){
        return "Id: " + id +
                "Nome: " + nome +
                "tipo: " + tipo +
                "Departamento: " + departamento.getNome();
    }
}
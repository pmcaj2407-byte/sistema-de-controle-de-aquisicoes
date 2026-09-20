import java.util.ArrayList;
import java.util.List;
public class Pedido {
    
    public  enum StatusPedido { ABERTO, APROVADO , REPROVADO};
    private StatusPedido statusP;
    private Usuario func;
    private Departamento dep;
    private String dataI;
    private String dataF;
    private List<Item> itens;
    
    public StatusPedido getStatusP() {
        return statusP;
    }

    public Usuario getFunc() {
        return func;
    }

    public Departamento getDep() {
        return dep;
    }

    public String getDataI() {
        return dataI;
    }

    public String getDataF() {
        return dataF;
    }

    public List<Item> getItens() {
        return itens;
    }

    public Pedido(Usuario func,Departamento dep,String dataI ) {
        this.func = func;
        this.dep = dep;
        this.dataI = dataI;
        this.dataF = null;
        this.statusP = StatusPedido.ABERTO; // todo pedido inicia em aberto
        this.itens = new ArrayList<>();
    }

        // adicionar um novo item a lista
    public void adicionarItem (Item item){
        itens.add(item);
    }
    public double getValorTotal (){
        // valor total de todos os itens do Pedido
        double valor = 0;
        for(Item valores : itens){
            valor+= valores.getValorTotal();
        }
        return valor;
    }
}

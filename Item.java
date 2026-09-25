public class Item {
    
    private String descricao;
    private double valorUnitario;
    private int qnt;

    public Item(String descricao, double valorUnitario, int qnt) {
        this.descricao = descricao;
        this.valorUnitario = valorUnitario;
        this.qnt = qnt;
        
    }
    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(double valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public int getQnt() {
        return qnt;
    }

    public void setQnt(int qnt) {
        this.qnt = qnt;
    }
    // valor total do Item
    public double getValorTotal() {
        return valorUnitario*qnt;
    }


    
}

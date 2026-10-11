package fintrack.model;

import fintrack.utils.Formatador;
import java.time.LocalDate;

public class Transacao {
    private int id;
    private String descricao;
    private double valor;
    private boolean ehReceita;
    private LocalDate data;

    public Transacao(String descricao, double valor, boolean ehReceita, LocalDate data) {
        this.descricao = descricao;
        this.valor = valor;
        this.ehReceita = ehReceita;
        this.data = data;
    }

    public String obterTipoTransacao() {
        return ehReceita ? "Receita (Única)" : "Despesa (Única)";
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public double getValor() { return valor; }
    public void setValor(double valor) { this.valor = valor; }

    public boolean isEhReceita() { return ehReceita; }
    public void setEhReceita(boolean ehReceita) { this.ehReceita = ehReceita; }

    public LocalDate getData() { return data; }
    public void setData(LocalDate data) { this.data = data; }

    @Override
    public String toString() {
        String sinal = ehReceita ? "+" : "-";
        return String.format("[%s] %s %s - %s (Data: %s)", 
            obterTipoTransacao(), 
            sinal, 
            Formatador.formatarMoeda(valor), 
            descricao, 
            Formatador.formatarData(data)
        );
    }
}
package fintrack.model;

import java.time.LocalDate;

public class TransacaoMensal extends Transacao {
    private int mesesRecorrencia;

    public TransacaoMensal(String descricao, double valor, boolean ehReceita, LocalDate data, int mesesRecorrencia) {
        super(descricao, valor, ehReceita, data);
        this.mesesRecorrencia = mesesRecorrencia;
    }

    public int getMesesRecorrencia() { return mesesRecorrencia; }
    public void setMesesRecorrencia(int mesesRecorrencia) { this.mesesRecorrencia = mesesRecorrencia; }

    @Override
    public String obterTipoTransacao() {
        return isEhReceita() ? "Receita (Recorrente/" + mesesRecorrencia + "m)" : "Despesa (Recorrente/" + mesesRecorrencia + "m)";
    }
}
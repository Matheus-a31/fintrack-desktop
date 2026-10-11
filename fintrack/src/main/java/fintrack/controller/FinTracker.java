package fintrack.controller;

import fintrack.model.Transacao;
import fintrack.exceptions.EntradaInvalidaException;
import java.util.ArrayList;
import java.util.List;

public class FinTracker {
    private List<Transacao> transacoes;

    public FinTracker() {
        this.transacoes = new ArrayList<>();
    }

    public void adicionarTransacao(Transacao transacao) throws EntradaInvalidaException {
        if (transacao.getValor() <= 0) {
            throw new EntradaInvalidaException("O valor da transação deve ser maior que zero.");
        }
        transacoes.add(transacao);
    }

    public List<Transacao> listarTransacoes() {
        return transacoes;
    }

    public Transacao removerTransacao(int id) throws EntradaInvalidaException {
        int index = id - 1;
        if (index < 0 || index >= transacoes.size()) {
            throw new EntradaInvalidaException("ID de transação não encontrado.");
        }
        return transacoes.remove(index);
    }

    public double calcularSaldoTotal() {
        double saldo = 0;
        for (Transacao t : transacoes) {
            if (t.isEhReceita()) {
                saldo += t.getValor();
            } else {
                saldo -= t.getValor();
            }
        }
        return saldo;
    }
}
package fintrack.app;

import fintrack.controller.FinTracker;
import fintrack.exceptions.EntradaInvalidaException;
import fintrack.model.Transacao;
import fintrack.model.TransacaoMensal;
import fintrack.utils.Formatador;

import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        FinTracker tracker = new FinTracker();
        boolean rodando = true;

        while (rodando) {
            System.out.println("\n===== FINTRACK - SEU CONTROLE FINANCEIRO =====");
            System.out.println("1. Adicionar nova transação");
            System.out.println("2. Listar transações");
            System.out.println("3. Mostrar saldo atual");
            System.out.println("4. Remover transação");
            System.out.println("5. Sair");
            System.out.print("Escolha uma opção: ");

            try {
                int opcao = scanner.nextInt();
                scanner.nextLine(); 

                switch (opcao) {
                    case 1:
                        adicionarTransacaoFluxo(scanner, tracker);
                        break;
                    case 2:
                        System.out.println("\n--- SUAS TRANSAÇÕES ---");
                        List<Transacao> lista = tracker.listarTransacoes();
                        if (lista.isEmpty()) {
                            System.out.println("Nenhuma transação cadastrada.");
                        } else {
                            for (int i = 0; i < lista.size(); i++) {
                                System.out.println("ID " + (i + 1) + " | " + lista.get(i).toString());
                            }
                        }
                        break;
                    case 3:
                        double saldo = tracker.calcularSaldoTotal();
                        System.out.println("\nSaldo Atual: " + Formatador.formatarMoeda(saldo));
                        break;
                    case 4:
                        System.out.print("Digite o ID da transação que deseja remover: ");
                        int id = scanner.nextInt();
                        Transacao removida = tracker.removerTransacao(id);
                        System.out.println("Transação removida: " + removida.getDescricao());
                        break;
                    case 5:
                        System.out.println("Saindo do FinTrack. Até logo!");
                        rodando = false;
                        break;
                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Erro: Entrada inválida. Por favor, digite um número.");
                scanner.nextLine(); 
            } catch (EntradaInvalidaException e) {
                System.out.println("Erro de Operação: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Ocorreu um erro inesperado: " + e.getMessage());
            }
        }
        scanner.close();
    }

    private static void adicionarTransacaoFluxo(Scanner scanner, FinTracker tracker) throws EntradaInvalidaException {
        System.out.print("Descrição da transação: ");
        String descricao = scanner.nextLine();

        System.out.print("Valor (ex: 150,50): ");
        double valor = scanner.nextDouble();

        System.out.print("É uma receita ou despesa? (1 para Receita, 2 para Despesa): ");
        int tipo = scanner.nextInt();
        boolean ehReceita = (tipo == 1);

        System.out.print("É uma transação recorrente/mensal? (1 para Sim, 2 para Não): ");
        int recorrente = scanner.nextInt();

        Transacao novaTransacao;
        if (recorrente == 1) {
            System.out.print("Por quantos meses ela se repete?: ");
            int meses = scanner.nextInt();
            novaTransacao = new TransacaoMensal(descricao, valor, ehReceita, LocalDate.now(), meses);
        } else {
            novaTransacao = new Transacao(descricao, valor, ehReceita, LocalDate.now());
        }

        tracker.adicionarTransacao(novaTransacao);
        System.out.println("Transação adicionada com sucesso!");
    }
}
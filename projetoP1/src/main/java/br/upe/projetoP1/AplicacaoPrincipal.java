package br.upe.projetoP1;

import analisador.AnalisadorDeLogs;

import java.util.Scanner;

public class AplicacaoPrincipal {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        AnalisadorDeLogs analisador = new AnalisadorDeLogs("src/main/java/br/upe/projetoP1/access2.log", "Analise");

        int opcao;
        do {
            System.out.println("\nMENU");
            System.out.println("1 - Recursos grandes respondidos");
            System.out.println("2 - Não respondidos");
            System.out.println("3 - % de requisições por SO");
            System.out.println("4 - Média das requisições POST");
            System.out.println("0 - Sair");
            System.out.print("Opção: ");
            opcao = scanner.nextInt();

            switch (opcao) {
                case 1 -> analisador.processarRecursosGrandes();
                case 2 -> analisador.processarNaoRespondidosNovembro();
                case 3 -> analisador.calcularPercentualPorSO();
                case 4 -> analisador.calcularMediaPost2021();
                case 0 -> System.out.println("Encerrando...");
                default -> System.out.println("Opção inválida!");
            }
        } while (opcao != 0);

        scanner.close();
    }
}
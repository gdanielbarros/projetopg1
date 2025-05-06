package analisador;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import modelo.EntradaDeLog;
import parser.LeitorDeLog;

public class AnalisadorDeLogs {
    private final String caminhoLog;
    private final String pastaSaida;

    public AnalisadorDeLogs(String caminhoLog, String pastaSaida) {
        this.caminhoLog = caminhoLog;
        this.pastaSaida = pastaSaida;
        new File(pastaSaida).mkdirs();
    }

    public void processarRecursosGrandes() {
        try (Scanner scanner = new Scanner(new File(caminhoLog));
             PrintWriter writer = new PrintWriter(new File(pastaSaida, "recursosGrandes.txt"))) {
            while (scanner.hasNextLine()) {
                EntradaDeLog entrada = LeitorDeLog.ler(scanner.nextLine());
                if (entrada != null && entrada.foiSucesso() && entrada.tamanho > 2000) {
                    writer.println(entrada.codigo + " " + entrada.tamanho + " " + entrada.ip);
                }
            }
            System.out.println("Arquivo recursosGrandes.txt criado.");
        } catch (IOException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    public void processarNaoRespondidosNovembro() {
        try (Scanner scanner = new Scanner(new File(caminhoLog));
             PrintWriter writer = new PrintWriter(new File(pastaSaida, "naoRespondidosNovembro.txt"))) {
            while (scanner.hasNextLine()) {
                EntradaDeLog entrada = LeitorDeLog.ler(scanner.nextLine());
                if (entrada != null && entrada.foiErroCliente() && entrada.ehDeNovembro2021()) {
                    writer.println(entrada.codigo + " \"" + entrada.url + "\" Nov/2021");
                }
            }
            System.out.println("Arquivo naoRespondidosNovembro.txt criado.");
        } catch (IOException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    public void calcularPercentualPorSO() {
        Map<String, Integer> contagemSO = new HashMap<>();
        int total = 0;

        try (Scanner scanner = new Scanner(new File(caminhoLog));
             PrintWriter writer = new PrintWriter(new File(pastaSaida, "sistemasOperacionais.txt"))) {
            while (scanner.hasNextLine()) {
                EntradaDeLog entrada = LeitorDeLog.ler(scanner.nextLine());
                if (entrada != null && entrada.ehDoAno2021()) {
                    total++;
                    String agente = entrada.userAgent.toLowerCase();
                    if (agente.contains("windows")) adicionar(contagemSO, "Windows");
                    else if (agente.contains("macintosh")) adicionar(contagemSO, "Macintosh");
                    else if (agente.contains("ubuntu")) adicionar(contagemSO, "Ubuntu");
                    else if (agente.contains("fedora")) adicionar(contagemSO, "Fedora");
                    else if (agente.contains("android") || agente.contains("mobile")) adicionar(contagemSO, "Mobile");
                    else if (agente.contains("x11")) adicionar(contagemSO, "Linux, outros");
                }
            }
            for (String so : List.of("Windows", "Macintosh", "Ubuntu", "Fedora", "Mobile", "Linux, outros")) {
                double percentual = 100.0 * contagemSO.getOrDefault(so, 0) / total;
                writer.printf("%s %.4f%n", so, percentual);
            }
            System.out.println("Arquivo sistemasOperacionais.txt criado.");
        } catch (IOException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    public void calcularMediaPost2021() {
        int quantidade = 0;
        long totalBytes = 0;

        try (Scanner scanner = new Scanner(new File(caminhoLog))) {
            while (scanner.hasNextLine()) {
                EntradaDeLog entrada = LeitorDeLog.ler(scanner.nextLine());
                if (entrada != null && entrada.ehDoAno2021() && entrada.foiSucesso() && entrada.metodo.equals("POST")) {
                    totalBytes += entrada.tamanho;
                    quantidade++;
                }
            }
            if (quantidade > 0) {
                System.out.printf("Média das requisições POST de 2021: %.2f%n", (double) totalBytes / quantidade);
            } else {
                System.out.println("Nenhuma requisição POST encontrada.");
            }
        } catch (IOException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void adicionar(Map<String, Integer> mapa, String chave) {
        mapa.put(chave, mapa.getOrDefault(chave, 0) + 1);
    }
}

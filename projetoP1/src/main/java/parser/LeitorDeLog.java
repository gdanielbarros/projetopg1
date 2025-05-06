package parser;

import modelo.EntradaDeLog;

import java.util.regex.*;

public class LeitorDeLog {
    public static EntradaDeLog ler(String linha) {
        try {
        	Pattern pattern = Pattern.compile("(\\S+) - - \\[(.*?)\\] \"(\\w+) (.*?) HTTP/\\S+\" (\\d{3}) (\\d+|-) .*?\\\".*?\\\" \\\"(.*?)\\\"");
            Matcher matcher = pattern.matcher(linha);

            if (matcher.find()) {
                EntradaDeLog entrada = new EntradaDeLog();
                entrada.ip = matcher.group(1);
                entrada.dataCompleta = matcher.group(2);
                entrada.metodo = matcher.group(3);
                entrada.url = matcher.group(4);
                entrada.codigo = Integer.parseInt(matcher.group(5));
                entrada.tamanho = matcher.group(6).equals("-") ? 0 : Integer.parseInt(matcher.group(6));
                entrada.userAgent = matcher.group(7);
                return entrada;
            }
        } catch (Exception ignored) {
        }
        return null;
    }
}
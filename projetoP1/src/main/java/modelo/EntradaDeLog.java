package modelo;

public class EntradaDeLog {
    public String ip;
    public String dataCompleta;
    public String metodo;
    public String url;
    public int codigo;
    public int tamanho;
    public String userAgent;

    public boolean foiSucesso() {
        return codigo >= 200 && codigo <= 299;
    }

    public boolean foiErroCliente() {
        return codigo >= 400 && codigo <= 499;
    }

    public boolean ehDeNovembro2021() {
        return dataCompleta.contains("/Nov/2021:");
    }

    public boolean ehDoAno2021() {
        return dataCompleta.contains("/2021:");
    }
}

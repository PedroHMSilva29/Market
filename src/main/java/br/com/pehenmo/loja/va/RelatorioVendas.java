package br.com.pehenmo.loja.va;

import java.math.BigDecimal;
import java.time.LocalDate;

public class RelatorioVendas {

    private String nomeProduto;
    private Long quantidade;
    private LocalDate date;

    public RelatorioVendas() {}

    public RelatorioVendas(String nomeProduto, Long quantidade, LocalDate date) {
        this.nomeProduto = nomeProduto;
        this.quantidade = quantidade;
        this.date = date;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public Long getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Long quantidade) {
        this.quantidade = quantidade;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "RelatorioVendas{" +
                "nomeProduto='" + nomeProduto + '\'' +
                ", quantidade=" + quantidade +
                ", date=" + date +
                '}';
    }
}

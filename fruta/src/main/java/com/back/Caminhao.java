package com.back;

public class Caminhao {

    private int id;
    private String marca;
    private int ano;
    private int valor;
    private String tipo;
    private double carroceria;
    private int carga;

    public Caminhao(int id, String marca, int ano, int valor, String tipo, double carroceria, int carga) {
        this.id = id;
        this.marca = marca;
        this.ano = ano;
        this.valor = valor;
        this.tipo = tipo;
        this.carroceria = carroceria;
        this.carga = carga;
    }

    public int getId() {
        return id;
    }

    public String getMarca() {
        return marca;
    }

    public int getAno() {
        return ano;
    }

    public int getValor() {
        return valor;
    }

    public String getTipo() {
        return tipo;
    }

    public double getCarroceria() {
        return carroceria;
    }

    public int getCarga() {
        return carga;
    }
}

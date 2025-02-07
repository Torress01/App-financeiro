package com.caixa.planilha.models;

import com.caixa.planilha.Enum.Status;
import jakarta.persistence.*;

@Entity
@Table(name = "caixas")
public class Caixa {
    @Id
    private int id;
    @Column(length = 200)
    private String tipo;
    private float valor;
    private Status status;

    // Getters e Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

}

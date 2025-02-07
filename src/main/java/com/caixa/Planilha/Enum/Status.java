package com.caixa.planilha.Enum;

public enum Status {
    RECEITA(1),
    DESPESA(2);

    private final int valor;

    Status(int valor){
      this.valor = valor;
    }

    public int getValor(){
      return valor;
    }

}

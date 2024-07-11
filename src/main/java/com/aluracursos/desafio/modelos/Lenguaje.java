package com.aluracursos.desafio.modelos;


public enum Lenguaje {
    INGLES ("en", "Inglés"),
    ESPAÑOL("es", "Español");

    private final String abreviatura;
    private final String idioma;


    Lenguaje(String abreviatura, String idioma) {
        this.abreviatura = abreviatura;
        this.idioma = idioma;
    }
    public String getAbreviatura(){
        return abreviatura;
    }
    public String getIdioma(){
        return idioma;
    }
    public static Lenguaje fromString(String texto){
        for (Lenguaje lenguaje : Lenguaje.values()){
            if (lenguaje.abreviatura.equalsIgnoreCase(texto))
                return lenguaje;
        }
        throw new IllegalArgumentException("No hay idioma con abreviatura: " + texto);
    }
}
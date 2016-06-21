package br.com.cimobile.livrosfirebase;

/**
 * Created by saturnino on 20/06/16.
 */

public class Livro {

    private String titulo;
    private String autor;
    private String capa;

    public Livro() {
    }

    public Livro(String titulo, String autor, String capa) {
        this.titulo = titulo;
        this.autor = autor;
        this.capa = capa;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getCapa() {
        return capa;
    }

    public void setCapa(String capa) {
        this.capa = capa;
    }
}

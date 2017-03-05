package app.devmedia.com.br.appdevmedia.entity;

public class User {

    private String nome;
    private String email;
    private String minibio;
    private char sexo;
    private Profissao profissao;


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMinibio() {
        return minibio;
    }

    public void setMinibio(String minibio) {
        this.minibio = minibio;
    }

    public char getSexo() {
        return sexo;
    }

    public void setSexo(char sexo) {
        this.sexo = sexo;
    }

    public Profissao getProfissao() {
        return profissao;
    }

    public void setProfissao(Profissao profissao) {
        this.profissao = profissao;
    }

}

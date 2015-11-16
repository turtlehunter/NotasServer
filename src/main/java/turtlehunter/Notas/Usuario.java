package turtlehunter.Notas;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.ArrayList;

public class Usuario {
    private String username;
    private String password;
    private ArrayList<Materia> materias;
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Usuario usuario = (Usuario) o;

        return new EqualsBuilder()
                .append(getUsername(), usuario.getUsername())
                .append(getPassword(), usuario.getPassword())
                .append(getMaterias(), usuario.getMaterias())
                .append(getToken(), usuario.getToken())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getUsername())
                .append(getPassword())
                .append(getMaterias())
                .append(getToken())
                .toHashCode();
    }

    public Usuario() {
    }

    public Usuario(String username, String password) {
        new Usuario(username, password, null);
    }

    public Usuario(String username, String password, ArrayList<Materia> materias) {
        this.username = username;
        this.password = password;
        this.materias = materias;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<Materia> getMaterias() {
        return materias;
    }

    public void setMaterias(ArrayList<Materia> materias) {
        this.materias = materias;
    }

    public void addMateria(Materia materia) {
        this.materias.add(materia);
    }

    public void removeMateria(Materia materia) {
        this.materias.remove(materia);
    }

    public Usuario clone() {
        try {
            super.clone();
            Usuario usuario = new Usuario();
            usuario.setToken(this.token);
            usuario.setMaterias(this.materias);
            usuario.setPassword(this.password);
            usuario.setUsername(this.username);
            return usuario;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }
}

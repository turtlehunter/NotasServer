package turtlehunter.Notas;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.ArrayList;

public class Trimestre {
    private ArrayList<Nota> notas;

    public Trimestre() {
    }

    public ArrayList<Nota> getNotas() {
        return notas;
    }

    public void setNotas(ArrayList<Nota> notas) {
        this.notas = notas;
    }

    public Trimestre(ArrayList<Nota> notas) {

        this.notas = notas;
    }

    public void addNota(Nota nota) {
        notas.add(nota);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Trimestre trimestre = (Trimestre) o;

        return new EqualsBuilder()
                .append(getNotas(), trimestre.getNotas())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getNotas())
                .toHashCode();
    }

    public void removeNota(Nota nota) {
        notas.remove(nota);

    }
}

package turtlehunter.Notas;

public class Nota {
    private int nota;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Nota nota1 = (Nota) o;

        return new org.apache.commons.lang3.builder.EqualsBuilder()
                .append(getNota(), nota1.getNota())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new org.apache.commons.lang3.builder.HashCodeBuilder(17, 37)
                .append(getNota())
                .toHashCode();
    }

    public int getNota() {

        return nota;
    }

    public void setNota(int nota) {
        this.nota = nota;
    }

    public Nota(int nota) {

        this.nota = nota;
    }
}

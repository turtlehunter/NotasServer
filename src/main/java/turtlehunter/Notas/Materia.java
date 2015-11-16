package turtlehunter.Notas;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Materia {
    public Trimestre trimestre1;
    public Trimestre trimestre2;
    public Trimestre trimestre3;

    public Trimestre getTrimestre1() {
        return trimestre1;
    }

    public void setTrimestre1(Trimestre trimestre1) {
        this.trimestre1 = trimestre1;
    }

    public Trimestre getTrimestre2() {
        return trimestre2;
    }

    public void setTrimestre2(Trimestre trimestre2) {
        this.trimestre2 = trimestre2;
    }

    public Trimestre getTrimestre3() {
        return trimestre3;
    }

    public void setTrimestre3(Trimestre trimestre3) {
        this.trimestre3 = trimestre3;
    }

    public Materia(Trimestre trimestre1, Trimestre trimestre2, Trimestre trimestre3) {

        this.trimestre1 = trimestre1;
        this.trimestre2 = trimestre2;
        this.trimestre3 = trimestre3;
    }

    public void addNota(int trimestre, Nota nota) {
        switch (trimestre) {
            case 1:
                trimestre1.addNota(nota);
            case 2:
                trimestre2.addNota(nota);
            case 3:
                trimestre3.addNota(nota);
            default:
                throw new IllegalArgumentException("trimestre out of range");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Materia materia = (Materia) o;

        return new EqualsBuilder()
                .append(getTrimestre1(), materia.getTrimestre1())
                .append(getTrimestre2(), materia.getTrimestre2())
                .append(getTrimestre3(), materia.getTrimestre3())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getTrimestre1())
                .append(getTrimestre2())
                .append(getTrimestre3())
                .toHashCode();
    }

    public void removeNota(int trimestre, Nota nota) {
        switch (trimestre) {
            case 1:
                trimestre1.removeNota(nota);
            case 2:
                trimestre2.removeNota(nota);
            case 3:
                trimestre3.removeNota(nota);
            default:
                throw new IllegalArgumentException("trimestre out of range");
        }
    }

    public Materia() {
    }
}

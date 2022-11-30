package org.lonpe.model;

import java.time.LocalDate;
import java.math.BigDecimal;

public class Calificacion extends AbstractDcLon implements IDcLon {

    public Calificacion() {
        super();
    }

    private BigDecimal calificacion;

    /**
     *
     * @return calificacion
     */
    public BigDecimal getCalificacion() {
        return this.calificacion;
    }

    /**
     *
     * @param calificacion
     */
    public void setCalificacion(BigDecimal calificacion) {
        this.calificacion = calificacion;
    }

    private LocalDate fecha;

    /**
     *
     * @return fecha
     */
    public LocalDate getFecha() {
        return this.fecha;
    }

    /**
     *
     * @param fecha
     */
    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    private Alumno alumno;

    /**
     *
     * @return alumno
     */
    public Alumno getAlumno() {
        return this.alumno;
    }

    /**
     *
     * @param alumno
     */
    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }

    private Materia materia;

    /**
     *
     * @return materia
     */
    public Materia getMateria() {
        return this.materia;
    }

    /**
     *
     * @param materia
     */
    public void setMateria(Materia materia) {
        this.materia = materia;
    }

}

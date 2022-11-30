package org.lonpe.model;

import java.util.Set;

public class Alumno extends AbstractDcLon implements IDcLon {

    public Alumno() {
        super();
    }

    private Boolean activo;

    /**
     *
     * @return activo
     */
    public Boolean getActivo() {
        return this.activo;
    }

    /**
     *
     * @param activo
     */
    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    private String pname;

    /**
     *
     * @return pname
     */
    public String getPname() {
        return this.pname;
    }

    /**
     *
     * @param pname
     */
    public void setPname(String pname) {
        this.pname = pname;
    }

    private String primer_apellido;

    /**
     *
     * @return primer_apellido
     */
    public String getPrimer_apellido() {
        return this.primer_apellido;
    }

    /**
     *
     * @param primer_apellido
     */
    public void setPrimer_apellido(String primer_apellido) {
        this.primer_apellido = primer_apellido;
    }

    private String segundo_apellido;

    /**
     *
     * @return segundo_apellido
     */
    public String getSegundo_apellido() {
        return this.segundo_apellido;
    }

    /**
     *
     * @param segundo_apellido
     */
    public void setSegundo_apellido(String segundo_apellido) {
        this.segundo_apellido = segundo_apellido;
    }

    private Set<Calificacion> calificaciones;

    /**
     *
     * @return calificaciones
     */
    public Set<Calificacion> getCalificaciones() {
        return this.calificaciones;
    }

    /**
     *
     * @param calificaciones
     */
    public void setCalificaciones(Set<Calificacion> calificaciones) {
        this.calificaciones = calificaciones;
    }

}

package org.lonpe.model;

import java.util.Set;

public class Materia extends AbstractDcLon implements IDcLon {

    public Materia() {
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

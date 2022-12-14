package org.lonpe.model;

import java.util.Set;

public class Program extends AbstractDcLon implements IDcLon {

    public Program() {
        super();
    }

    private String description;

    /**
     *
     * @return description
     */
    public String getDescription() {
        return this.description;
    }

    /**
     *
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    private String fastKey;

    /**
     *
     * @return fastKey
     */
    public String getFastKey() {
        return this.fastKey;
    }

    /**
     *
     * @param fastKey
     */
    public void setFastKey(String fastKey) {
        this.fastKey = fastKey;
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

    private Set<ProgramBaseTimePeriod> programBaseTimePeriods;

    /**
     *
     * @return programBaseTimePeriods
     */
    public Set<ProgramBaseTimePeriod> getProgramBaseTimePeriods() {
        return this.programBaseTimePeriods;
    }

    /**
     *
     * @param programBaseTimePeriods
     */
    public void setProgramBaseTimePeriods(Set<ProgramBaseTimePeriod> programBaseTimePeriods) {
        this.programBaseTimePeriods = programBaseTimePeriods;
    }

    private Set<ProgramUserLon> programUserLons;

    /**
     *
     * @return programUserLons
     */
    public Set<ProgramUserLon> getProgramUserLons() {
        return this.programUserLons;
    }

    /**
     *
     * @param programUserLons
     */
    public void setProgramUserLons(Set<ProgramUserLon> programUserLons) {
        this.programUserLons = programUserLons;
    }

}

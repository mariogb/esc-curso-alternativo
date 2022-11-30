package org.lonpe.model;

import java.util.Set;

public class ProgramBaseTimePeriod extends AbstractDcLon implements IDcLon {

    public ProgramBaseTimePeriod() {
        super();
    }

    private BaseTimePeriod baseTimePeriod;

    /**
     *
     * @return baseTimePeriod
     */
    public BaseTimePeriod getBaseTimePeriod() {
        return this.baseTimePeriod;
    }

    /**
     *
     * @param baseTimePeriod
     */
    public void setBaseTimePeriod(BaseTimePeriod baseTimePeriod) {
        this.baseTimePeriod = baseTimePeriod;
    }

    private Program program;

    /**
     *
     * @return program
     */
    public Program getProgram() {
        return this.program;
    }

    /**
     *
     * @param program
     */
    public void setProgram(Program program) {
        this.program = program;
    }

  
}

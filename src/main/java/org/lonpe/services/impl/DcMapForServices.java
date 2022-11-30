package org.lonpe.services.impl;

import io.vertx.core.json.JsonObject;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import org.lonpe.services.AbstractServiceLon;

public class DcMapForServices {

    final Map<String, AbstractServiceLon> m;

    public DcMapForServices() {
        this.m = new HashMap<>();


        m.put("alumno", new AlumnoService());
        
        
   
        m.put("base", new BaseService());
        m.put("baseTimePeriod", new BaseTimePeriodService());
        m.put("baseUserLon", new BaseUserLonService());
        m.put("calificacion", new CalificacionService());


        m.put("departament", new DepartamentService());
        m.put("departamentBaseTimePeriod", new DepartamentBaseTimePeriodService());

        m.put("departamentUserLon", new DepartamentUserLonService());
        m.put("entityPermisionRole", new EntityPermisionRoleService());

        m.put("materia", new MateriaService());
        m.put("meUsrInterface", new MeUsrInterfaceService());

        m.put("program", new ProgramService());
        m.put("programBaseTimePeriod", new ProgramBaseTimePeriodService());

        m.put("programUserLon", new ProgramUserLonService());
        m.put("role", new RoleService());
      
        m.put("timePeriod", new TimePeriodService());
        m.put("userLon", new UserLonService());
        m.put("userRole", new UserRoleService());
     

    }

    public JsonObject model() {

        final JsonObject jsm = new JsonObject();
        m.forEach((String t, AbstractServiceLon u) -> {
            jsm.put(t, u.elModelo());
        });

        return new JsonObject().put("m_dcmodel", jsm);

    }

    public AbstractServiceLon getServiceFor(final String dc) {
        return m.get(dc);
    }

    public JsonObject modelFiltered(List<String> l_exclude) {
        final JsonObject jsm = new JsonObject();
        m.forEach((String t, AbstractServiceLon u) -> {
            if (!l_exclude.contains(t)) {
                jsm.put(t, u.elModelo());
            }
        });
        return jsm;
    }
}

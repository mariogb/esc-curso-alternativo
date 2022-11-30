package org.lonpe.mapstore;

import org.lonpe.model.Alumno;
import org.lonpe.services.DBLon1;
import org.lonpe.services.DBLon0;
import java.util.Collection;
import java.util.Map;
import java.util.LinkedHashMap;

public class AlumnoMapStore extends AbstractDCMapStore<Alumno> {

    private DBLon1 dBLon1;
    private DBLon0 dBLon0;

    public AlumnoMapStore(DBLon1 dBLon1, DBLon0 dBLon0) {
        this.dBLon1 = dBLon1;
        this.dBLon0 = dBLon0;
    }

    @Override
    public void store(String key, Alumno alumno) {
        dBLon0.store00("alumno", alumno);
    }

    @Override
    public Alumno load(String key) {
        return (Alumno) dBLon1.load00("alumno", key);
    }

    @Override
    public Iterable<String> loadAllKeys() {
        return dBLon1.loadAllKeys00("alumno");
    }

    @Override
    public Map<String, Alumno> loadAll(Collection<String> keys) {
        Map<String, Alumno> m = new LinkedHashMap<>();
        keys.stream().forEach((String t) -> {
            m.put(t, load(t));

        });
        return m;
    }

}

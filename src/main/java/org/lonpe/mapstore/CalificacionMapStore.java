package org.lonpe.mapstore;

import org.lonpe.model.Calificacion;
import org.lonpe.services.DBLon1;
import org.lonpe.services.DBLon0;
import java.util.Collection;
import java.util.Map;
import java.util.LinkedHashMap;

public class CalificacionMapStore extends AbstractDCMapStore<Calificacion> {

    private DBLon1 dBLon1;
    private DBLon0 dBLon0;

    public CalificacionMapStore(DBLon1 dBLon1, DBLon0 dBLon0) {
        this.dBLon1 = dBLon1;
        this.dBLon0 = dBLon0;
    }

    @Override
    public void store(String key, Calificacion calificacion) {
        dBLon0.store00("calificacion", calificacion);
    }

    @Override
    public Calificacion load(String key) {
        return (Calificacion) dBLon1.load00("calificacion", key);
    }

    @Override
    public Iterable<String> loadAllKeys() {
        return dBLon1.loadAllKeys00("calificacion");
    }

    @Override
    public Map<String, Calificacion> loadAll(Collection<String> keys) {
        Map<String, Calificacion> m = new LinkedHashMap<>();
        keys.stream().forEach((String t) -> {
            m.put(t, load(t));

        });
        return m;
    }

}

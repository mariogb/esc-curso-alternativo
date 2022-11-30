package org.lonpe.mapstore;

import org.lonpe.model.Materia;
import org.lonpe.services.DBLon1;
import org.lonpe.services.DBLon0;
import java.util.Collection;
import java.util.Map;
import java.util.LinkedHashMap;

public class MateriaMapStore extends AbstractDCMapStore<Materia> {

    private DBLon1 dBLon1;
    private DBLon0 dBLon0;

    public MateriaMapStore(DBLon1 dBLon1, DBLon0 dBLon0) {
        this.dBLon1 = dBLon1;
        this.dBLon0 = dBLon0;
    }

    @Override
    public void store(String key, Materia materia) {
        dBLon0.store00("materia", materia);
    }

    @Override
    public Materia load(String key) {
        return (Materia) dBLon1.load00("materia", key);
    }

    @Override
    public Iterable<String> loadAllKeys() {
        return dBLon1.loadAllKeys00("materia");
    }

    @Override
    public Map<String, Materia> loadAll(Collection<String> keys) {
        Map<String, Materia> m = new LinkedHashMap<>();
        keys.stream().forEach((String t) -> {
            m.put(t, load(t));

        });
        return m;
    }

}

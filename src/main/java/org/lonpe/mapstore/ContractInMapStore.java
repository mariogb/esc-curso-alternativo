package org.lonpe.mapstore;

import org.lonpe.model.ContractIn;
import org.lonpe.services.DBLon1;
import org.lonpe.services.DBLon0;
import java.util.Collection;
import java.util.Map;
import java.util.LinkedHashMap;

public class ContractInMapStore extends AbstractDCMapStore<ContractIn> {

    private DBLon1 dBLon1;
    private DBLon0 dBLon0;

    public ContractInMapStore(DBLon1 dBLon1, DBLon0 dBLon0) {
        this.dBLon1 = dBLon1;
        this.dBLon0 = dBLon0;
    }

    @Override
    public void store(String key, ContractIn contractIn) {
        dBLon0.store00("contractIn", contractIn);
    }

    @Override
    public ContractIn load(String key) {
        return (ContractIn) dBLon1.load00("contractIn", key);
    }

    @Override
    public Iterable<String> loadAllKeys() {
        return dBLon1.loadAllKeys00("contractIn");
    }

    @Override
    public Map<String, ContractIn> loadAll(Collection<String> keys) {
        Map<String, ContractIn> m = new LinkedHashMap<>();
        keys.stream().forEach((String t) -> {
            m.put(t, load(t));

        });
        return m;
    }

}

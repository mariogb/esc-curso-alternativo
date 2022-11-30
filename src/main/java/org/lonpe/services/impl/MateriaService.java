package org.lonpe.services.impl;

import io.vertx.core.MultiMap;
import java.util.Map;
import io.vertx.core.json.JsonObject;
import io.vertx.sqlclient.Tuple;
import io.vertx.core.json.JsonArray;
import org.lonpe.model.*;
import io.vertx.sqlclient.Row;
import java.util.LinkedHashSet;
import java.util.HashMap;
import java.util.LinkedHashMap;
import org.lonpe.lonvx.sqlbuilders.SqlZtatBuilder;
import org.lonpe.services.AbstractServiceLon;
import org.lonpe.services.ConditionInfo;
import org.lonpe.lonvx.sqlbuilders.SqlLonConditionsBuilder;
import org.apache.poi.xssf.usermodel.XSSFRow;
import static org.lonpe.lonvx.ctes.CteLon.*;

/**
 * MateriaService
 *
 */
public class MateriaService extends AbstractServiceLon<Materia> {

    private static final String SQLINSERT = "INSERT INTO materia(pkey,activo,pname) VALUES ($1,$2,$3) returning id,pkey";
    private static final String SQLUPDATE = "UPDATE materia SET activo = $1,pname = $2 WHERE id = $3 returning id,pkey";
    private static final String SQLUPDATEPKEY = "UPDATE materia SET activo = $1,pname = $2 WHERE pkey = $3 returning id,pkey";
    private static final String SQLVIEW = "SELECT * FROM materia_view";
    private static final String SQLCOUNT = "SELECT count(*) FROM materia_view";
    private static final String SQLKEYS = "SELECT materia_pkey FROM materia_view";
    private static final String SQLIDBYPKEY = "SELECT id from materia WHERE pkey = $1";
    private static final String SQLLKEYIN = "SELECT id,pkey from materia WHERE pkey in ($1)";
    private static final String SQLDELETE = "DELETE FROM materia WHERE id = $1 returning id";
    private static final String TABLENAME = "materia";

    public MateriaService() {
        init0();
    }

    @Override
    public String getTableName() {
        return TABLENAME;
    }

    @Override
    public String getSqlDelete() {
        return SQLDELETE;
    }

    @Override
    public String getSqlKeyIn() {
        return SQLLKEYIN;
    }

    /**
     * private static String sql00 = "SELECT materia.id as materia_id,
     * materia.pkey as materia_pkey, materia.activo as materia_activo,
     * materia.pname as materia_pname FROM materia ; "
     */
    @Override
    public String getSqlKeys() {
        return SQLKEYS;
    }

    @Override
    public String getSqlCount() {
        return SQLCOUNT;
    }

    @Override
    public String getSqlIdByPkey() {
        return SQLIDBYPKEY;
    }

    /**
     * sql select property alias field names
     */
    private final LinkedHashSet<String> names = new LinkedHashSet<>();
    ;
    
    /**
     * Map field insert/update to property 
     */
    private final HashMap<String, String> insertMapFields = new HashMap<>();

    /**
     * Map property to field order
     */
    private final HashMap<String, String> sortMapFields = new HashMap<>();

    private final JsonObject dcModel = new JsonObject();

    private void init0() {

        dcModel.put("dc", "materia");

//ID 
        names.add("id");

        sortMapFields.put("id", "materia_id");

        final JsonArray ps = new JsonArray();

//pkey
        doFieldSort("pkey", "pkey", "materia");

//Used to map error on index to source property because IS Unique
        insertMapFields.put("materia.materia_uidx_pkey", "pkey");

//Create property pkey       
        final JsonObject pkey = psString("pkey", true);

// IS Unique     
        pkey.put("uq", true);

        ps.add(pkey);

//activo
        doFieldSort("activo", "activo", "materia");

//Create property activo       
        final JsonObject activo = psBoolean("activo", true);

        ps.add(activo);

//pname
        doFieldSort("pname", "pname", "materia");

//Create property pname       
        final JsonObject pname = psString("pname", true);

//PC
        dcModel.put("pc", "pname");

        ps.add(pname);

//Add ps to model            
        dcModel.put("ps", ps);

        final JsonArray otm = new JsonArray();

        applyOtm(otm, "calificaciones", "calificacion");

        /**
         * OTM ON MODEL  *
         */
        dcModel.put("otm", otm);

    }

    @Override
    public LinkedHashSet<String> getNames() {
        return names;
    }

    @Override
    public HashMap<String, String> getInsertMapFields() {
        return insertMapFields;
    }

    @Override
    public HashMap<String, String> getSortMapFields() {
        return sortMapFields;
    }

    @Override
    public JsonObject elModelo() {
        return dcModel;
    }

    @Override
    public JsonArray toJsonArray(final Row r) {
        final JsonArray jsa = new JsonArray();
        jsa.add(r.getLong("materia_id"));
        jsa.add(r.getString("materia_pkey"));
        jsa.add(r.getBoolean("materia_activo"));
        jsa.add(r.getString("materia_pname"));
        return jsa;
    }

    @Override
    public int fillXRow(final Row r, final XSSFRow row, int nc, boolean withIds) {
        return fillXRow0(r, row, nc, withIds);
    }

    @Override
    public HashMap<String, String> lXRowH(final boolean withIds, final int level) {

        final LinkedHashMap<String, String> m = new LinkedHashMap<>();

        if (withIds) {
            m.put("materia_id", LONG);
        }
//pkey    
        m.put("materia_pkey", STRING);
//activo    
        m.put("materia_activo", BOOLEAN);
//pname    
        m.put("materia_pname", STRING);

        return m;

    }

    private int fillXRow0(final Row r, final XSSFRow row, int nc, final boolean withIds) {

        if (withIds) {
            lToCell(r, row, "materia_id", nc++);
        }            //pkey       
        sToCell(r, row, "materia_pkey", nc++);     //activo     
        bToCell(r, row, "materia_activo", nc++);     //pname       
        sToCell(r, row, "materia_pname", nc++);
        return nc;
    }

    @Override
    public String getSqlView() {
        return SQLVIEW;
    }

    @Override
    public String getSqlByKey() {
        return SQLVIEW + " WHERE materia_pkey =$1";
    }

    @Override
    public String getSqlInsert() {
        return SQLINSERT;
    }

    @Override
    public void fillTupleInsert(final Materia dc0, final Tuple t) {

        t.addString(dc0.getPkey());
        t.addBoolean(dc0.getActivo());
        t.addString(dc0.getPname());
    }

    @Override
    public void fillTupleUpdate(final Materia dc0, final Tuple t) {

        t.addBoolean(dc0.getActivo());
        t.addString(dc0.getPname());
        t.addLong(dc0.getId());

    }

    @Override
    public void fillTupleInsert(final Map<String, Object> obj, final Tuple t) {

        fTString("pkey", obj, t);

        fTBoolean("activo", obj, t);

        fTString("pname", obj, t);
    }

    @Override
    public void populateParentsIds(final Map<String, Object> obj, final Map<String, Map<String, Long>> mapParents) {

    }

    @Override
    public void fillTupleInsert(final JsonObject js, final Tuple t) {

        fTString("pkey", js, t);
        fTBoolean("activo", js, t);
        fTString("pname", js, t);
    }

    @Override
    public void fillTupleUpdate(JsonObject js, Tuple t) {
        fTBoolean("activo", js, t);
        fTString("pname", js, t);
        fTLong("id", js, t);
    }

    @Override
    public String getSqlIUpdate() {
        return SQLUPDATE;
    }

    @Override
    public String getSqlIUpdatePkey() {
        return SQLUPDATEPKEY;
    }

    @Override
    public Materia doFrom(final Row r) {
        final Materia materia = new Materia();
        materia.setId(r.getLong("materia_id"));
        materia.setPkey(r.getString("materia_pkey"));
        materia.setActivo(r.getBoolean("materia_activo"));
        materia.setPname(r.getString("materia_pname"));
        return materia;
    }

    @Override
    public Materia doFromJson(final JsonObject js) {
        Materia materia = new Materia();
        materia.setId(js.getLong("id"));

        materia.setPkey(js.getString("pkey"));
        materia.setActivo(js.getBoolean("activo"));
        materia.setPname(js.getString("pname"));
        return materia;
    }

    @Override
    public JsonObject toJson(final Materia o) {
        final JsonObject jso = new JsonObject();
        jso.put("id", o.getId());
        jso.put("pkey", o.getPkey());
        jso.put("activo", o.getActivo());
        jso.put("pname", o.getPname());
        return jso;
    }

    @Override
    public ConditionInfo doCondiciones(final MultiMap params, final Tuple tuple) {

        final SqlLonConditionsBuilder slcb = new SqlLonConditionsBuilder(params, tuple);

        //Check Id      
        slcb.doInLongCondition("id", "materia_id");
        //*---PKEY ---       
        slcb.doIlPSimple2("pkey", "materia_pkey");
        slcb.doEqInPSimple("pkey", "materia_pkey");
//*---PC ---" + pname
        slcb.doIlPSimple2("pname", "materia_pname");
        slcb.doEqInPSimple("pname", "materia_pname");

        slcb.doSQLORDEN(sortMapFields);

        return slcb.getConditionInfo();

    }

    @Override
    public SqlZtatBuilder doZtat(final MultiMap params) {
        final SqlZtatBuilder sz0 = new SqlZtatBuilder(params, "materia");
        sz0.addField("COUNT(materia.id) as c_materia_id").addName("count");

        return sz0;
    }
}

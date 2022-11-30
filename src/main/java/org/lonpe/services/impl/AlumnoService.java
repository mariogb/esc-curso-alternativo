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
 * AlumnoService
 *
 */
public class AlumnoService extends AbstractServiceLon<Alumno> {

    private static final String SQLINSERT = "INSERT INTO alumno(pkey,activo,pname,primer_apellido,segundo_apellido) VALUES ($1,$2,$3,$4,$5) returning id,pkey";
    private static final String SQLUPDATE = "UPDATE alumno SET activo = $1,pname = $2,primer_apellido = $3,segundo_apellido = $4 WHERE id = $5 returning id,pkey";
    private static final String SQLUPDATEPKEY = "UPDATE alumno SET activo = $1,pname = $2,primer_apellido = $3,segundo_apellido = $4 WHERE pkey = $5 returning id,pkey";
    private static final String SQLVIEW = "SELECT * FROM alumno_view";
    private static final String SQLCOUNT = "SELECT count(*) FROM alumno_view";
    private static final String SQLKEYS = "SELECT alumno_pkey FROM alumno_view";
    private static final String SQLIDBYPKEY = "SELECT id from alumno WHERE pkey = $1";
    private static final String SQLLKEYIN = "SELECT id,pkey from alumno WHERE pkey in ($1)";
    private static final String SQLDELETE = "DELETE FROM alumno WHERE id = $1 returning id";
    private static final String TABLENAME = "alumno";

    public AlumnoService() {
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
     * private static String sql00 = "SELECT alumno.id as alumno_id, alumno.pkey
     * as alumno_pkey, alumno.activo as alumno_activo, alumno.pname as
     * alumno_pname, alumno.primer_apellido as alumno_primer_apellido,
     * alumno.segundo_apellido as alumno_segundo_apellido FROM alumno ; "
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

        dcModel.put("dc", "alumno");

//ID 
        names.add("id");

        sortMapFields.put("id", "alumno_id");

        final JsonArray ps = new JsonArray();

//pkey
        doFieldSort("pkey", "pkey", "alumno");

//Used to map error on index to source property because IS Unique
        insertMapFields.put("alumno.alumno_uidx_pkey", "pkey");

//Create property pkey       
        final JsonObject pkey = psString("pkey", true);

// IS Unique     
        pkey.put("uq", true);

        ps.add(pkey);

//activo
        doFieldSort("activo", "activo", "alumno");

//Create property activo       
        final JsonObject activo = psBoolean("activo", true);

        ps.add(activo);

//pname
        doFieldSort("pname", "pname", "alumno");

//Create property pname       
        final JsonObject pname = psString("pname", true);

//PC
        dcModel.put("pc", "pname");

        ps.add(pname);

//primer_apellido
        doFieldSort("primer_apellido", "primer_apellido", "alumno");

//Create property primer_apellido       
        final JsonObject primer_apellido = psString("primer_apellido", true);

        ps.add(primer_apellido);

//segundo_apellido
        doFieldSort("segundo_apellido", "segundo_apellido", "alumno");

//Create property segundo_apellido       
        final JsonObject segundo_apellido = psString("segundo_apellido", true);

        ps.add(segundo_apellido);

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
        jsa.add(r.getLong("alumno_id"));
        jsa.add(r.getString("alumno_pkey"));
        jsa.add(r.getBoolean("alumno_activo"));
        jsa.add(r.getString("alumno_pname"));
        jsa.add(r.getString("alumno_primer_apellido"));
        jsa.add(r.getString("alumno_segundo_apellido"));
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
            m.put("alumno_id", LONG);
        }
//pkey    
        m.put("alumno_pkey", STRING);
//activo    
        m.put("alumno_activo", BOOLEAN);
//pname    
        m.put("alumno_pname", STRING);
//primer_apellido    
        m.put("alumno_primer_apellido", STRING);
//segundo_apellido    
        m.put("alumno_segundo_apellido", STRING);

        return m;

    }

    private int fillXRow0(final Row r, final XSSFRow row, int nc, final boolean withIds) {

        if (withIds) {
            lToCell(r, row, "alumno_id", nc++);
        }            //pkey       
        sToCell(r, row, "alumno_pkey", nc++);     //activo     
        bToCell(r, row, "alumno_activo", nc++);     //pname       
        sToCell(r, row, "alumno_pname", nc++);     //primer_apellido       
        sToCell(r, row, "alumno_primer_apellido", nc++);     //segundo_apellido       
        sToCell(r, row, "alumno_segundo_apellido", nc++);
        return nc;
    }

    @Override
    public String getSqlView() {
        return SQLVIEW;
    }

    @Override
    public String getSqlByKey() {
        return SQLVIEW + " WHERE alumno_pkey =$1";
    }

    @Override
    public String getSqlInsert() {
        return SQLINSERT;
    }

    @Override
    public void fillTupleInsert(final Alumno dc0, final Tuple t) {

        t.addString(dc0.getPkey());
        t.addBoolean(dc0.getActivo());
        t.addString(dc0.getPname());
        t.addString(dc0.getPrimer_apellido());
        t.addString(dc0.getSegundo_apellido());
    }

    @Override
    public void fillTupleUpdate(final Alumno dc0, final Tuple t) {

        t.addBoolean(dc0.getActivo());
        t.addString(dc0.getPname());
        t.addString(dc0.getPrimer_apellido());
        t.addString(dc0.getSegundo_apellido());
        t.addLong(dc0.getId());

    }

    @Override
    public void fillTupleInsert(final Map<String, Object> obj, final Tuple t) {

        fTString("pkey", obj, t);

        fTBoolean("activo", obj, t);

        fTString("pname", obj, t);

        fTString("primer_apellido", obj, t);

        fTString("segundo_apellido", obj, t);
    }

    @Override
    public void populateParentsIds(final Map<String, Object> obj, final Map<String, Map<String, Long>> mapParents) {

    }

    @Override
    public void fillTupleInsert(final JsonObject js, final Tuple t) {

        fTString("pkey", js, t);
        fTBoolean("activo", js, t);
        fTString("pname", js, t);
        fTString("primer_apellido", js, t);
        fTString("segundo_apellido", js, t);
    }

    @Override
    public void fillTupleUpdate(JsonObject js, Tuple t) {
        fTBoolean("activo", js, t);
        fTString("pname", js, t);
        fTString("primer_apellido", js, t);
        fTString("segundo_apellido", js, t);
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
    public Alumno doFrom(final Row r) {
        final Alumno alumno = new Alumno();
        alumno.setId(r.getLong("alumno_id"));
        alumno.setPkey(r.getString("alumno_pkey"));
        alumno.setActivo(r.getBoolean("alumno_activo"));
        alumno.setPname(r.getString("alumno_pname"));
        alumno.setPrimer_apellido(r.getString("alumno_primer_apellido"));
        alumno.setSegundo_apellido(r.getString("alumno_segundo_apellido"));
        return alumno;
    }

    @Override
    public Alumno doFromJson(final JsonObject js) {
        Alumno alumno = new Alumno();
        alumno.setId(js.getLong("id"));

        alumno.setPkey(js.getString("pkey"));
        alumno.setActivo(js.getBoolean("activo"));
        alumno.setPname(js.getString("pname"));
        alumno.setPrimer_apellido(js.getString("primer_apellido"));
        alumno.setSegundo_apellido(js.getString("segundo_apellido"));
        return alumno;
    }

    @Override
    public JsonObject toJson(final Alumno o) {
        final JsonObject jso = new JsonObject();
        jso.put("id", o.getId());
        jso.put("pkey", o.getPkey());
        jso.put("activo", o.getActivo());
        jso.put("pname", o.getPname());
        jso.put("primer_apellido", o.getPrimer_apellido());
        jso.put("segundo_apellido", o.getSegundo_apellido());
        return jso;
    }

    @Override
    public ConditionInfo doCondiciones(final MultiMap params, final Tuple tuple) {

        final SqlLonConditionsBuilder slcb = new SqlLonConditionsBuilder(params, tuple);

        //Check Id      
        slcb.doInLongCondition("id", "alumno_id");
        //*---PKEY ---       
        slcb.doIlPSimple2("pkey", "alumno_pkey");
        slcb.doEqInPSimple("pkey", "alumno_pkey");
//*---PC ---" + pname
        slcb.doIlPSimple2("pname", "alumno_pname");
        slcb.doEqInPSimple("pname", "alumno_pname");

        slcb.doSQLORDEN(sortMapFields);

        return slcb.getConditionInfo();

    }

    @Override
    public SqlZtatBuilder doZtat(final MultiMap params) {
        final SqlZtatBuilder sz0 = new SqlZtatBuilder(params, "alumno");
        sz0.addField("COUNT(alumno.id) as c_alumno_id").addName("count");

        return sz0;
    }
}

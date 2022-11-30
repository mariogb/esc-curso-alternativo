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
import java.time.LocalDate;

import java.math.BigDecimal;
import org.lonpe.lonvx.sqlbuilders.ZtatUnitInfoLon;
import org.lonpe.lonvx.sqlbuilders.ZtatUnitInfoLon2;
import org.lonpe.lonvx.sqlbuilders.ZtatUnitInfoLon3;

/**
 * CalificacionService
 *
 */
public class CalificacionService extends AbstractServiceLon<Calificacion> {

    private static final String SQLINSERT = "INSERT INTO calificacion(pkey,calificacion,fecha,alumno_id,materia_id) VALUES ($1,$2,$3,$4,$5) returning id,pkey";
    private static final String SQLUPDATE = "UPDATE calificacion SET calificacion = $1,fecha = $2 WHERE id = $7 returning id,pkey";
    private static final String SQLUPDATEPKEY = "UPDATE calificacion SET calificacion = $1,fecha = $2 WHERE pkey = $7 returning id,pkey";
    private static final String SQLVIEW = "SELECT * FROM calificacion_view";
    private static final String SQLCOUNT = "SELECT count(*) FROM calificacion_view";
    private static final String SQLKEYS = "SELECT calificacion_pkey FROM calificacion_view";
    private static final String SQLIDBYPKEY = "SELECT id from calificacion WHERE pkey = $1";
    private static final String SQLLKEYIN = "SELECT id,pkey from calificacion WHERE pkey in ($1)";
    private static final String SQLDELETE = "DELETE FROM calificacion WHERE id = $1 returning id";
    private static final String TABLENAME = "calificacion";

    public CalificacionService() {
        init0();
    }

    private static final Map<String, ZtatUnitInfoLon> mz1 = new HashMap<>(6);

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
     * private static String sql00 = "SELECT calificacion.id as calificacion_id,
     * calificacion.pkey as calificacion_pkey, calificacion.calificacion as
     * calificacion_calificacion, calificacion.fecha as calificacion_fecha,
     * alumno.id as alumno_id,alumno.pkey as alumno_pkey,alumno.pname as
     * alumno_pname, materia.id as materia_id,materia.pkey as
     * materia_pkey,materia.pname as materia_pname FROM calificacion, alumno as
     * alumno, materia as materia WHERE calificacion.alumno_id = alumno.id AND
     * calificacion.materia_id = materia.id; "
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

        dcModel.put("dc", "calificacion");

//ID 
        names.add("id");

        sortMapFields.put("id", "calificacion_id");

        final JsonArray ps = new JsonArray();

//pkey
        doFieldSort("pkey", "pkey", "calificacion");

//Used to map error on index to source property because IS Unique
        insertMapFields.put("calificacion.calificacion_uidx_pkey", "pkey");

//Create property pkey       
        final JsonObject pkey = psString("pkey", true);

// IS Unique     
        pkey.put("uq", true);

        ps.add(pkey);

//calificacion
        doFieldSort("calificacion", "calificacion", "calificacion");

//Create property calificacion       
        final JsonObject calificacion = psBigDecimal("calificacion", true);

        calificacion.put("min", 0);

        calificacion.put("max", 10);

        ps.add(calificacion);

//fecha
        doFieldSort("fecha", "fecha", "calificacion");

//Create property fecha       
        final JsonObject fecha = psLocalDate("fecha", true);

        ps.add(fecha);

//Add ps to model            
        dcModel.put("ps", ps);

        final JsonArray mto = new JsonArray();

//(1)  alumno
        doFieldMT0("calificacion", "alumno", "alumno");

        final JsonObject alumno = doMto("alumno", "alumno");

        names.add("alumno_pname");
        sortMapFields.put("alumno_pname", "alumno_pname");
        alumno.put("pc", "pname");

        mto.add(alumno);

        //1  alumno  -- alumno_id
        final ZtatUnitInfoLon zAlumno = new ZtatUnitInfoLon("alumno_id", "alumno", "alumno", "pname", "alumno");
        mz1.put("zAlumno", zAlumno);

//(1)  materia
        doFieldMT0("calificacion", "materia", "materia");

        final JsonObject materia = doMto("materia", "materia");

        names.add("materia_pname");
        sortMapFields.put("materia_pname", "materia_pname");
        materia.put("pc", "pname");

        mto.add(materia);

        //1  materia  -- materia_id
        final ZtatUnitInfoLon zMateria = new ZtatUnitInfoLon("materia_id", "materia", "materia", "pname", "materia");
        mz1.put("zMateria", zMateria);

        dcModel.put("mto", mto);

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
        jsa.add(r.getLong("calificacion_id"));
        jsa.add(r.getString("calificacion_pkey"));
        jsa.add(r.getBigDecimal("calificacion_calificacion"));
        jsa.add(r.getLocalDate("calificacion_fecha").toString()); // undefined
        jsa.add(r.getLong("alumno_id"));
        jsa.add(r.getString("alumno_pkey"));

        jsa.add(r.getString("alumno_pname"));
        jsa.add(r.getLong("materia_id"));
        jsa.add(r.getString("materia_pkey"));

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
            m.put("calificacion_id", LONG);
        }
//pkey    
        m.put("calificacion_pkey", STRING);
//calificacion    
        m.put("calificacion_calificacion", BIGDECIMAL);
//fecha    
        m.put("calificacion_fecha", LOCALDATE);
        if (level < 1) {
            return m;
        }
// alumno   alumno
        if (withIds) {
            m.put("alumno_id", LONG);
        }
        m.put("alumno_pkey", STRING);
        m.put("alumno_pname", STRING);
// materia   materia
        if (withIds) {
            m.put("materia_id", LONG);
        }
        m.put("materia_pkey", STRING);
        m.put("materia_pname", STRING);

        return m;

    }

    private int fillXRow0(final Row r, final XSSFRow row, int nc, final boolean withIds) {

        if (withIds) {
            lToCell(r, row, "calificacion_id", nc++);
        }            //pkey       
        sToCell(r, row, "calificacion_pkey", nc++);     //calificacion     
        bdToCell(r, row, "calificacion_calificacion", nc++);     //fecha            
        ldToCell(r, row, "calificacion_fecha", nc++);
//alumno   alumno        
        if (withIds) {
            lToCell(r, row, "alumno_id", nc++);
        }
        sToCell(r, row, "alumno_pkey", nc++);
        sToCell(r, row, "alumno_pname", nc++);
//materia   materia        
        if (withIds) {
            lToCell(r, row, "materia_id", nc++);
        }
        sToCell(r, row, "materia_pkey", nc++);
        sToCell(r, row, "materia_pname", nc++);
        return nc;
    }

    @Override
    public String getSqlView() {
        return SQLVIEW;
    }

    @Override
    public String getSqlByKey() {
        return SQLVIEW + " WHERE calificacion_pkey =$1";
    }

    @Override
    public String getSqlInsert() {
        return SQLINSERT;
    }

    @Override
    public void fillTupleInsert(final Calificacion dc0, final Tuple t) {

        t.addString(dc0.getPkey());
        t.addBigDecimal(dc0.getCalificacion());
        t.addLocalDate(dc0.getFecha());
        if (dc0.getAlumno() != null) {
            t.addLong(dc0.getAlumno().getId());
        }
        if (dc0.getMateria() != null) {
            t.addLong(dc0.getMateria().getId());
        }
    }

    @Override
    public void fillTupleUpdate(final Calificacion dc0, final Tuple t) {

        t.addBigDecimal(dc0.getCalificacion());
        t.addLocalDate(dc0.getFecha());
//      if(dc0.getAlumno()!=null){
//            t.addLong(dc0.getAlumno().getId());
//      }   
//      if(dc0.getMateria()!=null){
//            t.addLong(dc0.getMateria().getId());
//      }
        t.addLong(dc0.getId());

    }

    @Override
    public void fillTupleInsert(final Map<String, Object> obj, final Tuple t) {

        fTString("pkey", obj, t);

        fTBigDecimal("calificacion", obj, t);

        t.addLocalDate((LocalDate) obj.get("fecha"));

        fTLong("alumno_id", obj, t);

        fTLong("materia_id", obj, t);
    }

    @Override
    public void populateParentsIds(final Map<String, Object> obj, final Map<String, Map<String, Long>> mapParents) {

        final Map<String, Long> alumno = mapParents.get("alumno");
        final Long alumno_id = alumno.get((String) obj.get("alumno_pkey"));
        obj.put("alumno_id", alumno_id);

        final Map<String, Long> materia = mapParents.get("materia");
        final Long materia_id = materia.get((String) obj.get("materia_pkey"));
        obj.put("materia_id", materia_id);
    }

    @Override
    public void fillTupleInsert(final JsonObject js, final Tuple t) {

        fTString("pkey", js, t);
        fTBigDecimal("calificacion", js, t);
        t.addLocalDate(LocalDate.parse(js.getString("fecha")));
        fTLong("alumno_id", js, t);
        fTLong("materia_id", js, t);
    }

    @Override
    public void fillTupleUpdate(JsonObject js, Tuple t) {
        fTBigDecimal("calificacion", js, t);
        t.addLocalDate(LocalDate.parse(js.getString("fecha")));

        //     fTLong("alumno_id",js,t);
        //     fTLong("materia_id",js,t);
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
    public Calificacion doFrom(final Row r) {
        final Calificacion calificacion = new Calificacion();
        calificacion.setId(r.getLong("calificacion_id"));
        calificacion.setPkey(r.getString("calificacion_pkey"));
        calificacion.setCalificacion(r.getBigDecimal("calificacion_calificacion"));
        calificacion.setFecha(r.getLocalDate("fecha"));
        final Alumno alumno = new Alumno();
        alumno.setId(r.getLong("alumno_id"));
        alumno.setPkey(r.getString("alumno_pkey"));

        alumno.setPname(r.getString("alumno_pname"));
        calificacion.setAlumno(alumno);

        final Materia materia = new Materia();
        materia.setId(r.getLong("materia_id"));
        materia.setPkey(r.getString("materia_pkey"));

        materia.setPname(r.getString("materia_pname"));
        calificacion.setMateria(materia);

        return calificacion;
    }

    @Override
    public Calificacion doFromJson(final JsonObject js) {
        Calificacion calificacion = new Calificacion();
        calificacion.setId(js.getLong("id"));

        calificacion.setPkey(js.getString("pkey"));
        calificacion.setCalificacion(new BigDecimal(js.getString("calificacion")));
        calificacion.setFecha(LocalDate.parse(js.getString("fecha")));
        calificacion.setId(js.getLong("alumno_id"));
        calificacion.setId(js.getLong("materia_id"));
        return calificacion;
    }

    @Override
    public JsonObject toJson(final Calificacion o) {
        final JsonObject jso = new JsonObject();
        jso.put("id", o.getId());
        jso.put("pkey", o.getPkey());
        jso.put("calificacion", o.getCalificacion());
        jso.put("fecha", o.getFecha());

        final Alumno alumno = o.getAlumno();
        if (alumno != null) {
            jso.put("alumno_id", alumno.getId());
            jso.put("alumno_pkey", alumno.getPkey());
        }

        final Materia materia = o.getMateria();
        if (materia != null) {
            jso.put("materia_id", materia.getId());
            jso.put("materia_pkey", materia.getPkey());
        }

        return jso;
    }

    @Override
    public ConditionInfo doCondiciones(final MultiMap params, final Tuple tuple) {

        final SqlLonConditionsBuilder slcb = new SqlLonConditionsBuilder(params, tuple);

        //Check Id      
        slcb.doInLongCondition("id", "calificacion_id");
        //*---PKEY ---       
        slcb.doIlPSimple2("pkey", "calificacion_pkey");
        slcb.doEqInPSimple("pkey", "calificacion_pkey");
        slcb.doGEPSimpleLocalDate("fecha", "calificacion_fecha");
        slcb.doLPSimpleLocalDate("fecha", "calificacion_fecha");

        slcb.doIlPSimple2("alumno_pkey", "alumno_pkey");
        slcb.doEQPSimple2("alumno_pkey", "alumno_pkey");
        slcb.doInLongCondition("alumno_id", "alumno_id");
//Alumno 2        --
        slcb.doIlPSimple2("alumno_pname", "alumno_pname");

        slcb.doIlPSimple2("materia_pkey", "materia_pkey");
        slcb.doEQPSimple2("materia_pkey", "materia_pkey");
        slcb.doInLongCondition("materia_id", "materia_id");
//Materia 2        --
        slcb.doIlPSimple2("materia_pname", "materia_pname");

        slcb.doSQLORDEN(sortMapFields);

        return slcb.getConditionInfo();

    }

    @Override
    public SqlZtatBuilder doZtat(final MultiMap params) {
        final SqlZtatBuilder sz0 = new SqlZtatBuilder(params, "calificacion");
        sz0.addField("COUNT(calificacion.id) as c_calificacion_id").addName("count");

        sz0.addField("sum(calificacion.calificacion) as sum_calificacion_calificacion").addName("sum_calificacion");

//level 1
        sz0.applyG1(mz1.get("zAlumno"));
        sz0.applyG1(mz1.get("zMateria"));
//level 2    
//level 3    
        return sz0;
    }
}

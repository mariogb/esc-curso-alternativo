/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.lonpe.services;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.lonpe.lonvx.sqlbuilders.SqlZtatBuilder;
import org.lonpe.model.AbstractDcLon;

import io.vertx.core.MultiMap;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.sqlclient.Row;
import io.vertx.sqlclient.Tuple;
import java.util.Set;
import org.lonpe.model.IDcLon;
import static org.lonpe.lonvx.ctes.CteLon.*;

/**
 *
 * @author Mario García Burgos
 * @param <DC>
 */
public abstract class AbstractServiceLon<DC extends IDcLon> implements IServiceLon<DC> {

    @Override
    public abstract void populateParentsIds(final Map<String, Object> obj, final Map<String, Map<String, Long>> mapParents);

    @Override
    public abstract void fillTupleInsert(final Map<String, Object> obj, final Tuple t);

    @Override
    public abstract ConditionInfo doCondiciones(final MultiMap params, final Tuple tuple);

    @Override
    public abstract String getSqlInsert();

    @Override
    public abstract String getSqlIUpdate();

    @Override
    public abstract String getSqlIUpdatePkey();

    @Override
    public abstract String getSqlView();

    @Override
    public abstract String getSqlKeys();

    @Override
    public abstract String getSqlCount();

    @Override
    public abstract String getTableName();

    @Override
    public abstract Set<String> getNames();

    @Override
    public abstract String getSqlByKey();

    @Override
    public abstract void fillTupleInsert(JsonObject js, Tuple t);

    @Override
    public abstract void fillTupleUpdate(JsonObject js, Tuple t);

    @Override
    public abstract void fillTupleInsert(DC dc0, Tuple t);

    @Override
    public abstract void fillTupleUpdate(DC dc0, Tuple t);

    @Override
    public abstract JsonArray toJsonArray(Row r);

    @Override
    public abstract DC doFrom(Row r);

    @Override
    public abstract DC doFromJson(JsonObject js);

    @Override
    public abstract JsonObject toJson(DC o);

    @Override
    public abstract JsonObject elModelo();

    @Override
    public abstract String getSqlIdByPkey();

    @Override
    public abstract int fillXRow(Row r, XSSFRow row, int nc, boolean withIds);

    @Override
    public abstract Map<String, String> lXRowH(boolean withIds, int level);

    @Override
    public abstract Map<String, String> getInsertMapFields();

    @Override
    public abstract Map<String, String> getSortMapFields();

    @Override
    public abstract SqlZtatBuilder doZtat(MultiMap params);

    @Override
    public abstract String getSqlKeyIn();

    @Override
    public abstract String getSqlDelete();

    protected void lPToJsAy(final Row r, JsonArray jsa, final String sqlN) {
        jsa.add(r.getLong(sqlN));
    }

    protected void sPToJsAy(final Row r, JsonArray jsa, final String sqlN) {
        jsa.add(r.getString(sqlN));
    }

    protected void lToCell(final Row r, final XSSFRow row, final String sqlN, int nc) {
        final XSSFCell cell = row.createCell(nc);
        cell.setCellValue(r.getLong(sqlN));
    }

    protected void iToCell(final Row r, final XSSFRow row, final String sqlN, int nc) {
        final XSSFCell cell = row.createCell(nc);
        cell.setCellValue(r.getInteger(sqlN));
    }

    protected void bdToCell(final Row r, final XSSFRow row, final String sqlN, int nc) {
        final XSSFCell cell = row.createCell(nc);
        cell.setCellValue(r.getBigDecimal(sqlN).doubleValue());
    }

    protected void sToCell(final Row r, final XSSFRow row, final String sqlN, int nc) {
        final XSSFCell cell = row.createCell(nc);
        cell.setCellValue(r.getString(sqlN));
    }

    protected void bToCell(final Row r, final XSSFRow row, final String sqlN, int nc) {
        final XSSFCell cell = row.createCell(nc);
        cell.setCellValue(r.getBoolean(sqlN));
    }

    protected void shToCell(final XSSFRow row, final String t, int nc) {
        final XSSFCell cell = row.createCell(nc);
        cell.setCellValue(t);
    }

    protected void ldToCell(final Row r, final XSSFRow row, final String sqlN, int nc) {
        final XSSFCell cell = row.createCell(nc);
        cell.setCellValue(new Date(r.getLocalDate(sqlN).atStartOfDay().toEpochSecond(ZoneOffset.UTC)));
    }

    protected void ldtToCell(final Row r, final XSSFRow row, final String sqlN, int nc) {
        final XSSFCell cell = row.createCell(nc);
        cell.setCellValue(new Date(r.getLocalDateTime(sqlN).toEpochSecond(ZoneOffset.UTC)));
    }

    private static JsonObject ps00a(String n, String t, boolean required) {
        final JsonObject o = new JsonObject()
                .put("t", t)
                .put("n", n);
        if (required) {
            o.put("rq", required);
        }
        return o;

    }

    protected static JsonObject psString(String n, boolean required) {
        return ps00a(n, STRING, required);
    }

    protected static JsonObject psLong(String n, boolean required) {
        return ps00a(n, LONG, required);
    }

    protected static JsonObject psInteger(String n, boolean required) {
        return ps00a(n, INTEGER, required);
    }

    protected static JsonObject psBoolean(String n, boolean required) {
        return ps00a(n, BOOLEAN, required);
    }

    protected static JsonObject psLocalDate(String n, boolean required) {
        return ps00a(n, LOCALDATE, required);
    }

    protected static JsonObject psLocalDateTime(String n, boolean required) {
        return ps00a(n, LOCALDATETIME, required);
    }

    protected static JsonObject psBigDecimal(String n, boolean required) {
        return ps00a(n, BIGDECIMAL, required);
    }

    protected static JsonObject doMto(String n, String t) {
        return new JsonObject()
                .put("n", n)
                .put("t", t);
    }

    protected static JsonObject doMto2(String n, String t) {
        return new JsonObject()
                .put("n", n)
                .put("t", t);
    }

    protected static JsonObject doMto2(String n, String t, String from) {
        return new JsonObject()
                .put("n", n)
                .put("t", t)
                .put("from", from);
    }

    protected static void applyOtm(final JsonArray otm, final String n, final String t) {
        applyOtm(otm, n, t, null);
    }

    protected static void applyOtm(final JsonArray otm, final String n, final String t, final String onRelation) {
        final JsonObject jso = new JsonObject().put("n", n).put("t", t);

        if (onRelation != null) {
            jso.put(ONRELATION, onRelation);
        }
        otm.add(jso);
    }

    protected static JsonObject initOtm2(final String n, final String t, final String from) {
        return new JsonObject().put("n", n).put("t", t).put("from", from);

    }

    protected static void applyOtm2(final JsonArray otm, final String n, final String t, final String from) {
        final JsonObject jso = initOtm2(n, t, from);
        otm.add(jso);
    }

    protected static void applyOtm2(final JsonArray otm, final String n, final String t, final String from, final String onRelation, final String onBiRelation) {
        final JsonObject jso = initOtm2(n, t, from);
        if (onRelation != null) {
            jso.put(ONRELATION, onRelation);
        }
        if (onBiRelation != null) {
            jso.put(ONBIRELATION, onBiRelation);
        }
        otm.add(jso);
    }

    protected static void applyOtm3(final JsonArray otm, final String n, final String t, final String from, final String onRelation, final String onBiRelation, final String onTriRelation) {
        final JsonObject jso = initOtm2(n, t, from);
        if (onRelation != null) {
            jso.put(ONRELATION, onRelation);
        }
        if (onBiRelation != null) {
            jso.put(ONBIRELATION, onBiRelation);
        }
        if (onTriRelation != null) {
            jso.put(ONTRIRELATION, onTriRelation);
        }
        otm.add(jso);
    }

    protected void fTString(final String k, final JsonObject js, final Tuple t) {
        t.addString(js.getString(k));
    }

    protected void fTBoolean(final String k, final JsonObject js, final Tuple t) {
        t.addBoolean(js.getBoolean(k));
    }

    protected void fTLong(final String k, final JsonObject js, final Tuple t) {
        t.addLong(js.getLong(k));
    }

    protected void fTBigDecimal(final String k, final JsonObject js, final Tuple t) {
        t.addBigDecimal(BigDecimal.valueOf(js.getDouble(k)));
    }

    protected void fTString(final String k, final Map<String, Object> obj, final Tuple t) {
        t.addString((String) obj.get(k));
    }

    protected void fTLong(final String k, final Map<String, Object> obj, final Tuple t) {
        t.addLong((Long) obj.get(k));
    }

    protected void fTBigDecimal(final String k, final Map<String, Object> obj, final Tuple t) {
        t.addBigDecimal((BigDecimal) obj.get(k));
    }

    protected void fTBoolean(final String k, final Map<String, Object> obj, final Tuple t) {
        t.addBoolean((Boolean) obj.get(k));
    }

    protected void fTInteger(final String k, final JsonObject js, final Tuple t) {
        t.addInteger(js.getInteger(k));
    }

    protected void fTInteger(final String k, final JsonObject js, final Tuple t, Integer min, Integer max) {
        Integer i = js.getInteger(k);
        if (i != null) {
            if (min != null && i < min) {
                i = min;
            }
            if (max != null && i > max) {
                i = max;
            }
        }
        t.addInteger(i);
    }

    protected void fTInteger(final String k, final Map<String, Object> obj, final Tuple t) {
        Integer i = (Integer) obj.get(k);

        t.addInteger(i);

    }

    protected void fTLocalDateTime(final String k, final JsonObject js, final Tuple t) {
        final String val = js.getString(k);
        if (val != null) {
            t.addLocalDateTime(LocalDateTime.parse(js.getString(k)));
        } else {
            t.addLocalDateTime(null);
        }

    }

    protected void fTLocalDateTime(final String k, Map<String, Object> obj, Tuple t) {
        Object o = obj.get(k);
        if (o != null) {
            t.addLocalDateTime(LocalDateTime.parse(o.toString()));
        } else {
            t.addLocalDateTime(null);
        }

    }

    public void asMaybeNullLocalDate(final Row r, final String name, JsonArray jsa) {
        LocalDate localDate = r.getLocalDate(name);
        if (localDate != null) {
            jsa.add(localDate.toString());
        } else {
            jsa.add(null);
        }
    }

    protected void doFieldSort(final String pn, final String pnSqlField, final String tblName) {
        doField(pn, pnSqlField, tblName);
        getSortMapFields().put(pn, tblName + "_" + pnSqlField);
    }

    protected void doField(final String pn, final String pnSqlField, final String tblName) {
        getNames().add(pn);
        getInsertMapFields().put(tblName + "." + pnSqlField, pn);
    }

    private static final String IDENDIX = "_id";
    private static final String PKEYENDIX = "_pkey";

    protected void doFieldMT0(final String tbl0, final String dcpn, final String sqlNameMto) {
        getNames().add(dcpn + IDENDIX);
        getInsertMapFields().put(tbl0 + "." + sqlNameMto + IDENDIX, dcpn + IDENDIX);

        getNames().add(dcpn + PKEYENDIX);
        getSortMapFields().put(dcpn + PKEYENDIX, sqlNameMto + PKEYENDIX);

    }

    protected void doFieldMT02(final String tbl0, final String dcpn, final String sqlNameMto) {
        getNames().add(dcpn + IDENDIX);
        getNames().add(dcpn + PKEYENDIX);
        getSortMapFields().put(dcpn + PKEYENDIX, sqlNameMto + PKEYENDIX);

    }

    /*
            names.add("workSpace_id");
        insertMapFields.put("appointment.work_space_id", "workSpace_id");

        names.add("workSpace_pkey");
        sortMapFields.put("workSpace_pkey", "work_space_pkey");

        names.add("workSpace_pname");
        sortMapFields.put("workSpace_pname", "work_space_pname");
    
     */
}

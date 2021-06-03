package com.amarsoft.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.amarsoft.bean.DataBaseBean;
import com.amarsoft.config.DataBaseConfig;
import com.amarsoft.dao.als.DataBaseConfigMapper;
import com.amarsoft.exportdoc.util.JSONTools;
import com.amarsoft.service.DataBaseConfigService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
@Slf4j
public class DataBaseConfigImpl implements DataBaseConfigService {

    @Autowired
    DataBaseConfigMapper dataBaseConfigMapper;

    @Autowired
    DataBaseConfig dataBaseConfig;

    @Override
    public List<DataBaseBean> getAllconfig(String environment) {
        return dataBaseConfigMapper.getAllconfig(environment);
    }

    /**
     * 获取字段结构
     * @param jo
     * @return
     */
    public JSONArray queryField(JSONObject jo) {
        String sql = JSONTools.getString(jo,"sql");
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        JSONArray ja = new JSONArray();
        try {
            conn = DataBaseConfig.getConn(JSONTools.getString(jo,"database"));
            sql =  "select * from ("+sql+") bieming where 1=2" ;
            log.info("获取结构的sql为："+sql);
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            for (int i = 1; i < rsmd.getColumnCount() + 1; i++) {
                String columnName = rsmd.getColumnName(i).toLowerCase();
                ja.add(columnName.toLowerCase()+"@"+columnName.toLowerCase());
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            try {
                if(rs != null){
                    rs.close();
                }
                if(ps != null){
                    rs.close();
                }
                if(conn != null){
                    rs.close();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return ja;
    }

    /**
     * 获取数据
     * @param jo
     * @return
     */
    public JSONObject queryData(JSONObject jo) {
        JSONObject ret = new JSONObject();

        String sql = JSONTools.getString(jo,"sql");
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        JSONArray datas = new JSONArray();
        int pageIndex = jo.getInteger("page");
        int pageSize = jo.getInteger("limit");
        String key = jo.getString("key");
        int start = (pageIndex - 1) * pageSize;
        int count = 0;
        try {
            conn = DataBaseConfig.getConn(JSONTools.getString(jo,"database"));
            String countSql = "select count(*) from ("+sql+") otbname";
            log.info("统计sql："+countSql);
            ps = conn.prepareStatement(countSql);
            rs = ps.executeQuery();
            if(rs.next()){
                count = rs.getInt(1);
            }
            sql =  "select * from ("+sql+") otbname " +"  limit "+start + "," + pageSize;
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            List<String> fieldNames = new ArrayList<String>();
            ResultSetMetaData rsmd = rs.getMetaData();
            for (int i = 1; i < rsmd.getColumnCount() + 1; i++) {
                String columnName = rsmd.getColumnName(i).toLowerCase();
                fieldNames.add(columnName);
            }
            while(rs.next()){
                JSONObject field = new JSONObject(true);
                for (int i = 0; i < fieldNames.size(); i++) {
                    String val = rs.getString(fieldNames.get(i));
                    field.put(fieldNames.get(i).toLowerCase(),val == null?"":val);
                }
                if(StringUtils.isNoneBlank(key)){
                    if(field.toJSONString().toLowerCase().indexOf(key.toLowerCase())>-1){
                        datas.add(field);
                    }
                }else{
                    datas.add(field);
                }
            }
            ret.put("code",0);
            ret.put("msg","查询成功");
            ret.put("count",count);
            ret.put("data",datas);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            ret.put("code",-1);
            ret.put("msg","查询失败");
            ret.put("count",0);
            ret.put("data",new JSONArray());
        }finally {
            try {
                if(rs != null){
                    rs.close();
                }
                if(ps != null){
                    rs.close();
                }
                if(conn != null){
                    rs.close();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        }
        return ret;
    }


}

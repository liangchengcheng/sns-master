package com.lcc.osf.dao.impl;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by lcc on 2017/2/19.
 */
@Repository("shortPostDao")
public class ShortPostDAO extends PostDAOImpl{

    public long count(int user_id) {
        final String sql = "select count(1) counter from " + TABLE + " where post_author=? and post_title is null";
        return jdbcTemplate.query(sql, new Object[]{user_id}, new ResultSetExtractor<Integer>(){

            public Integer extractData(ResultSet rs) throws SQLException,
                    DataAccessException {
                int user_id = 0;
                if(rs.next()) {
                    user_id = rs.getInt("counter");
                }
                return user_id;
            }
        });
    }
}

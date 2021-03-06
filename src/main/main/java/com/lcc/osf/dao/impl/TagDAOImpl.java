package com.lcc.osf.dao.impl;

import com.lcc.osf.dao.TagDAO;
import com.lcc.osf.model.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

/**
 * Created by lcc on 2017/2/21.
 */
@Repository("tagDao")
public class TagDAOImpl implements TagDAO {

    private static final String TABLE = "osf_tags";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    @Qualifier("redisTemplate")
    private RedisTemplate<String,String> redisTemplate;

    @Resource(name = "redisTemplate")
    private ListOperations<String,Integer> listOps;

    /**
     * 增加一个tag
     */
    public int save(final String tag) {
        final String sql = "insert into "+TABLE +"(tag) values(?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(sql,new String[]{"id"});
                ps.setString(1,tag);
                return ps;
            }
        },keyHolder);
        return keyHolder.getKey().intValue();
    }

    /**
     * 通过id获取一个tag
     */
    public Tag getTagByID(int id) {
        String sql = "select * from "+TABLE+" WHERE ID=?";
        return jdbcTemplate.query(sql, new Object[]{id}, new ResultSetExtractor<Tag>() {
            public Tag extractData(ResultSet rs) throws SQLException, DataAccessException {
               Tag tag = new Tag();
                if (rs.next()){
                    tag.setId(rs.getInt("id"));
                    tag.setTag(rs.getString("tag"));
                    tag.setAdd_ts(rs.getTimestamp("add_ts"));
                    tag.setCover(rs.getString("cover"));
                }
                return tag;
            }
        });
    }

    public int getTagID(final String tag) {
        final String sql = "select id from "+TABLE +" WHERE TAG=?";
        return jdbcTemplate.query(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setString(1,tag);
                return ps;
            }
        }, new ResultSetExtractor<Integer>() {
            public Integer extractData(ResultSet resultSet) throws SQLException, DataAccessException {
                if (resultSet.next()){
                    return resultSet.getInt("id");
                }
                return 0;
            }
        });
    }

    /**
     * 获取tag的集合
     */
    public List<Tag> getTags(List<Integer> tags_id) {
        String sql = "select * from "+TABLE+" where id in (:ids)";
        HashMap<String,Object> paramMap = new HashMap<String, Object>();
        paramMap.put("ids",tags_id);
        return namedParameterJdbcTemplate.query(sql, paramMap, new RowMapper<Tag>() {
            public Tag mapRow(ResultSet rs, int i) throws SQLException {
                Tag tag = new Tag();
                tag.setId(rs.getInt("id"));
                tag.setTag(rs.getString("tag"));
                tag.setAdd_ts(rs.getTimestamp("add_ts"));
                tag.setCover(rs.getString("cover"));
                return tag;
            }
        });
    }

    /**
     * 获取tag的集合
     */
    public List<Tag> getTagsHasCover() {
        String sql = "select * from "+ TABLE + " where cover is not null limit 12";
        return jdbcTemplate.query(sql, new RowMapper<Tag>() {

            public Tag mapRow(ResultSet rs, int row) throws SQLException {
                Tag tag = new Tag();
                tag.setId(rs.getInt("id"));
                tag.setTag(rs.getString("tag"));
                tag.setAdd_ts(rs.getTimestamp("add_ts"));
                tag.setCover(rs.getString("cover"));
                return tag;
            }
        });
    }
}

package com.lcc.osf.dao.impl;

import com.lcc.osf.dao.LikeDAO;
import com.lcc.osf.util.Dic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by lcc on 2017/2/20.
 */
@Repository("likeDao")
public class LikeDAOImpl implements LikeDAO {

    private static final String TABLE = "osf_likes";

    private static final String LIKE_KEY = "like:";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    @Qualifier("redisTemplate")
    private RedisTemplate<String, String> redisTemplate;

    @Resource(name="redisTemplate")
    private SetOperations<String, Integer> setOps;

    public void like(int user_id, int object_type, int object_id) {

    }

    public void undoLike(int user_id, int object_type, int object_id) {

    }

    public boolean isLike(int user_id, int object_type, int object_id) {
        return false;
    }

    public long likersCount(int object_type, int object_id) {
        return 0;
    }

    public List<Integer> getLikers(int object_type,int object_id){
        //key e.g like:post:1
        final String key = LIKE_KEY+Dic.checkType(object_type) + ":"+object_id;
        List<Integer> likers = null;
        if(!redisTemplate.hasKey(key) ){
            final String sql = "select user_id from " + TABLE + " where object_type=? and object_id=?";
            likers = jdbcTemplate.query(sql, new Object[]{object_type, object_id},  new RowMapper<Integer>(){

                public Integer mapRow(ResultSet rs, int row)
                        throws SQLException {
                    setOps.add(key, rs.getInt("user_id"));
                    return rs.getInt("user_id");
                }

            });
        }
        return likers;
    }
}

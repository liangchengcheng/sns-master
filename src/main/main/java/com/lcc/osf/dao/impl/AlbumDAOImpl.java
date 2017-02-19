package com.lcc.osf.dao.impl;

import com.lcc.osf.dao.AlbumDAO;
import com.lcc.osf.model.Album;
import com.lcc.osf.model.Photo;
import com.lcc.osf.service.TagService;
import com.qiniu.common.QiniuException;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

/**
 * Created by lcc on 2017/2/19.
 */
public class AlbumDAOImpl implements AlbumDAO {
    private static final String TABLE_ALBUM = "osf_albums";
    private static final String TABLE_PHOTO = "osf_photos";

    private static final String AK = "1mAeoCNoX25n_QiPGK-aS8895kQ4RedWWYb6LCpK";
    private static final String SK = "kJBUkzruYDjmnmx8UDsjMHD2OEw5SzTi36WP2BD4";
    private static final String bucket = "osfimg";

    private Auth auth = Auth.create(AK,SK);
    private BucketManager bucketManager = new BucketManager(auth);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public int saveAlbum(final Album album) {
        StringBuilder sb = new StringBuilder();
        sb.append("insert into ");
        sb.append(TABLE_ALBUM);
        sb.append("user_id, album_title,album_desc,status,cover");
        sb.append(" values(?,?,?,?,?)");
        final String sql = sb.toString();
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(sql,new String[]{"id"});
                ps.setInt(1,album.getUser_id());
                ps.setString(2,album.getAlbum_title());
                ps.setString(3,album.getAlbum_desc());
                ps.setInt(4,album.getStatus());
                ps.setString(5,album.getCover());
                return ps;
            }
        },keyHolder);
        return keyHolder.getKey().intValue();
    }

    public int savePhoto(final Photo photo) {
        StringBuilder sb = new StringBuilder();
        sb.append("insert into ");
        sb.append(TABLE_PHOTO);
        sb.append("('key',album_id,'desc')");
        sb.append(" values(?,?,?)");

        final String sql = sb.toString();
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
                PreparedStatement ps = conn.prepareStatement(sql,new String[]{"id"});
                ps.setString(1,photo.getKey());
                ps.setInt(2,photo.getAlbum_id());
                ps.setString(3,photo.getDesc());
                return ps;
            }
        },keyHolder);
        return keyHolder.getKey().intValue();
    }

    public String uploadPhoto(MultipartFile img, Photo details) {
        UploadManager uploadManager = new UploadManager();
        try{
            uploadManager.put(img.getBytes(),details.getKey(),getUpToken());
        }catch (QiniuException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return details.getKey();
    }

    public String uploadPhoto(byte[] img, String key) {
        return null;
    }

    public String getKey(int id) {
        String sql = "select 'key' from "+ TABLE_PHOTO +" where id=?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, String.class);
    }

    public List<String> getKeys(List<Integer> ids) {
        String sql = "select 'key' from "+TABLE_PHOTO+" where id in (:ids)";
        HashMap<String,Object> paramMap = new HashMap<String, Object>();
        paramMap.put("ids",ids);
        return namedParameterJdbcTemplate.queryForList(sql,paramMap,String.class);
    }

    public int getAlbumUser(int id) {
        String sql = "select user_id from "+TABLE_ALBUM +" where id=?";
        int user_id = jdbcTemplate.queryForObject(sql,new Object[]{id},Integer.class);
        return user_id;
    }

    public int getAlbum(int user_id, int status) {
        String sql = "select id from "+ TABLE_ALBUM +"where user_id=? and status=?";
        return jdbcTemplate.query(sql,new Object[]{user_id,status},new ResultSetExtractor<Integer>(){

            public Integer extractData(ResultSet resultSet) throws SQLException, DataAccessException {
                if (resultSet.next()){
                    return resultSet.getInt("id");
                }
                return 0;
            }
        });
    }

    public int updateAlbumInfo(Album album) {
        String sql = "update " + TABLE_ALBUM +
                " set album_desc=?, photos_count=?, status=?,cover=?,album_tags=? where id=?";
        return jdbcTemplate.update(sql,new Object[]{
                album.getAlbum_desc(),
                album.getPhotos_count(),
                album.getStatus(),
                album.getCover(),
                TagService.toString(album.getAlbum_tags()),
                album.getId()
        });

    }

    public int updateAlbumDesc(int album_id, String album_desc, int album_status) {
        String sql = "update "+ TABLE_PHOTO + "set album_desc=?, status=? where id=?";
        return jdbcTemplate.update(sql,new Object[]{album_desc, album_status, album_id});
    }

    public int updateAlbumCover(int album_id, String cover) {
        String sql = "update "+ TABLE_ALBUM +" set cover=? where id=?";
        return jdbcTemplate.update(sql,new Object[]{cover,album_id});
    }

    public int updatePhotosCount(int album_id, int count) {
        String sql = "update "+ TABLE_ALBUM +" set photos_count=? where id=?";
        return jdbcTemplate.update(sql,new Object[]{count,album_id});
    }

    public int updatePhotoDesc(int photo_id, String photo_desc) {
        String sql = "update " + TABLE_PHOTO+ " set `desc`=? where id=?";
        return jdbcTemplate.update(sql, new Object[]{photo_desc, photo_id});
    }

    public Album getAlbum(final int id) {
        final String sql = "select * from "+ TABLE_ALBUM + " where id=?";

        return jdbcTemplate.query(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps =connection.prepareStatement(sql);
                ps.setInt(1,id);
                return ps;
            }
        }, new ResultSetExtractor<Album>() {
            public Album extractData(ResultSet rs) throws SQLException, DataAccessException {
                Album album = new Album();
                album.setAlbum_desc(rs.getString("album_desc"));
                album.setAlbum_title(rs.getString("album_title"));
                album.setId(rs.getInt("id"));
                album.setUser_id(rs.getInt("user_id"));
                album.setLast_add_ts(rs.getTimestamp("last_add_ts"));
                album.setCreate_ts(rs.getTimestamp("create_ts"));
                album.setAlbum_tags(TagService.toList(rs.getString("album_tags")));
                return album;
            }
        });
    }

    public List<Album> getAlbumsOfUser(int id) {
        final String sql = "select * from "+TABLE_ALBUM +" where user_id=?";
        List<Album> albums = jdbcTemplate.query(sql, new Object[]{id}, new RowMapper<Album>() {
            public Album mapRow(ResultSet rs, int i) throws SQLException {
                Album album = new Album();
                album.setAlbum_desc(rs.getString("album_desc"));
                album.setAlbum_title(rs.getString("album_title"));
                album.setCover(rs.getString("cover"));
                album.setId(rs.getInt("id"));
                album.setUser_id(rs.getInt("user_id"));
                album.setLast_add_ts(rs.getTimestamp("last_add_ts"));
                album.setCreate_ts(rs.getTimestamp("create_ts"));
                return album;
            }
        });
        return albums;
    }

    public List<Photo> getPhotos(int album_id) {
        final String sql = "select * from " + TABLE_PHOTO + "where album_id=? order by ts asc";
        List<Photo> photos = jdbcTemplate.query(sql,new Object[]{album_id},new RowMapper<Photo>(){

            public Photo mapRow(ResultSet resultSet, int i) throws SQLException {
                Photo photo = new Photo();
                photo.setId(resultSet.getInt("id"));
                photo.setAlbum_id(resultSet.getInt("album_id"));
                photo.setDesc(resultSet.getString("desc"));
                photo.setKey(resultSet.getString("key"));
                photo.setTs(resultSet.getTimestamp("ts"));
                return photo;
            }
        });
        return photos;
    }

    public int getAuthorOfAlbum(final int id) {
        final String sql = "select * from "+TABLE_ALBUM +" where id=?";
        return jdbcTemplate.query(sql, new Object[]{id}, new ResultSetExtractor<Integer>() {
            public Integer extractData(ResultSet rs) throws SQLException, DataAccessException {
               int user_id =0;
                if (rs.next()){
                    user_id = rs.getInt("user_id");
                }
                return user_id;
            }
        });
    }

    public Album getAlbumContainPhoto(int photo_id) {
        String sql = "select * from " + TABLE_PHOTO + " t1," + TABLE_ALBUM + " t2 where t1.id=? and t1.album_id=t2.id";
        return jdbcTemplate.query(sql, new Object[]{photo_id}, new ResultSetExtractor<Album>(){

            public Album extractData(ResultSet rs) throws SQLException, DataAccessException {
                Album album = new Album();
                if(rs.next()) {
                    album.setAlbum_desc(rs.getString("album_desc"));
                    album.setAlbum_title(rs.getString("album_title"));
                    album.setCover(rs.getString("cover"));
                    album.setId(rs.getInt("id"));
                    album.setUser_id(rs.getInt("user_id"));
                    album.setLast_add_ts(rs.getTimestamp("last_add_ts"));
                    album.setCreate_ts(rs.getTimestamp("create_ts"));
                    album.setAlbum_tags(TagService.toList(rs.getString("album_tags")));
                }
                return album;
            }});
    }

    public void delPhoto(int id) {
        String sql = "delete from " + TABLE_PHOTO + " where id=?";
        jdbcTemplate.update(sql, new Object[]{id});
    }

    private String getUpToken(){
        return auth.uploadToken(bucket, null, 3600, new StringMap().
                putNotEmpty("returnBody", "{\"key\": $(key), \"hash\": $(etag), \"width\": $(imageInfo.width), \"height\": $(imageInfo.height)}"));
    }

    public void delPhotoInBucket(String key){
        try{
            bucketManager.delete(bucket,key);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}

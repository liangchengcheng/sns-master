package com.lcc.osf.service;

import com.lcc.osf.dao.LikeDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * Created by lcc on 2017/2/19.
 */

@Service("likeService")
public class LikeService {

    @Autowired
    @Qualifier("likeDao")
    private LikeDAO likeDao;

    public void like(int user_id, int object_type, int object_id){
        likeDao.like(user_id, object_type, object_id);
    }

    public void undoLike(int user_id, int object_type, int object_id){
        likeDao.undoLike(user_id, object_type, object_id);
    }

    /**
     * 判断用户是否喜欢某个对象
     */
    public boolean isLike(int user_id, int object_type, int object_id) {
        return likeDao.isLike(user_id, object_type, object_id);
    }

    /**
     * 返回喜欢某个对象的用户数量
     */
    public long likersCount(int object_type, int object_id) {
        return likeDao.likersCount(object_type, object_id);
    }

    /**
     * 返回喜欢某个对象的用户ID列表
     */
    public List<Integer> likers(int object_type, int object_id){
        return likeDao.getLikers(object_type, object_id);
    }

    /**
     * 用户喜欢的对象数量(部分对象类型)
     */
    public int likeCount(int user_id){
        return 0;
    }
}

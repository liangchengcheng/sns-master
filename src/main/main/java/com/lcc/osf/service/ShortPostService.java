package com.lcc.osf.service;

import com.lcc.osf.dao.impl.ShortPostDAO;
import com.lcc.osf.model.ShortPost;
import com.lcc.osf.util.Property;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lcc on 2017/2/19.
 */
@Service("shortPostService")
public class ShortPostService extends PostService{

    @Autowired
    @Qualifier("shortPostDao")
    private ShortPostDAO shortPostDao;

    public Map<String, Object> newPost(Integer author, String content){
        Map<String, Object> map = new HashMap<String, Object>();
        if(content == null || content.length() == 0){
            map.put("status", Property.ERROR_POST_EMPTY);
            return map;
        }

        ShortPost spost = new ShortPost();
        spost.setPost_author(author);
        spost.setPost_content(content);
        spost.setId(savePost(spost));
        map.put("spost", spost);
        map.put("status", Property.SUCCESS_POST_CREATE);
        return map;
    }

    @Override
    public long count(int user_id){
        return shortPostDao.count(user_id);
    }
}

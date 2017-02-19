package com.lcc.osf.dao.impl;

import com.lcc.osf.dao.FeedDAO;
import com.lcc.osf.service.FeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Iterator;
import java.util.List;

/**
 * Created by lcc on 2017/2/19.
 */
@Repository("feedDao")
public class FeedDAOImpl implements FeedDAO{

    @Autowired
    @Qualifier("redisTemplate")
    private RedisTemplate<String,String> redisTemplate;

    @Resource(name="redisTemplate")
    private ListOperations<String, Integer> listOps;

    public void save(String key, int event_id) {
        listOps.leftPush(key,event_id);
    }

    public void saveAll(String key, List<Integer> events_id) {
        Iterator<Integer> events_it = events_id.iterator();
        int count = 0;
        while (events_it.hasNext() && count < FeedService.FEED_COUNT){
            save(key,events_it.next());
            count ++;
        }
    }

    public void delete(String key, int event_id) {
        listOps.remove(key, 0, event_id);
    }

    public Long count(String key) {
        return listOps.size(key);
    }

    public List<Integer> fetch(String key) {
        return listOps.range(key,0,listOps.size(key)-1);
    }

    public List<Integer> fetch(String key, long start, long step) {
        return listOps.range(key, start, start+step);
    }
}

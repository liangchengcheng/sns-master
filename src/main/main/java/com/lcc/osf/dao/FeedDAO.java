package com.lcc.osf.dao;

import java.util.List;

/**
 * Created by lcc on 2017/2/19.
 */
public interface FeedDAO {
    void save(String key, int event_id);

    void saveAll(String key, List<Integer> events_id);

    void delete(String key, int event_id);

    Long count(String key);

    List<Integer> fetch(String key);

    List<Integer> fetch(String key, long start, long step);
}

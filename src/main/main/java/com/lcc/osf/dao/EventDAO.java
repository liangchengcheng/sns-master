package com.lcc.osf.dao;

import com.lcc.osf.model.Event;

import java.util.List;
import java.util.Map;

/**
 * Created by lcc on 2017/2/19.
 */
public interface EventDAO {
    int save(Event event);

    List<Event> getEvents(int start, int step);

    List<Event> getEventsWithIDs(List<Integer> event_ids);

    List<Event> getEventsWithRelations(Map<Integer, List<Integer>> relations);

    List<Event> getEventsOfUser(int user_id, int count);

    List<Event> getEventsHasPhoto(int start, int step);

    void delete(int id);

    void delete(int object_type, int object_id);

    Event getEvent(int object_type, int object_id);
}

package com.lcc.osf.dao;

import com.lcc.osf.model.Relation;

import java.util.List;

/**
 * Created by lcc on 2017/2/19.
 */
public interface RelationDAO {
    int save(int object_type, int object_id, int tag_id);

    int save(int object_type, int object_id, int[] tags_id);

    boolean delete();

    List<Relation> get(int tag_id);

    List<Relation> getRelationsInTags(List<Integer> tags_id);
}

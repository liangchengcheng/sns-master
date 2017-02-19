package com.lcc.osf.dao;

import com.lcc.osf.model.Tag;
import java.util.List;

/**
 * Created by lcc on 2017/2/19.
 */
public interface TagDAO {

    int save(String tag);

    Tag getTagByID(int id);

    int getTagID(String tag);


    List<Tag> getTags(List<Integer> tags_id);

    /**
     * 获取有封面的tag
     */
    List<Tag> getTagsHasCover();
}

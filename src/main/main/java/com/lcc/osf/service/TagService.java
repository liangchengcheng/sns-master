package com.lcc.osf.service;

import com.lcc.osf.model.Tag;

import java.util.List;

/**
 * Created by lcc on 2017/2/19.
 */
public class TagService {

    public static List<Tag> toList(String tags) {
        return null;
    }

    public static String toString(List<Tag> tags) {
        if(tags == null || tags.size() == 0)
            return null;
        StringBuffer buffer = new StringBuffer();
        for(Tag tag: tags) {
            buffer.append(tag.getTag()+":"+tag.getId()+" ");
        }
        return buffer.toString();
    }

}

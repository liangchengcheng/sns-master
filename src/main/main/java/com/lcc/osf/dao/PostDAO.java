package com.lcc.osf.dao;

import com.lcc.osf.model.Post;

import java.util.List;

/**
 * Created by lcc on 2017/2/19.
 */
public interface PostDAO {
    Post getPostByID(int id);

    List<Post> getPostsByIDs(int[] ids);

    List<Post> getPostsByUserID(int id);

    int save(Post post);

    void delete(int id);

    int getAuthorOfPost(int id);

    long count(int user_id);
}

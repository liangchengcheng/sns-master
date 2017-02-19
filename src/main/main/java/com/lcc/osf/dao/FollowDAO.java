package com.lcc.osf.dao;

import com.lcc.osf.model.Follower;
import com.lcc.osf.model.Following;
import java.util.List;
import java.util.Map;

/**
 * Created by lcc on 2017/2/19.
 */
public interface FollowDAO {
    int saveFollowing(Following following);

    int saveFollower(Follower follower);

    boolean delFollowing(Following following);

    boolean delFollower(Follower follower);

    List<Integer> getFollowingIDs(int user_id);

    List<Integer> getFollowerIDs(int user_id);

    List<Following> getFollowings(int user_id);

    List<Follower> getFollowers(int user_id);

    boolean hasFollowing(int user_a, int user_b);

    boolean hasFollower(int user_a, int user_b);

    Map<Integer, Boolean> isFollowingUsers(int user_id, List<Integer> following_ids);

    long getFollowersCount(int user_id);

    long getFollowingsCount(int user_id);
}

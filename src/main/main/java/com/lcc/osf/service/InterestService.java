package com.lcc.osf.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.lcc.osf.dao.InterestDAO;
import com.lcc.osf.model.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("interestService")
public class InterestService {

    @Autowired
    @Qualifier("interestDao")
    private InterestDAO interestDao;

    /**
     * 关注tag
     */
    public void interestInTag(int user_id, int tag_id) {
        interestDao.saveInterest(user_id, tag_id);
    }

    /**
     * 撤销关注tag
     */
    public void undoInterestInTag(int user_id, int tag_id){
        interestDao.delInterest(user_id, tag_id);
    }

    /**
     * 获取关注tag_id的用户列表
     */
    public List<Integer> getUsersInterestedInTag(int tag_id) {
        return interestDao.getUsersInterestInTag(tag_id);
    }

    /**
     * 判断用户对tag是否已经关注
     */
    public boolean hasInterestInTag(int user_id, int tag_id) {
        return interestDao.hasInterestInTag(user_id, tag_id);
    }

    /**
     * 判断用户对列表中的tag是否已经关注
     */
    public Map<Integer, Boolean> hasInterestInTags(int user_id, List<Tag> tags){
        if(tags == null || tags.size() == 0 ){
            return null;
        }
        List<Integer> tags_id = new ArrayList<Integer>();
        for(Tag tag: tags){
            tags_id.add(tag.getId());
        }

        return interestDao.hasInterestInTags(user_id, tags_id);
    }

    /**
     * 获取用户关注的tag列表
     */
    public List<Tag> getTagsUserInterestedIn(int user_id){
        return interestDao.getTagsUserInterestedIn(user_id);
    }
}

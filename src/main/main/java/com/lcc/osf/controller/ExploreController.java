package com.lcc.osf.controller;

import com.lcc.osf.model.Event;
import com.lcc.osf.model.Tag;
import com.lcc.osf.model.User;
import com.lcc.osf.service.*;
import com.lcc.osf.util.Dic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lcc on 2017/2/23.
 */
@Controller
@RequestMapping("/explore")
public class ExploreController {

    @Autowired
    @Qualifier("interestService")
    private InterestService interestService;

    @Autowired
    @Qualifier("tagService")
    private TagService tagService;

    @Autowired
    @Qualifier("feedService")
    private FeedService feedService;

    @Autowired
    @Qualifier("eventService")
    private EventService eventService;

    @Autowired
    @Qualifier("userService")
    private UserService userService;

    @Autowired
    @Qualifier("followService")
    private FollowService followService;

    @RequestMapping("")
    public ModelAndView explore(HttpSession session) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("explore");

        User user = (User) session.getAttribute("user");
        mav.addObject("events", feedService.getRecommendFeeds(user == null ? 0 : user.getId()));

        List<Tag> tags_recommend = tagService.getRecommendTags(user == null ? 0 : user.getId());
        mav.addObject("tags", tags_recommend);
        mav.addObject("isInterests", interestService.hasInterestInTags(user == null ? 0 : user.getId(), tags_recommend));
        List<User> rec_users = userService.getRecommendUsers(user == null ? 0 : user.getId(), 4);
        mav.addObject("isFollowings", followService.isFollowing(user == null ? 0 : user.getId(), rec_users));

        Map<User, List<Event>> feeds = new HashMap<User, List<Event>>();
        for (User rec_user : rec_users) {
            feeds.put(rec_user, eventService.getEventsOfUser(rec_user.getId(), 4));
        }
        mav.addObject("feeds", feeds);
        mav.addObject("dic", new Dic());
        return mav;
    }
}

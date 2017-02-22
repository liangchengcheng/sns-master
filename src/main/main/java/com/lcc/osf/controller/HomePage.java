package com.lcc.osf.controller;

import com.lcc.osf.model.Event;
import com.lcc.osf.model.User;
import com.lcc.osf.service.*;
import com.lcc.osf.util.Dic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by lcc on 2017/2/22.
 */
@Controller
public class HomePage {

    @Autowired
    @Qualifier("eventService")
    private EventService eventService;

    @Autowired
    @Qualifier("feedService")
    private FeedService feedService;

    @Autowired
    @Qualifier("userService")
    private UserService userService;

    @Autowired
    @Qualifier("followService")
    private FollowService followService;

    @Autowired
    @Qualifier("tagService")
    private TagService tagService;

    @Autowired
    @Qualifier("interestService")
    private InterestService interestService;

    @RequestMapping("/")
    public ModelAndView showHomePage(HttpSession session){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("index");

        User user = (User) session.getAttribute("user");
        if (user == null){
            return new ModelAndView("redirect:/welcome");
        }

        mav.addObject("counter",userService.getCounterOfFollowAndShortPost(user.getId()));
        List<Event> feeds = feedService.getFeeds(user.getId());
        mav.addObject("feeds", feeds);
        mav.addObject("dic", new Dic());
        return mav;
    }
}

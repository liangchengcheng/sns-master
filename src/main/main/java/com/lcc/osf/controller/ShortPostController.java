package com.lcc.osf.controller;

import com.lcc.osf.model.ShortPost;
import com.lcc.osf.model.User;
import com.lcc.osf.service.EventService;
import com.lcc.osf.service.FeedService;
import com.lcc.osf.service.ShortPostService;
import com.lcc.osf.service.UserService;
import com.lcc.osf.util.Dic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * Created by lcc on 2017/2/22.
 */
@Controller
@RequestMapping("/spost")
public class ShortPostController {

    @Autowired
    @Qualifier("shortPostService")
    private ShortPostService shortPostService;

    @Autowired
    @Qualifier("eventService")
    private EventService eventService;

    @Autowired
    @Qualifier("feedService")
    private FeedService feedService;

    @Autowired
    @Qualifier("userService")
    private UserService userService;

    @ResponseBody
    @RequestMapping(value="/create", method= RequestMethod.POST)
    public Map<String, Object> createPost(@RequestParam("content") String content, HttpSession session){
        User user = (User) session.getAttribute("user");
        Map<String, Object> map = shortPostService.newPost(user.getId(), content);

        ShortPost spost = (ShortPost) map.get("spost");
        int event_id = eventService.newEvent(Dic.OBJECT_TYPE_SHORTPOST, spost);
        feedService.push(user.getId(), event_id);

        map.put("avatar", userService.findById(user.getId()).getUser_avatar());
        map.put("author_name", user.getUser_name());

        return map;
    }
}

package com.lcc.osf.controller;

import com.lcc.osf.model.Notification;
import com.lcc.osf.model.User;
import com.lcc.osf.service.FollowService;
import com.lcc.osf.service.NotificationService;
import com.lcc.osf.service.UserService;
import com.lcc.osf.util.Dic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * Created by lcc on 2017/2/23.
 */
@Controller
@RequestMapping("/follow")
public class FollowController {

    @Autowired
    @Qualifier("followService")
    private FollowService followService;

    @Autowired
    @Qualifier("userService")
    private UserService userService;

    @Autowired
    @Qualifier("notificationService")
    private NotificationService notificationService;

    public Map<String,Object> follow(@PathVariable("following_user_id") int following_user_id,
                                     HttpSession session){
        User user = (User) session.getAttribute("user");
        Map<String,Object> map = followService.newFollowing(user.getId(),
                user.getUser_name(),
                following_user_id,
                userService.findById(following_user_id).getUser_name());
        Notification notification = new Notification(Dic.NOTIFY_TYPE_FOLLOW,
                0,
                Dic.OBJECT_TYPE_USER,
                following_user_id,
                following_user_id,
                user.getId());
        notificationService.doNotify(notification);
        return map;
    }

    @ResponseBody
    @RequestMapping("/undo/{following_user_id}")
    public Map<String, Object> undoFollow(@PathVariable("following_user_id") int following_user_id,
                                          HttpSession session) {
        User user = (User) session.getAttribute("user");
        Map<String, Object> map = followService.undoFollow(user.getId(),following_user_id);
        return map;
    }
}

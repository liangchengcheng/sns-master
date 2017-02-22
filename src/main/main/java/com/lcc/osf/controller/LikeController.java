package com.lcc.osf.controller;

import com.lcc.osf.model.Notification;
import com.lcc.osf.model.User;
import com.lcc.osf.service.LikeService;
import com.lcc.osf.service.NotificationService;
import com.lcc.osf.util.Dic;
import com.lcc.osf.util.Property;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lcc on 2017/2/22.
 */
@Controller
@RequestMapping("/like")
public class LikeController {

    @Autowired
    @Qualifier("likeService")
    private LikeService likeService;

    @Autowired
    @Qualifier("notificationService")
    private NotificationService notificationService;

    @ResponseBody
    @RequestMapping("/do")
    public Map<String,String> like(@RequestParam("author") int author,
                                   @RequestParam("object_type") int object_type,
                                   @RequestParam("object_id") int object_id,
                                   HttpSession session){
        User me = (User) session.getAttribute("user");
        likeService.like(me.getId(),object_type,object_id);
        Map<String,String> ret = new HashMap<String, String>();
        ret.put("status", Property.SUCCESS_LIKE);

        Notification notification = new Notification(Dic.NOTIFY_TYPE_LIKE, 0,
                object_type,
                object_id,
                author,
                me.getId());
        notificationService.doNotify(notification);
        return ret;
    }

    @ResponseBody
    @RequestMapping("/undo")
    public Map<String,String> undoLike(@RequestParam("object_type") int object_type,
                                       @RequestParam("object_id") int object_id,
                                       HttpSession session){
        User me = (User) session.getAttribute("user");
        likeService.undoLike(me.getId(),object_type,object_id);
        Map<String,String> ret = new HashMap<String, String>();
        ret.put("status",Property.SUCCESS_LIKE_UNDO);
        return ret;
    }

}

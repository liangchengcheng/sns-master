package com.lcc.osf.web;

import com.lcc.osf.model.User;
import com.lcc.osf.service.NotificationService;
import com.lcc.osf.util.Property;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by lcc on 2017/2/21.
 */
public class NotifyInterceptor implements HandlerInterceptor {

    @Autowired
    @Qualifier("notificationService")
    private NotificationService notificationService;

    public boolean preHandle(HttpServletRequest req, HttpServletResponse httpServletResponse, Object o) throws Exception {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        if(user != null){
            session.setAttribute("notifications", notificationService.getNotificationsCount(user.getId()));
        }
        session.setAttribute("img_base_url", Property.IMG_BASE_URL);
        session.setAttribute("post_cover_thumbnail", Property.POST_COVER_THUMBNAIL);
        session.setAttribute("album_thumbnail", Property.ALBUM_THUMBNAIL);
        return true;
    }

    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}

package com.lcc.osf.web;

import com.lcc.osf.util.Property;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by lcc on 2017/2/19.
 */
@WebFilter("/InitFilter")
public class InitFilter implements Filter {

    public InitFilter() {

    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        HttpSession session = ((HttpServletRequest)request).getSession();
        session.setAttribute("img_base_url", Property.IMG_BASE_URL);
        session.setAttribute("post_cover_thumbnail", Property.POST_COVER_THUMBNAIL);
        session.setAttribute("album_thumbnail", Property.ALBUM_THUMBNAIL);
        filterChain.doFilter(request,response);
    }

    public void destroy() {
        System.out.println("destroy");
    }

    public void init(FilterConfig fConfig) throws ServletException {
    }
}

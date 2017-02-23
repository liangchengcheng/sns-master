package com.lcc.osf.controller;

import com.lcc.osf.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by lcc on 2017/2/23.
 */
@Controller
@RequestMapping("/album")
public class AlbumController {

    @Autowired
    @Qualifier("albumService")
    private AlbumService albumService;

    @Autowired
    @Qualifier("eventService")
    private EventService eventService;

    @Autowired
    @Qualifier("feedService")
    private FeedService feedService;

    @Autowired
    @Qualifier("interestService")
    private InterestService interestService;

    @Autowired
    @Qualifier("followService")
    private FollowService followService;

    @Autowired
    @Qualifier("userService")
    private UserService userService;
}

package com.tcn.meetandnote.controller;

import com.tcn.meetandnote.entity.Link;
import com.tcn.meetandnote.services.impl.LinkService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/link")
public class LinkController {

    private final LinkService linkService;

    public LinkController(LinkService linkService) {
        this.linkService = linkService;
    }

    @GetMapping("")
    public Link generateLinkPreview(@RequestParam("url") String url) {
        return linkService.generateLinkPreview(url);
    }

}

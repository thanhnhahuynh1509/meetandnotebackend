package com.tcn.meetandnote.services.impl;

import com.tcn.meetandnote.entity.Link;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.net.URL;

@Service
public class LinkService {

    public Link generateLinkPreview(String url) {
        if(!url.startsWith("http")) {
            url = "http://" + url;
        }
        try {
            Document document = Jsoup.connect(url).get();
            String title = getMetaTagContent(document, "meta[name=title]");
            String desc = getMetaTagContent(document, "meta[name=description]");
            String ogVideoURL = getMetaTagContent(document, "meta[property=og:video:url]");
            String ogUrl = getMetaTagContent(document, "meta[property=og:url]");
            String ogTitle = getMetaTagContent(document, "meta[property=og:title]");
            String ogDesc = getMetaTagContent(document, "meta[property=og:description]");
            String ogImage = getMetaTagContent(document, "meta[property=og:image]");
            String ogImageAlt = getMetaTagContent(document, "meta[property=og:image:alt]");
            String domain = ogUrl;
            return new Link(domain, url, StringUtils.hasText(ogTitle) ? ogTitle :  title,
                    StringUtils.hasText(ogDesc) ? ogDesc :  desc, ogImage, ogImageAlt, ogVideoURL);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getMetaTagContent(Document doc, String cssSelector) {
        Element element = doc.selectFirst(cssSelector);
        return element != null ? element.attr("content") : "";
     }

}

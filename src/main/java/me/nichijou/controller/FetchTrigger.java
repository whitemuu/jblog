package me.nichijou.controller;

import freemarker.template.TemplateException;
import me.nichijou.service.FetchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;

/**
 * Created by nichijou on 8/27/17.
 */
@Controller
public class FetchTrigger {
    @Autowired
    private FetchService fetchService;

    // response to github push webhook
    @RequestMapping(value = "fetch", method = RequestMethod.POST)
    public ResponseEntity<Void> refreshArticles(HttpEntity<String> httpEntity) {
        try {
            String json = httpEntity.getBody();
            this.fetchService.updateArticles(json);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (TemplateException e) {
            // 更新rss失败
            e.printStackTrace();
            // TODO add a message here
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

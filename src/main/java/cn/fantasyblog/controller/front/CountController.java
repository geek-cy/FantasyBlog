package cn.fantasyblog.controller.front;

import cn.fantasyblog.service.ArticleService;
import cn.fantasyblog.service.RedisService;
import cn.fantasyblog.service.VisitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description
 * @Author Cy
 * @Date 2021/5/16 20:07
 */
@Controller
@RequestMapping
public class CountController {

    @Autowired
    private VisitorService visitorService;

    @Autowired
    private ArticleService articleService;

    @GetMapping("/count")
    public ResponseEntity<Object> count(){
        Map<String,Object> map = new HashMap<>();
        map.put("count",articleService.countViewAll());
        map.put("visitor",visitorService.countAll());
        return new ResponseEntity<>(map,HttpStatus.OK);
    }
}

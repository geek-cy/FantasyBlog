package cn.fantasyblog.controller.front;

import cn.fantasyblog.service.ArticleService;
import cn.fantasyblog.service.RedisService;
import cn.fantasyblog.service.VisitorService;
import cn.fantasyblog.utils.StringUtils;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description 统计文章访问量
 * @Author Cy
 * @Date 2021/5/16 20:07
 */
@Api("前台：PV和UV")
@Controller
@RequestMapping
public class CountController {

    @Autowired
    private RedisService redisService;

    @GetMapping("/count")
    public ResponseEntity<Object> count(HttpServletRequest request){
        Map<String,Object> map = new HashMap<>();
        map.put("pv",redisService.pv());
        map.put("uv",redisService.uv(StringUtils.getIp(request)));
        return new ResponseEntity<>(map,HttpStatus.OK);
    }
}

package cn.fantasyblog.controller.front;

import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Description
 * @Author Cy
 * @Date 2021/5/17 16:32
 */
@Api(tags = "接口测试用")
@Controller
@RequestMapping("/demo")
public class demoController {

    @GetMapping
    public String test(){
        return "front/activation";
    }
}

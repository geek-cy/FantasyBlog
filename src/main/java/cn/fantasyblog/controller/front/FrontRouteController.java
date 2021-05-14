package cn.fantasyblog.controller.front;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Description
 * @Author Cy
 * @Date 2021-03-28 13:49
 */
@Api(tags ="前台：页面路由")
@Controller
public class FrontRouteController {

    @ApiOperation("访问前台主页")
    @GetMapping
    public String home(){
        return "front/index";
    }

    @ApiOperation("页面路由")
    @GetMapping("/page/{pageName}")
    public ModelAndView page(@PathVariable("pageName") String pageName){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("front/"+pageName);
        return modelAndView;
    }
}

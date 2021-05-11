package cn.fantasyblog.controller.front;


import cn.fantasyblog.anntation.AccessLog;
import cn.fantasyblog.common.Constant;
import cn.fantasyblog.entity.Link;
import cn.fantasyblog.service.LinkService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description
 * @Author Cy
 * @Date 2021-04-13 22:09
 */
@Api("前台友链显示")
@RestController
@RequestMapping("/links")
public class LinksController {

    @Autowired
    LinkService linkService;

    @ApiOperation("前台分页查询友链")
    @AccessLog("前台分页查询友链")
    @GetMapping
    public ResponseEntity<Object> listDisplayByPage(@RequestParam(value = "current",defaultValue = Constant.PAGE) Integer current,
                                                  @RequestParam(value = "size",defaultValue = Constant.PAGE_SIZE) Integer size){
        Page<Link> pageInfo = linkService.listPreviewByPage(current, size);
        return new ResponseEntity<>(pageInfo, HttpStatus.OK);
    }

}

package xyz.zhezhi.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xyz.zhezhi.common.R;
import xyz.zhezhi.module.entity.Say;
import xyz.zhezhi.service.SayService;

import java.util.List;

/**
 * @author zhezhi
 * @className: SayController
 * @description:
 * @date 2021/10/29 上午11:37
 * @version：1.0
 */
@Api(tags = "说说管理")
@RestController
@RequestMapping("/say/")
public class SayController {
    SayService sayService;

    public SayController(SayService sayService) {
        this.sayService = sayService;
    }
    @PostMapping("addSay")
    @ApiOperation("添加说说")
    @SaCheckLogin
    public R addSay(@Validated @RequestBody Say say) {
        say.setAuthorId(StpUtil.getLoginIdAsLong());
        int result = sayService.addSay(say);
        if (result == 1) {
            return R.ok().message("发布成功");
        }
        return R.error().message("发布失败");
    }
    @GetMapping("querySayByUserId/{id}")
    @ApiOperation("查询说说")
    public R querySayByUserId(@PathVariable("id")String userId) {
        List<Say> says = sayService.querySayByUserId(userId);
        return R.ok().data("says", says);
    }
    @DeleteMapping("deleteSayById/{id}")
    @SaCheckLogin
    @ApiOperation("删除说说")
    public R deleteSayById(@PathVariable("id")Long sayId) {
        int result = sayService.deleteSayById(sayId);
        if (result >= 1) {
            return R.ok().message("删除成功");
        }
        return R.error().message("删除失败");
    }
}

package xyz.zhezhi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import xyz.zhezhi.module.entity.Say;

import java.util.List;

/**
 * @author zhezhi
 * @className: SayService
 * @description:
 * @date 2021/10/29 上午11:34
 * @version：1.0
 */
public interface SayService extends IService<Say> {
    int addSay(Say say);
    List<Say> querySayByUserId(String userId);
    int deleteSayById(Long sayId);
}

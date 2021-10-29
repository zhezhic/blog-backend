package xyz.zhezhi.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import xyz.zhezhi.mapper.SayMapper;
import xyz.zhezhi.module.entity.Say;
import xyz.zhezhi.service.SayService;

import java.util.List;

/**
 * @author zhezhi
 * @className: SayServiceImpl
 * @description:
 * @date 2021/10/29 上午11:35
 * @version：1.0
 */
@Service
public class SayServiceImpl extends ServiceImpl<SayMapper, Say> implements SayService {
    SayMapper sayMapper;

    public SayServiceImpl(SayMapper sayMapper) {
        this.sayMapper = sayMapper;
    }

    @Override
    public int addSay(Say say) {
        return sayMapper.insert(say);
    }

    @Override
    public List<Say> querySayByUserId(String userId) {
        QueryWrapper<Say> wrapper = new QueryWrapper<>();
        wrapper.eq("author_id", userId);
        return sayMapper.selectList(wrapper);
    }

    @Override
    public int deleteSayById(Long sayId) {
        QueryWrapper<Say> wrapper = new QueryWrapper<>();
        wrapper.eq("id", sayId).eq("author_id", StpUtil.getLoginIdAsLong());
        return sayMapper.delete(wrapper);
    }
}

package com.chenjx.office.api.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chenjx.office.api.entity.TbCity;
import com.chenjx.office.api.service.TbCityService;
import com.chenjx.office.api.mapper.TbCityMapper;
import org.springframework.stereotype.Service;

/**
* @author chenjx
* @description 针对表【tb_city(疫情城市列表)】的数据库操作Service实现
* @createDate 2022-08-12 17:03:42
*/
@Service
public class TbCityServiceImpl extends ServiceImpl<TbCityMapper, TbCity>
    implements TbCityService{

}





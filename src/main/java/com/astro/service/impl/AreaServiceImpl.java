package com.astro.service.impl;

import com.astro.dao.AreaDao;
import com.astro.entity.Area;
import com.astro.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by astro on 2017/12/20.
 */
@Service
public class AreaServiceImpl implements AreaService{

    @Autowired
    private AreaDao areaDao;

    @Override
    public List<Area> getAreaList() {
        List<Area> list = areaDao.queryArea();
        return list;
    }
}

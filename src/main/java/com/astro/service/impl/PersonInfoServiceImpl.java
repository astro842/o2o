package com.astro.service.impl;

import com.astro.dao.PersonInfoDao;
import com.astro.entity.PersonInfo;
import com.astro.service.PersonInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by astro on 2018/2/4.
 */
@Service
public class PersonInfoServiceImpl implements PersonInfoService{

    @Autowired
    private PersonInfoDao personInfoDao;

    @Override
    public PersonInfo getPersonInfoById(Long userId) {
        return personInfoDao.queryPersonInfoById(userId);
    }
}

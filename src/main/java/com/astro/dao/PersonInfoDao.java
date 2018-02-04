package com.astro.dao;

import com.astro.entity.PersonInfo;

import java.util.List;

/**
 * Created by astro on 2017/12/20.
 */
public interface PersonInfoDao {

    PersonInfo queryPersonInfoById(long userId);

    int insertPersonInfo(PersonInfo personInfo);
}

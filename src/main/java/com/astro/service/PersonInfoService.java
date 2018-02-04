package com.astro.service;

import com.astro.entity.PersonInfo;

/**
 * Created by astro on 2018/2/4.
 */
public interface PersonInfoService {

    PersonInfo getPersonInfoById(Long userId);

}

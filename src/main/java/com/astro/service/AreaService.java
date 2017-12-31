package com.astro.service;

import com.astro.entity.Area;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by astro on 2017/12/20.
 */
@Service
public interface AreaService {

    List<Area> getAreaList();
}

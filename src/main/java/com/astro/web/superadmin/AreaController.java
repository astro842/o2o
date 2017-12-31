package com.astro.web.superadmin;

import com.astro.entity.Area;
import com.astro.service.AreaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by astro on 2017/12/23.
 */
@Controller
@RequestMapping("superadmin")
public class AreaController {

    Logger logger = LoggerFactory.getLogger(AreaController.class);

    @Autowired
    private AreaService areaService;

    @GetMapping("/areaList")
    @ResponseBody
    public Map<String,Object> areaList(){
        logger.info("start-------------------");

        Map<String,Object> map = new HashMap<String,Object>();
        List<Area> list =new ArrayList<>();
        list = areaService.getAreaList();
        map.put("rows",list);
        map.put("total",list.size());
        logger.error("errrrrrrrrrrrrrrrrrrrrrr");
        logger.debug("1212121212121");
        logger.info("end-------------------");
        return  map;


    }

}

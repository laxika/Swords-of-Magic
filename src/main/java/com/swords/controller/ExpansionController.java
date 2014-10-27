package com.swords.controller;

import com.swords.model.Expansion;
import com.swords.model.repository.ExpansionRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ExpansionController {

    @Autowired
    private ExpansionRepository expansionRepository;
    
    @RequestMapping("/expansion/template")
    public String expansionIndexTemplate() {
        return "expansion/indextemplate";
    }

    @ResponseBody
    @RequestMapping(value = "/expansion/data", produces = "application/json; charset=utf-8")
    public List<Expansion> expansionIndexData() {
        return expansionRepository.findAll(new Sort(new Sort.Order(Sort.Direction.DESC, "releaseDate")));
    }
    
    @RequestMapping("/expansion/specific")
    public String expansionTemplate() {
        return "expansion/template";
    }
    
    @ResponseBody
    @RequestMapping(value = "/expansion/data/{setname}", produces = "application/json; charset=utf-8")
    public List<Expansion> expansionData(@PathVariable String setname) {
        return expansionRepository.findAll();
    }
}

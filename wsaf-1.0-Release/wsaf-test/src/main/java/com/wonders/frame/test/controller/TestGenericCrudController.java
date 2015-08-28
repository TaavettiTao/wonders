package com.wonders.frame.test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wonders.frame.core.controller.AbstractSingleCrudController;
import com.wonders.frame.test.model.bo.Test;

@Controller
@RequestMapping("/api/test")
public class TestGenericCrudController extends AbstractSingleCrudController<Test>{

}

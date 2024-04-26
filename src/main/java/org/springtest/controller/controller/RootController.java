package org.springtest.controller.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class RootController {

    @RequestMapping(value = "/", method = {RequestMethod.GET})
    public ModelAndView root() {
        return new ModelAndView("redirect:note/list");
    }

    @RequestMapping(value = "/note", method = {RequestMethod.GET})
    public ModelAndView rootNotes() {
        return new ModelAndView("redirect:note/list");
    }
}

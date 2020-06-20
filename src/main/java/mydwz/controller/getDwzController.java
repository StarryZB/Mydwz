package mydwz.controller;


import mydwz.beans.MyDwz;
import mydwz.service.getDwzService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/mydwz")
public class getDwzController {

    @Autowired
    private getDwzService getDwzService;

    @RequestMapping("index")
    public ModelAndView getIndex() {
        return new ModelAndView("index");
    }

    @RequestMapping("/getMyDwz")
    public ModelAndView getMyDwz(@RequestParam("longUrl") String longUrl) {
        ModelAndView mv = new ModelAndView("getdwz");
        MyDwz myDwz = getDwzService.getMyDwz(longUrl);
        mv.addObject("getdwz", myDwz);
        return mv;
    }

    @RequestMapping(value = "/{shorturl}", method = RequestMethod.GET)
    public ModelAndView redirect(@PathVariable String shorturl) {
        String longUrl = getDwzService.getMyOriginalWz(shorturl);
        if (longUrl == null) {
            return new ModelAndView("notfound");
        }
        return new ModelAndView("redirect:http://" + longUrl);
    }
}
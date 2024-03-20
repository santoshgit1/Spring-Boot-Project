package com.springbootwithmvc.controller;

import com.springbootwithmvc.model.ApplicationRequest;
import com.springbootwithmvc.model.ReleaseRequest;
import com.springbootwithmvc.model.ReleaseTO;
import com.springbootwithmvc.service.ReleaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/releases")
public class ReleaseController {
    @Autowired
    private ReleaseService releaseService;

    @GetMapping("/list")
    public String findAllReleases(Model model){
        log.info("Inside the ReleaseController.findAllReleases");
        List<ReleaseTO> releaseTOS = null;

        try{
            releaseTOS = releaseService.findAllReleases();
        } catch (Exception ex){
            log.error("Exception while getting release details");
            model.addAttribute("errorMessage", "Release details not found");
        }

        model.addAttribute("releases", releaseTOS);
        return "releases";
    }

    @GetMapping("/create")
    public String createRelease(Model model){
        log.info("Inside the ReleaseController.createRelease");
        try{
            ReleaseRequest releaseRequest = new ReleaseRequest();
            model.addAttribute("release", releaseRequest);
        } catch (Exception ex){
         log.error("Exception while getting release details");
            model.addAttribute("errorMessage", "Release details not found");
        }

        return "releasenew";
    }

    @PostMapping("/save")
    public RedirectView saveRelease(@ModelAttribute("release") ReleaseRequest request, Model model){
        log.info("Inside the ReleaseController.saveRelease, releaseRequest:{}", request);
            ReleaseTO releaseTO = null;
        try{
            releaseTO = releaseService.saveRelease(request);
            log.info("Saved release details, releaseTO:{}", releaseTO);
        } catch (Exception ex){
            log.error("Exception while getting release details");
            model.addAttribute("errorMessage", "Release details not saved");
        }

        RedirectView redirectView = new RedirectView();
        redirectView.setContextRelative(true);
        redirectView.setUrl("/releases/list");
        return redirectView;
    }
}

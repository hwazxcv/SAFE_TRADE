package com.safetrade.safe_trade.controllers.members;

import com.safetrade.safe_trade.commons.CommonProcess;
import com.safetrade.safe_trade.commons.MemberUtil;
import com.safetrade.safe_trade.commons.Utils;
import com.safetrade.safe_trade.controllers.members.dtos.RequestJoin;
import com.safetrade.safe_trade.models.member.MemberSaveService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController implements CommonProcess {

    private final Utils utils;
    private final MemberUtil memberUtil;
    private final MemberSaveService saveService;
    
    @GetMapping("/join")
    public String join(@ModelAttribute RequestJoin form, Model model) {
        commonProcess(model, "회원가입");

        return utils.tpl("member/join");
    }

    @PostMapping("/join")
    public String joinPs(@Valid RequestJoin form, Errors errors, Model model) {
        commonProcess(model, "회원가입");

        saveService.join(form, errors);

        if (errors.hasErrors()) {
            return utils.tpl("member/join");
        }

        return "redirect:/member/login";
    }


    @GetMapping("/login")
    public String login(String redirectURL, Model model) {
        commonProcess(model, "로그인");

        model.addAttribute("redirectURL", redirectURL);

        return utils.tpl("member/login");
    }
    @Override
    public void commonProcess(Model model, String pageTitle) {
        CommonProcess.super.commonProcess(model, pageTitle);

        model.addAttribute("addCss", new String[] { "member/style" });
    }

}





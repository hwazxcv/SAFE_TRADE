package com.safetrade.safe_trade.controllers.admins;

import com.safetrade.safe_trade.commons.ListData;
import com.safetrade.safe_trade.controllers.members.dtos.MemberSearch;
import com.safetrade.safe_trade.entities.Member;
import com.safetrade.safe_trade.models.member.MemberInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("adminMemberController")
@RequestMapping("/admin/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberInfoService infoService;


    @GetMapping
    public String selectmember(@ModelAttribute MemberSearch memberSearch, Model model) {


        ListData<Member> data = infoService.getList(memberSearch);

        model.addAttribute("members", data.getContent());
        model.addAttribute("pagination", data.getPagination());

        return "admin/member/index";
    }
}
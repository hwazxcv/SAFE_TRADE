package com.safetrade.safe_trade.controllers;

import com.safetrade.safe_trade.commons.ListData;
import com.safetrade.safe_trade.commons.Utils;
import com.safetrade.safe_trade.controllers.members.dtos.SearchTrade;
import com.safetrade.safe_trade.entities.Trade;
import com.safetrade.safe_trade.models.trade.TradeInfoService;
import com.safetrade.safe_trade.repositories.TradeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
public class IndexController {
    private final Utils utils;
    private final TradeInfoService infoService;


    @GetMapping("/")
    public String index(@ModelAttribute SearchTrade form, Model model) {

        ListData<Trade> data = infoService.getList(form);

        model.addAttribute("items", data.getContent());
        model.addAttribute("pagination", data.getPagination());
        model.addAttribute("menuCode", form.getMode());
        //메인 페이지에 들어갈 css는 여기서
        model.addAttribute("addCss", new String[] { "mypage/style"});
        return utils.tpl("main/index");
    }
}
package com.safetrade.safe_trade.controllers.members;

import com.safetrade.safe_trade.commons.ListData;
import com.safetrade.safe_trade.commons.MemberUtil;
import com.safetrade.safe_trade.commons.ScriptExceptionProcess;
import com.safetrade.safe_trade.commons.Utils;
import com.safetrade.safe_trade.controllers.members.dtos.RequestTrade;
import com.safetrade.safe_trade.controllers.members.dtos.SearchTrade;
import com.safetrade.safe_trade.entities.Trade;
import com.safetrade.safe_trade.models.trade.TradeInfoService;
import com.safetrade.safe_trade.models.trade.TradeSaveService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/mypage")
@RequiredArgsConstructor
public class MypageController implements ScriptExceptionProcess {
    private final Utils utils;
    private final TradeSaveService saveService;
    private final TradeInfoService infoService;


    @GetMapping
    public String index(@ModelAttribute SearchTrade search, Model model) {
        commonProcess("mypage", model);
        ListData<Trade> data = infoService.getList(search);
        model.addAttribute("lists", data.getContent());
        model.addAttribute("pagination", data.getPagination());

        return utils.tpl("mypage/index");
    }

    // 리스트 클릭시 상세 페이지로 이동
    @GetMapping("/product/{seq}")
    public String product(@PathVariable Long seq, Model model) {
        commonProcess("add", model);
        Trade  list = infoService.get(seq);
        model.addAttribute("list", list);
        return utils.tpl("mypage/product");
    }

    @GetMapping("/trade/add")
    public String trade(@ModelAttribute RequestTrade form, Model model) {
        commonProcess("add", model);
        return utils.tpl("mypage/trade_add");
    }

    @GetMapping("/trade/edit/{seq}") // 게시판 시퀀스가 넘어오면 그것으로 수정
    public String tradeEdit(@PathVariable Long seq, Model model) {
        commonProcess("edit", model);

        return utils.tpl("mypage/trade_edit");
    }


    @PostMapping("/trade/save") // 추가와 수정 -> 수정이 완료되면 목록으로 이동 -> 등록과 수정은 동일한 양식의 템플릿
    // 현재 NotBlank두개가 추가되어있는데 검증을 하려면 알려줘야함 (@Valid를 사용) Errors를 넣어주면 당겨짐
    public String tradeSave(@Valid RequestTrade form, Errors errors, Model model) {
        String mode = Objects.requireNonNullElse(form.getMode(), "add");
        commonProcess(mode, model);

        if (errors.hasErrors()) {
            // 에러가 있으면 양식을 보여줘야하는데 add와 edit로 나뉘어져있다.
            // form에있는 mode값이 넘어오는데 그것으로 대체하면 된다.
            // String mode = form.getMode();로 가져오면 됨
            // 이후 form으로 넘어가서 에러연동 및 커맨드 객체 연동
            // 모델 서비스 후 연동
            return utils.tpl("mypage/trade_" + mode);
        }
        saveService.save(form);
        return "redirect:/";
}

    private void commonProcess(String mode, Model model) {
        String pageTitle = "게시글 목록"; // 기본값
        if (mode.equals("add")) pageTitle = "게시글 작성";
        else if (mode.equals("edit")) pageTitle = "게시글 수정";

        List<String> addCommonScript = new ArrayList<>();
        List<String> addScript = new ArrayList<>();
        List<String> addCss = new ArrayList<>();
        addCss.add("mypage/style");

        if (mode.equals("add") || mode.equals("edit")) {
            addCommonScript.add("ckeditor/ckeditor");
            addCommonScript.add("fileManager");
            addScript.add("mypage/trade");
            addCss.add("mypage/trade");
        }
        model.addAttribute("menuCode", "mypage");
        model.addAttribute("pageTitle", pageTitle);
        model.addAttribute("addCommonScript", addCommonScript);
        model.addAttribute("addScript", addScript);
        model.addAttribute("addCss", addCss);
    }



}

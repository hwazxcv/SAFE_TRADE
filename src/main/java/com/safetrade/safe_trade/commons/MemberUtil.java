package com.safetrade.safe_trade.commons;

import com.safetrade.safe_trade.commons.constants.MemberType;
import com.safetrade.safe_trade.entities.Member;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberUtil {
    private final HttpSession session;

    /**
     * 로그인 여부 체크
     * @return
     */
    public boolean isLogin() {
        return getMember() != null;
    }

    /**
     * 관리자 여부 체크
     * @return
     */
    public boolean isAdmin() {
        return isLogin() && getMember().getMtype() == MemberType.ADMIN;
    }


    public Member getMember() {
        return (Member)session.getAttribute("loginMember");
    }
}

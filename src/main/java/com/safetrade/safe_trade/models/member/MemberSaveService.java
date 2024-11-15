package com.safetrade.safe_trade.models.member;

import com.safetrade.safe_trade.commons.constants.MemberType;
import com.safetrade.safe_trade.controllers.members.dtos.RequestJoin;
import com.safetrade.safe_trade.controllers.members.validators.JoinValidator;
import com.safetrade.safe_trade.entities.Member;
import com.safetrade.safe_trade.repositories.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

@Service
@RequiredArgsConstructor
public class MemberSaveService {
    private final MemberRepository repository;
    private final PasswordEncoder encoder;
    private final JoinValidator validator;

    /**
     * 회원가입 처리
     * @param form
     * @param errors
     */
    public Member join(RequestJoin form, Errors errors) {
        validator.validate(form, errors);

        if (errors.hasErrors()) {
            return null;
        }

        String hash = encoder.encode(form.getPassword());

        Member member = Member.builder()
                .email(form.getEmail())
                .userNm(form.getUserNm())
                .mobile(form.getMobile())
                .mtype(MemberType.USER)
                .password(hash)
                .build();

        save(member);

        member = repository.findByEmail(form.getEmail()).get();
        return member;
    }

    public void save(Member member) {
        String mobile = member.getMobile();
        if (mobile != null) {
            mobile = mobile.replaceAll("\\D", "");
            member.setMobile(mobile);
        }

        repository.saveAndFlush(member);
    }

}
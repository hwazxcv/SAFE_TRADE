package com.safetrade.safe_trade.models.member;

import com.querydsl.core.BooleanBuilder;
import com.safetrade.safe_trade.commons.ListData;
import com.safetrade.safe_trade.commons.Pagination;
import com.safetrade.safe_trade.commons.Utils;
import com.safetrade.safe_trade.controllers.members.dtos.MemberSearch;
import com.safetrade.safe_trade.entities.Member;
import com.safetrade.safe_trade.repositories.MemberRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

import static org.springframework.data.domain.Sort.Order.desc;

@Service
@RequiredArgsConstructor
public class MemberInfoService implements UserDetailsService {

    private final MemberRepository repository;

    private final HttpServletRequest request;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Member member = repository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException(username));

        List<GrantedAuthority> authorities
                = Arrays.asList(new SimpleGrantedAuthority(member.getMtype().name()));


        return MemberInfo.builder()
                .email(member.getEmail())
                .password(member.getPassword())
                .authorities(authorities)
                .member(member)
                .build();
    }
//맴버 조회와 페이지 리스트
    public ListData<Member> getList(MemberSearch search) {

        int page = Utils.getNumber(search.getPage(), 1);
        int limit = Utils.getNumber(search.getLimit(), 20);


        BooleanBuilder andBuilder = new BooleanBuilder();
        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by(desc("createdAt")));

        Page<Member> data = repository.findAll(andBuilder, pageable);

        // (int page, int total, int ranges, int limit, HttpServletRequest request)
        Pagination pagination = new Pagination(page, (int)data.getTotalElements(), 10, limit, request);

        ListData<Member> listData = new ListData<>();
        listData.setContent(data.getContent());
        listData.setPagination(pagination);

        return listData;
    }
}

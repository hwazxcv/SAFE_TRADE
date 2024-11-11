package com.safetrade.safe_trade.models.trade;

import com.safetrade.safe_trade.commons.constants.TradeType;
import com.safetrade.safe_trade.controllers.members.dtos.RequestTrade;
import com.safetrade.safe_trade.entities.Trade;
import com.safetrade.safe_trade.repositories.FileInfoRepository;
import com.safetrade.safe_trade.repositories.TradeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TradeSaveService {

    private final TradeRepository tradeRepository;
    private final FileInfoRepository fileInfoRepository;


    // (커맨드 객체)
    public void save(RequestTrade form) {

        Long seq = form.getSeq();
        String gid = Objects.requireNonNullElse(form.getGid(), UUID.randomUUID().toString());
        String mode = Objects.requireNonNullElse(form.getMode(), "add");
        Trade data = null; // 엔티티꺼
        if (mode.equals("edit") && seq != null) {
            data = tradeRepository.findById(seq).orElseThrow(TradeNotFoundException::new);
        } else {
            data = new Trade(); // 게시물이 없으면 추가
            data.setGid(form.getGid());
        }

        data.setType(TradeType.valueOf(form.getType()));
        data.setSubject(form.getSubject());
        data.setPrice(form.getPrice());

        tradeRepository.saveAndFlush(data); // 이후 다시 컨트롤러로 이동해서 연동


        fileInfoRepository.processDone(gid);
    }
}

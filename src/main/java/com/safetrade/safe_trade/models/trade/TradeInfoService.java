package com.safetrade.safe_trade.models.trade;

import com.querydsl.core.BooleanBuilder;
import com.safetrade.safe_trade.commons.ListData;
import com.safetrade.safe_trade.commons.Pagination;
import com.safetrade.safe_trade.commons.Utils;
import com.safetrade.safe_trade.commons.constants.TradeType;
import com.safetrade.safe_trade.controllers.members.dtos.SearchTrade;
import com.safetrade.safe_trade.entities.QTrade;
import com.safetrade.safe_trade.entities.Trade;
import com.safetrade.safe_trade.repositories.FileInfoRepository;
import com.safetrade.safe_trade.repositories.TradeRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.safetrade.safe_trade.entities.FileInfo;
import org.springframework.util.StringUtils;

import java.util.List;

import static org.springframework.data.domain.Sort.Order.desc;

@Service
@RequiredArgsConstructor
public class TradeInfoService {
    private final TradeRepository repository;
    private final FileInfoRepository fileInfoRepository;
    private final HttpServletRequest request;
    public Trade get(Long seq) { // 게시글 상세 조회
        Trade data = repository.findById(seq).orElseThrow(TradeNotFoundException::new);
        addFileInfo(data);

        return data;
    }

    //페이징 처리 및 검색 구현 (미완)
    public ListData<Trade> getList(SearchTrade search) { // 게시글 검색기능
        int page = Utils.getNumber(search.getPage(), 1);
        int limit = Utils.getNumber(search.getLimit(), 20);
        String mode = search.getMode();
        String keyword = search.getKeyword();
        QTrade trade = QTrade.trade;

        BooleanBuilder andBuilder = new BooleanBuilder();
        BooleanBuilder orBuilder = new BooleanBuilder();
        if (StringUtils.hasText(keyword)) {
            orBuilder.or(trade.subject.contains(keyword))
                    .or(trade.content.contains(keyword));
            andBuilder.and(orBuilder);
        }

        if (StringUtils.hasText(mode)) {
            andBuilder.and(QTrade.trade.type.eq(TradeType.valueOf(mode.toUpperCase())));
        }

        PageRequest pageable = PageRequest.of(page - 1, limit, Sort.by(desc("createdAt")));

        Page<Trade> data = repository.findAll(andBuilder, pageable);
        List<Trade> items = data.getContent();
        items.stream().forEach(this::addFileInfo);

        Pagination pagination = new Pagination(page, (int)data.getTotalElements(), 10, limit, request);
        ListData<Trade> listData = new ListData<>();
        listData.setContent(items);
        listData.setPagination(pagination);

        return listData;
    }

    private void addFileInfo(Trade data) {
        String gid = data.getGid();
        List<FileInfo> editorImages = fileInfoRepository.getFiles(gid, "editor");
        data.setEditorImages(editorImages);
    }
}

package kr.co.sboard.service;

import kr.co.sboard.dao.ArticleDAO;
import kr.co.sboard.dto.ArticleDTO;
import kr.co.sboard.dto.FileDTO;
import kr.co.sboard.dto.PageRequestDTO;
import kr.co.sboard.dto.PageResponseDTO;
import kr.co.sboard.entity.Article;
import kr.co.sboard.entity.File;
import kr.co.sboard.entity.User;
import kr.co.sboard.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Log4j2
@RequiredArgsConstructor
@Service
public class ArticleService {

    private final ArticleDAO dao;
    private final ArticleRepository repository;

    public ArticleDTO get(int ano){
        // Mybatis
        ArticleDTO articleDTO = dao.select(ano);

        return articleDTO;
    }

    public ArticleDTO find(int ano){
        // JPA
        Optional<Article> optArticle = repository.findById(ano);

        if(optArticle.isPresent()){
            Article entity = optArticle.get();

            List<File> entityFileList = entity.getFileList();

            List<FileDTO> dtoFileList = entityFileList.stream()
                    .map(fileEntity -> fileEntity.toDTO())
                    .toList();

            ArticleDTO articleDTO = entity.toDTO();
            articleDTO.setFileList(dtoFileList);

            log.info(articleDTO);
            return articleDTO;
        }
        return null;
    }


    public PageResponseDTO getAll(PageRequestDTO pageRequestDTO){
        // Mybatis
        List<ArticleDTO> dtoList = dao.selectAll(pageRequestDTO);

        int total = dao.selectCountAll(pageRequestDTO);

        return PageResponseDTO.builder()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total(total)
                .build();
    }

    public PageResponseDTO findAll(PageRequestDTO pageRequestDTO){
        // Pageable은 JPA에서 페이징 처리를 위한 객체
        Pageable pageable = pageRequestDTO.getPageable("ano");

        Page<Article> pageArticle = repository.findAll(pageable);

        List<ArticleDTO> dtoList = pageArticle.getContent()
                .stream()
                .map(entity -> {
                    // 엔티티를 DTO 변환
                    ArticleDTO dto = entity.toDTO();

                    // 엔티티에 관계설정한 User 엔티티 참조
                    User user = entity.getUser();

                    // User 엔티티의 nick 가져오기
                    dto.setNick(user.getNick());

                    return dto;
                })
                .toList();

        int total = (int) repository.count(); // 전체 갯수

        return PageResponseDTO.builder()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total(total)
                .build();
    }

    public int register(ArticleDTO dto){
        dao.insert(dto);

        // 매퍼 dao에 인자로 전달되는 dto의 속성 ano에 pk값 초기화
        return dto.getAno();
    }

    public void modify(ArticleDTO dto){

    }

    public void modifyHit(int ano){
        dao.updateHit(ano);
    }

    public void remove(int ano){

    }
}
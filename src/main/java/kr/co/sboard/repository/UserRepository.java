package kr.co.sboard.repository;

import kr.co.sboard.entity.Article;
import kr.co.sboard.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    int countByUserid(String userid); // SELECT COUNT(*) FROM user WHERE userid = ?;
    int countByNick(String nick);
    int countByEmail(String email);
    int countByHp(String hp);

    // Spring Data JPA가 메서드 이름을 분석해서 SQL을 자동 생성
}
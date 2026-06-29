package kr.co.sboard.service;

import kr.co.sboard.dao.UserDAO;
import kr.co.sboard.dto.UserDTO;
import kr.co.sboard.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Log4j2
@Service
public class UserService {
    private final UserDAO dao;
    private final UserRepository repository;

    public UserDTO get(String userid) {
        return null;
    }

    public List<UserDTO> getAll() {
        return null;

    }

    public void register(UserDTO dto) {

    }

    public void modify(UserDTO dto) {

    }

    public void remove(String userid) {

    }

}

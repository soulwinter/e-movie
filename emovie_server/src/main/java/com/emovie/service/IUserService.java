package com.emovie.service;

import com.emovie.entity.User;
import com.emovie.util.Result;
import org.springframework.stereotype.Service;

@Service
public interface IUserService {

    Result login(String telephone, String password);
}

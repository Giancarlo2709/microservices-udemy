package com.gyarleque.springboot.app.oauth.services;

import com.gyarleque.springboot.app.commons.users.models.entity.User;

public interface UserService {
	
	User findByUsername(String username);
	
	User update(User user,Long id);

}

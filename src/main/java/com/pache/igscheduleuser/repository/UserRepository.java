package com.pache.igscheduleuser.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pache.igscheduleuser.entity.User;

/**
 * Created by lpache on 7/20/17.
 */
public interface UserRepository extends JpaRepository<User,Long>{

    List<User> findByName(String name);
    
    User findByUserId(Long id);
}

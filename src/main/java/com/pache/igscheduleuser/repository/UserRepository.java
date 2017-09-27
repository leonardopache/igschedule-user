package com.pache.igscheduleuser.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pache.igscheduleuser.entity.User;

/**
 * Created by lpache on 7/20/17.
 */
@Repository
public interface UserRepository extends JpaRepository<User,Long>{

    List<User> findByName(String name);
}

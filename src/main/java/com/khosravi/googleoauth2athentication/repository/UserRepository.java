package com.khosravi.googleoauth2athentication.repository;

import com.khosravi.googleoauth2athentication.entity.User;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, String>, JpaSpecificationExecutor<User> {

}

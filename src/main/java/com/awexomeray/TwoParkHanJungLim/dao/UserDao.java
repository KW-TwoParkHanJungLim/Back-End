package com.awexomeray.TwoParkHanJungLim.dao;

import com.awexomeray.TwoParkHanJungLim.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;


import javax.validation.constraints.Null;
import java.util.List;

@Repository
public class UserDao {
    @Autowired
    private MongoTemplate mongoTemplate;

    public List<UserEntity> getUsers() {
        return mongoTemplate.findAll(UserEntity.class, "_users");
    }

    public UserEntity getUser(String id) throws NullPointerException {
        UserEntity userEntity = mongoTemplate.findOne(
                Query.query(Criteria.where("id").is(id)),
                UserEntity.class,
                "_users"
        );

        return userEntity;
    }
}

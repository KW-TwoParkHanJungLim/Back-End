package com.awexomeray.TwoParkHanJungLim.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface GraphRepository extends MongoRepository<AirDataModel, String> {
}

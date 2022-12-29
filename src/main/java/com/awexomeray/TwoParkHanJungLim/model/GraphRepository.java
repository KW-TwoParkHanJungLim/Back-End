package com.awexomeray.TwoParkHanJungLim.model;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface GraphRepository extends MongoRepository<GraphModel, String> {
}

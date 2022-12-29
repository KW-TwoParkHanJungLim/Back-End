package com.awexomeray.TwoParkHanJungLim.repository;

import com.awexomeray.TwoParkHanJungLim.model.GraphModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface GraphRepository extends MongoRepository<GraphModel, String> {
}

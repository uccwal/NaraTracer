package com.uccwal.naratracer.Repository;

import com.uccwal.naratracer.Entity.TracerEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TracerRepository extends MongoRepository<TracerEntity, String> {
}

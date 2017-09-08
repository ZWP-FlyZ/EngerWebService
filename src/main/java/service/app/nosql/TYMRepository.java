package service.app.nosql;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface TYMRepository extends MongoRepository<CacheData, String> {

}

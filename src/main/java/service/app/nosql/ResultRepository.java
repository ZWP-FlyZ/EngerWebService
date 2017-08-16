package service.app.nosql;

import org.springframework.data.mongodb.repository.MongoRepository;


public interface ResultRepository extends MongoRepository<RespResult, String> {
	
	public Object findById(String id);
	public Object findByName(String name);
	
}

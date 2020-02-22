package com.techblog.backend;

import com.techblog.backend.repository.PostRepository;
import com.techblog.backend.resolvers.Query;
import graphql.ExecutionResult;
import graphql.Scalars;
import graphql.schema.GraphQLObjectType;
import graphql.schema.GraphQLSchema;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

	/* FOR LATER
	@PostMapping("/posts")
	public ResponseEntity<Object> login(@RequestBody String query){
		// ExecutionResult executionResult = userService.getGraphQL().execute(query);

		// Check if there are errors
		// if(!executionResult.getErrors().isEmpty()){
		//	return new ResponseEntity<>(executionResult.getErrors().get(0).getMessage(), HttpStatus.UNAUTHORIZED);
		// }

		return new ResponseEntity<>("Hello World", HttpStatus.OK);
	} */

	@Bean
	GraphQLSchema schema() {
		return GraphQLSchema.newSchema()
				.query(GraphQLObjectType.newObject()
						.name("query")
						.field(field -> field
								.name("test")
								.type(Scalars.GraphQLString)
								.dataFetcher(environment -> "response")
						)
						.build())
				.build();
	}

}

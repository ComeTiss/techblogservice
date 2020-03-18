package com.techblog.backend;

import com.techblog.backend.datafetchers.post.AllPostDataFetcher;
import com.techblog.backend.datafetchers.post.MutatePostDataFetcher;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;

@Service
public class GraphQLService {

    @Autowired private AllPostDataFetcher allPostDataFetcher;
    @Autowired private MutatePostDataFetcher mutatePostDataFetcher;

    @Getter
    private GraphQL graphQL;

    @PostConstruct
    private void loadSchema() throws IOException {
        File schemaFile = new File("src/main/resources/graphql/post.graphqls");
        TypeDefinitionRegistry typeRegistry = new SchemaParser().parse(schemaFile);
        RuntimeWiring wiring = buildRuntimeWiring();
        GraphQLSchema schema = new SchemaGenerator().makeExecutableSchema(typeRegistry, wiring);
        this.graphQL = GraphQL.newGraphQL(schema).build();
    }

    private RuntimeWiring buildRuntimeWiring() {
        return RuntimeWiring.newRuntimeWiring()
                .type("Query", type ->
                        type.dataFetcher("getAllPosts", allPostDataFetcher))
                .type("Mutation", type ->
                        type.dataFetcher("mutatePost", mutatePostDataFetcher))
                .build();
    }
}

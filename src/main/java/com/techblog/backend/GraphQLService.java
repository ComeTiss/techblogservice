package com.techblog.backend;

import com.google.common.collect.ImmutableList;
import com.techblog.backend.datafetchers.PostDataFetcher;
import com.techblog.backend.datafetchers.PostVotesDataFetcher;
import com.techblog.backend.datafetchers.UserDataFetcher;
import com.techblog.backend.types.error.ServiceExceptionHandler;
import graphql.GraphQL;
import graphql.execution.AsyncSerialExecutionStrategy;
import graphql.schema.DataFetcher;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GraphQLService {

  @Autowired private PostDataFetcher postDataFetcher;
  @Autowired private UserDataFetcher userDataFetcher;
  @Autowired private PostVotesDataFetcher postVotesDataFetcher;

  @Getter private GraphQL graphQL;

  @PostConstruct
  private void loadSchema() throws IOException {
    List<File> files = loadFiles();
    SchemaParser schemaParser = new SchemaParser();
    TypeDefinitionRegistry typeRegistry = new TypeDefinitionRegistry();
    files.forEach(file -> typeRegistry.merge(schemaParser.parse(file)));

    RuntimeWiring wiring = buildRuntimeWiring();
    GraphQLSchema schema = new SchemaGenerator().makeExecutableSchema(typeRegistry, wiring);
    this.graphQL =
        GraphQL.newGraphQL(schema)
            .mutationExecutionStrategy(
                new AsyncSerialExecutionStrategy(new ServiceExceptionHandler()))
            .build();
  }

  private List<File> loadFiles() {
    List<String> filenames =
        ImmutableList.of(
            "src/main/resources/graphql/user.graphqls",
            "src/main/resources/graphql/post.graphqls",
            "src/main/resources/graphql/post_votes.graphqls");
    List<File> files = new ArrayList<>();
    filenames.forEach(f -> files.add(new File(f)));
    return files;
  }

  private RuntimeWiring buildRuntimeWiring() {
    return RuntimeWiring.newRuntimeWiring()
        .type("Query", type -> type.dataFetchers(queryDataFetchers()))
        .type("Mutation", type -> type.dataFetchers(mutationDataFetchers()))
        .build();
  }

  private Map<String, DataFetcher> queryDataFetchers() {
    Map<String, DataFetcher> dataFetchersMap = new HashMap<>();
    dataFetchersMap.put("getPostsWithFilters", postDataFetcher::getPostsWithFilters);
    return dataFetchersMap;
  }

  private Map<String, DataFetcher> mutationDataFetchers() {
    Map<String, DataFetcher> dataFetchersMap = new HashMap<>();
    dataFetchersMap.put("mutatePost", postDataFetcher::mutatePost);
    dataFetchersMap.put("deletePostsByIds", postDataFetcher::deletePostByIds);

    dataFetchersMap.put("mutateVote", postVotesDataFetcher::mutateVote);
    dataFetchersMap.put("deleteVote", postVotesDataFetcher::deleteVote);

    dataFetchersMap.put("signup", userDataFetcher::signUp);
    dataFetchersMap.put("login", userDataFetcher::login);
    return dataFetchersMap;
  }
}

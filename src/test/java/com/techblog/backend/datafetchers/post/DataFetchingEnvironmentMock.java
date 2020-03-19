package com.techblog.backend.datafetchers.post;

import graphql.cachecontrol.CacheControl;
import graphql.execution.ExecutionId;
import graphql.execution.ExecutionStepInfo;
import graphql.execution.MergedField;
import graphql.execution.directives.QueryDirectives;
import graphql.language.Document;
import graphql.language.Field;
import graphql.language.FragmentDefinition;
import graphql.language.OperationDefinition;
import graphql.schema.*;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.dataloader.DataLoader;
import org.dataloader.DataLoaderRegistry;

public class DataFetchingEnvironmentMock implements DataFetchingEnvironment {

  private Map<String, Object> arguments;

  public void DataFetchingEnvironmentMock() {
    this.arguments = new HashMap<>();
  }

  public void setArguments(Map<String, Object> arguments) {
    this.arguments = arguments;
  }

  public void putArgument(String key, Object value) {
    this.arguments.put(key, value);
  }

  @Override
  public <T> T getSource() {
    return null;
  }

  @Override
  public Map<String, Object> getArguments() {
    return this.arguments;
  }

  @Override
  public boolean containsArgument(String name) {
    return false;
  }

  @Override
  public <T> T getArgumentOrDefault(String name, T defaultValue) {
    return null;
  }

  @Override
  public <T> T getContext() {
    return null;
  }

  @Override
  public <T> T getLocalContext() {
    return null;
  }

  @Override
  public <T> T getRoot() {
    return null;
  }

  @Override
  public GraphQLFieldDefinition getFieldDefinition() {
    return null;
  }

  @Override
  public List<Field> getFields() {
    return null;
  }

  @Override
  public MergedField getMergedField() {
    return null;
  }

  @Override
  public Field getField() {
    return null;
  }

  @Override
  public GraphQLOutputType getFieldType() {
    return null;
  }

  @Override
  public ExecutionStepInfo getExecutionStepInfo() {
    return null;
  }

  @Override
  public GraphQLType getParentType() {
    return null;
  }

  @Override
  public GraphQLSchema getGraphQLSchema() {
    return null;
  }

  @Override
  public Map<String, FragmentDefinition> getFragmentsByName() {
    return null;
  }

  @Override
  public ExecutionId getExecutionId() {
    return null;
  }

  @Override
  public DataFetchingFieldSelectionSet getSelectionSet() {
    return null;
  }

  @Override
  public QueryDirectives getQueryDirectives() {
    return null;
  }

  @Override
  public <K, V> DataLoader<K, V> getDataLoader(String dataLoaderName) {
    return null;
  }

  @Override
  public DataLoaderRegistry getDataLoaderRegistry() {
    return null;
  }

  @Override
  public CacheControl getCacheControl() {
    return null;
  }

  @Override
  public Locale getLocale() {
    return null;
  }

  @Override
  public OperationDefinition getOperationDefinition() {
    return null;
  }

  @Override
  public Document getDocument() {
    return null;
  }

  @Override
  public Map<String, Object> getVariables() {
    return null;
  }

  @Override
  public Object getArgument(String name) {
    return this.arguments.get(name);
  }
}

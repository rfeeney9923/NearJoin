/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
/*
 * This code was generated by https://code.google.com/p/google-apis-client-generator/
 * (build: 2014-11-17 18:43:33 UTC)
 * on 2014-12-02 at 01:28:03 UTC 
 * Modify at your own risk.
 */

package edu.ohio_state.cse.nearjoin.eventbackend.myUser;

/**
 * Service definition for MyUser (v1).
 *
 * <p>
 * This is an API
 * </p>
 *
 * <p>
 * For more information about this service, see the
 * <a href="" target="_blank">API Documentation</a>
 * </p>
 *
 * <p>
 * This service uses {@link MyUserRequestInitializer} to initialize global parameters via its
 * {@link Builder}.
 * </p>
 *
 * @since 1.3
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public class MyUser extends com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient {

  // Note: Leave this static initializer at the top of the file.
  static {
    com.google.api.client.util.Preconditions.checkState(
        com.google.api.client.googleapis.GoogleUtils.MAJOR_VERSION == 1 &&
        com.google.api.client.googleapis.GoogleUtils.MINOR_VERSION >= 15,
        "You are currently running with version %s of google-api-client. " +
        "You need at least version 1.15 of google-api-client to run version " +
        "1.19.0 of the myUser library.", com.google.api.client.googleapis.GoogleUtils.VERSION);
  }

  /**
   * The default encoded root URL of the service. This is determined when the library is generated
   * and normally should not be changed.
   *
   * @since 1.7
   */
  public static final String DEFAULT_ROOT_URL = "https://eternal-calling-763.appspot.com/_ah/api/";

  /**
   * The default encoded service path of the service. This is determined when the library is
   * generated and normally should not be changed.
   *
   * @since 1.7
   */
  public static final String DEFAULT_SERVICE_PATH = "myUser/v1/";

  /**
   * The default encoded base URL of the service. This is determined when the library is generated
   * and normally should not be changed.
   */
  public static final String DEFAULT_BASE_URL = DEFAULT_ROOT_URL + DEFAULT_SERVICE_PATH;

  /**
   * Constructor.
   *
   * <p>
   * Use {@link Builder} if you need to specify any of the optional parameters.
   * </p>
   *
   * @param transport HTTP transport, which should normally be:
   *        <ul>
   *        <li>Google App Engine:
   *        {@code com.google.api.client.extensions.appengine.http.UrlFetchTransport}</li>
   *        <li>Android: {@code newCompatibleTransport} from
   *        {@code com.google.api.client.extensions.android.http.AndroidHttp}</li>
   *        <li>Java: {@link com.google.api.client.googleapis.javanet.GoogleNetHttpTransport#newTrustedTransport()}
   *        </li>
   *        </ul>
   * @param jsonFactory JSON factory, which may be:
   *        <ul>
   *        <li>Jackson: {@code com.google.api.client.json.jackson2.JacksonFactory}</li>
   *        <li>Google GSON: {@code com.google.api.client.json.gson.GsonFactory}</li>
   *        <li>Android Honeycomb or higher:
   *        {@code com.google.api.client.extensions.android.json.AndroidJsonFactory}</li>
   *        </ul>
   * @param httpRequestInitializer HTTP request initializer or {@code null} for none
   * @since 1.7
   */
  public MyUser(com.google.api.client.http.HttpTransport transport, com.google.api.client.json.JsonFactory jsonFactory,
      com.google.api.client.http.HttpRequestInitializer httpRequestInitializer) {
    this(new Builder(transport, jsonFactory, httpRequestInitializer));
  }

  /**
   * @param builder builder
   */
  MyUser(Builder builder) {
    super(builder);
  }

  @Override
  protected void initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest<?> httpClientRequest) throws java.io.IOException {
    super.initialize(httpClientRequest);
  }

  /**
   * Create a request for the method "add".
   *
   * This request holds the parameters needed by the myUser server.  After setting any optional
   * parameters, call the {@link Add#execute()} method to invoke the remote operation.
   *
   * @param name
   * @return the request
   */
  public Add add(java.lang.String name) throws java.io.IOException {
    Add result = new Add(name);
    initialize(result);
    return result;
  }

  public class Add extends MyUserRequest<Void> {

    private static final String REST_PATH = "addUser/{name}";

    /**
     * Create a request for the method "add".
     *
     * This request holds the parameters needed by the the myUser server.  After setting any optional
     * parameters, call the {@link Add#execute()} method to invoke the remote operation. <p> {@link
     * Add#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)} must be
     * called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @param name
     * @since 1.13
     */
    protected Add(java.lang.String name) {
      super(MyUser.this, "POST", REST_PATH, null, Void.class);
      this.name = com.google.api.client.util.Preconditions.checkNotNull(name, "Required parameter name must be specified.");
    }

    @Override
    public Add setAlt(java.lang.String alt) {
      return (Add) super.setAlt(alt);
    }

    @Override
    public Add setFields(java.lang.String fields) {
      return (Add) super.setFields(fields);
    }

    @Override
    public Add setKey(java.lang.String key) {
      return (Add) super.setKey(key);
    }

    @Override
    public Add setOauthToken(java.lang.String oauthToken) {
      return (Add) super.setOauthToken(oauthToken);
    }

    @Override
    public Add setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (Add) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public Add setQuotaUser(java.lang.String quotaUser) {
      return (Add) super.setQuotaUser(quotaUser);
    }

    @Override
    public Add setUserIp(java.lang.String userIp) {
      return (Add) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.String name;

    /**

     */
    public java.lang.String getName() {
      return name;
    }

    public Add setName(java.lang.String name) {
      this.name = name;
      return this;
    }

    @Override
    public Add set(String parameterName, Object value) {
      return (Add) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "delete".
   *
   * This request holds the parameters needed by the myUser server.  After setting any optional
   * parameters, call the {@link Delete#execute()} method to invoke the remote operation.
   *
   * @param name
   * @return the request
   */
  public Delete delete(java.lang.String name) throws java.io.IOException {
    Delete result = new Delete(name);
    initialize(result);
    return result;
  }

  public class Delete extends MyUserRequest<Void> {

    private static final String REST_PATH = "user/{name}";

    /**
     * Create a request for the method "delete".
     *
     * This request holds the parameters needed by the the myUser server.  After setting any optional
     * parameters, call the {@link Delete#execute()} method to invoke the remote operation. <p> {@link
     * Delete#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)} must
     * be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @param name
     * @since 1.13
     */
    protected Delete(java.lang.String name) {
      super(MyUser.this, "DELETE", REST_PATH, null, Void.class);
      this.name = com.google.api.client.util.Preconditions.checkNotNull(name, "Required parameter name must be specified.");
    }

    @Override
    public Delete setAlt(java.lang.String alt) {
      return (Delete) super.setAlt(alt);
    }

    @Override
    public Delete setFields(java.lang.String fields) {
      return (Delete) super.setFields(fields);
    }

    @Override
    public Delete setKey(java.lang.String key) {
      return (Delete) super.setKey(key);
    }

    @Override
    public Delete setOauthToken(java.lang.String oauthToken) {
      return (Delete) super.setOauthToken(oauthToken);
    }

    @Override
    public Delete setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (Delete) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public Delete setQuotaUser(java.lang.String quotaUser) {
      return (Delete) super.setQuotaUser(quotaUser);
    }

    @Override
    public Delete setUserIp(java.lang.String userIp) {
      return (Delete) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.String name;

    /**

     */
    public java.lang.String getName() {
      return name;
    }

    public Delete setName(java.lang.String name) {
      this.name = name;
      return this;
    }

    @Override
    public Delete set(String parameterName, Object value) {
      return (Delete) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "listUsers".
   *
   * This request holds the parameters needed by the myUser server.  After setting any optional
   * parameters, call the {@link ListUsers#execute()} method to invoke the remote operation.
   *
   * @param count
   * @return the request
   */
  public ListUsers listUsers(java.lang.Integer count) throws java.io.IOException {
    ListUsers result = new ListUsers(count);
    initialize(result);
    return result;
  }

  public class ListUsers extends MyUserRequest<edu.ohio_state.cse.nearjoin.eventbackend.myUser.model.CollectionResponseUserRecord> {

    private static final String REST_PATH = "userrecord/{count}";

    /**
     * Create a request for the method "listUsers".
     *
     * This request holds the parameters needed by the the myUser server.  After setting any optional
     * parameters, call the {@link ListUsers#execute()} method to invoke the remote operation. <p>
     * {@link
     * ListUsers#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)}
     * must be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @param count
     * @since 1.13
     */
    protected ListUsers(java.lang.Integer count) {
      super(MyUser.this, "GET", REST_PATH, null, edu.ohio_state.cse.nearjoin.eventbackend.myUser.model.CollectionResponseUserRecord.class);
      this.count = com.google.api.client.util.Preconditions.checkNotNull(count, "Required parameter count must be specified.");
    }

    @Override
    public com.google.api.client.http.HttpResponse executeUsingHead() throws java.io.IOException {
      return super.executeUsingHead();
    }

    @Override
    public com.google.api.client.http.HttpRequest buildHttpRequestUsingHead() throws java.io.IOException {
      return super.buildHttpRequestUsingHead();
    }

    @Override
    public ListUsers setAlt(java.lang.String alt) {
      return (ListUsers) super.setAlt(alt);
    }

    @Override
    public ListUsers setFields(java.lang.String fields) {
      return (ListUsers) super.setFields(fields);
    }

    @Override
    public ListUsers setKey(java.lang.String key) {
      return (ListUsers) super.setKey(key);
    }

    @Override
    public ListUsers setOauthToken(java.lang.String oauthToken) {
      return (ListUsers) super.setOauthToken(oauthToken);
    }

    @Override
    public ListUsers setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (ListUsers) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public ListUsers setQuotaUser(java.lang.String quotaUser) {
      return (ListUsers) super.setQuotaUser(quotaUser);
    }

    @Override
    public ListUsers setUserIp(java.lang.String userIp) {
      return (ListUsers) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.Integer count;

    /**

     */
    public java.lang.Integer getCount() {
      return count;
    }

    public ListUsers setCount(java.lang.Integer count) {
      this.count = count;
      return this;
    }

    @Override
    public ListUsers set(String parameterName, Object value) {
      return (ListUsers) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "update".
   *
   * This request holds the parameters needed by the myUser server.  After setting any optional
   * parameters, call the {@link Update#execute()} method to invoke the remote operation.
   *
   * @param name
   * @return the request
   */
  public Update update(java.lang.String name) throws java.io.IOException {
    Update result = new Update(name);
    initialize(result);
    return result;
  }

  public class Update extends MyUserRequest<Void> {

    private static final String REST_PATH = "void/{name}";

    /**
     * Create a request for the method "update".
     *
     * This request holds the parameters needed by the the myUser server.  After setting any optional
     * parameters, call the {@link Update#execute()} method to invoke the remote operation. <p> {@link
     * Update#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)} must
     * be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @param name
     * @since 1.13
     */
    protected Update(java.lang.String name) {
      super(MyUser.this, "PUT", REST_PATH, null, Void.class);
      this.name = com.google.api.client.util.Preconditions.checkNotNull(name, "Required parameter name must be specified.");
    }

    @Override
    public Update setAlt(java.lang.String alt) {
      return (Update) super.setAlt(alt);
    }

    @Override
    public Update setFields(java.lang.String fields) {
      return (Update) super.setFields(fields);
    }

    @Override
    public Update setKey(java.lang.String key) {
      return (Update) super.setKey(key);
    }

    @Override
    public Update setOauthToken(java.lang.String oauthToken) {
      return (Update) super.setOauthToken(oauthToken);
    }

    @Override
    public Update setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (Update) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public Update setQuotaUser(java.lang.String quotaUser) {
      return (Update) super.setQuotaUser(quotaUser);
    }

    @Override
    public Update setUserIp(java.lang.String userIp) {
      return (Update) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.String name;

    /**

     */
    public java.lang.String getName() {
      return name;
    }

    public Update setName(java.lang.String name) {
      this.name = name;
      return this;
    }

    @Override
    public Update set(String parameterName, Object value) {
      return (Update) super.set(parameterName, value);
    }
  }

  /**
   * Builder for {@link MyUser}.
   *
   * <p>
   * Implementation is not thread-safe.
   * </p>
   *
   * @since 1.3.0
   */
  public static final class Builder extends com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient.Builder {

    /**
     * Returns an instance of a new builder.
     *
     * @param transport HTTP transport, which should normally be:
     *        <ul>
     *        <li>Google App Engine:
     *        {@code com.google.api.client.extensions.appengine.http.UrlFetchTransport}</li>
     *        <li>Android: {@code newCompatibleTransport} from
     *        {@code com.google.api.client.extensions.android.http.AndroidHttp}</li>
     *        <li>Java: {@link com.google.api.client.googleapis.javanet.GoogleNetHttpTransport#newTrustedTransport()}
     *        </li>
     *        </ul>
     * @param jsonFactory JSON factory, which may be:
     *        <ul>
     *        <li>Jackson: {@code com.google.api.client.json.jackson2.JacksonFactory}</li>
     *        <li>Google GSON: {@code com.google.api.client.json.gson.GsonFactory}</li>
     *        <li>Android Honeycomb or higher:
     *        {@code com.google.api.client.extensions.android.json.AndroidJsonFactory}</li>
     *        </ul>
     * @param httpRequestInitializer HTTP request initializer or {@code null} for none
     * @since 1.7
     */
    public Builder(com.google.api.client.http.HttpTransport transport, com.google.api.client.json.JsonFactory jsonFactory,
        com.google.api.client.http.HttpRequestInitializer httpRequestInitializer) {
      super(
          transport,
          jsonFactory,
          DEFAULT_ROOT_URL,
          DEFAULT_SERVICE_PATH,
          httpRequestInitializer,
          false);
    }

    /** Builds a new instance of {@link MyUser}. */
    @Override
    public MyUser build() {
      return new MyUser(this);
    }

    @Override
    public Builder setRootUrl(String rootUrl) {
      return (Builder) super.setRootUrl(rootUrl);
    }

    @Override
    public Builder setServicePath(String servicePath) {
      return (Builder) super.setServicePath(servicePath);
    }

    @Override
    public Builder setHttpRequestInitializer(com.google.api.client.http.HttpRequestInitializer httpRequestInitializer) {
      return (Builder) super.setHttpRequestInitializer(httpRequestInitializer);
    }

    @Override
    public Builder setApplicationName(String applicationName) {
      return (Builder) super.setApplicationName(applicationName);
    }

    @Override
    public Builder setSuppressPatternChecks(boolean suppressPatternChecks) {
      return (Builder) super.setSuppressPatternChecks(suppressPatternChecks);
    }

    @Override
    public Builder setSuppressRequiredParameterChecks(boolean suppressRequiredParameterChecks) {
      return (Builder) super.setSuppressRequiredParameterChecks(suppressRequiredParameterChecks);
    }

    @Override
    public Builder setSuppressAllChecks(boolean suppressAllChecks) {
      return (Builder) super.setSuppressAllChecks(suppressAllChecks);
    }

    /**
     * Set the {@link MyUserRequestInitializer}.
     *
     * @since 1.12
     */
    public Builder setMyUserRequestInitializer(
        MyUserRequestInitializer myuserRequestInitializer) {
      return (Builder) super.setGoogleClientRequestInitializer(myuserRequestInitializer);
    }

    @Override
    public Builder setGoogleClientRequestInitializer(
        com.google.api.client.googleapis.services.GoogleClientRequestInitializer googleClientRequestInitializer) {
      return (Builder) super.setGoogleClientRequestInitializer(googleClientRequestInitializer);
    }
  }
}

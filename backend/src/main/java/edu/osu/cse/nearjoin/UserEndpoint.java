package edu.osu.cse.nearjoin;


/**
 * Created by Fish on 10/31/2014.
 */

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;

import java.util.List;
import java.util.logging.Logger;

import javax.inject.Named;

import static edu.osu.cse.nearjoin.OfyService.ofy;

/**
 * A registration endpoint class we are exposing for a device's GCM registration id on the backend
 *
 * For more information, see
 * https://developers.google.com/appengine/docs/java/endpoints/
 *
 * NOTE: This endpoint does not use any form of authorization or
 * authentication! If this app is deployed, anyone can access this endpoint! If
 * you'd like to add authentication, take a look at the documentation.
 */
@Api(name = "myUser", version = "v1", namespace = @ApiNamespace(
        ownerDomain = "eventBackend.nearjoin.cse.ohio_state.edu",
        ownerName = "eventBackend.nearjoin.cse.ohio_state.edu",
        packagePath=""))
public class UserEndpoint {

    private static final Logger log = Logger.getLogger(UserRecord.class.getName());

    /**
     * Register a device to the backend
     *
     * @param name The Google Cloud Messaging registration Id to add
     */
    @ApiMethod(name = "add")
    public void addUser(@Named("name") String name) {
        if(findUser(name) != null) {
            log.info("User " + name + " already registered, skipping register");
            return;
        }
        UserRecord user = new UserRecord();
        user.setName(name);
        ofy().save().entity(user).now();


    }

    @ApiMethod(name = "update")
    public void updateUser(@Named("name") String name) {
        UserRecord user = findUser(name);
        if( user == null) {
            log.info("User " + name + " not registered, add it");
            UserRecord new_user = new UserRecord();
            new_user.setName(name);
            ofy().save().entity(new_user).now();
        }
        else {
            user.setName(name);
            ofy().save().entity(user).now();
        }
    }

    /**
     * Unregister a device from the backend
     *
     * @param name The Google Cloud Messaging registration Id to remove
     */
    @ApiMethod(name = "delete")
    public void deleteUser(@Named("name") String name) {
        UserRecord user = findUser(name);
        if(user == null) {
            log.info("User " + name + " not registered, skipping unregister");
            return;
        }
        ofy().delete().entity(user).now();
    }

    /**
     * Return a collection of registered devices
     *
     * @param count The number of devices to list
     * @return a list of Google Cloud Messaging registration Ids
     */
    @ApiMethod(name = "listUsers")
    public CollectionResponse<UserRecord> listDevices(@Named("count") int count) {
        List<UserRecord> users = ofy().load().type(UserRecord.class).limit(count).list();
        return CollectionResponse.<UserRecord>builder().setItems(users).build();
    }

    private UserRecord findUser(String name) {
        return ofy().load().type(UserRecord.class).filter("name", name).first().now();
    }

}

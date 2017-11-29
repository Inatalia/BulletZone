package edu.unh.cs.cs619_2015_project2.g5.rest;

import edu.unh.cs.cs619_2015_project2.g5.util.BooleanWrapper;
import edu.unh.cs.cs619_2015_project2.g5.util.GridWrapper;
import edu.unh.cs.cs619_2015_project2.g5.util.LongWrapper;

import org.androidannotations.annotations.rest.Delete;
import org.androidannotations.annotations.rest.Get;
import org.androidannotations.annotations.rest.Post;
import org.androidannotations.annotations.rest.Put;
import org.androidannotations.annotations.rest.Rest;
import org.androidannotations.api.rest.RestClientErrorHandling;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestClientException;

/**
 * BulletZoneRestClient Interface
 * Created by rabbit on 10/21/15.
 */
@Rest(rootUrl = "http://stman1.cs.unh.edu:6191/games",
        converters = {StringHttpMessageConverter.class, MappingJackson2HttpMessageConverter.class}
)
public interface BulletZoneRestClient extends RestClientErrorHandling {
    void setRootUrl(String rootUrl);

    /**
     * Joins server to play the game
     * @return LongWrapper
     * @throws RestClientException
     */
    @Post("")
    LongWrapper join() throws RestClientException;

    /**
     * Returns the grid of field
     * @return GridWrapper grid or field
     */
    @Get("")
    GridWrapper grid();

    /**
     * Moves the tank
     * @param tankId tank's id
     * @param direction move to direction
     * @return BooleanWrapper
     */
    @Put("/{tankId}/move/{direction}")
    BooleanWrapper move(long tankId, byte direction);

    /**
     * Turns the tank
     * @param tankId tank's id
     * @param direction turn to direction
     * @return BooleanWrapper
     */
    @Put("/{tankId}/turn/{direction}")
    BooleanWrapper turn(long tankId, byte direction);

    /**
     * Shoots bullte
     * @param tankId tank's id
     * @return BooleanWrapper
     */
    @Put("/{tankId}/fire")
    BooleanWrapper fire(long tankId);

    /**
     * Leaves the game
     * @param tankId tank's id
     * @return BooleanWrapper
     */
    @Delete("/{tankId}/leave")
    BooleanWrapper leave(long tankId);


}


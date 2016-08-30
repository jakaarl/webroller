package webroller.handlers;

import static webroller.Constants.JSON_CONTENT_TYPE;
import static webroller.TestConstants.TEST_ROOM;

import org.junit.Test;

import io.vertx.core.http.HttpHeaders;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import webroller.AbstractVertxTestCase;
import webroller.Room;
import webroller.json.JsonConverter;

public class RoomCreationHandlerTest extends AbstractVertxTestCase {

	@Test
	public void createsRoom(TestContext testContext) {
		JsonObject json = new JsonObject();
		json.put("room", JsonConverter.json(TEST_ROOM));
		
		Async async = testContext.async();
		client.post(launcher.getPort(), "localhost", "/rooms", response -> {
			testContext.assertEquals(200, response.statusCode());
			response.bodyHandler(buffer -> {
				Room created = JsonConverter.room(buffer.toJsonObject());
				testContext.assertEquals(TEST_ROOM.name, created.name);
			});
			async.complete();
		})
		.putHeader(HttpHeaders.ACCEPT, JSON_CONTENT_TYPE)
		.putHeader(HttpHeaders.CONTENT_TYPE, JSON_CONTENT_TYPE)
		.end(json.encode());
	}
}

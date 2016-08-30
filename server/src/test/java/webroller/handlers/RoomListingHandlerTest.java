package webroller.handlers;

import static webroller.TestConstants.SECRET_TEST_ROOM;
import static webroller.TestConstants.TEST_ROOM;

import org.junit.Test;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpClientRequest;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import webroller.AbstractVertxTestCase;
import webroller.RoomDirectory;
import webroller.TestLauncher;

public class RoomListingHandlerTest extends AbstractVertxTestCase {
	
	private RoomDirectory roomDirectory;
	
	protected TestLauncher createLauncher(Vertx vertx) {
		roomDirectory = new RoomDirectory();
		return new TestLauncher(vertx, roomDirectory);
	}
	
	@Test
	public void returnsPublicRoom(TestContext testContext) {
		roomDirectory.addRoom(TEST_ROOM);
		HttpClientRequest request = client.get(launcher.getPort(), "localhost", "/rooms");
		Async async = testContext.async();
		request.handler(response -> {
			testContext.assertEquals(200, response.statusCode());
			response.bodyHandler(buffer -> {
				JsonObject json = buffer.toJsonObject();
				JsonArray rooms = json.getJsonArray("rooms");
				testContext.assertEquals(1, rooms.size());
			});
			async.complete();
		});
		request.end();
	}
	
	@Test
	public void omitsSecretRoom(TestContext testContext) {
		roomDirectory.addRoom(SECRET_TEST_ROOM);
		HttpClientRequest request = client.get(launcher.getPort(), "localhost", "/rooms");
		Async async = testContext.async();
		request.handler(response -> {
			testContext.assertEquals(200, response.statusCode());
			response.bodyHandler(buffer -> {
				JsonObject json = buffer.toJsonObject();
				JsonArray rooms = json.getJsonArray("rooms");
				testContext.assertEquals(0, rooms.size());
			});
			async.complete();
		});
		request.end();
	}

}

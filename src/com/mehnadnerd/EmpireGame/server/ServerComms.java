package com.mehnadnerd.EmpireGame.server;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpServer;
import com.mehnadnerd.EmpireGame.ServerMain;
import com.mehnadnerd.EmpireGame.things.EmpireFleet;
import com.mehnadnerd.EmpireGame.things.EmpireLocationRef;
import com.mehnadnerd.EmpireGame.things.EmpirePlanet;
import com.mehnadnerd.EmpireGame.things.EmpirePlayer;
import com.mehnadnerd.EmpireGame.things.EmpireShip;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class ServerComms {
	private final int port;
	private HttpServer httpServer;
	private final ServerMain server;

	public int getPort() {
		return port;
	}

	public ServerComms(int port, ServerMain server) {
		this.server = server;
		this.port = port;
		try {
			httpServer = HttpServer.create(new InetSocketAddress(port), 0);
		} catch (IOException e) {
			e.printStackTrace();
			// Hack to make crash
			assert(0 > 1);
		}

		// test, to check connection
		httpServer.createContext("/test", new testHandler());
		// download a specific planet
		httpServer.createContext("/planetdl", new planetDLHandler());
		// download a player - for starting a session
		httpServer.createContext("/playerdl", new playerDLHandler());
		// move a fleet
		httpServer.createContext("/move", new moveHandler());
		// fleet - reorganise fleets
		httpServer.createContext("/fleet", new fleetHandler());
		// build - build a new ship
		httpServer.createContext("/build", new buildHandler());
		// colonise- colonise a planet
		httpServer.createContext("/colonise", new coloniseHandler());
		// time - gets the turn number
		httpServer.createContext("/time", new timeHandler());
		// ready -tells that a player is ready
		httpServer.createContext("/ready", new readyHandler());

		httpServer.setExecutor(null);
		httpServer.start();
	}

	static class testHandler implements HttpHandler {

		@Override
		public void handle(HttpExchange t) throws IOException {
			System.out.println("Ping: " + t.getRemoteAddress());
			String response = "EmpireGame Test, Turtwig is the best!\n";
			t.sendResponseHeaders(200, response.length());
			OutputStream os = t.getResponseBody();
			os.write(response.getBytes());
			os.close();
		}
	}

	// Request headers:
	// EmpireThingName = Name of thing requesting
	//
	// EmpireThingLoc = Location of thing requesting
	//
	// RequestingPlayer = Name of Player Requesting
	//
	// EmpireThingData = Extra data, ex. for building or similar
	class planetDLHandler implements HttpHandler {
		@Override
		public void handle(HttpExchange t) throws IOException {
			System.out.println("PlanetDL from: " + t.getRemoteAddress());
			EmpirePlanet toSend;
			if (t.getRequestHeaders().containsKey("EmpireThingLoc")) {
				toSend = planetLookup(EmpireLocationRef.stringToRef(t.getRequestHeaders().getFirst("EmpireThingLoc")));
			} else if (t.getRequestHeaders().containsKey("EmpireThingName")) {
				toSend = server.getPlanetManager().getPlanet(t.getRequestHeaders().getFirst("EmpireThingName"));
			} else {
				toSend = EmpirePlanet.getDefault();
			}
			t.sendResponseHeaders(200, 0);
			ObjectOutputStream os = new ObjectOutputStream(t.getResponseBody());
			os.writeObject(toSend);
			os.close();
			t.close();
		}
	}

	class playerDLHandler implements HttpHandler {
		@Override
		public void handle(HttpExchange t) throws IOException {
			System.out.println("PlayerDL from: " + t.getRemoteAddress() + " Requesting Player: "
					+ t.getRequestHeaders().getFirst("EmpireThingName"));
			EmpirePlayer toSend;

			if (t.getRequestHeaders().containsKey("EmpireThingLoc")) {
				toSend = playerLookup(EmpireLocationRef.stringToRef(t.getRequestHeaders().getFirst("EmpireThingLoc")));
			} else if (t.getRequestHeaders().containsKey("EmpireThingName")) {
				toSend = playerLookup(t.getRequestHeaders().getFirst("EmpireThingName"));
			} else {
				System.out.println("No fields provided");
				toSend = EmpirePlayer.getDefault();
			}
			System.out.println("Responding with: " + toSend);
			t.sendResponseHeaders(200, 0);
			ObjectOutputStream os = new ObjectOutputStream(t.getResponseBody());
			os.writeObject(toSend);
			os.close();
			t.close();
		}
	}

	// More Request headers:
	//
	/**
	 * Format for requests to move: url is /move EmpireThingName header will
	 * have name of fleet RequestingPlayer is required EmpireThingLoc will have
	 * destination location Sends back the updated EmpirePlayer - If no
	 * movement, sends back the default EmpirePlayer
	 */
	class moveHandler implements HttpHandler {
		@Override
		public void handle(HttpExchange t) throws IOException {
			System.out.println("Move from " + t.getRemoteAddress());
			EmpirePlayer toSend = null;
			if (t.getRequestHeaders().containsKey("EmpireThingName")
					&& t.getRequestHeaders().containsKey("EmpireThingLoc")
					&& t.getRequestHeaders().containsKey("RequestingPlayer")) {
				// actual code---make toSend meaningful
				toSend = playerLookup(t.getRequestHeaders().getFirst("RequestingPlayer"));
				if (toSend.moveFleet(t.getRequestHeaders().getFirst("EmpireThingName"),
						EmpireLocationRef.stringToRef(t.getRequestHeaders().getFirst("EmpireThingLoc")))) {
					// fleet moved successfully!
					toSend.betterDiscover(planetLookup(
							EmpireLocationRef.stringToRef(t.getRequestHeaders().getFirst("EmpireThingLoc"))));
				} else {
					toSend = EmpirePlayer.getDefault();
				}
				// returning code
				t.sendResponseHeaders(200, 0);
				ObjectOutputStream os = new ObjectOutputStream(t.getResponseBody());
				os.writeObject(toSend);
				os.close();
				t.close();
			} else {
				// Tell client it screwed up
				String response = "Malformed move request";
				t.sendResponseHeaders(400, response.length());
				t.getResponseBody().write(response.getBytes());
				t.close();
			}

		}
	}

	/**
	 * Not working yet
	 * 
	 * @author JSweeney
	 *
	 */
	class fleetHandler implements HttpHandler {
		@Override
		public void handle(HttpExchange t) throws IOException {
			System.out.println("Move from " + t.getRemoteAddress());
			EmpirePlayer toSend = null;
			if (t.getRequestHeaders().containsKey("EmpireThingName")
					&& t.getRequestHeaders().containsKey("EmpireThingName")
					&& t.getRequestHeaders().containsKey("EmpireThingName")) {
				// actual code---make toSend meaningful

				// returning code
				t.sendResponseHeaders(200, 0);
				ObjectOutputStream os = new ObjectOutputStream(t.getResponseBody());
				os.writeObject(toSend);
				os.close();
				t.close();
			} else {
				// Tell client it screwed up
				String response = "Malformed move request";
				t.sendResponseHeaders(400, response.length());
				t.getResponseBody().write(response.getBytes());
				t.close();
			}

		}
	}

	/**
	 * Not working yet
	 * 
	 * @author JSweeney
	 *
	 */
	class buildHandler implements HttpHandler {
		@Override
		public void handle(HttpExchange t) throws IOException {
			System.out.println("Build from " + t.getRemoteAddress());
			System.out.println("Name: " + t.getRequestHeaders().getFirst("EmpireThingName"));
			System.out.println("Data: " + t.getRequestHeaders().getFirst("EmpireThingData"));
			System.out.println("Loc: " + t.getRequestHeaders().getFirst("EmpireThingLoc"));
			System.out.println("Requesting Player: " + t.getRequestHeaders().getFirst("RequestingPlayer"));
			EmpirePlayer toSend = null;
			if (t.getRequestHeaders().containsKey("EmpireThingName")
					&& t.getRequestHeaders().containsKey("EmpireThingLoc")
					&& t.getRequestHeaders().containsKey("RequestingPlayer")
					&& t.getRequestHeaders().containsKey("EmpireThingData")) {
				System.out.println("Request fine, attempting to build");
				// actual code---make toSend meaningful
				toSend = playerLookup(t.getRequestHeaders().getFirst("RequestingPlayer"));
				// check if can build it
				if (toSend
						.isPlanetOwned(server.getPlanetManager()
								.getPlanet(EmpireLocationRef
										.stringToRef(t.getRequestHeaders().getFirst("EmpireThingLoc"))))
						&& toSend.getMetal() > Integer
								.parseInt(t.getRequestHeaders().getFirst("EmpireThingData").split(":")[0])
						&& toSend.getPop() > Integer
								.parseInt(t.getRequestHeaders().getFirst("EmpireThingData").split(":")[1])) {
					System.out.println("Building!");
					// payfor it
					toSend.spendMetal(
							Integer.parseInt(t.getRequestHeaders().getFirst("EmpireThingData").split(":")[0]));
					toSend.spendPop(Integer.parseInt(t.getRequestHeaders().getFirst("EmpireThingData").split(":")[1]));
					// build it
					toSend.addFleet(
							new EmpireFleet(EmpireShip.buildShip(t.getRequestHeaders().getFirst("EmpireThingName"),
									Integer.parseInt(t.getRequestHeaders().getFirst("EmpireThingData").split(":")[1]),
									Integer.parseInt(t.getRequestHeaders().getFirst("EmpireThingData").split(":")[0]),
									EmpireLocationRef.stringToRef(t.getRequestHeaders().getFirst("EmpireThingLoc")))));
				} else {
					// failed to build
					System.out.println("Failed to build");
					System.out.println("Owns planet: " + toSend.isPlanetOwned(server.getPlanetManager().getPlanet(
							EmpireLocationRef.stringToRef(t.getRequestHeaders().getFirst("EmpireThingLoc")))));
					System.out.println("Planet might own: " + server.getPlanetManager().getPlanet(
							EmpireLocationRef.stringToRef(t.getRequestHeaders().getFirst("EmpireThingLoc"))));
					System.out.println("Planets does own: " + toSend.getPlayerOwnedPlanets());
					System.out
							.println("Equals?" + server.getPlanetManager()
									.getPlanet(EmpireLocationRef
											.stringToRef(t.getRequestHeaders().getFirst("EmpireThingLoc")))
							.equals(toSend.getPlayerOwnedPlanets().get(0)));
					toSend = EmpirePlayer.getDefault();
				}
				// returning code
				t.sendResponseHeaders(200, 0);
				ObjectOutputStream os = new ObjectOutputStream(t.getResponseBody());
				os.writeObject(toSend);
				os.close();
				t.close();
			} else {
				// Output debug stuff
				System.out.println(t.getRequestHeaders().getFirst("EmpireThingName"));
				System.out.println(t.getRequestHeaders().getFirst("EmpireThingData"));
				System.out.println(t.getRequestHeaders().getFirst("EmpireThingLoc"));
				System.out.println(t.getRequestHeaders().getFirst("RequestingPlayer"));
				// Tell client it screwed up
				String response = "Malformed build request";
				t.sendResponseHeaders(400, response.length());
				t.getResponseBody().write(response.getBytes());
				t.close();
			}

		}
	}

	/**
	 * Probably working! EmpireThingLoc: Loc of planet to colonise
	 * EmpireThingName: New name for planet RequestingPlayer: self-explanatory
	 */
	class coloniseHandler implements HttpHandler {
		@Override
		public void handle(HttpExchange t) throws IOException {
			System.out.println("Colonise from: " + t.getRemoteAddress());
			EmpirePlayer toSend = null;
			if (t.getRequestHeaders().containsKey("EmpireThingLoc")
					&& t.getRequestHeaders().containsKey("EmpireThingName")
					&& t.getRequestHeaders().containsKey("RequestingPlayer")) {
				// actual code---make toSend meaningful
				toSend = playerLookup(t.getRequestHeaders().getFirst("RequestingPlayer"));
				EmpirePlanet toColonise = planetLookup(
						EmpireLocationRef.stringToRef(t.getRequestHeaders().getFirst("EmpireThingLoc")));
				if (toColonise.getOwner().equals(EmpirePlayer.getDefault()) && toSend.isPlanetDiscovered(toColonise)
						&& toSend.spendPop(10)) {
					// colonisation succeeded!
					toColonise.setName(t.getRequestHeaders().getFirst("EmpireThingName"));
					toColonise.setOwner(toSend);
					server.getPlayerManager().refreshAllPlayerPlanets();
				} else {
					toSend = EmpirePlayer.getDefault();
				}
				// returning code
				t.sendResponseHeaders(200, 0);
				ObjectOutputStream os = new ObjectOutputStream(t.getResponseBody());
				os.writeObject(toSend);
				os.close();
				t.close();
			} else {
				// Tell client it screwed up
				String response = "Malformed colonise request";
				t.sendResponseHeaders(400, response.length());
				t.getResponseBody().write(response.getBytes());
				t.close();
			}

		}
	}

	class timeHandler implements HttpHandler {
		@Override
		public void handle(HttpExchange t) throws IOException {
			System.out.println("Time from " + t.getRemoteAddress());
			String response = "" + server.getTimeTab().getTurnNumber();
			t.sendResponseHeaders(200, response.length());
			OutputStream os = t.getResponseBody();
			os.write(response.getBytes());
			os.close();
		}
	}

	/**
	 * Should be finished-full reload
	 * 
	 * @author JSweeney
	 *
	 */
	class readyHandler implements HttpHandler {
		@Override
		public void handle(HttpExchange t) throws IOException {
			System.out.println("Ready from " + t.getRemoteAddress());
			EmpirePlayer toSend = null;

			if (t.getRequestHeaders().containsKey("RequestingPlayer")) {
				toSend = playerLookup(t.getRequestHeaders().getFirst("RequestingPlayer"));
				// actual code---make toSend meaningful
				toSend.setTurnFinished(true);
				// returning code
				t.sendResponseHeaders(200, 0);
				ObjectOutputStream os = new ObjectOutputStream(t.getResponseBody());
				os.writeObject(toSend);
				os.close();
				t.close();
			} else {
				// Tell client it screwed up
				String response = "Malformed ready request";
				t.sendResponseHeaders(400, response.length());
				t.getResponseBody().write(response.getBytes());
				t.close();
			}

		}
	}

	private EmpirePlayer playerLookup(String name) {
		return server.getPlayerManager().getPlayer(name);
	}

	private EmpirePlayer playerLookup(EmpireLocationRef loc) {
		return server.getPlayerManager().getPlayer(loc);
	}

	private EmpirePlanet planetLookup(EmpireLocationRef loc) {
		return server.getPlanetManager().getPlanet(loc);
	}
}
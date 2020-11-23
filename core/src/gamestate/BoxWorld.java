package gamestate;

import java.util.ArrayList;
import java.util.Iterator;
import java.lang.Class;
import java.util.Collections;

import net.dermetfan.gdx.physics.box2d.Box2DMapObjectParser;
import utilities.CharacterGeneration;
import utilities.OrthogonalCustomRenderer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.QueryCallback;
import com.badlogic.gdx.physics.box2d.World;
import com.orangeegames.suikorm.SuikodenRM;

import entities.AbstractChest;
import entities.Collider;
import entities.Door;
import entities.DrawableBox2D;
import entities.GameWorldCharacter;
import entities.Player;
import entities.Spawn;
import entities.StoryScene;
import entities.Conversation;
import entities.Barrel;

public class BoxWorld extends GameState {

	World world;
	public OrthogonalCustomRenderer mapRenderer;
	//public OrthogonalTiledMapRenderer mapRenderer;
	Box2DDebugRenderer box2drenderer;
	public OrthographicCamera camera;
	Player player;
	ArrayList<TiledMapTileLayer> foregrounds, backgrounds, objectLayers;
	ArrayList<DrawableBox2D> drawableBoxes;
	ArrayList<GameWorldCharacter> characters;
    ArrayList<Collider> colliders;
    StoryScene currentScene;
	Conversation currentConversation;
    public String musicTrackName;

	
	boolean disposeThis = false;
	
	float rotation = 0;
	float zoom = 0.75f;
	
	Door fromDoor;
	Door nextDoor;
	public ArrayList<Door> walkableDoors;
	public ArrayList<Spawn> mapSpawns;
	public static short PLAYER = 0x0001;
	public static short DOOR = 0x0002;
    public static short OBJECT = 0x0004;
	
    Body bodyToDestroy;
	Door swapDoor;
	
	public BoxWorld(Door fromDoor) {
        System.out.println("New boxworld");
		this.fromDoor = fromDoor;
		
		foregrounds = new ArrayList<TiledMapTileLayer>();
		backgrounds = new ArrayList<TiledMapTileLayer>();
		objectLayers = new ArrayList<TiledMapTileLayer>();

		drawableBoxes = new ArrayList<DrawableBox2D>();

		characters = new ArrayList<GameWorldCharacter>();
        colliders = new ArrayList<Collider>();
		
		walkableDoors = new ArrayList<Door>();
		
		mapSpawns = new ArrayList<Spawn>();
		
		initiate(fromDoor);
	}
	
	
	
	public void initiate(Door door) {
		world = new World(new Vector2(0, 0), true);
		camera = new OrthographicCamera();
		TiledMap map = new TmxMapLoader().load("maps/" + door.toMapName + ".tmx");
		Box2DMapObjectParser parser = new Box2DMapObjectParser(SuikodenRM.scale);

		parser.load(world, map);

        this.musicTrackName = (String) map.getProperties().get("musicTrackName");
		
		Iterator<MapLayer> mlIterator = map.getLayers().iterator();
		while(mlIterator.hasNext()) {
			MapLayer ml = mlIterator.next();
			
			String layerTypeString = ml.getName();
		
			if(layerTypeString.equals("foreground")) {
				foregrounds.add((TiledMapTileLayer) ml);
			}
			if(layerTypeString.equals("background")) {
				backgrounds.add((TiledMapTileLayer) ml);
			}
			if(layerTypeString.contains("2.5")) {
				objectLayers.add((TiledMapTileLayer) ml);
			}
			if(layerTypeString.equals("chests")) {
				Iterator<MapObject> moIterator = ml.getObjects().iterator();
				while(moIterator.hasNext()) {
					RectangleMapObject mo = (RectangleMapObject) moIterator.next();
					AbstractChest ac = new AbstractChest(false, this, (mo.getRectangle().x + mo.getRectangle().width/2)*SuikodenRM.scale, (mo.getRectangle().y + mo.getRectangle().height/2)*SuikodenRM.scale);
					drawableBoxes.add(ac);
					//AbstractChest ac = new AbstractChest(false, this, 180, 180);
				}
			}
			if(layerTypeString.equals("objects")) {
				Iterator<MapObject> moIterator = ml.getObjects().iterator();
				while(moIterator.hasNext()) {
					RectangleMapObject mo = (RectangleMapObject) moIterator.next();
                    String objectName = mo.getName();
                    if(objectName.equals("Barrel")) {
                        Barrel b = new Barrel(
                            this,
                            (mo.getRectangle().x + mo.getRectangle().width/2)*SuikodenRM.scale,

                            (mo.getRectangle().y + mo.getRectangle().height/2)*SuikodenRM.scale
                        );
                        drawableBoxes.add(b);
                    }
				}
			}
            if(layerTypeString.equals("colliders")) {
				Iterator<MapObject> moIterator = ml.getObjects().iterator();
				while(moIterator.hasNext()) {
					RectangleMapObject mo = (RectangleMapObject) moIterator.next();
					
					String message = (String) mo.getProperties().get("message");
					PolygonShape ps = new PolygonShape();
					ps.setAsBox((mo.getRectangle().width/2)*SuikodenRM.scale, (mo.getRectangle().height/2)*SuikodenRM.scale);
					FixtureDef fixture = new FixtureDef();
					fixture.shape = ps;
					BodyDef bodyDef = new BodyDef();
					bodyDef.position.set(new Vector2((mo.getRectangle().x + mo.getRectangle().width/2)*SuikodenRM.scale, (mo.getRectangle().y + mo.getRectangle().height/2)*SuikodenRM.scale));
					Body body = world.createBody(bodyDef);
					
					body.createFixture(fixture);
					Collider newCollider = new Collider(message);
					body.setUserData(newCollider);
				}
            }
			if(layerTypeString.equals("doors")) {
				Iterator<MapObject> moIterator = ml.getObjects().iterator();
				while(moIterator.hasNext()) {
					RectangleMapObject mo = (RectangleMapObject) moIterator.next();
					
					String toName = (String) mo.getProperties().get("toName");
					Integer toNumber = Integer.parseInt((String) mo.getProperties().get("toSpawnNumber"));
					PolygonShape ps = new PolygonShape();
					ps.setAsBox((mo.getRectangle().width/2)*SuikodenRM.scale, (mo.getRectangle().height/2)*SuikodenRM.scale);
					FixtureDef fixtureDoor = new FixtureDef();
					fixtureDoor.filter.categoryBits = DOOR;
					fixtureDoor.filter.maskBits = PLAYER;
					fixtureDoor.isSensor = true;
					fixtureDoor.shape = ps;
					BodyDef doorBodyDef = new BodyDef();
					doorBodyDef.position.set(new Vector2((mo.getRectangle().x + mo.getRectangle().width/2)*SuikodenRM.scale, (mo.getRectangle().y + mo.getRectangle().height/2)*SuikodenRM.scale));
					Body doorBody = world.createBody(doorBodyDef);
					
					doorBody.createFixture(fixtureDoor);
					Door newDoor = new Door(toName, toNumber);
					doorBody.setUserData(newDoor);
				}
			}
			if(layerTypeString.equals("scripts")) {
				Iterator<MapObject> moIterator = ml.getObjects().iterator();
				while(moIterator.hasNext()) {
					RectangleMapObject mo = (RectangleMapObject) moIterator.next();
					
					String convoName = (String) mo.getProperties().get("convoName");
                    int triggerMask = mo.getProperties().get("triggerMask", (int)PLAYER, Integer.class);
					PolygonShape ps = new PolygonShape();
					ps.setAsBox((mo.getRectangle().width/2)*SuikodenRM.scale, (mo.getRectangle().height/2)*SuikodenRM.scale);
					FixtureDef fixtureScript = new FixtureDef();
					fixtureScript.filter.categoryBits = DOOR;
					fixtureScript.filter.maskBits = (short)triggerMask;
					fixtureScript.isSensor = true;
					fixtureScript.shape = ps;
					BodyDef scriptBodyDef = new BodyDef();
					scriptBodyDef.position.set(new Vector2((mo.getRectangle().x + mo.getRectangle().width/2)*SuikodenRM.scale, (mo.getRectangle().y + mo.getRectangle().height/2)*SuikodenRM.scale));
					Body scriptBody = world.createBody(scriptBodyDef);
					
					scriptBody.createFixture(fixtureScript);
                    String onlyTriggerIfScript = (String) mo.getProperties().get("onlyTriggerIfScript");
					Conversation scene = new Conversation(convoName, onlyTriggerIfScript);
					scriptBody.setUserData(scene);
				}
			}
			if(layerTypeString.equals("spawns")) {
				Iterator<MapObject> moIterator = ml.getObjects().iterator();
				while(moIterator.hasNext()) {
					RectangleMapObject mo = (RectangleMapObject) moIterator.next();
					
					int spawnNumber = mo.getProperties().get("spawnNumber", Integer.class);
					
					float x = mo.getRectangle().x*SuikodenRM.scale;
					float y = mo.getRectangle().y*SuikodenRM.scale;
					
					mapSpawns.add(new Spawn(spawnNumber, x, y));
				}
			}
			if(layerTypeString.equals("characters")) {
				Iterator<MapObject> moIterator = ml.getObjects().iterator();
				while(moIterator.hasNext()) {
					RectangleMapObject mo = (RectangleMapObject) moIterator.next();
					
					float x = (mo.getRectangle().x + mo.getRectangle().width/2)*SuikodenRM.scale;
					float y = (mo.getRectangle().y + mo.getRectangle().height/2)*SuikodenRM.scale;
					
					GameWorldCharacter gc = CharacterGeneration.getWorldCharacter(
                        (String) mo.getProperties().get("character"),
                        this,
                        x,
                        y
                    );
                    gc.setName(mo.getName());
                    Integer start = mo.getProperties().get("startMessage", Integer.class);
                    Integer end = mo.getProperties().get("stopMessage", Integer.class);
                    if(start != null && end != null) {
					    gc.setMessage(start, end);
                    }
                    String message = mo.getProperties().get("message", String.class);
                    if(message != null) {
                        gc.setMessage(message);
                    }
                    gc.setPhases((String) mo.getProperties().get("phases"));
                    Float speed = mo.getProperties().get("speed", Float.class);
                    if(speed != null) {
                        gc.setMaxSpeed(speed);
                    }
                    String direction = mo.getProperties().get("face", String.class);
                    if(direction != null) {
                        gc.setDirection(direction);
                    }
                    String convoName = mo.getProperties().get("convoName", String.class);
                    if(convoName != null) {
                        gc.setConversation(new Conversation(convoName, null));
                    }
                    boolean hidden = mo.getProperties().get("hidden", false, Boolean.class);
                    if(hidden) {
                        gc.hide();
                    }
					drawableBoxes.add(gc);
					characters.add(gc);
				}
			}
			
		}
		
		BodyDef playerDef = new BodyDef();
		playerDef.type = BodyDef.BodyType.DynamicBody;
		float playerX = 0;
		float playerY = 0;
		for(Spawn spawn : mapSpawns) {
			if(fromDoor.toSpawnNumber == spawn.spawnNumber) {
				playerX = spawn.x;
				playerY = spawn.y;
				break;
			}
		}
		playerDef.position.set(new Vector2(playerX*SuikodenRM.scale, playerY*SuikodenRM.scale));

		Body body = world.createBody(playerDef);
		
		
		Vector2[] vec = new Vector2[4];
		vec[0] = new Vector2(7.8f*SuikodenRM.scale, 7.8f*SuikodenRM.scale);
		vec[1] = new Vector2(0f*SuikodenRM.scale, 3.9f*SuikodenRM.scale);
		vec[2] = new Vector2(7.8f*SuikodenRM.scale, 0f*SuikodenRM.scale);
		vec[3] = new Vector2(15.6f*SuikodenRM.scale, 3.9f*SuikodenRM.scale);
		
		PolygonShape shape = new PolygonShape();
		shape.set(vec);

		FixtureDef fd = new FixtureDef();
		fd.filter.categoryBits = PLAYER;
		fd.shape=shape;
		body.createFixture(fd);
		shape.dispose();
		
		player = new Player(body);
        drawableBoxes.add(player);
		
		world.setContactListener(new ContactListener() {

			@Override
			public void beginContact(Contact contact) {
				if(SuikodenRM.debug) {
					System.out.println("Contact");
				}
				
				Body bodyA = contact.getFixtureA().getBody();
				Body bodyB = contact.getFixtureB().getBody();
				
				if(bodyA.getUserData() instanceof Door || bodyB.getUserData() instanceof Door) {
					Door door;
					if(bodyA.getUserData() instanceof Door) {
						door = (Door) bodyA.getUserData();
					} else {
						door = (Door) bodyB.getUserData();
					}
					disposeThis = true;
					swapDoor = door;
				} else if (bodyA.getUserData() instanceof Conversation || bodyB.getUserData() instanceof Conversation) {
                    if(currentConversation != null) {
                        return;
                    }
                    if(bodyA.getUserData() instanceof Conversation) {
                        currentConversation = (Conversation) bodyA.getUserData();
                        bodyToDestroy = bodyA;
                    } else {
                        currentConversation = (Conversation) bodyB.getUserData();
                        bodyToDestroy = bodyB;
                    }
                    if(
                        !currentConversation.getTriggers().isEmpty() &&
                        Collections.disjoint(currentConversation.getTriggers(), SuikodenRM.gsm.getCompletedScripts())
                    ) {
                        bodyToDestroy = null;
                        currentConversation = null;
                        return;
                    }
                    SuikodenRM.gsm.triggerConversation(currentConversation);
                }
			}
			@Override
			public void endContact(Contact contact) {}
			@Override
			public void preSolve(Contact contact, Manifold oldManifold) {}
			@Override
			public void postSolve(Contact contact, ContactImpulse impulse) {}
		});
		
		mapRenderer = new OrthogonalCustomRenderer(map);
		//mapRenderer = new OrthogonalTiledMapRenderer(map);
		box2drenderer = new Box2DDebugRenderer();
		box2drenderer.setDrawAABBs(true);
	}

    public void triggerConversation(Conversation conversation) {
        currentConversation = conversation;
        currentConversation.initialize(characters, player);
    }
	
	@Override
	public void show() {
		
	}

	@Override
	public void dispose() {

		mapRenderer.dispose();
		box2drenderer.dispose();
		world.dispose();		
	
	}

	@Override
	public void hide() {
		if(disposeThis) {
			dispose();
		} else {
			pause();
		}
	}

	@Override
	public void pause() {
		// Do nothing, right now. Implement for Android.
	}
	

	@Override
	public void render(float delta) {
		if(!disposeThis) {
			Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
			
			if(!GameStateManager.PAUSED) {
                if(bodyToDestroy != null) {
                    world.destroyBody(bodyToDestroy);
                    bodyToDestroy = null;
                }
				world.step(1 / 60f, 8, 3);
				player.update2(delta);
                if(currentConversation != null) {
                    currentConversation.update(delta);
                    if(currentConversation.isOver()) {
                        currentConversation = null;
                    }
                }
				for(GameWorldCharacter gc : characters) {
					//gc.update(delta);
				}
                for(DrawableBox2D db2d : drawableBoxes) {
                    db2d.update(delta);
                }
			}
			
			//camera.zoom = 5;
			camera.position.set(player.getBody().getPosition().x + 7.9f*SuikodenRM.scale, player.getBody().getPosition().y - 50*SuikodenRM.scale, 0);
			camera.update();
			
			camera.rotate(rotation);
			camera.zoom = zoom;
			
			
			mapRenderer.setView(camera);
			mapRenderer.getBatch().begin();
			
			for(TiledMapTileLayer background : backgrounds) {
				mapRenderer.renderTileLayer(background);
			}
			
            mapRenderer.renderTileLayer(objectLayers, drawableBoxes);

			for(TiledMapTileLayer foreground : foregrounds) {
				mapRenderer.renderTileLayer(foreground);
			}
			
			mapRenderer.getBatch().end();
			if(SuikodenRM.debug) {
				box2drenderer.render(world, camera.combined);
			}
		} else {
			SuikodenRM.gsm.changeWorld(swapDoor);
		}
	}

	@Override
	public void resize(int width, int height) {
		camera.viewportWidth = width/2*SuikodenRM.scale;
		camera.viewportHeight = height/2*SuikodenRM.scale;
		camera.update();
		
	}

	@Override
	public void resume() {
		Input input = Gdx.input;
		if(input.isKeyPressed(Keys.W)) {
			player.setUp(true);
		} else {
			player.setUp(false);
		}
		if(input.isKeyPressed(Keys.A)) {
			player.setLeft(true);
		} else {
			player.setLeft(false);
		}
		if(input.isKeyPressed(Keys.S)) {
			player.setDown(true);
		} else {
			player.setDown(false);
		}
		if(input.isKeyPressed(Keys.D)) {
			player.setRight(true);
		} else {
			player.setRight(false);
		}
	}

	@Override
	public void init() {
		
	}

	@Override
	public void update(float delta) {
		if(SuikodenRM.debug) System.out.println("update");
	}

	@Override
	public void keyPressed(int k) {
		if(!disposeThis) {
			if(k == Keys.W) player.setUp(true);
			if(k == Keys.A) player.setLeft(true);
			if(k == Keys.S) player.setDown(true);
			if(k == Keys.D) player.setRight(true);
			if(k == Keys.ESCAPE) {
				SuikodenRM.gsm.setPauseState();
			}
			if(k == Keys.Q) {
				QueryCallback callback = new QueryCallback() {
					@Override
					public boolean reportFixture(Fixture fixture) {
						if(SuikodenRM.debug) {
							System.out.println("Fixture");
						}
						if(fixture.getBody().getUserData() instanceof DrawableBox2D) {
							DrawableBox2D db2d = ((DrawableBox2D) fixture.getBody().getUserData());
							db2d.interact(player);
							return true;
						}
                        if(fixture.getBody().getUserData() instanceof Collider) {
                            Collider col = ((Collider) fixture.getBody().getUserData());
                            col.interact(player);
                            return true;
                        }
                        //SuikodenRM.gsm.setInfo("Test message");
						return true;
					}
				};
			
				float xPos = player.getBody().getPosition().x;
				float yPos = player.getBody().getPosition().y;
				float viewDistance = 10f * SuikodenRM.scale;
				if(player.faceUp()) {
					world.QueryAABB(callback, 
							xPos, 
							yPos+6f*SuikodenRM.scale, 
							xPos+15.6f*SuikodenRM.scale, 
							yPos+7.8f*SuikodenRM.scale + viewDistance);
				}
				
				else if(player.faceDown()) {
					world.QueryAABB(callback, 
							xPos, 
							yPos-viewDistance, 
							xPos+15.6f*SuikodenRM.scale, 
							yPos);
				}
				else if(player.faceLeft()) {
					world.QueryAABB(callback, 
							xPos-viewDistance, 
							yPos, 
							xPos, 
							yPos+7.8f*SuikodenRM.scale);
				}
				else if(player.faceRight()) {
					world.QueryAABB(callback, 
							xPos+15.6f*SuikodenRM.scale, 
							yPos, 
							xPos+15.6f*SuikodenRM.scale + viewDistance, 
							yPos+7.8f*SuikodenRM.scale);
				}
			}
			if(k == Keys.O) {
				SuikodenRM.debug = !SuikodenRM.debug;
			}
			if(k == Keys.G) {
				SuikodenRM.gsm.setFightState();
			}
		}
	}

	@Override
	public void keyReleased(int k) {
		if(!disposeThis) {
			if(k == Keys.W) player.setUp(false);
			if(k == Keys.A) player.setLeft(false);
			if(k == Keys.S) player.setDown(false);
			if(k == Keys.D) player.setRight(false);
			if(k == Keys.LEFT) camera.rotate(10f, 0, 1, 0);
			if(k == Keys.UP) camera.rotate(10f, 1, 0, 0);
			if(k == Keys.DOWN) camera.rotate(10f, -1, 0, 0);
			if(k == Keys.RIGHT) camera.rotate(10f, 0, -1, 0);
			if(k == Keys.PLUS) zoom -= 0.1f;
			if(k == Keys.MINUS) zoom += 0.1f;
			if(k == Keys.COMMA) rotation += 1.0f;
			if(k == Keys.PERIOD) rotation -= 1.0f;
		}
	}

	@Override
	public void touchDown(int screenX, int screenY, int pointer, int button) {
		
	}

	@Override
	public void touchUp(int screenX, int screenY, int pointer, int button) {
		
	}
	
	public World getWorld() {
		return world;
	}

    public Player getPlayer() {
        return player;
    }



	@Override
	public void touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		
	}
	
}

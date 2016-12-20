package org.pccs.sandbox.Screens;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

import Entities.Player;
import box2dLight.RayHandler;
import net.dermetfan.gdx.physics.box2d.Box2DMapObjectParser;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.Screen;
/**
 * Created by JacobM on 11/1/16.
 */

public class JumperScreen implements Screen {
	private float width;
	private float height;
	private TiledMap tiledMap;
    private OrthographicCamera camera;
    private TiledMapRenderer tiledMapRenderer;
    private SpriteBatch batch;
    private Player player;
    private World world;
    private Box2DDebugRenderer box2dDebugRenderer;
	private RayHandler rayHandler;
    
    @Override
    public void show() {
    	width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();

		camera = new OrthographicCamera();
        camera.setToOrtho(false, width, height);
        camera.position.set(100, 100, 0);
        camera.update();

        world = new World(new Vector2(0, -9.81f), true);
        box2dDebugRenderer = new Box2DDebugRenderer();
        //tile map
        tiledMap = new TmxMapLoader().load("mapone.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        Box2DMapObjectParser parser = new Box2DMapObjectParser();
        parser.load(world, tiledMap);
        batch = new SpriteBatch();
        //map physics
        rayHandler = new RayHandler(world);
        rayHandler.setCombinedMatrix(camera.combined);
        player = new Player(world, rayHandler);

    }

    @Override
    public void render(float delta) {
    	Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClearColor(1.0f, 0.25f, 0.25f, 0.0f);
        //tiled map render
        if(Gdx.input.isKeyPressed(Input.Keys.A)){
        	player.translateandupdate(-500, 0);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.D)){
        	player.translateandupdate(500, 0);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.W)){
        	player.translateandupdate(0, 500);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.S)){
        	player.translateandupdate(0, -500);
        }
     
        
        camera.position.set(player.getX()+player.getWidth()/2, player.getY()+player.getHeight()/2, 0);
        //player rotation
        float mouseX = Gdx.input.getX()-width/2;
        float mouseY = -Gdx.input.getY()+height-height/2;
        world.step(1/60f, 6, 2);
        box2dDebugRenderer.render(world, camera.combined);
        camera.update();
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        player.updatepos();
        batch.draw(new TextureRegion(player.getTexture()), player.getX(), player.getY(), player.getWidth()/2, player.getHeight()/2, player.getWidth(), player.getHeight(), 1, 1, player.getRotation());
       
        batch.end();
        //rayHandler.setCombinedMatrix(camera.combined);
        //rayHandler.updateAndRender();
    }
    
  
    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}

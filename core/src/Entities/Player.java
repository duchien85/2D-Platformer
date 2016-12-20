package Entities;

import box2dLight.ConeLight;
import box2dLight.PointLight;
import box2dLight.RayHandler;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

public class Player extends Sprite{
	private Texture playerTexture;
	Body body;

    public Player(World world){
    	playerTexture = new Texture("mario.png");
    	this.setTexture(playerTexture);
    	this.setSize(30, 50);
    	this.setOriginCenter();
    	this.setPosition(100, 100);

    	BodyDef bodyDef = new BodyDef();
    	bodyDef.type = BodyType.DynamicBody;
    	bodyDef.position.set(this.getX()+this.getWidth()/2, this.getY()+this.getHeight()/2);
    	body = world.createBody(bodyDef);
    	CircleShape circle = new CircleShape();
    	circle.setRadius(this.getWidth()/2);
    	FixtureDef fixtureDef = new FixtureDef();
    	fixtureDef.shape = circle;
    	fixtureDef.density = 0.5f;
    	fixtureDef.friction = 0.4f;
    	fixtureDef.restitution = 0.0f;
    	Fixture fixture = body.createFixture(fixtureDef);
    	body.setBullet(true);
    	circle.dispose();
    }

    public Player(World world, RayHandler rayHandler){
    	playerTexture = new Texture("mario.png");
    	this.setTexture(playerTexture);
    	this.setSize(64, 64);
    	this.setOriginCenter();
    	this.setPosition(500, 500);

    	BodyDef bodyDef = new BodyDef();
    	bodyDef.type = BodyType.DynamicBody;
    	bodyDef.position.set(this.getX()+this.getWidth()/2, this.getY()+this.getHeight()/2);
    	body = world.createBody(bodyDef);
    	CircleShape circle = new CircleShape();
    	circle.setRadius(this.getWidth()/2);
    	FixtureDef fixtureDef = new FixtureDef();
    	fixtureDef.shape = circle;
    	fixtureDef.density = 0.5f;
    	fixtureDef.friction = 0.4f;
    	fixtureDef.restitution = 0.0f;
    	Fixture fixture = body.createFixture(fixtureDef);
    	body.setBullet(true);
    	circle.dispose();

    	//lights
    	PointLight light = new PointLight(rayHandler, 200, new Color(1,1,1,1), 200 , 100, 100);
        light.attachToBody(body);
        ConeLight conelight = new ConeLight(rayHandler, 200, new Color(1,0.8f,1,1), 500, 100, 100, 90, 30);
        conelight.setSoft(true);
        conelight.setSoftnessLength(400);
        conelight.attachToBody(body);
    }

    public void translateandupdate(float x, float y){
    	//body.setTransform(body.getTransform().getPosition().x+x, body.getTransform().getPosition().y+y, 0);
    	body.setLinearVelocity(x, y);
    	this.setPosition(body.getTransform().getPosition().x-this.getWidth()/2, body.getTransform().getPosition().y-this.getHeight()/2);
    	body.setTransform(body.getTransform().getPosition(), (float) Math.toRadians(this.getRotation()+90));
    }

    public void updatepos(){
    	this.setPosition(body.getTransform().getPosition().x-this.getWidth()/2, body.getTransform().getPosition().y-this.getHeight()/2);
    	body.setTransform(body.getTransform().getPosition(), (float) Math.toRadians(this.getRotation()+90));
    }

    public Texture getTexture(){
    	return playerTexture;
    }

    public void dispose(){
    	playerTexture.dispose();
    }
}

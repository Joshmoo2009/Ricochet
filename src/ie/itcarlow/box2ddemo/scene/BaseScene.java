package ie.itcarlow.box2ddemo.scene;

import ie.itcarlow.box2ddemo.Box2DSpriteCollisions;
import ie.itcarlow.box2ddemo.ResourceManager;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.util.GLState;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public abstract class BaseScene extends Scene
{
	protected Engine engine;
	protected VertexBufferObjectManager vbom;
	protected Box2DSpriteCollisions activity;
	protected Camera camera;
	
	public BaseScene()
	{
		this.engine = ResourceManager.getInstance().engine;
		this.vbom = ResourceManager.getInstance().vbom;
		this.activity = ResourceManager.getInstance().activity;
		this.camera = ResourceManager.getInstance().camera;
	}
	
	protected Sprite createSprite(float x, float y, ITextureRegion region, VertexBufferObjectManager vbom)
	{
		Sprite sprite = new Sprite(x, y, region, vbom) 
		{	
			@Override
			protected void preDraw(GLState glState, Camera camera)
			{
				super.preDraw(glState, camera);
				glState.enableDither();
			}
		};
		return sprite;
	}
	
	public abstract void createScene();
	
	public abstract void onBackPressed();
	
	public abstract void disposeScene();
}

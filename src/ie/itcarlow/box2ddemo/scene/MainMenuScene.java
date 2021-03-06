package ie.itcarlow.box2ddemo.scene;

import ie.itcarlow.box2ddemo.ResourceManager;

import org.andengine.entity.scene.background.Background;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ScaleMenuItemDecorator;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.util.color.Color;

public class MainMenuScene extends BaseScene implements IOnMenuItemClickListener
{
	
	private MenuScene menu;
	private final int MENU_PLAY = 0;
	private final int MENU_EXIT = 1;
	private final int MENU_AUDIO = 2;
	
	public IMenuItem playItem;
	public IMenuItem exitItem;
	
	public IMenuItem audioItem;
	
	@Override
	public void createScene() 
	{
		setBackground(new Background(Color.WHITE));	
		createMenu();
		
		ResourceManager.getInstance().sprBanana = new AnimatedSprite(playItem.getX() - 100, playItem.getY() - 150, ResourceManager.getInstance().regBanana, ResourceManager.getInstance().vbom);
		attachChild(ResourceManager.getInstance().sprBanana);
		 
		ResourceManager.getInstance().sprBanana.animate(100);
	}
	
	@Override
	public void onBackPressed() {
		//System.exit(0);
	}

	@Override
	public void disposeScene() {
		ResourceManager.getInstance().UnloadMenuResources();	
	}
	
	private void createMenu()
	{
		menu = new MenuScene(camera);
		menu.setPosition(0,0);
		playItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_PLAY, ResourceManager.getInstance().play_button_region, vbom), 1.2f, 1);
		exitItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_EXIT, ResourceManager.getInstance().exit_button_region, vbom), 1.2f, 1);
	
		audioItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_AUDIO, ResourceManager.getInstance().audio_button_region, vbom), 1.2f, 1);
		
		menu.addMenuItem(playItem);
		menu.addMenuItem(exitItem);
		menu.addMenuItem(audioItem);
		
		menu.buildAnimations();
		menu.setBackgroundEnabled(false);
		playItem.setPosition(playItem.getX(), playItem.getY() + 20);
		exitItem.setPosition(playItem.getX(), playItem.getY() + 100);
		audioItem.setPosition(10, 10);
		
		menu.setOnMenuItemClickListener(this);
		setChildScene(menu);
	}

	public boolean onMenuItemClicked(MenuScene scene, IMenuItem item, float localX, float localY)
	{
		switch(item.getID())
		{
		case MENU_PLAY:
			if(SceneManager.getInstance().audio_toggle)
				ResourceManager.getInstance().menuClickedSE.play();
			SceneManager.getInstance().setGameScene();
			return true;
		case MENU_EXIT:
			if(SceneManager.getInstance().audio_toggle)
				ResourceManager.getInstance().menuClickedSE.play();
			System.exit(0);
			return true;
		case MENU_AUDIO:
			ResourceManager.getInstance().menuClickedSE.play();
			if(SceneManager.getInstance().audio_toggle)
			{
				SceneManager.getInstance().audio_toggle = false;
				ResourceManager.getInstance().menuBE.pause();
			}
			else
			{
				SceneManager.getInstance().audio_toggle = true;
				ResourceManager.getInstance().menuBE.play();
			}
			SharedPreferencesManager.getInstance(ResourceManager.getInstance().activity).saveMusic(SceneManager.getInstance().audio_toggle);
			return true;
		}
		return false;
	}
}

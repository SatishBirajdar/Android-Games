
// Game application: Developped by Satish & Percy Niclair
// Date Dec 13, 2012
// Beware Chicken Ahead, is a Game developed for Android phone and tablets - 
// Help the chicken to cross the road & hunt the worms.



// This is the Main Menu Activity


package com.hatboy.chicken;

import javax.microedition.khronos.opengles.GL10;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.anddev.andengine.entity.modifier.ScaleAtModifier;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.menu.MenuScene;
import org.anddev.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.anddev.andengine.entity.scene.menu.item.IMenuItem;
import org.anddev.andengine.entity.scene.menu.item.TextMenuItem;
import org.anddev.andengine.entity.scene.menu.item.decorator.ColorMenuItemDecorator;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.entity.util.FPSLogger;
import org.anddev.andengine.opengl.font.Font;
import org.anddev.andengine.opengl.font.FontFactory;
import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.opengl.texture.region.TextureRegionFactory;
import org.anddev.andengine.ui.activity.BaseGameActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;

public class OptionsActivity extends BaseGameActivity implements IOnMenuItemClickListener {
	// ===========================================================
	// Constants
	// ===========================================================

	private static final int CAMERA_WIDTH = 480;
	private static final int CAMERA_HEIGHT = 320;

	protected static final int MENU_MUSIC = 0;
	protected static final int MENU_EFFECTS = MENU_MUSIC + 3;

	// ===========================================================
	// Fields
	// ===========================================================

	protected Camera mCamera;

	protected Scene mMainScene;
	protected Handler mHandler;

	private Texture mMenuBackTexture;
	private TextureRegion mMenuBackTextureRegion;

	protected MenuScene mOptionsMenuScene;
	private TextMenuItem mTurnMusicOff, mTurnMusicOn;
	private TextMenuItem mTurnEffectsOff, mTurnEffectsOn;
	private IMenuItem musicMenuItem;
	private IMenuItem effectsMenuItem;

	private Texture mFontTexture;
	private Font mFont;
	
	public boolean isMusicOn = true;
	public boolean isEffectsOn = true;


	//Override the onLoadEngine

	@Override
	public Engine onLoadEngine() {
		mHandler = new Handler();
		this.mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		return new Engine(new EngineOptions(true, ScreenOrientation.LANDSCAPE, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), this.mCamera));
	}

	//Override the onLoadRessources class 
	@Override
	public void onLoadResources() {
		/* Load Font/Textures. */
		this.mFontTexture = new Texture(256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);

		FontFactory.setAssetBasePath("font/");
		this.mFont = FontFactory.createFromAsset(this.mFontTexture, this, "Flubber.ttf", 20, true, Color.WHITE);
		this.mEngine.getTextureManager().loadTexture(this.mFontTexture);
		this.mEngine.getFontManager().loadFont(this.mFont);

		this.mMenuBackTexture = new Texture(512, 512, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mMenuBackTextureRegion = TextureRegionFactory.createFromAsset(this.mMenuBackTexture, this, "gfx/OptionsMenu/OptionsMenuBk.png", 0, 0);
		this.mEngine.getTextureManager().loadTexture(this.mMenuBackTexture);
	
		mTurnMusicOn = new TextMenuItem(MENU_MUSIC, mFont, " ");
		mTurnMusicOff = new TextMenuItem(MENU_MUSIC, mFont, "");
		mTurnEffectsOn = new TextMenuItem(MENU_EFFECTS, mFont, "CONTINUE");
		mTurnEffectsOff = new TextMenuItem(MENU_EFFECTS, mFont, "CONTINUE");
	}

	//Override the onLoadScene
	@Override
	public Scene onLoadScene() {
		this.mEngine.registerUpdateHandler(new FPSLogger());

		this.createOptionsMenuScene();

		/* Center the background on the camera. */
		final int centerX = (CAMERA_WIDTH - this.mMenuBackTextureRegion.getWidth()) / 2;
		final int centerY = (CAMERA_HEIGHT - this.mMenuBackTextureRegion.getHeight()) / 2;

		this.mMainScene = new Scene(1);
		/* Add the background and static menu */
		final Sprite menuBack = new Sprite(centerX, centerY, this.mMenuBackTextureRegion);
		mMainScene.getLastChild().attachChild(menuBack);
		mMainScene.setChildScene(mOptionsMenuScene);

		return this.mMainScene;
	}

	@Override
	public void onLoadComplete() {
	}

	@Override
	public void onResumeGame() {
		super.onResumeGame();
		mMainScene.registerEntityModifier(new ScaleAtModifier(0.5f, 0.0f, 1.0f, CAMERA_WIDTH/2, CAMERA_HEIGHT/2));
		mOptionsMenuScene.registerEntityModifier(new ScaleAtModifier(0.5f, 0.0f, 1.0f, CAMERA_WIDTH/2, CAMERA_HEIGHT/2));
	}

	// Overide the onMenuClick class  
	@Override
	public boolean onMenuItemClicked(final MenuScene pMenuScene, final IMenuItem pMenuItem, final float pMenuItemLocalX, final float pMenuItemLocalY) {
		switch(pMenuItem.getID()) {
			case MENU_MUSIC:
				if (isMusicOn) {
					isMusicOn = false;
				} else {
					isMusicOn = true;
				}
				mHandler.postDelayed(mLaunchTask,500);									
				return true;
			case MENU_EFFECTS:
				if (isEffectsOn) {
					isEffectsOn = false;
				} else {
					isEffectsOn = true;
					
				}

				mHandler.postDelayed(mLaunchTask,500);
				return true;
			default:
				return false;
		}
	}
	

    private Runnable mLaunchTask = new Runnable() {
        public void run() {
    		Intent myIntent = new Intent(OptionsActivity.this, MainMenuActivity.class);
    		OptionsActivity.this.startActivity(myIntent);
    		OptionsActivity.this.finish();
        }
     };

	// ===========================================================
	// Methods
	// ===========================================================
	
	protected void createOptionsMenuScene() {
		this.mOptionsMenuScene = new MenuScene(this.mCamera);

		if (isMusicOn) {
			musicMenuItem = new ColorMenuItemDecorator( mTurnMusicOff, 0.5f, 0.5f, 0.5f, 1.0f, 0.0f, 0.0f);
		} else {
			musicMenuItem = new ColorMenuItemDecorator( mTurnMusicOn, 0.5f, 0.5f, 0.5f, 1.0f, 0.0f, 0.0f);
		}
		musicMenuItem.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		this.mOptionsMenuScene.addMenuItem(musicMenuItem);

		if (isEffectsOn) {
			effectsMenuItem = new ColorMenuItemDecorator( mTurnEffectsOff, 0.5f, 0.5f, 0.5f, 1.0f, 0.0f, 0.0f);
		} else {
			effectsMenuItem = new ColorMenuItemDecorator( mTurnEffectsOn, 0.5f, 0.5f, 0.5f, 1.0f, 0.0f, 0.0f);			
		}
		effectsMenuItem.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		this.mOptionsMenuScene.addMenuItem(effectsMenuItem);

		this.mOptionsMenuScene.buildAnimations();
		
		this.mOptionsMenuScene.setBackgroundEnabled(false);

		this.mOptionsMenuScene.setOnMenuItemClickListener(this);
	}

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================

}

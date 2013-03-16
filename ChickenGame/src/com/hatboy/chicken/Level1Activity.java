
// Game application: Developped by Satish & Percy Niclair
// Date Dec 13, 2012
// Beware Chicken Ahead, is a Game developed for Android phone and tablets - 
// Help the chicken to cross the road & hunt the worms.



// This is the Level 1 Activity




// Load required library to enable the game
package com.hatboy.chicken;

import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

import javax.microedition.khronos.opengles.GL10;

import org.anddev.andengine.audio.music.Music;
import org.anddev.andengine.audio.music.MusicFactory;
import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.camera.hud.controls.AnalogOnScreenControl;
import org.anddev.andengine.engine.camera.hud.controls.AnalogOnScreenControl.IAnalogOnScreenControlListener;
import org.anddev.andengine.engine.camera.hud.controls.BaseOnScreenControl;
import org.anddev.andengine.engine.handler.IUpdateHandler;
import org.anddev.andengine.engine.handler.physics.PhysicsHandler;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.anddev.andengine.entity.modifier.AlphaModifier;
import org.anddev.andengine.entity.modifier.MoveModifier;
import org.anddev.andengine.entity.modifier.ScaleModifier;
import org.anddev.andengine.entity.modifier.SequenceEntityModifier;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.background.ColorBackground;
import org.anddev.andengine.entity.shape.IShape;
import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.entity.text.ChangeableText;
import org.anddev.andengine.entity.util.FPSLogger;
import org.anddev.andengine.opengl.font.Font;
import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;
import org.anddev.andengine.ui.activity.BaseGameActivity;
import org.anddev.andengine.opengl.texture.region.TextureRegionFactory;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Handler;
import android.widget.Toast;

// The main class extends the BaseGame Activity
public class Level1Activity extends BaseGameActivity {

	
	
	// Set constants related to the camera
	private static final int CAMERA_WIDTH = 480;
	private static final int CAMERA_HEIGHT = 320;
	private static final int ROAD_START = -120;
	// Declare the field variable

	private Camera mCamera;
	private Texture mChickenTexture;
	private TextureRegion mChickenTextureRegion;
	private Texture mTestTexture;
	private Texture mBackgroundTexture;
	private Texture mTruckBlueTexture;
	private Texture mTruckRedTexture;
	private Texture mCarRedTexture;
	private Texture mCarBlueTexture;
	private Texture mWormTexture;
	private Texture mgameOverTexture;
	private Texture mDeadChickenTexture; 
	private Texture mOnScreenControlTexture;
	private TextureRegion mOnScreenControlBaseTextureRegion;
	private TextureRegion mOnScreenControlKnobTextureRegion;
	private TextureRegion mBackgroundTextureRegion;
	private TiledTextureRegion mTruckBlueTextureRegion;
	private TiledTextureRegion mTruckRedTextureRegion;
	private TiledTextureRegion mCarRedTextureRegion;
	private TiledTextureRegion mCarBlueTextureRegion;
	private TiledTextureRegion mWormTextureRegion;
	private TextureRegion mTestTextureRegion;
	private TextureRegion mgameOverTextureRegion;
	private TextureRegion mDeadChickenTextureRegion;

	// The array of sprite
	private AnimatedSprite[] vehicle1 = new AnimatedSprite[500];
	private AnimatedSprite[] vehicle2 = new AnimatedSprite[500];
	private AnimatedSprite[] vehicle3= new AnimatedSprite[500];
	private AnimatedSprite[] vehicle4 = new AnimatedSprite[500];
	private AnimatedSprite[] worm = new AnimatedSprite[500];
	private AnimatedSprite[] deadChicken = new AnimatedSprite[20];

	// the counter for sprite to check collision with the chicken 
	private Handler mHandler;
	private int nDanger1;
	private int nDanger2;
	private int nDanger3;
	private int nDanger4;
	private int nfood;
	private int ndead;
	
	// the indexers variable 
	private int i1;
	private int i2;
	private int i3;
	private int i4;
	private int i5;
	private int d;
	private float deadX=0;
	private float deadY=0;
	
	//The music crash wav file 
	private Music crashMusic;

	private Sprite ChickenPlaying;

	private Texture mFontTexture;
	private Font mFont;

	private ChangeableText score;
	private ChangeableText life;
	private int lives=5;
	private int scoreCount=0;


// Override the onLoadEngine class
	@Override
	public Engine onLoadEngine() {
		// crating the camera
		mHandler = new Handler();
		this.mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		return new Engine(new EngineOptions(true, ScreenOrientation.LANDSCAPE, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), this.mCamera).setNeedsSound(true).setNeedsMusic(true));
	}

	
	
	// override the on load Resources class 
	@Override
	public void onLoadResources() {
		//set the access path for the texture region 
		TextureRegionFactory.setAssetBasePath("gfx/Level1/");

		// load the main background
		mBackgroundTexture = new Texture(512, 512, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		mBackgroundTextureRegion = TextureRegionFactory.createFromAsset(this.mBackgroundTexture, this, "level1bk.png", 0, 0);
		mEngine.getTextureManager().loadTexture(this.mBackgroundTexture);

		mFontTexture = new Texture(256, 256,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);

		mEngine.getTextureManager().loadTexture(mFontTexture);

		// create the texture region 
		mFont = new Font(mFontTexture, Typeface.create(Typeface.DEFAULT,
				Typeface.BOLD), 14, true, Color.RED);
		mEngine.getFontManager().loadFont(mFont);


		// load the chicken 
		mChickenTexture = new Texture(512, 512, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		mChickenTextureRegion = TextureRegionFactory.createFromAsset(this.mChickenTexture, this, "myChicken.png", 0, 0);

		// load the onload contreoller
		mOnScreenControlTexture = new Texture(256, 128, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		mOnScreenControlBaseTextureRegion = TextureRegionFactory.createFromAsset(this.mOnScreenControlTexture, this, "onscreen_control_base.png", 0, 0);
		mOnScreenControlKnobTextureRegion = TextureRegionFactory.createFromAsset(this.mOnScreenControlTexture, this, "onscreen_control_knob.png", 128, 0);

		mEngine.getTextureManager().loadTextures(this.mChickenTexture, this.mOnScreenControlTexture);

		// load the vehicle 
		mTruckBlueTexture = new Texture(512, 256, TextureOptions.DEFAULT);
		mTruckBlueTextureRegion = TextureRegionFactory.createTiledFromAsset(this.mTruckBlueTexture, this, "truckBlueLeft.png", 0, 0, 1, 1);
		mEngine.getTextureManager().loadTexture(this.mTruckBlueTexture);

		mDeadChickenTexture = new Texture(512, 256, TextureOptions.DEFAULT);
		mDeadChickenTextureRegion = TextureRegionFactory.createFromAsset(this.mDeadChickenTexture, this, "chickenDead.png", 0, 0);
		mEngine.getTextureManager().loadTexture(this.mDeadChickenTexture);
		
		
		// load the vehicle 
		mTruckRedTexture = new Texture(512, 256, TextureOptions.DEFAULT);
		mTruckRedTextureRegion = TextureRegionFactory.createTiledFromAsset(this.mTruckRedTexture, this, "truckRedLeft.png", 0, 0, 1, 1);
		mEngine.getTextureManager().loadTexture(this.mTruckRedTexture);

		// load the vehicle 
		mCarRedTexture = new Texture(512, 256, TextureOptions.DEFAULT);
		mCarRedTextureRegion = TextureRegionFactory.createTiledFromAsset(this.mCarRedTexture, this, "carRed.png", 0, 0, 1, 1);
		mEngine.getTextureManager().loadTexture(this.mCarRedTexture);

		// load the vehicle 
		mCarBlueTexture = new Texture(512, 256, TextureOptions.DEFAULT);
		mCarBlueTextureRegion = TextureRegionFactory.createTiledFromAsset(this.mCarBlueTexture, this, "carBlue.png", 0, 0, 1, 1);
		mEngine.getTextureManager().loadTexture(this.mCarBlueTexture);

		// load the worm
		mWormTexture = new Texture(512, 256, TextureOptions.DEFAULT);
		mWormTextureRegion = TextureRegionFactory.createTiledFromAsset(this.mWormTexture, this, "worm1.png", 0, 0, 1, 1);
		mEngine.getTextureManager().loadTexture(this.mWormTexture);

		// load the testing for collision  
		mTestTexture = new Texture(512, 256, TextureOptions.DEFAULT);
		mTestTextureRegion = TextureRegionFactory.createFromAsset(this.mTestTexture, this, "myChicken.png", 0, 0);
		mEngine.getTextureManager().loadTexture(this.mTestTexture);

		// load the gameover screen 
		mgameOverTexture = new Texture(512, 256, TextureOptions.DEFAULT);
		mgameOverTextureRegion = TextureRegionFactory.createFromAsset(this.mgameOverTexture, this, "gameOver.png", 0, 0);
		mEngine.getTextureManager().loadTexture(this.mgameOverTexture);

		//path for the music.
		MusicFactory.setAssetBasePath("mfx/");
		
		//try catch block for music file
		try {
			crashMusic = MusicFactory.createMusicFromAsset(mEngine
					.getMusicManager(), this, "carCrash.wav");
			crashMusic.setLooping(false);
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	

	// Override the onLoadScene class
	@Override
	public Scene onLoadScene() {
		this.mEngine.registerUpdateHandler(new FPSLogger());

		//create the scene
		final Scene scene = new Scene(1);
		//define center of screen for x
		final int centerX = (CAMERA_WIDTH - mBackgroundTextureRegion.getWidth()) / 2;
		//define center of screen for y
		final int centerY = (CAMERA_HEIGHT - mBackgroundTextureRegion.getHeight()) / 2;
		// create new sprite for the screen 
		final Sprite background = new Sprite(centerX, -centerY, mBackgroundTextureRegion);
		// create new sprite for the chicken 
		final Sprite Chicken = new Sprite(220, 270,mChickenTextureRegion);
		// create new sprite for the gameOver 
		final Sprite gameOver = new Sprite(135, 100,mgameOverTextureRegion);
		// create new sprite for the physic handler for velocity 
		final PhysicsHandler physicsHandler = new PhysicsHandler(Chicken);
		
		// register the hanler to control the chicken 
		Chicken.registerUpdateHandler(physicsHandler);
		scene.getLastChild().attachChild(background);
		scene.getLastChild().attachChild(Chicken);
		//	scene.getLastChild().attachChild(Test);

		
		//create the analog control  and position it to the screen 
		final AnalogOnScreenControl analogOnScreenControl = new AnalogOnScreenControl(0, CAMERA_HEIGHT - this.mOnScreenControlBaseTextureRegion.getHeight(), this.mCamera, this.mOnScreenControlBaseTextureRegion, this.mOnScreenControlKnobTextureRegion, 0.1f, 200, new IAnalogOnScreenControlListener() {
			@Override
			public void onControlChange(final BaseOnScreenControl pBaseOnScreenControl, final float pValueX, final float pValueY) {
				physicsHandler.setVelocity(pValueX * 100, pValueY * 100);
			}

			// override the onControl clicl
			@Override
			public void onControlClick(final AnalogOnScreenControl pAnalogOnScreenControl) {
				Chicken.registerEntityModifier(new SequenceEntityModifier(new ScaleModifier(0.25f, 0.75f, 0.75f), new ScaleModifier(0.25f,  0.75f, 0.75f)));


			}
		});
		
		// get the control parameter to for the controller
		analogOnScreenControl.getControlBase().setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		analogOnScreenControl.getControlBase().setAlpha(0.5f);
		analogOnScreenControl.getControlBase().setScaleCenter(0, 128);
		analogOnScreenControl.getControlBase().setScale(1.25f);
		analogOnScreenControl.getControlKnob().setScale(1.25f);
		analogOnScreenControl.refreshControlKnobPosition();

		scene.setChildScene(analogOnScreenControl);
		
		//set  counter cariable for collission 
		nDanger1 = 0;	// the vehicle 
		nDanger2 = 0;	// the vehicle 
		nDanger3 = 0;	// the vehicle 
		nDanger4 = 0;	// the vehicle 
		nfood=0;	// the worm
		ndead=0;	// the blood 
		mHandler.postDelayed(mStartVehicle1,1000);	// load the vehicle 1 with the delay
		mHandler.postDelayed(mStartVehicle2,1000);	// load the vehicle 1
		mHandler.postDelayed(mStartVehicle3,1000);	// load the vehicle 1
		mHandler.postDelayed(mStartVehicle4,1000);	// load the vehicle 1
		mHandler.postDelayed(mStartFood,1000);	// load the worm
		score = new ChangeableText(435, 276, mFont, String.valueOf(scoreCount));
		scene.getLastChild().attachChild(score);	// attach the score screen.
		life = new ChangeableText(435, 291, mFont, String.valueOf(lives));
		scene.getLastChild().attachChild(life);


		scene.registerUpdateHandler(new IUpdateHandler() {
			@Override
			public void reset() { }

			// override the update class
			@Override
			public void onUpdate(final float pSecondsElapsed) {

				//check the bounderies upper and lowe screen
				if (Chicken.getY()>270)Chicken.setPosition(Chicken.getX(), Chicken.getY()-5f);
				if (Chicken.getY()<0)Chicken.setPosition(Chicken.getX(), Chicken.getY()+5f);
				

				//check for collission.
				if(vehicle1[i1].collidesWith(Chicken) || vehicle2[i2].collidesWith(Chicken)||vehicle3[i3].collidesWith(Chicken) || vehicle4[i4].collidesWith(Chicken))
				{lives--;
				// check position of chicken to position the blood.
				deadX= Chicken.getX();
				deadY= Chicken.getY();
				
				// create the new blood sprite upon collision.
				final Sprite dead = new Sprite(deadX, deadY,mDeadChickenTextureRegion);
				
				// attach the blood to the screen
				scene.getLastChild().attachChild(dead);
				
				
				//reposition a new chicken 
				Chicken.setPosition(220, 270);
				scene.getLastChild().attachChild(Chicken);
			
				// play the crash 
				crashMusic.play();
			
				//set the screen 
				life.setText(String.valueOf(lives));
				}
				
				//set collision if two cars appear on same line.
				if (i1>1){
					if(vehicle1[i1-1].collidesWith(Chicken) || vehicle2[i2-1].collidesWith(Chicken)||vehicle3[i3-1].collidesWith(Chicken) || vehicle4[i4-1].collidesWith(Chicken))
					{lives--;
					
					// check position of chicken to position the blood.
					deadX= Chicken.getX();
					deadY= Chicken.getY();
					final Sprite dead = new Sprite(deadX, deadY,mDeadChickenTextureRegion);
					
					// display new value of life.
					life.setText(String.valueOf(lives));
					//play crash.
					crashMusic.play();
					
					//attach the blood
					scene.getLastChild().attachChild(dead);
					Chicken.setPosition(220, 270);
					scene.getLastChild().attachChild(Chicken);
					}}

				//check collision with worm for 
				if(worm[i5].collidesWith(Chicken) )
				{scoreCount++;
				
				//show score update
				score.setText(String.valueOf(scoreCount));
				//attach new worm sprite
				scene.getLastChild().detachChild(worm[i5]);
				}
				
				// set value of final score screen. 
				if (lives==0){life.setText(String.valueOf("0"));
				//attach the sprite
				scene.getLastChild().attachChild(gameOver);
				score = new ChangeableText(305, 154, mFont, String.valueOf(scoreCount));
				scene.getLastChild().attachChild(score);
					mHandler.postDelayed(mLaunchOptionsTask, 500);
					crashMusic.stop();	
				}
				


			}
			
		});
		return scene;
	}

	//class for menu option 
	private Runnable mLaunchOptionsTask = new Runnable() {
		public void run() {
			//create the intent
			Intent myIntent = new Intent(Level1Activity.this, MainMenuActivity.class);
			Level1Activity.this.startActivity(myIntent);
		}
	};

	//overide on complete class
	@Override
	public void onLoadComplete() {
	}
	

	
	
	// class for the worm 
	private Runnable mStartFood = new Runnable() {
		public void run() {
			 i5 = nfood++;
			 //launch screen
			Scene scene = Level1Activity.this.mEngine.getScene();

			
			//AI for positioning of worm using random system 
			Random randomWormX = new Random();
			int RandomnumberWormX = randomWormX.nextInt(400);

			//generate random number.
			Random randomWormY = new Random();
			int RandomnumberWormY = randomWormY.nextInt(230);
			if (RandomnumberWormY<100)RandomnumberWormX=+100;

			worm[i5] = new AnimatedSprite(RandomnumberWormX, RandomnumberWormY, mWormTextureRegion.clone());
			if (i5>0){scene.getLastChild().detachChild(worm[i5-1]);}
			scene.getLastChild().attachChild(worm[i5]);

			
			if (nfood< 500){
				Random ranTime = new Random();

				int Randomnumber = ranTime.nextInt(5);		

				mHandler.postDelayed(mStartFood,1500*Randomnumber);
			}
		}};
		
		
		//class for first vehicle.
		private Runnable mStartVehicle1 = new Runnable() {
			public void run() {
				i1 = nDanger1++;
				Scene scene = Level1Activity.this.mEngine.getScene();
				float startY = 64f;
				
				// create new vehicle
				vehicle1[i1] = new AnimatedSprite(CAMERA_WIDTH - 15.0f, startY, mTruckBlueTextureRegion.clone());
				// frame duration - good if we plane to change the sprite and add sprite sheet.
				final long[] frameDurations = new long[26];
				Arrays.fill(frameDurations, 1);
				vehicle1[i1].animate(frameDurations, 0, 25, true);
				vehicle1[i1].registerEntityModifier(
						new SequenceEntityModifier (
								new AlphaModifier(1.0f, 0.0f, 1.0f),
								new MoveModifier(3.0f, vehicle1[i1].getX(), -500.0f, 
										vehicle1[i1].getY(), startY)));
				scene.getLastChild().attachChild(vehicle1[i1]);

				if (nDanger1 < 500){
					Random ranTime = new Random();

					int Randomnumber = ranTime.nextInt(5);		// random from 1 to 49	

					mHandler.postDelayed(mStartVehicle1,2000*Randomnumber);
				}
			}
		};

		//class for second vehicle.
		private Runnable mStartVehicle2 = new Runnable() {
			public void run() {
				i2 = nDanger2++;
				Scene scene = Level1Activity.this.mEngine.getScene();
				float startY = 111f;
				vehicle2[i2] = new AnimatedSprite(CAMERA_WIDTH - 15.0f, startY, mTruckRedTextureRegion.clone());
				// frame duration - good if we plane to change the sprite and add sprite sheet.
				final long[] frameDurations = new long[26];
				Arrays.fill(frameDurations, 1);
				vehicle2[i2].animate(frameDurations, 0, 25, true);
				vehicle2[i2].registerEntityModifier(
						new SequenceEntityModifier (
								new AlphaModifier(1.0f, 0.0f, 1.0f),
								new MoveModifier(3.0f, vehicle2[i2].getX(), -500.0f, 
										vehicle2[i2].getY(), startY)));
				scene.getLastChild().attachChild(vehicle2[i2]);
				if (nDanger2 < 500){
					Random ranTime = new Random();

					//generate random number 
					int Randomnumber = ranTime.nextInt(5);		
					// random time display of vehicle 5
					mHandler.postDelayed(mStartVehicle2,2000*Randomnumber);
				}
			}
		};

		//class for third vehicle.
		private Runnable mStartVehicle3 = new Runnable() {
			public void run() {
				i3 = nDanger3++;
				Scene scene = Level1Activity.this.mEngine.getScene();
				float startY = 155;
				vehicle3[i3] = new AnimatedSprite(ROAD_START + 15.0f, startY, mCarRedTextureRegion.clone());
				// frame duration - good if we plane to change the sprite and add sprite sheet.
				final long[] frameDurations = new long[26];
				Arrays.fill(frameDurations, 1);
				vehicle3[i3].animate(frameDurations, 0, 25, true);
				vehicle3[i3].registerEntityModifier(
						new SequenceEntityModifier (
								new AlphaModifier(1.0f, 0.0f, 1.0f),
								new MoveModifier(3.0f, vehicle3[i3].getX(), 550.0f, 
										vehicle3[i3].getY(), startY)));
				scene.getLastChild().attachChild(vehicle3[i3]);

				if (nDanger3 < 500){
					Random ranTime = new Random();
					//generate random number 
					int Randomnumber = ranTime.nextInt(5);		// random from 1 to 49	

					// random time display of vehicle 3
					mHandler.postDelayed(mStartVehicle3,2000*Randomnumber);
				}
			}};

// The forth vehicle class.
			private Runnable mStartVehicle4 = new Runnable() {
				public void run() {
					i4 = nDanger4++;
					Scene scene = Level1Activity.this.mEngine.getScene();
					float startY = 207;
					vehicle4[i4] = new AnimatedSprite(ROAD_START + 15.0f, startY, mCarBlueTextureRegion.clone());
					// frame duration - good if we plane to change the sprite and add sprite sheet.
					final long[] frameDurations = new long[26];
					Arrays.fill(frameDurations, 1);
					vehicle4[i4].animate(frameDurations, 0, 25, true);
					vehicle4[i4].registerEntityModifier(
							new SequenceEntityModifier (
									new AlphaModifier(1.0f, 0.0f, 1.0f),
									new MoveModifier(3.0f, vehicle4[i4].getX(), 650.0f, 
											vehicle4[i4].getY(), startY)));
					scene.getLastChild().attachChild(vehicle4[i4]);

					if (nDanger4 < 500){
						Random ranTime = new Random();
						//generate random number 
						int Randomnumber = ranTime.nextInt(5);		// random from 1 to 49	

						// random time display of vehicle 4
						mHandler.postDelayed(mStartVehicle4,2000*Randomnumber);
					}





				}};



}

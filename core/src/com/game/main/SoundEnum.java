package com.game.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

import lombok.AllArgsConstructor;
import lombok.Getter;

/** Representation of Sound Effects */
public enum SoundEnum{
	EXPLOSION("expExplosion"),
	BIG_EXPLOSION("bigExplosion");
	
	/** The name of this Sounds file */
	@Getter private String name;
	
	/** The local volume at which this sound is to be played */
	@Getter private float volume;
	
	/** The actual sound object */
	private Sound sound;
	
	
	/** Constructs new SoundEnum */
	private SoundEnum(String name, float volume){
		this.name = name;
		this.volume = volume;
		this.sound = null;
	}
	
	/** Constructs new SoundEnum */
	private SoundEnum(String name){
		this(name, 1f);
	}
	
	
	/** Returns the full path of the sound effect */
	public String getPath(){
		return "sounds/"+name+".wav";
	}
	
	/** Loads in and returns the Sound Effect */
	private void loadSound(){
		this.sound = Gdx.audio.newSound(Gdx.files.internal(getPath()));
	}
	
	/** Disposes of actual Sound object */
	public void disposeSound(){
		if(sound!=null){
			sound.dispose();
		}
		this.sound = null;
	}
	
	/** Plays the sound (loads it in if not previously buffered) */
	public void play(){
		if(sound==null){
			loadSound();
		}
		sound.play(volume*Constants.MASTER_VOLUME_SCALER);
	}
	
}

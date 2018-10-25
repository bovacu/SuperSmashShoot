package characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import general.Converter;
import general.GameObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnimationController {

    private Map<String, Animation<TextureRegion>> namesAndAnimations;
    private List<Float> animationTimes;
    private Animation<TextureRegion> activeAnimation;

    private int indexOfAnimation;
    private GameObject gameObject;

    public AnimationController(GameObject gameObject, String animationNames[], int animationIDs[], int frames[], float timeFrames[], Animation.PlayMode playModes[]){
        this.namesAndAnimations = new HashMap<>();
        this.animationTimes = new ArrayList<>();
        for(int i = 0; i < animationNames.length; i++) {
            this.namesAndAnimations.put(animationNames[i], this.createAnimation(animationIDs[i], frames[i], timeFrames[i], playModes[i]));
            this.animationTimes.add((float) 0);
        }
        this.activeAnimation = this.namesAndAnimations.get(animationNames[0]);
        indexOfAnimation = 0;
        this.gameObject = gameObject;
    }

    public void changeAnimationTo(String animation){
        if(!this.isFlipX())
            this.activeAnimation = this.namesAndAnimations.get(animation);
        else{
            this.flipAnimation();
            this.activeAnimation = this.namesAndAnimations.get(animation);
            this.flipAnimation();
        }
        this.animationTimes.set(this.indexOfAnimation, 0f);
        List<String> keys = new ArrayList<>(this.namesAndAnimations.keySet());

        for(int i = 0; i < keys.size(); i++)
            if(keys.get(i).equals(animation))
                this.indexOfAnimation = i;
    }

    public String getCurrentAnimationName(){
        return (String)this.namesAndAnimations.keySet().toArray()[this.indexOfAnimation];
    }

    public boolean isFlipX(){
        return this.activeAnimation.getKeyFrame((this.animationTimes.get(this.indexOfAnimation))).isFlipX();
    }

    public void flipAnimation(){
        for(TextureRegion t : this.activeAnimation.getKeyFrames())
            t.flip(true, false);
    }
    public boolean isAnimationFinished(){
        return (this.animationTimes.get(this.indexOfAnimation) >= this.activeAnimation.getAnimationDuration());
    }

    public void play(SpriteBatch batch, int size){
        this.animationTimes.set(this.indexOfAnimation, this.animationTimes.get(this.indexOfAnimation) + Gdx.graphics.getDeltaTime());
        batch.draw(this.activeAnimation.getKeyFrame(this.animationTimes.get(this.indexOfAnimation)),
                this.gameObject.getSprite().getX(), this.gameObject.getSprite().getY(), size, size);
    }

    private Animation<TextureRegion> createAnimation(int id, int frames, float time, Animation.PlayMode playMode){
        Animation<TextureRegion> animation;
        Texture sheet = Converter.idToSprite(id).getTexture();
        TextureRegion [][] tmp = TextureRegion.split(sheet, sheet.getWidth()/frames, sheet.getHeight());
        TextureRegion animationFrames[] = new TextureRegion[frames];
        for (int i = 0; i < animationFrames.length; i++) animationFrames[i] = tmp[0][i];
        animation = new Animation<>(time, animationFrames);
        animation.setPlayMode(playMode);
        return animation;
    }

    public void dispose(){
        for(Animation<TextureRegion> a :this.namesAndAnimations.values()){
            for(TextureRegion t : a.getKeyFrames())
                t.getTexture().dispose();
        }

        for(TextureRegion t : this.activeAnimation.getKeyFrames()){
            t.getTexture().dispose();
        }
    }
}

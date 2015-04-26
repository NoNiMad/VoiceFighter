package fr.nonimad.ld32;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.kotcrab.vis.ui.VisUI;

public class ScreenAudioDebug extends ScreenAdapter {
    private Stage stage = new Stage();
    
	SpriteBatch batch;
	ShapeRenderer sRenderer;
	BitmapFont font;
	
	SoundAnalyser soundAnalyser;
	
	@Override
	public void show() {
	    Gdx.input.setInputProcessor(stage);
	    
        batch = new SpriteBatch();
        sRenderer = new ShapeRenderer();
        font = new BitmapFont();
        
        soundAnalyser = new SoundAnalyser();
        
        TextButton btn_back = new TextButton("Return", VisUI.getSkin());
        btn_back.setPosition(10, LudumDare32.HEIGHT - 40);
        btn_back.setSize(100, 30);
        btn_back.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((LudumDare32)Gdx.app.getApplicationListener()).setScreen(new ScreenMainMenu());
            }
        });
        stage.addActor(btn_back);
	}
	
	@Override
	public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
		
		byte size = 6;
		
		batch.begin();
		font.setColor(Color.BLACK);
		font.draw(batch, "Live audio spectrum preview : ", 10, LudumDare32.HEIGHT - 50);
        font.draw(batch, "Period found in the spectrum (still experimental) : ", 10, LudumDare32.HEIGHT - 450);
        batch.end();
		
		sRenderer.begin(ShapeType.Line);
		sRenderer.setColor(Color.BLACK);
		
		for(int i = 0; i < soundAnalyser.data.length; i++) {
			sRenderer.rect(10 + i*size, LudumDare32.HEIGHT - 250, size, soundAnalyser.data[i]*3);
		}
		
		sRenderer.line(10, LudumDare32.HEIGHT - 600, 10 + (soundAnalyser.endPeriod - soundAnalyser.startPeriod)*size, LudumDare32.HEIGHT - 600);
		
		Period p = soundAnalyser.getLastPeriodIfValid();
		
		if(p != null) {
    		for(int i = 0; i < p.duration - 1; i++) {
    		    sRenderer.line(10 + i*size, LudumDare32.HEIGHT - 600 + p.data[i]*3, 10 + (i+1)*size, LudumDare32.HEIGHT - 600 + p.data[i+1]*3);
                sRenderer.circle(10 + i*size, LudumDare32.HEIGHT - 600 + p.data[i]*3, 5);
            }
    		
    		sRenderer.circle(10 + (p.duration-1)*size, LudumDare32.HEIGHT - 600 + p.data[p.duration - 1]*3, 5);
    		
    		batch.begin();
    		font.draw(batch, "Amplitude : " + p.amplitude, 400, LudumDare32.HEIGHT - 450);
    		font.draw(batch, "Duration : " + p.duration, 500, LudumDare32.HEIGHT - 450);
    		batch.end();
		}
		
		sRenderer.end();
	}
	
	@Override
	public void hide() {
	    dispose();
	}
	
	@Override
	public void dispose() {
		soundAnalyser.stopped = true;
		stage.dispose();
		batch.dispose();
		font.dispose();
		sRenderer.dispose();
	}
}

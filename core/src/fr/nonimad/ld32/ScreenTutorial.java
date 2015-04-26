package fr.nonimad.ld32;

import java.awt.Desktop;
import java.net.URI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.kotcrab.vis.ui.VisUI;

public class ScreenTutorial implements Screen {
    private Stage stage = new Stage();
    
    BitmapFont bmf = new BitmapFont();
    
    @Override
    public void show() {
        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(new InputListener());
        multiplexer.addProcessor(stage);
        Gdx.input.setInputProcessor(multiplexer);
        
        Label title = new Label("Tutorial", new Label.LabelStyle(Assets.f_mainTitle, Color.WHITE));
        title.setPosition(LudumDare32.WIDTH / 2 - new GlyphLayout(Assets.f_mainTitle, "Tutorial").width / 2, LudumDare32.HEIGHT - 200);
        stage.addActor(title);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
        
        stage.getBatch().begin();
        
        String explanations = "Welcome Soldier ! You are here for war. You know what that means, right ?\n"
                + "You will have to use your f*cking voice ! Using sweet notes and hard screams,\n"
                + "we will be able to kill these little bastards.\n\n"
                + "Here is how it works : The higher the note, the more power you will throw.\n"
                + "There one side note (huhu), if they get to you, you will quickly die.\n\n"
                + "I only wish you one thing : Good Luck and make the country proud of You !";
        Assets.f_text.draw(stage.getBatch(), explanations, LudumDare32.WIDTH / 2 - new GlyphLayout(Assets.f_text, explanations).width / 2, LudumDare32.HEIGHT - 240);
        
        bmf.draw(stage.getBatch(), "Press D for debug mode", LudumDare32.WIDTH / 2 - new GlyphLayout(bmf, "Press Ctrl + D for debug mode").width / 2, 20);
        
        stage.getBatch().end();
        
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
        
        Label byNoNiMad = new Label("Created by NoNiMad", new Label.LabelStyle(Assets.f_titles, Color.WHITE));
        byNoNiMad.setPosition(LudumDare32.WIDTH / 2 - new GlyphLayout(Assets.f_titles, "Created by NoNiMad").width / 2, 250);
        byNoNiMad.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                try {
                    Desktop.getDesktop().browse(new URI("http://twitter.com/NoNiMad"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        stage.addActor(byNoNiMad);
        
        Label byZetsyog = new Label("Sprites by Zetsyog", new Label.LabelStyle(Assets.f_titles, Color.WHITE));
        byZetsyog.setPosition(LudumDare32.WIDTH / 2 - new GlyphLayout(Assets.f_titles, "Sprites by Zetsyog").width / 2, 190);
        byZetsyog.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                try {
                    Desktop.getDesktop().browse(new URI("https://twitter.com/Zetsyog"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        stage.addActor(byZetsyog);
        
        Label bySar = new Label("And Sar", new Label.LabelStyle(Assets.f_titles, Color.WHITE));
        bySar.setPosition(LudumDare32.WIDTH / 2 - new GlyphLayout(Assets.f_titles, "And Sar").width / 2, 130);
        bySar.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                try {
                    Desktop.getDesktop().browse(new URI("http://mab.eviscerate.net"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        stage.addActor(bySar);
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
        dispose();
    }

    @Override
    public void dispose() {
        stage.dispose();
        bmf.dispose();
    }

    class InputListener implements InputProcessor {
        @Override public boolean keyDown(int keycode) { return false; }
        @Override public boolean keyUp(int keycode) { return false; }
        @Override public boolean keyTyped(char character) {
            if(character == 'D') {
                LudumDare32.debugMode = !LudumDare32.debugMode;
                return true;
            }
            return false;
        }
        @Override public boolean touchDown(int screenX, int screenY, int pointer, int button) { return false; }
        @Override public boolean touchUp(int screenX, int screenY, int pointer, int button) { return false; }
        @Override public boolean touchDragged(int screenX, int screenY, int pointer) { return false; }
        @Override public boolean mouseMoved(int screenX, int screenY) { return false; }
        @Override public boolean scrolled(int amount) { return false; }
    }
}

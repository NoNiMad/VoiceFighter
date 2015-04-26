package fr.nonimad.ld32;

import java.awt.Desktop;
import java.net.URI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.kotcrab.vis.ui.VisUI;

public class ScreenMainMenu extends ScreenAdapter {
    private Stage stage = new Stage();
    
    private Image i_tdLogo = new Image(Assets.t_ldLogo);
    
    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        
        i_tdLogo.setPosition(LudumDare32.WIDTH / 2 - i_tdLogo.getWidth() / 2, 20);
        i_tdLogo.addAction(Actions.forever(Actions.sequence(Actions.alpha(0.7f, 1), Actions.alpha(1.0f, 1))));
        i_tdLogo.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                try {
                    Desktop.getDesktop().browse(new URI("http://ludumdare.com"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        stage.addActor(i_tdLogo);
        
        
        TextButton btn_play = new TextButton("Play", VisUI.getSkin());
        btn_play.setPosition(362, 350);
        btn_play.setSize(300, 40);
        btn_play.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((LudumDare32)Gdx.app.getApplicationListener()).setScreen(new ScreenInGame());
            }
        });
        stage.addActor(btn_play);
        
        
        TextButton btn_tuto = new TextButton("Tutorial", VisUI.getSkin());
        btn_tuto.setPosition(362, 300);
        btn_tuto.setSize(300, 40);
        btn_tuto.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((LudumDare32)Gdx.app.getApplicationListener()).setScreen(new ScreenTutorial());
            }
        });
        stage.addActor(btn_tuto);
        
        
        TextButton btn_quit = new TextButton("Quit", VisUI.getSkin());
        btn_quit.setPosition(362, 250);
        btn_quit.setSize(300, 40);
        btn_quit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
        stage.addActor(btn_quit);
        
        
        if(LudumDare32.debugMode) {
            TextButton btn_debug = new TextButton("Audio Debugging", VisUI.getSkin());
            btn_debug.setPosition(362, 200);
            btn_debug.setSize(300, 40);
            btn_debug.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    ((LudumDare32)Gdx.app.getApplicationListener()).setScreen(new ScreenAudioDebug());
                }
            });
            stage.addActor(btn_debug);
        }
        
        
        Label title = new Label(LudumDare32.TITLE, new Label.LabelStyle(Assets.f_mainTitle, Color.WHITE));
        title.setPosition(512 - new GlyphLayout(Assets.f_mainTitle, LudumDare32.TITLE).width / 2, 550);
        stage.addActor(title);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

}

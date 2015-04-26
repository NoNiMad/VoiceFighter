package fr.nonimad.ld32;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.kotcrab.vis.ui.VisUI;

public class ScreenGameOver extends ScreenAdapter {
    private Stage stage = new Stage();
    
    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        
        TextButton btn_return = new TextButton("Return to Menu", VisUI.getSkin());
        btn_return.setPosition(362, 350);
        btn_return.setSize(300, 40);
        btn_return.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((LudumDare32)Gdx.app.getApplicationListener()).setScreen(new ScreenMainMenu());
            }
        });
        stage.addActor(btn_return);
        
        Label title = new Label("Game Over", new Label.LabelStyle(Assets.f_mainTitle, Color.WHITE));
        title.setPosition(512 - new GlyphLayout(Assets.f_mainTitle, "Game Over").width / 2, 550);
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

package no.fusiontd.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import no.fusiontd.FusionTD;
import no.fusiontd.MenuStage;
import no.fusiontd.menu.ExitButton;
import no.fusiontd.menu.NormalTextButtonFactory;
import no.fusiontd.menu.TextButtonFactory;

public class MapDeleteScreen implements Screen{
        private FusionTD game;
        private MenuStage stage;
        private TextButtonFactory textButtonFactory;
        private ExitButton exitButton;
        private TextButton textButton;

        public MapDeleteScreen(FusionTD game) {
            this.game = game;
            exitButton = ExitButton.create(game);
        }

        @Override
        public void show() {
            stage = new MenuStage();
            textButtonFactory = new NormalTextButtonFactory();
            Gdx.input.setInputProcessor(stage);

        /*Texture backgroundImage = new Texture(Gdx.files.internal("backgrounds/main_menu_with_creeps.png"));
        stage.setBackground(new Image(backgroundImage));*/

            //1. Navigate to the map folder, find amount of maps. maps are found in android/maps.
            FileHandle[] files = createFilehandle();
            int numberOfMaps=files.length;

            //2. create and add textButtons to a table.
            final Table table = new Table();
            for (int i=0; i<numberOfMaps; i++){
                final String mapName=files[i].nameWithoutExtension();//
                textButton = textButtonFactory.createTextButton(mapName, new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        table.removeActor(textButton);
                        deleteMap(mapName);
                    }
                });
                table.add(textButton);
                table.row();
            }

            //3. create a scrollPane (?) with the table

            ScrollPane scrollPane = new ScrollPane(table);
            Table container = new Table();
            container.add(scrollPane).width(500f).height(410f);

            //4. set scrollPane's position and size. You can disable scrolling in a direction using scrollPane.setScrollingDisabled()

            //5. add ScrollPane to stage.
            stage.addMenuContent(container);

            stage.addImageButton(exitButton);
        }

        @Override
        public void render(float delta) {
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            stage.act(Gdx.graphics.getDeltaTime());
            stage.draw();
        }

        public void resize(int width, int height) {
            stage.getViewport().update(width, height, true);
        }

        public void dispose() {
            stage.dispose();
            textButtonFactory.dispose();
            exitButton.dispose();
        }

        @Override
        public void pause() {

        }

        @Override
        public void resume() {

        }

        @Override
        public void hide() {

        }

        public void deleteMap(String mapName){
            String locRoot = Gdx.files.getLocalStoragePath();
            String mapSaveString = locRoot + "maps/" + mapName + ".txt";
            FileHandle file = Gdx.files.absolute(mapSaveString);

            file.delete();
            System.out.println("deleted " + mapName);//"False"-value overwrites current content.
        }

        private FileHandle[] createFilehandle(){
            String locRoot = Gdx.files.getLocalStoragePath();
            FileHandle[] assetMaps = Gdx.files.internal("maps/").list();
            FileHandle[] createdMaps = Gdx.files.absolute(locRoot + "maps/").list();

            boolean equalArrays = true;
            if(assetMaps.length != createdMaps.length){
                equalArrays = false;
            } else{
                for (int i = 0; i < assetMaps.length; i++) {
                    if(!assetMaps[i].nameWithoutExtension().equals(createdMaps[i].nameWithoutExtension())){
                        equalArrays = false;
                        break;
                    }

                }
            }
            FileHandle[] files;
            if(equalArrays){
                files = assetMaps;
            }
            else{
                files = new FileHandle[createdMaps.length + assetMaps.length];
                int count = 0;
                for(int i = 0; i<assetMaps.length; i++) {
                    files[i] = assetMaps[i];
                    count++;
                }
                for(int j = 0;j<createdMaps.length;j++) {
                    files[count++] = createdMaps[j];
                }
            }

            return files;
        }
    }

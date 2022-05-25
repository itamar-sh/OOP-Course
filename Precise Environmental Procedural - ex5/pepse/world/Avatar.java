package pepse.world;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.components.CoordinateSpace;
import danogl.gui.ImageReader;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Vector2;

import java.awt.event.KeyEvent;
import java.util.Random;

/**
 * This class is responsible for the Avatar in the game. The avatar can move around, jump and fly.
 * Moreover, the Avatar creation make an AvatarEnergy that display the Energy of the Avatar.
 * Bonus: we have 3 states. 2 of them with unique abilities. (specialRunning, specialFlying)
 */
public class Avatar extends GameObject{
    private final UserInputListener inputListener;
    // energy constants
    private static final Vector2 ENERGY_LOCATION = new Vector2(15, 15);
    private static final Vector2 ENERGY_DIMENSIONS = new Vector2(80, 30);
    private static final float FLY_COST_ENERGY = -1f;
    private static final float STANDING_BONUS_ENERGY = 0.5f;
    private static final int MIN_ENERGY_TO_FLY = 10;
    private final AvatarEnergy energy;
    // speed constants
    private static final float VELOCITY_X = 300;
    private static final float SPECIAL_RUNNER_VELOCITY_X = 400;
    private static final float VELOCITY_Y = -350;
    private static final float SPECIAL_RUNNER_VELOCITY_Y = -500;
    private static final float FLY_VELOCITY_Y = -450;
    private static final float SPECIAL_FLYING_FLY_VELOCITY_Y = -200;
    private static final float GRAVITY = 600;
    private static final float MAX_VELOCITY = 600;
    public static final String GROUND_TAG = "ground";
    // avatar visualization constants
    private static final Vector2 DIMENSIONS = new Vector2(60, 60);
    private static final boolean LEFT_SIDE = true;
    private static final boolean RIGHT_SIDE = false;
    // standing images and renderables
    private static final String REGULAR_AVATAR_IMAGE_STANDING = "pepse/assets/standing1.png";
    private static final String SPECIAL_FLYING_AVATAR_IMAGE_STANDING =
            "pepse/assets/standing_special_flyer.png";
    private static final String SPECIAL_RUNNING_AVATAR_IMAGE_STANDING = "pepse/assets/" +
            "standing_special_runner.png";
    private Renderable regularAvatarStandingImage;
    private Renderable specialFlyingAvatarStandingImage;
    private Renderable specialRunningAvatarStandingImage;
    // jumping images and renderables
    private static final String REGULAR_AVATAR_IMAGE_JUMPING = "pepse/assets/jumping.png";
    private static final String SPECIAL_FLYING_AVATAR_IMAGE_JUMPING =
            "pepse/assets/jumping_special_flyer.png";
    private static final String SPECIAL_RUNNING_AVATAR_IMAGE_JUMPING =
            "pepse/assets/jumping_special_runner.png";
    private Renderable regularAvatarJumpingImage;
    private Renderable specialFlyingAvatarJumpingImage;
    private Renderable specialRunningAvatarJumpingImage;
    // walking images and renderables
    private enum Position {STANDING, JUMPING, WALKING, FLYING}
    private Position position;
    private static final int NUM_OF_WALKING_IMAGES = 2;
    private static final String[] REGULAR_AVATAR_IMAGES_WALKING = {"pepse/assets/walking1.png",
            "pepse/assets/walking2.png"};
    private static final String[] SPECIAL_FLYING_AVATAR_IMAGES_WALKING = {
            "pepse/assets/walking1_special_flyer.png",
            "pepse/assets/walking2_special_flyer.png"};
    private static final String[] SPECIAL_RUNNING_AVATAR_IMAGES_WALKING = {
            "pepse/assets/walking1_special_runner.png",
            "pepse/assets/walking2_special_runner.png"};
    private final Renderable[] regularAvatarWalkingImage = new Renderable[NUM_OF_WALKING_IMAGES];
    private final Renderable[] specialFlyingAvatarWalkingImage = new Renderable[NUM_OF_WALKING_IMAGES];
    private final Renderable[] specialRunningAvatarWalkingImage = new Renderable[NUM_OF_WALKING_IMAGES];
    // flying images and renderables
    private static final String REGULAR_AVATAR_IMAGES_FLYING = "pepse/assets/flying1.png";
    private static final String[] SPECIAL_FLYING_AVATAR_IMAGES_FLYING = {"pepse/assets/bat_flying1.png",
            "pepse/assets/bat_flying2.png"};
    private static final String SPECIAL_RUNNING_AVATAR_IMAGES_FLYING =
            "pepse/assets/flying1_special_runner.png";
    private Renderable regularAvatarFlyingImage;
    private final Renderable[] specialFlyingAvatarFlyingImage = new Renderable[NUM_OF_WALKING_IMAGES];
    private Renderable specialRunningAvatarFlyingImage;
    // figure state.  Bonus area.
    private enum State {REGULAR, SPECIAL_RUNNING, SPECIAL_FLYING}
    private State state;
    private final GameObject characterDescription;
    private final Vector2 DescriptionPosition = new Vector2(15, 60);
    private final Vector2 DescriptionDimensions = new Vector2(60, 40);
    private TextRenderable regularDescription;
    private TextRenderable specialFlyingDescription;
    private TextRenderable specialRunningDescription;
    private static final String REGULAR_CHARACTER_DESCRIPTION = "Louis is the normal state of the character.\n" +
            "press 1 for Louis, 2 for LouisEX, 3 for Batman!";
    private static final String SPECIAL_FLYING_CHARACTER_DESCRIPTION = "Batman dont lose energy while flying!\n" +
            "press 1 for Louis, 2 for LouisEX, 3 for Batman!";
    private static final String SPECIAL_RUNNER_CHARACTER_DESCRIPTION = "LouisEX can jump higher and run faster!\n" +
            "press 1 for Louis, 2 for LouisEX, 3 for Batman!";
    private static final String FONT_NAME = "David";

    /**
     * init Avatar - including the energy and the description off the current state.
     * @param gameObjects  object that control all the objects in the game
     * @param pos  location to place the Avatar in the creation on the screen.
     * @param dimensions  dimensions of the number to display
     * @param inputListener Contains a single method: isKeyPressed, which returns whether if a key is pressed
     * @param renderable The renderable representing the object. Can be null, in which case
     * @param imageReader object that can make an image to renderable object
     */
    public Avatar(GameObjectCollection gameObjects, Vector2 pos, Vector2 dimensions, UserInputListener inputListener,
                  Renderable renderable, ImageReader imageReader) {
        super(pos, dimensions, renderable);
        // overall attributes like gravity and staying on the ground
        physics().preventIntersectionsFromDirection(Vector2.ZERO);
        transform().setAccelerationY(GRAVITY);
        this.inputListener = inputListener;
        // images control
        createRenderers(imageReader);
        // default state
        this.state = State.REGULAR;
        // energy control
        this.energy = AvatarEnergy.create(gameObjects, Layer.BACKGROUND, ENERGY_LOCATION, ENERGY_DIMENSIONS);
        this.energy.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        // description init
        this.characterDescription = initCharacterDescription(gameObjects);

    }

    /**
     * init the description, it has TextRenderable for every state.
     * @param gameObjects  object that control all the objects in the game
     * @return Game object that Renderer the Description
     */
    private GameObject initCharacterDescription(GameObjectCollection gameObjects) {
        this.regularDescription = new TextRenderable(REGULAR_CHARACTER_DESCRIPTION,FONT_NAME,
                true,false);
        this.specialFlyingDescription = new TextRenderable(SPECIAL_FLYING_CHARACTER_DESCRIPTION,FONT_NAME,
                true,false);
        this.specialRunningDescription = new TextRenderable(SPECIAL_RUNNER_CHARACTER_DESCRIPTION,FONT_NAME,
                true,false);
        GameObject characterDescription = new GameObject(DescriptionPosition, DescriptionDimensions,
                this.regularDescription);
        gameObjects.addGameObject(characterDescription, Layer.BACKGROUND);
        characterDescription.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        return characterDescription;
    }


    /**
     * create new object of avatar including all the remains objects for it.
     * @param gameObjects  object that control all the objects in the game
     * @param layer  number of the layer of the game.
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param inputListener Contains a single method: isKeyPressed, which returns whether if a key is pressed
     * @param imageReader object that can make an image to renderable object
     * @return return the created Avatar object.
     */
    public static Avatar create(GameObjectCollection gameObjects,
                                int layer, Vector2 topLeftCorner,
                                UserInputListener inputListener, ImageReader imageReader){
        Renderable avatarImageStanding = imageReader.readImage(REGULAR_AVATAR_IMAGE_STANDING, true);
        Avatar avatar = new Avatar(gameObjects, topLeftCorner, DIMENSIONS,inputListener,
                avatarImageStanding, imageReader);
        gameObjects.addGameObject(avatar, layer);
        return avatar;
    }

    /**
     * image the update according to the state (Regular or special Flying or special runner)
     * and the position (fly, jump, walk, stand).
     */
    private void updateImage() {
        switch (this.position){
            case STANDING:
                    updateFromThree(this.regularAvatarStandingImage,this.specialRunningAvatarStandingImage,
                            this.specialFlyingAvatarStandingImage);
                    break;
            case JUMPING:
                updateFromThree(this.regularAvatarJumpingImage,this.specialRunningAvatarJumpingImage,
                        this.specialFlyingAvatarJumpingImage);
                break;
            case WALKING:
                // generate number so we most of the time will get the same pic but sometimes it will be changed.
                int walkPicNUmber = generateNumber();
                updateFromThree(this.regularAvatarWalkingImage[walkPicNUmber],
                        this.specialRunningAvatarWalkingImage[walkPicNUmber],
                        this.specialFlyingAvatarWalkingImage[walkPicNUmber]);
                break;
            case FLYING:
                int flyPicNUmber = generateNumber();
                updateFromThree( this.regularAvatarFlyingImage, this.specialRunningAvatarFlyingImage,
                        this.specialFlyingAvatarFlyingImage[flyPicNUmber]);
                break;
        }
    }
    //helper function for updateImage
    private void updateFromThree(Renderable r1, Renderable r2, Renderable r3){
        switch (this.state){
            case REGULAR:
                renderer().setRenderable(r1);
                return;
            case SPECIAL_RUNNING:
                renderer().setRenderable(r2);
                return;
            case SPECIAL_FLYING:
                renderer().setRenderable(r3);
                break;
        }
    }

    /**
     * enerate number so we most of the time will 0 the same pic but sometimes it will be changed to 1.
     * @return number. 0 or 1.
     */
    private int generateNumber() {
        // generate number that most of the time will be 0
        Random random = new Random();
        int firstFactor = random.nextInt(NUM_OF_WALKING_IMAGES*NUM_OF_WALKING_IMAGES);
        firstFactor = firstFactor / (NUM_OF_WALKING_IMAGES*NUM_OF_WALKING_IMAGES-1);
        return firstFactor;
    }

    /**
     *  This is the main method that been performed all the time in cycling.
     *  Conrtol the speed, the energy and the status of the avatar in relation to the keys pressed.
     * @param deltaTime The time, in seconds, that passed since the last invocation
     *                  of this method (i.e., since the last frame). This is useful
     *                  for either accumulating the total time that passed since some
     *                  event, or for physics integration (i.e., multiply this by
     *                  the acceleration to get an estimate of the added velocity
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        handleState();

        handleHighSpeed();

        handleEnergy();

        updateImage();

    }

    /**
     * change the description according to the state.
     */
    private void updateCharacterDescription() {
        switch (this.state){
            case REGULAR:
                this.characterDescription.renderer().setRenderable(this.regularDescription);
                break;
            case SPECIAL_RUNNING:
                this.characterDescription.renderer().setRenderable(this.specialRunningDescription);
                break;
            case SPECIAL_FLYING:
                this.characterDescription.renderer().setRenderable(this.specialFlyingDescription);
                break;
        }
    }

    /**
     * Responsible to add Energy to the Avatar in case of standing in the place.
     */
    private void handleEnergy() {
        if (transform().getVelocity().y() == 0 && transform().getVelocity().x() == 0){
            this.energy.addEnergy(STANDING_BONUS_ENERGY);
            this.position = Position.STANDING;
        }
    }

    /**
     * Responsible to block the velocity so the avatar won't fall to high. (like in real life - the wind resistance
     * after some speed is equal to the gravity and speed is remain constant).
     */
    private void handleHighSpeed() {
        if (transform().getVelocity().y() > MAX_VELOCITY){
            transform().setVelocityY(MAX_VELOCITY);
        }
    }

    /**
     * Responsible to the current state of the avatar.
     */
    private void handleState() {
        boolean action_played = false;
        checkChangeState();
        // turning left
        if(inputListener.isKeyPressed(KeyEvent.VK_LEFT)) {
            handleChangingSide(LEFT_SIDE);
            action_played = true;
        }
        // turning left
        if(inputListener.isKeyPressed(KeyEvent.VK_RIGHT)){
            handleChangingSide(RIGHT_SIDE);
            action_played = true;
        }
        // jump
        if(inputListener.isKeyPressed(KeyEvent.VK_SPACE) && getVelocity().y() == 0){
            handleJumping();
            action_played = true;
        }
        // fly
        if(inputListener.isKeyPressed(KeyEvent.VK_SPACE) && inputListener.isKeyPressed(KeyEvent.VK_SHIFT)) {
            action_played = handleFlying();
        }
        // no fly jump or turn
        if (!action_played){
            handleVoidAction();
        }

    }

    /**
     * check if we need to change the state.
     */
    private void checkChangeState() {
        // change state for regular state
        if(inputListener.isKeyPressed(KeyEvent.VK_1)) {
            this.state = State.REGULAR;
        }
        // change state for special flying state
        if(inputListener.isKeyPressed(KeyEvent.VK_2)) {
            this.state = State.SPECIAL_RUNNING;
        }
        // change state for special running state
        if(inputListener.isKeyPressed(KeyEvent.VK_3)) {
            this.state = State.SPECIAL_FLYING;
        }
        updateCharacterDescription();
    }

    /**
     * Responsible to void state. with no fly jump or turn.
     */
    private void handleJumping() {
        if (this.state == State.SPECIAL_RUNNING){
            transform().setVelocityY(SPECIAL_RUNNER_VELOCITY_Y);
        } else {
            transform().setVelocityY(VELOCITY_Y);
        }
        this.position = Position.JUMPING;
    }

    /**
     * Responsible to void state. with no fly jump or turn.
     */
    private void handleVoidAction() {
        if (transform().getVelocity().y() != 0){
            this.position = Position.JUMPING;
        } else {
            this.position = Position.STANDING;
        }
        transform().setVelocityX(0);
    }

    /**
     * Responsible to flt state. Bound by case that there isn't enough energy to fly.
     * @return if the Avatar flew.
     */
    private boolean handleFlying() {
        if (this.energy.getEnergyValue() >= MIN_ENERGY_TO_FLY){
            this.position = Position.FLYING;
            if (this.state == State.SPECIAL_FLYING){
                transform().setVelocityY(SPECIAL_FLYING_FLY_VELOCITY_Y);
            } else {
                transform().setVelocityY(FLY_VELOCITY_Y);
                this.energy.addEnergy(FLY_COST_ENERGY);
            }
            return true;
        }
        return false;
    }

    /**
     * Responsible to turn Left or Right.
     * @param flip whether to flip the image or not.
     */
    private void handleChangingSide(boolean flip) {
        renderer().setIsFlippedHorizontally(flip);
        if (flip){
            if (this.state == State.SPECIAL_RUNNING){
                transform().setVelocityX(-SPECIAL_RUNNER_VELOCITY_X);
            } else {
                transform().setVelocityX(-VELOCITY_X);
            }
        } else {
            if (this.state == State.SPECIAL_RUNNING){
                transform().setVelocityX(SPECIAL_RUNNER_VELOCITY_X);
            } else {
                transform().setVelocityX(VELOCITY_X);
            }
        }
        // if we in the midair we want to look like we are jumping.
        if (transform().getVelocity().y() != 0){
            this.position = Position.JUMPING;
        } else{
            this.position = Position.WALKING;
        }
    }



    /**
     * In case of collision with the ground we want to make the Y speed to be 0.
     * @param other The GameObject with which a collision occurred.
     * @param collision Information regarding this collision.
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);

        if (other.getTag().equals(GROUND_TAG)){
            transform().setVelocityY(0);
        }
    }

    /**
     * create all the required renderers for the images.
     * @param imageReader object that can make an image to renderable object
     */
    private void createRenderers(ImageReader imageReader) {
        // standing init
        this.regularAvatarStandingImage = imageReader.readImage(REGULAR_AVATAR_IMAGE_STANDING,
                true);
        this.specialFlyingAvatarStandingImage = imageReader.readImage(SPECIAL_FLYING_AVATAR_IMAGE_STANDING,
                true);
        this.specialRunningAvatarStandingImage = imageReader.readImage(SPECIAL_RUNNING_AVATAR_IMAGE_STANDING,
                true);
        // jumping init
        this.regularAvatarJumpingImage = imageReader.readImage(REGULAR_AVATAR_IMAGE_JUMPING,
                true);
        this.specialFlyingAvatarJumpingImage = imageReader.readImage(SPECIAL_FLYING_AVATAR_IMAGE_JUMPING,
                true);
        this.specialRunningAvatarJumpingImage = imageReader.readImage(SPECIAL_RUNNING_AVATAR_IMAGE_JUMPING,
                true);
        // walking init
        for (int i = 0; i < NUM_OF_WALKING_IMAGES; i++) {
            this.regularAvatarWalkingImage[i] = imageReader.readImage(
                    Avatar.REGULAR_AVATAR_IMAGES_WALKING[i], true);
            this.specialFlyingAvatarWalkingImage[i] = imageReader.readImage(
                    Avatar.SPECIAL_FLYING_AVATAR_IMAGES_WALKING[i], true);
            this.specialRunningAvatarWalkingImage[i] = imageReader.readImage(
                    Avatar.SPECIAL_RUNNING_AVATAR_IMAGES_WALKING[i], true);
        }
        // flying init
        this.regularAvatarFlyingImage = imageReader.readImage(
                Avatar.REGULAR_AVATAR_IMAGES_FLYING, true);
        for (int i = 0; i < this.specialFlyingAvatarFlyingImage.length; i++) {
            this.specialFlyingAvatarFlyingImage[i] = imageReader.readImage(
                    Avatar.SPECIAL_FLYING_AVATAR_IMAGES_FLYING[i], true);
        }
        this.specialRunningAvatarFlyingImage = imageReader.readImage(
                Avatar.SPECIAL_RUNNING_AVATAR_IMAGES_FLYING, true);
    }
}

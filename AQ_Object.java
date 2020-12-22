package Editor;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class AQ_Object {

    private String mName;
    private int mAmount;
    private BufferedImage mImage;
    private int mGameBox;
    private String mImagePath;

    public static final int ARCADIA_QUEST = 1;
    public static final int INFERNO = 2;
    public static final int BEYOND_THE_GRAVE = 3;
    public static final int PETS = 4;
    public static final int WHOLE_LOTTA_LAVA = 5;
    public static final int RAIDERS = 6;
    public static final int FALL_OF_ARCADIA = 7;

    public static final int FIRE_DRAGON = 8;
    public static final int FROST_DRAGON = 9;
    public static final int POISON_DRAGON = 10;
    public static final int CHAOS_DRAGON = 11;

    public static final int OTHER = 12;

    public AQ_Object(String pImagePath, String pName, int pAmount, int pGameBox) {
        mImage = readImage(pImagePath);
        mImagePath = pImagePath;
        mName = pName;
        mAmount = pAmount;
        mGameBox = pGameBox;

    }

    public AQ_Object(AQ_Object pObject) {
        copy(pObject);
    }

    private BufferedImage readImage(String pPath) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File(pPath));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        return image;
    }

    public int getAmount() {
        return mAmount;
    }

    public BufferedImage getImage() {
        return mImage;
    }

    public String getName() {
        return mName;
    }

    public int getGameBox() {
        return mGameBox;
    }

    public String getImagePath() {
        return mImagePath;
    }

    public void setAmount(int pAmount) {
        mAmount = pAmount;
    }

    public void setName(String pName) {
        mName = pName;
    }

    public void setImagePath(String pPath) {
        mImagePath = pPath;
    }

    public void setGameBox(int pGameBox) {
        mGameBox = pGameBox;
    }

    public void setImage(BufferedImage pImage) {
        mImage = pImage;
    }

    public static BufferedImage deepCopy(BufferedImage bi) {
        ColorModel cm = bi.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = bi.copyData(null);
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }

    public void copy(AQ_Object pObject) {
        setName(pObject.getName());
        setAmount(pObject.getAmount());
        setImage(deepCopy(pObject.getImage()));
        setGameBox(pObject.getGameBox());
        setImagePath(pObject.getImagePath());
    }

}

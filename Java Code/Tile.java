
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Image;

import javax.imageio.ImageIO;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

public class Tile extends AQ_Object {
    /**
     * ////////////////////////////////////////////////////////////////////////////////////////
     * Variables
     * ////////////////////////////////////////////////////////////////////////////////////////
     */

    private BufferedImage[] mTileImages = new BufferedImage[5];
    private String mPath;
    private int mSelectedImage;
    private BufferedImage mCurrentImage;
    private ArrayList<AQ_Object> mNormalObjects;
    private Door[] mDoors = { null, null, null, null };
    private Tile[] mNeighbors = { null, null, null, null };
    private MapPanel mMapPanel;
    private AQ_Object mSelectedObject;

    private int mSize = 133;
    private int mStartX;
    private int mStartY;

    public static final int NORMAL_IMAGE = 0;
    public static final int GRAY_IMAGE = 1;
    public static final int START_IMAGE = 2;
    public static final int GREEN_IMAGE = 3;
    public static final int VIOLET_IMAGE = 4;
    public static final String[] TILE_TYPES = { "Normal", "Gray", "Start", "Green", "Violet" };
    public static final String FILE_TYPE_JPG = ".jpg";

    public static final int TOP = 0;
    public static final int RIGHT = 1;
    public static final int BOTTOM = 2;
    public static final int LEFT = 3;

    private JPopupMenu mPopupMenu;
    private JMenuItem mGrayTile;
    private JMenuItem mNormalTile;
    private JMenuItem mStartTile;
    private JMenuItem mGreenTile;
    private JMenuItem mVioletTile;

    /**
     * ////////////////////////////////////////////////////////////////////////////////////////
     * Constructor
     * ////////////////////////////////////////////////////////////////////////////////////////
     */

    public Tile(String pImagePath, String pName, int pGameBox) {
        super(pImagePath + "\\img" + pName + "\\img" + pName + TILE_TYPES[GRAY_IMAGE] + FILE_TYPE_JPG, pName, 1,
                pGameBox, 1, 1, 1, 1);
        mPath = pImagePath + "\\img" + pName + "\\img" + pName;
        mSelectedImage = GRAY_IMAGE;
        mTileImages = readImages(mPath, FILE_TYPE_JPG);
        mNormalObjects = new ArrayList<>();
        mCurrentImage = mTileImages[mSelectedImage];
        mMapPanel = null;
        initComponents();
    }

    public Tile(Tile pTile) {
        super(pTile.getImagePath(), pTile.getName(), pTile.getAmount(), pTile.getGameBox(), 1, 1, 1, 1);
        copy(pTile);
        initComponents();
    }

    private void initComponents() {

        mPopupMenu = new JPopupMenu();

        mNormalTile = new JMenuItem("Use normal tile");
        mNormalTile.addActionListener(e -> {
            setSelectedPos(Tile.NORMAL_IMAGE);
            setCurrentImage(mSelectedImage);
            mMapPanel.repaint();
        });

        mGrayTile = new JMenuItem("Use gray tile");
        mGrayTile.addActionListener(e -> {
            setSelectedPos(Tile.GRAY_IMAGE);
            setCurrentImage(mSelectedImage);
            mMapPanel.repaint();
        });

        mStartTile = new JMenuItem("Use start tile");
        mStartTile.addActionListener(e -> {
            setSelectedPos(Tile.START_IMAGE);
            setCurrentImage(mSelectedImage);
            mMapPanel.repaint();
        });

        mGreenTile = new JMenuItem("Use green tile");
        mGreenTile.addActionListener(e -> {
            setSelectedPos(Tile.GREEN_IMAGE);
            setCurrentImage(mSelectedImage);
            mMapPanel.repaint();
        });

        mVioletTile = new JMenuItem("Use violet tile");
        mVioletTile.addActionListener(e -> {
            setSelectedPos(Tile.VIOLET_IMAGE);
            setCurrentImage(mSelectedImage);
            mMapPanel.repaint();
        });

        mPopupMenu.add(mNormalTile);
        mPopupMenu.add(mGrayTile);
        mPopupMenu.add(mStartTile);
        mPopupMenu.add(mGreenTile);
        mPopupMenu.add(mVioletTile);

    }

    /**
     * ////////////////////////////////////////////////////////////////////////////////////////
     * Paint
     * ////////////////////////////////////////////////////////////////////////////////////////
     */

    public void paint(Graphics g) {
        // only draw if background image is not null
        if (mCurrentImage != null) {
            // draw background
            mCurrentImage = resize(mCurrentImage, mSize, mSize);
            g.drawImage(mCurrentImage, mStartX, mStartY, mCurrentImage.getWidth(), mCurrentImage.getHeight(), null);

            // draw Doors

            // draw Stone cards

            // draw Monsters
            if (mNormalObjects != null && mNormalObjects.size() > 0) {
                // draw Object in the center of Tile
                int amount = mNormalObjects.size();
                AQ_Object ob;
                // dummy Image
                BufferedImage img = new BufferedImage(1, 1, 1);
                int xStart;
                int yStart;
                int xEnd;
                int yEnd;

                if (amount == 1) {
                    // Center of Tile
                    xStart = mSize / 2 - img.getWidth() / 2;
                    yStart = mSize / 2 - img.getHeight() / 2;
                    drawObjectAtPos(g, mNormalObjects.get(0), xStart, yStart);

                } else {
                    ////
                    // Top-Right
                    ////

                    xStart = mSize - img.getWidth() - 5;
                    yStart = 5;
                    drawObjectAtPos(g, mNormalObjects.get(0), xStart, yStart);

                    ////
                    // Bottom-Left
                    ////

                    xStart = 5;
                    yStart = mSize - img.getHeight() - 5;
                    drawObjectAtPos(g, mNormalObjects.get(1), xStart, yStart);

                    if (amount >= 3) {
                        ////
                        // Bottom-Right
                        ////

                        xStart = mSize - img.getWidth() - 5;
                        yStart = mSize - img.getHeight() - 5;
                        drawObjectAtPos(g, mNormalObjects.get(1), xStart, yStart);
                    }
                    if (amount == 4) {
                        ////
                        // Top-Left
                        ////

                        xStart = 5;
                        yStart = 5;
                        drawObjectAtPos(g, mNormalObjects.get(1), xStart, yStart);
                    }

                }
            }
        }
    }

    public void drawObjectAtPos(Graphics g, AQ_Object ob, int pStartX, int pStartY) {
        ob.setImage(resize(ob.getImage(), ob.getPrefImageWidth(), ob.getPrefImageHeight()));
        BufferedImage img = ob.getImage();

        int xEnd = img.getWidth();
        int yEnd = img.getHeight();

        g.drawImage(img, pStartX, pStartY, xEnd, yEnd, null);
        // Draw Border
        if (mSelectedObject != null && ob == mSelectedObject) {
            g.setColor(Color.BLACK);
            g.drawRect(pStartX - 1, pStartY - 1, xEnd + 1, yEnd + 1);
        }

    }

    /**
     * ////////////////////////////////////////////////////////////////////////////////////////
     * Static
     * ////////////////////////////////////////////////////////////////////////////////////////
     */

    public static BufferedImage[] readImages(String pPicturePath, String pPictureType) {
        BufferedImage[] images = new BufferedImage[5];
        try {
            for (int i = 0; i < images.length; i++) {
                images[i] = ImageIO.read(new File(pPicturePath + TILE_TYPES[i] + pPictureType));
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        return images;
    }

    public static BufferedImage resize(BufferedImage img, int width, int height) {
        Image tmp = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resized.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        return resized;
    }

    /**
     * ////////////////////////////////////////////////////////////////////////////////////////
     * Rotate mehtods
     * ////////////////////////////////////////////////////////////////////////////////////////
     */

    private BufferedImage rotate(BufferedImage pImg, double angle) {
        if (pImg != null) {
            int w = pImg.getWidth();
            int h = pImg.getHeight();

            BufferedImage rotated = new BufferedImage(w, h, pImg.getType());
            Graphics2D graphic = rotated.createGraphics();
            graphic.rotate(Math.toRadians(angle), w / 2, h / 2);
            graphic.drawImage(pImg, null, 0, 0);
            graphic.dispose();
            return rotated;
        }
        return null;
    }

    public void rotateRight() {
        System.out.println("Rotate right");
        for (int i = 0; i < mTileImages.length; i++) {
            BufferedImage rotated = rotate(mTileImages[i], 90);
            mTileImages[i] = rotated;
        }
        setCurrentImage(mSelectedImage);
    }

    public void rotateLeft() {
        for (int i = 0; i < mTileImages.length; i++) {
            BufferedImage rotated = rotate(mTileImages[i], -90);
            mTileImages[i] = rotated;
        }
        setCurrentImage(mSelectedImage);
    }

    public void rotate180() {
        for (int i = 0; i < mTileImages.length; i++) {
            BufferedImage rotated = rotate(mTileImages[i], 180);
            mTileImages[i] = rotated;
        }
        setCurrentImage(mSelectedImage);
    }

    /**
     * ////////////////////////////////////////////////////////////////////////////////////////
     * Getter
     * ////////////////////////////////////////////////////////////////////////////////////////
     */

    public BufferedImage[] getImages() {
        return mTileImages;
    }

    public String getPath() {
        return mPath;
    }

    public BufferedImage getCurrentImage() {
        return mTileImages[mSelectedImage];
    }

    public BufferedImage getImageAtPos(int pPos) {
        return mTileImages[pPos];
    }

    public int getSelectedPos() {
        return mSelectedImage;
    }

    public AQ_Object getAQ_ObjectAtLocation(int pPosX, int pPosY) {
        int halfPanelSize = mSize / 2;
        AQ_Object o;
        BufferedImage img;
        int startW;
        int startH;
        if (mNormalObjects == null) {
            return null;
        }
        if (mNormalObjects.size() == 0) {
            mSelectedObject = null;
            return null;
        }
        if (mNormalObjects.size() == 1) {
            // picture in the center of the panel
            o = mNormalObjects.get(0);
            img = o.getImage();
            startW = halfPanelSize - img.getWidth() / 2;
            startH = halfPanelSize - img.getHeight() / 2;
            if (pPosX >= startW && pPosY >= startH && pPosX <= startW + img.getWidth()
                    && pPosY <= startH + img.getHeight()) {
                mSelectedObject = o;
                return o;
            }
            mSelectedObject = null;
            return null;
        } else {
            // picture in the top right-hand corner
            o = mNormalObjects.get(0);
            img = o.getImage();
            startW = halfPanelSize + 5;
            startH = 7;
            if (pPosX >= startW && pPosY >= startH && pPosX <= startW + img.getWidth()
                    && pPosY <= startH + img.getHeight()) {
                mSelectedObject = o;
                return o;
            }
            // picture in the bottom left-hand corner
            o = mNormalObjects.get(1);
            img = o.getImage();
            startW = 7;
            startH = halfPanelSize + 5;
            if (pPosX >= startW && pPosY >= startH && pPosX <= startW + img.getWidth()
                    && pPosY <= startH + img.getHeight()) {
                mSelectedObject = o;
                return o;
            }
            if (mNormalObjects.size() >= 3) {
                // picture in the bottom right-hand Corner
                o = mNormalObjects.get(2);
                img = o.getImage();
                startW = halfPanelSize + 5;
                startH = halfPanelSize + 5;
                if (pPosX >= startW && pPosY >= startH && pPosX <= startW + img.getWidth()
                        && pPosY <= startH + img.getHeight()) {
                    mSelectedObject = o;
                    return o;
                }
                // picture in the top left-hand corner
                if (mNormalObjects.size() == 4) {
                    o = mNormalObjects.get(3);
                    img = o.getImage();
                    startW = 5;
                    startH = 5;
                    if (pPosX >= startW && pPosY >= startH && pPosX <= startW + img.getWidth()
                            && pPosY <= startH + img.getHeight()) {
                        mSelectedObject = o;
                        return o;
                    }
                }
            }
            mSelectedObject = null;
            return null;
        }
    }

    private int getCurrentSize() {
        int currentSize = 0;
        for (AQ_Object o : mNormalObjects) {
            if (o instanceof Monster) {
                currentSize += ((Monster) o).getSize();
            }
        }
        return currentSize;
    }

    public ArrayList<AQ_Object> getAqObecjts() {
        return mNormalObjects;
    }

    public AQ_Object getAqObecjtAtPos(int pPos) {
        return mNormalObjects.get(pPos);
    }

    public int getStartPosX() {
        return mStartX;
    }

    public int getStartPosY() {
        return mStartY;
    }

    public int getSize() {
        return mSize;
    }

    public Tile[] getNeighbors() {
        return mNeighbors;
    }

    /**
     * ////////////////////////////////////////////////////////////////////////////////////////
     * Setter
     * ////////////////////////////////////////////////////////////////////////////////////////
     */

    public void setStartPos(int pPosX, int pPosY) {
        mStartX = pPosX;
        mStartY = pPosY;
    }

    public void setImages(BufferedImage[] pTilesImages) {
        mTileImages = pTilesImages;
    }

    public void setPath(String pPath) {
        mPath = pPath;
    }

    public BufferedImage[] loadImages() {
        BufferedImage[] newImages = new BufferedImage[5];
        newImages = readImages(mPath, FILE_TYPE_JPG);
        return newImages;
    }

    public void setSelectedPos(int pPos) {
        mSelectedImage = pPos;
    }

    public void setImageAtPos(BufferedImage pImg, int pPos) {
        mTileImages[pPos] = pImg;
    }

    public void setCurrentImage(BufferedImage pImg) {
        mCurrentImage = pImg;
    }

    public void setCurrentImage(int pPos) {
        mCurrentImage = mTileImages[pPos];
    }

    public void setAllImages(BufferedImage[] pImg) {
        mTileImages = pImg;
    }

    public void setPopupMenuLocation(int pPosX, int pPosY) {
        mPopupMenu.setLocation(pPosX, pPosY);
    }

    public void setShowPopup(boolean pShow) {
        mPopupMenu.setVisible(pShow);
    }

    public void deSelectObject() {
        mSelectedObject = null;
    }

    public void addAqObject(AQ_Object pObject) {
        if (mNormalObjects.size() < 4) {
            if (pObject instanceof Monster) {
                if ((getCurrentSize() + ((Monster) pObject).getSize()) <= 2) {
                    mNormalObjects.add(pObject);
                }
            } else {
                mNormalObjects.add(pObject);
            }
            mMapPanel.revalidate();
            mMapPanel.repaint();

        }
    }

    public void setAqObjects(ArrayList<AQ_Object> pObjects) {
        mNormalObjects = pObjects;
    }

    public void removeAqObject(AQ_Object pObject) {
        if (mNormalObjects.size() >= 1) {
            mNormalObjects.remove(pObject);
            mMapPanel.revalidate();
            mMapPanel.repaint();
        }
    }

    public void setSize(int pSize) {
        mSize = pSize;
    }

    public void setMapPanel(MapPanel pMapPanel) {
        mMapPanel = pMapPanel;
    }

    public void setNeighborAtPos(Tile pTile, int pPos) {
        mNeighbors[pPos] = pTile;
    }

    public void setNeighbors(Tile[] pNeighbors) {
        mNeighbors = pNeighbors;
    }

    public void copy(Tile pTile) {
        BufferedImage[] newImages = new BufferedImage[5];
        for (int i = 0; i < newImages.length; i++) {
            newImages[i] = AQ_Object.deepCopy(pTile.getImageAtPos(i));
        }
        this.setImages(newImages);
        this.setSelectedPos(pTile.getSelectedPos());
        this.setPath(pTile.getPath());
        this.setAqObjects(new ArrayList<>(pTile.getAqObecjts()));
        this.setCurrentImage(deepCopy(pTile.getCurrentImage()));
        this.setSize(pTile.getSize());
        this.setNeighbors(pTile.getNeighbors());
        this.setStartPos(pTile.getStartPosX(), pTile.getStartPosY());
    }
}

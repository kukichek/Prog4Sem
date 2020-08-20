import com.sun.j3d.utils.universe.SimpleUniverse;
import com.sun.j3d.utils.geometry.*;

import javax.media.j3d.*;
import javax.swing.*;
import javax.vecmath.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class MainWindow extends JFrame {
    private Text3D text3D;
    private Light frontLight;
    private Light backLight;
    private ColoringAttributes frontLightColorAttributes = new ColoringAttributes();
    private ColoringAttributes backLightColorAttributes = new ColoringAttributes();
    private Sphere frontLightSphere;

    private int VERTICAL_OFFSET = 100;
    private int HORIZONTAL_OFFSET = 450;
    private int WINDOW_WIDTH = 640;
    private int WINDOW_HEIGHT = 480;

    private MainWindow() {
        super("Text 3D");
        setLocation(new Point(HORIZONTAL_OFFSET, VERTICAL_OFFSET));
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel root = new JPanel(new BorderLayout());
        root.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        setResizable(false);

        GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
        Canvas3D canvas = new Canvas3D(config);
        SimpleUniverse simpleUniverse = new SimpleUniverse(canvas);
        BranchGroup branchGroup = new BranchGroup();

        TransformGroup sceneTransformGroup = new TransformGroup();
        Transform3D scaleTransform = new Transform3D();
        scaleTransform.setScale(0.3);
        sceneTransformGroup.setTransform(scaleTransform);
        branchGroup.addChild(sceneTransformGroup);

        BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100.0);

        Vector3d frontLightPos = new Vector3d(1, 0.8, 1.5);
        Vector3d backLightPos = new Vector3d(3, 3, -6);

        Point3f attenuation = new Point3f(1f, 0f, 0f);

        frontLightSphere = new Sphere(0.2f);
        Sphere backLightSphere = new Sphere(0.2f);

        frontLight = initLight(new Color3f((float) 0xCB / 0xFF, (float) 0xF9 / 0xFF, (float) 0x3E / 0xFF), frontLightPos, attenuation, bounds, frontLightSphere, frontLightColorAttributes);
        backLight = initLight(new Color3f((float) 0xEF / 0xFF, 0, (float) 0x2A / 0xFF), backLightPos, attenuation, bounds, backLightSphere, backLightColorAttributes);

        TransformGroup frontLightTransformGroup = initLightTransformGroup(frontLightPos, frontLight, frontLightSphere);
        TransformGroup backLightTransformGroup = initLightTransformGroup(backLightPos, backLight, backLightSphere);

        text3D = init3DText();

        TransformGroup textTransformGroup = new TransformGroup();
        Shape3D textShape = wrapTextWithShape(text3D);

        Transform3D rotateText = new Transform3D();
        rotateText.rotX(0.4);

        textTransformGroup.setTransform(rotateText);
        textTransformGroup.addChild(textShape);

        sceneTransformGroup.addChild(textTransformGroup);
        sceneTransformGroup.addChild(frontLightTransformGroup);
        sceneTransformGroup.addChild(backLightTransformGroup);

        branchGroup.compile();

        canvas.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                String text = text3D.getString();
                if (e.getKeyChar() == '\b') {
                    if (!text.isEmpty()) {
                        String newText = text.substring(0, text.length() - 1);
                        text3D.setString(newText);
                    }
                } else {
                    text3D.setString(text + String.valueOf(e.getKeyChar()));
                }
            }
        });

        root.add(canvas, BorderLayout.CENTER);
        root.add(initSettingsPanel(frontLightTransformGroup), BorderLayout.SOUTH);

        simpleUniverse.getViewingPlatform().setNominalViewingTransform();
        simpleUniverse.getViewer().getView().setBackClipDistance(100);
        simpleUniverse.addBranchGraph(branchGroup);

        add(root);
        setVisible(true);
        pack();
    }

    void setLightColorWitDialog(Light light, ColoringAttributes attributes) {
        Color color = JColorChooser.showDialog(null, "Choose color for light", Color.black);
        if (color != null) {
            light.setColor(new Color3f(color));
            attributes.setColor(new Color3f(color));
        }
    }

    private JPanel initSettingsPanel(TransformGroup lightTransformGroup) {
        JPanel panel = new JPanel(new FlowLayout());
        panel.setPreferredSize(new Dimension(WINDOW_WIDTH, 40));

        Button setFrontLightColorButton = new Button("Front Light Color");
        setFrontLightColorButton.addActionListener(e -> { setLightColorWitDialog(frontLight, frontLightColorAttributes); });
        Button setBackLightColorButton = new Button("Back Light Color");
        setBackLightColorButton.addActionListener(e -> { setLightColorWitDialog(backLight, backLightColorAttributes); });

        JSlider frontLightSlider = new JSlider(-300, 300, 50);
        frontLightSlider.addChangeListener(e -> {
            Vector3d vector = new Vector3d();
            Transform3D transform = new Transform3D();
            lightTransformGroup.getTransform(transform);
            transform.get(vector);
            vector.x = (double) frontLightSlider.getValue() / 50;
            transform.setTranslation(vector);
            lightTransformGroup.setTransform(transform);
        });

        panel.add(setFrontLightColorButton);
        panel.add(setBackLightColorButton);
        panel.add(frontLightSlider);

        return panel;
    }

    private Light initLight(Color3f color, Vector3d position, Point3f attenuation, BoundingSphere bounds, Sphere lightSphere, ColoringAttributes coloringAttributes) {
        coloringAttributes.setCapability(ColoringAttributes.ALLOW_COLOR_WRITE);
        coloringAttributes.setColor(color);
        Appearance appearance = new Appearance();
        appearance.setColoringAttributes(coloringAttributes);
        lightSphere.setAppearance(appearance);

        PointLight light = new PointLight(color, new Point3f(position), attenuation);
        light.setCapability(Light.ALLOW_COLOR_WRITE);
        light.setInfluencingBounds(bounds);

        return light;
    }

    private TransformGroup initLightTransformGroup(Vector3d position, Light light, Sphere lightSphere) {
        Transform3D lightTransform = new Transform3D();
        lightTransform.set(position);
        TransformGroup lightTransformGroup = new TransformGroup(lightTransform);
        lightTransformGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
        lightTransformGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        lightTransformGroup.addChild(light);
        lightTransformGroup.addChild(lightSphere);

        return lightTransformGroup;
    }

    private Text3D init3DText() {
        Font3D textFont = new Font3D(new Font("Verdana", Font.PLAIN, 1), new FontExtrusion());
        Text3D text = new Text3D(textFont, "Sample Text", new Point3f(0f, -1.8f, -5f));

        text.setAlignment(Text3D.ALIGN_CENTER);
        text.setCapability(Text3D.ALLOW_STRING_READ);
        text.setCapability(Text3D.ALLOW_STRING_WRITE);

        return text;
    }

    private Shape3D wrapTextWithShape(Text3D text3D) {
        Material material = new Material();
        material.setLightingEnable(true);

        Appearance appearance = new Appearance();
        appearance.setMaterial(material);

        Shape3D textShape = new Shape3D();
        textShape.setGeometry(text3D);
        textShape.setAppearance(appearance);

        return textShape;
    }

    public static void main(String[] args) {
        new MainWindow();
    }
}

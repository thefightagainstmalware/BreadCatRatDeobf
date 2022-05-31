/*
 * Decompiled with CFR 0.152.
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.util.Locale;
import java.util.Objects;
import java.util.jar.JarFile;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;

public class SkyblockAddonsInstallerFrame
extends JFrame
implements ActionListener,
MouseListener {
    private static final Pattern IN_MODS_SUBFOLDER = Pattern.compile("1\\.8\\.9[/\\\\]?$");
    private JLabel logo = null;
    private JLabel versionInfo = null;
    private JLabel labelFolder = null;
    private JPanel panelCenter = null;
    private JPanel panelBottom = null;
    private JPanel totalContentPane = null;
    private JTextArea descriptionText = null;
    private JTextArea forgeDescriptionText = null;
    private JTextField textFieldFolderLocation = null;
    private JButton buttonChooseFolder = null;
    private JButton buttonInstall = null;
    private JButton buttonOpenFolder = null;
    private JButton buttonClose = null;
    private static final int TOTAL_HEIGHT = 435;
    private static final int TOTAL_WIDTH = 404;
    private int x = 0;
    private int y = 0;
    private int w = 404;
    private int h;
    private int margin;

    public SkyblockAddonsInstallerFrame() {
        try {
            this.setName("SkyblockAddonsInstallerFrame");
            this.setTitle("SkyblockAddons Installer");
            this.setResizable(false);
            this.setSize(404, 435);
            this.setContentPane(this.getPanelContentPane());
            this.getButtonFolder().addActionListener(this);
            this.getButtonInstall().addActionListener(this);
            this.getButtonOpenFolder().addActionListener(this);
            this.getButtonClose().addActionListener(this);
            this.getForgeTextArea().addMouseListener(this);
            this.pack();
            this.setDefaultCloseOperation(3);
            this.getFieldFolder().setText(this.getModsFolder().getPath());
            this.getButtonInstall().setEnabled(true);
            this.getButtonInstall().requestFocus();
        }
        catch (Exception ex) {
            SkyblockAddonsInstallerFrame.showErrorPopup(ex);
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            SkyblockAddonsInstallerFrame frame = new SkyblockAddonsInstallerFrame();
            frame.centerFrame(frame);
            frame.setVisible(true);
        }
        catch (Exception ex) {
            SkyblockAddonsInstallerFrame.showErrorPopup(ex);
        }
    }

    private JPanel getPanelContentPane() {
        if (this.totalContentPane == null) {
            try {
                this.totalContentPane = new JPanel();
                this.totalContentPane.setName("PanelContentPane");
                this.totalContentPane.setLayout(new BorderLayout(5, 5));
                this.totalContentPane.setPreferredSize(new Dimension(404, 435));
                this.totalContentPane.add((Component)this.getPanelCenter(), "Center");
                this.totalContentPane.add((Component)this.getPanelBottom(), "South");
            }
            catch (Throwable ivjExc) {
                SkyblockAddonsInstallerFrame.showErrorPopup(ivjExc);
            }
        }
        return this.totalContentPane;
    }

    private JPanel getPanelCenter() {
        if (this.panelCenter == null) {
            try {
                this.panelCenter = new JPanel();
                this.panelCenter.setName("PanelCenter");
                this.panelCenter.setLayout(null);
                this.panelCenter.add((Component)this.getPictureLabel(), this.getPictureLabel().getName());
                this.panelCenter.add((Component)this.getVersionInfo(), this.getVersionInfo().getName());
                this.panelCenter.add((Component)this.getTextArea(), this.getTextArea().getName());
                this.panelCenter.add((Component)this.getForgeTextArea(), this.getForgeTextArea().getName());
                this.panelCenter.add((Component)this.getLabelFolder(), this.getLabelFolder().getName());
                this.panelCenter.add((Component)this.getFieldFolder(), this.getFieldFolder().getName());
                this.panelCenter.add((Component)this.getButtonFolder(), this.getButtonFolder().getName());
            }
            catch (Throwable ivjExc) {
                SkyblockAddonsInstallerFrame.showErrorPopup(ivjExc);
            }
        }
        return this.panelCenter;
    }

    private JLabel getPictureLabel() {
        if (this.logo == null) {
            try {
                this.h = this.w / 2;
                this.margin = 5;
                BufferedImage myPicture = ImageIO.read(Objects.requireNonNull(this.getClass().getClassLoader().getResourceAsStream("assets/skyblockaddons/logo.png"), "Logo not found."));
                Image scaled = myPicture.getScaledInstance(this.w - this.margin * 2, this.h - this.margin, 4);
                this.logo = new JLabel(new ImageIcon(scaled));
                this.logo.setName("Logo");
                this.logo.setBounds(this.x + this.margin, this.y + this.margin, this.w - this.margin * 2, this.h - this.margin);
                this.logo.setFont(new Font("Dialog", 1, 18));
                this.logo.setHorizontalAlignment(0);
                this.logo.setPreferredSize(new Dimension(this.w, this.h));
                this.y += this.h;
            }
            catch (Throwable ivjExc) {
                SkyblockAddonsInstallerFrame.showErrorPopup(ivjExc);
            }
        }
        return this.logo;
    }

    private JLabel getVersionInfo() {
        if (this.versionInfo == null) {
            try {
                this.h = 25;
                this.versionInfo = new JLabel();
                this.versionInfo.setName("LabelMcVersion");
                this.versionInfo.setBounds(this.x, this.y, this.w, this.h);
                this.versionInfo.setFont(new Font("Dialog", 1, 14));
                this.versionInfo.setHorizontalAlignment(0);
                this.versionInfo.setPreferredSize(new Dimension(this.w, this.h));
                this.versionInfo.setText("v" + this.getVersionFromMcmodInfo() + " by Biscuit - for Minecraft 1.8.9");
                this.y += this.h;
            }
            catch (Throwable ivjExc) {
                SkyblockAddonsInstallerFrame.showErrorPopup(ivjExc);
            }
        }
        return this.versionInfo;
    }

    private JTextArea getTextArea() {
        if (this.descriptionText == null) {
            try {
                this.h = 60;
                this.margin = 10;
                this.descriptionText = new JTextArea();
                this.descriptionText.setName("TextArea");
                this.setTextAreaProperties(this.descriptionText);
                this.descriptionText.setText("This installer will copy SkyblockAddons into your forge mods folder for you, and replace any old versions that already exist. Close this if you prefer to do this yourself!");
                this.descriptionText.setWrapStyleWord(true);
                this.y += this.h;
            }
            catch (Throwable ivjExc) {
                SkyblockAddonsInstallerFrame.showErrorPopup(ivjExc);
            }
        }
        return this.descriptionText;
    }

    private void setTextAreaProperties(JTextArea textArea) {
        textArea.setBounds(this.x + this.margin, this.y + this.margin, this.w - this.margin * 2, this.h - this.margin);
        textArea.setEditable(false);
        textArea.setHighlighter(null);
        textArea.setEnabled(true);
        textArea.setFont(new Font("Dialog", 0, 12));
        textArea.setLineWrap(true);
        textArea.setOpaque(false);
        textArea.setPreferredSize(new Dimension(this.w - this.margin * 2, this.h - this.margin));
    }

    private JTextArea getForgeTextArea() {
        if (this.forgeDescriptionText == null) {
            try {
                this.h = 55;
                this.margin = 10;
                this.forgeDescriptionText = new JTextArea();
                this.forgeDescriptionText.setName("TextAreaForge");
                this.setTextAreaProperties(this.forgeDescriptionText);
                this.forgeDescriptionText.setText("However, you still need to install Forge client in order to be able to run this mod. Click here to visit the download page for Forge 1.8.9!");
                this.forgeDescriptionText.setForeground(Color.BLUE.darker());
                this.forgeDescriptionText.setCursor(Cursor.getPredefinedCursor(12));
                this.forgeDescriptionText.setWrapStyleWord(true);
                this.y += this.h;
            }
            catch (Throwable ivjExc) {
                SkyblockAddonsInstallerFrame.showErrorPopup(ivjExc);
            }
        }
        return this.forgeDescriptionText;
    }

    private JLabel getLabelFolder() {
        if (this.labelFolder == null) {
            this.h = 16;
            this.w = 65;
            this.x += 10;
            try {
                this.labelFolder = new JLabel();
                this.labelFolder.setName("LabelFolder");
                this.labelFolder.setBounds(this.x, this.y + 2, this.w, this.h);
                this.labelFolder.setPreferredSize(new Dimension(this.w, this.h));
                this.labelFolder.setText("Mods Folder");
            }
            catch (Throwable ivjExc) {
                SkyblockAddonsInstallerFrame.showErrorPopup(ivjExc);
            }
            this.x += this.w;
        }
        return this.labelFolder;
    }

    private JTextField getFieldFolder() {
        if (this.textFieldFolderLocation == null) {
            this.h = 20;
            this.w = 287;
            try {
                this.textFieldFolderLocation = new JTextField();
                this.textFieldFolderLocation.setName("FieldFolder");
                this.textFieldFolderLocation.setBounds(this.x, this.y, this.w, this.h);
                this.textFieldFolderLocation.setEditable(false);
                this.textFieldFolderLocation.setPreferredSize(new Dimension(this.w, this.h));
            }
            catch (Throwable ivjExc) {
                SkyblockAddonsInstallerFrame.showErrorPopup(ivjExc);
            }
            this.x += this.w;
        }
        return this.textFieldFolderLocation;
    }

    private JButton getButtonFolder() {
        if (this.buttonChooseFolder == null) {
            this.h = 20;
            this.w = 25;
            this.x += 10;
            try {
                BufferedImage myPicture = ImageIO.read(Objects.requireNonNull(this.getClass().getClassLoader().getResourceAsStream("assets/skyblockaddons/gui/folder.png"), "Folder icon not found."));
                Image scaled = myPicture.getScaledInstance(this.w - 8, this.h - 6, 4);
                this.buttonChooseFolder = new JButton(new ImageIcon(scaled));
                this.buttonChooseFolder.setName("ButtonFolder");
                this.buttonChooseFolder.setBounds(this.x, this.y, this.w, this.h);
                this.buttonChooseFolder.setPreferredSize(new Dimension(this.w, this.h));
            }
            catch (Throwable ivjExc) {
                SkyblockAddonsInstallerFrame.showErrorPopup(ivjExc);
            }
        }
        return this.buttonChooseFolder;
    }

    private JPanel getPanelBottom() {
        if (this.panelBottom == null) {
            try {
                this.panelBottom = new JPanel();
                this.panelBottom.setName("PanelBottom");
                this.panelBottom.setLayout(new FlowLayout(1, 15, 10));
                this.panelBottom.setPreferredSize(new Dimension(390, 55));
                this.panelBottom.add((Component)this.getButtonInstall(), this.getButtonInstall().getName());
                this.panelBottom.add((Component)this.getButtonOpenFolder(), this.getButtonOpenFolder().getName());
                this.panelBottom.add((Component)this.getButtonClose(), this.getButtonClose().getName());
            }
            catch (Throwable ivjExc) {
                SkyblockAddonsInstallerFrame.showErrorPopup(ivjExc);
            }
        }
        return this.panelBottom;
    }

    private JButton getButtonInstall() {
        if (this.buttonInstall == null) {
            this.w = 100;
            this.h = 26;
            try {
                this.buttonInstall = new JButton();
                this.buttonInstall.setName("ButtonInstall");
                this.buttonInstall.setPreferredSize(new Dimension(this.w, this.h));
                this.buttonInstall.setText("Install");
            }
            catch (Throwable ivjExc) {
                SkyblockAddonsInstallerFrame.showErrorPopup(ivjExc);
            }
        }
        return this.buttonInstall;
    }

    private JButton getButtonOpenFolder() {
        if (this.buttonOpenFolder == null) {
            this.w = 130;
            this.h = 26;
            try {
                this.buttonOpenFolder = new JButton();
                this.buttonOpenFolder.setName("ButtonOpenFolder");
                this.buttonOpenFolder.setPreferredSize(new Dimension(this.w, this.h));
                this.buttonOpenFolder.setText("Open Mods Folder");
            }
            catch (Throwable ivjExc) {
                SkyblockAddonsInstallerFrame.showErrorPopup(ivjExc);
            }
        }
        return this.buttonOpenFolder;
    }

    private JButton getButtonClose() {
        if (this.buttonClose == null) {
            this.w = 100;
            this.h = 26;
            try {
                this.buttonClose = new JButton();
                this.buttonClose.setName("ButtonClose");
                this.buttonClose.setPreferredSize(new Dimension(this.w, this.h));
                this.buttonClose.setText("Cancel");
            }
            catch (Throwable ivjExc) {
                SkyblockAddonsInstallerFrame.showErrorPopup(ivjExc);
            }
        }
        return this.buttonClose;
    }

    public void onFolderSelect() {
        File currentDirectory = new File(this.getFieldFolder().getText());
        JFileChooser jFileChooser = new JFileChooser(currentDirectory);
        jFileChooser.setFileSelectionMode(1);
        jFileChooser.setAcceptAllFileFilterUsed(false);
        if (jFileChooser.showOpenDialog(this) == 0) {
            File newDirectory = jFileChooser.getSelectedFile();
            this.getFieldFolder().setText(newDirectory.getPath());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.getButtonClose()) {
            this.dispose();
            System.exit(0);
        }
        if (e.getSource() == this.getButtonFolder()) {
            this.onFolderSelect();
        }
        if (e.getSource() == this.getButtonInstall()) {
            this.onInstall();
        }
        if (e.getSource() == this.getButtonOpenFolder()) {
            this.onOpenFolder();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == this.getForgeTextArea()) {
            try {
                Desktop.getDesktop().browse(new URI("http://files.minecraftforge.net/maven/net/minecraftforge/forge/index_1.8.9.html"));
            }
            catch (IOException | URISyntaxException ex) {
                SkyblockAddonsInstallerFrame.showErrorPopup(ex);
            }
        }
    }

    public void onInstall() {
        try {
            File modsFolder = new File(this.getFieldFolder().getText());
            if (!modsFolder.exists()) {
                this.showErrorMessage("Folder not found: " + modsFolder.getPath());
                return;
            }
            if (!modsFolder.isDirectory()) {
                this.showErrorMessage("Not a folder: " + modsFolder.getPath());
                return;
            }
            this.tryInstall(modsFolder);
        }
        catch (Exception e) {
            SkyblockAddonsInstallerFrame.showErrorPopup(e);
        }
    }

    private void tryInstall(File modsFolder) {
        File thisFile = this.getThisFile();
        if (thisFile != null) {
            boolean failed;
            File newFile;
            boolean inSubFolder = false;
            if (IN_MODS_SUBFOLDER.matcher(modsFolder.getPath()).find()) {
                inSubFolder = true;
            }
            if (thisFile.equals(newFile = new File(modsFolder, "SkyblockAddons-1.8.9-" + this.getVersionFromMcmodInfo() + ".jar"))) {
                this.showErrorMessage("You are opening this file from where the file should be installed... there's nothing to be done!");
                return;
            }
            boolean deletingFailure = false;
            if (modsFolder.isDirectory() && (failed = this.findSkyblockAddonsAndDelete(modsFolder.listFiles()))) {
                deletingFailure = true;
            }
            if (inSubFolder) {
                if (modsFolder.getParentFile().isDirectory() && (failed = this.findSkyblockAddonsAndDelete(modsFolder.getParentFile().listFiles()))) {
                    deletingFailure = true;
                }
            } else {
                boolean failed2;
                File subFolder = new File(modsFolder, "1.8.9");
                if (subFolder.exists() && subFolder.isDirectory() && (failed2 = this.findSkyblockAddonsAndDelete(subFolder.listFiles()))) {
                    deletingFailure = true;
                }
            }
            if (deletingFailure) {
                return;
            }
            if (thisFile.isDirectory()) {
                this.showErrorMessage("This file is a directory... Are we in a development environment?");
                return;
            }
            try {
                Files.copy(thisFile.toPath(), newFile.toPath(), new CopyOption[0]);
            }
            catch (Exception ex) {
                SkyblockAddonsInstallerFrame.showErrorPopup(ex);
                return;
            }
            this.showMessage("SkyblockAddons has been successfully installed into your mods folder.");
            this.dispose();
            System.exit(0);
        }
    }

    private boolean findSkyblockAddonsAndDelete(File[] files) {
        if (files == null) {
            return false;
        }
        for (File file : files) {
            if (file.isDirectory() || !file.getPath().endsWith(".jar")) continue;
            try {
                InputStream inputStream;
                String modID;
                JarFile jarFile = new JarFile(file);
                ZipEntry mcModInfo = jarFile.getEntry("mcmod.info");
                if (mcModInfo != null && (modID = this.getModIDFromInputStream(inputStream = jarFile.getInputStream(mcModInfo))).equals("skyblockaddons")) {
                    jarFile.close();
                    try {
                        boolean deleted = file.delete();
                        if (deleted) continue;
                        throw new Exception();
                    }
                    catch (Exception ex) {
                        ex.printStackTrace();
                        this.showErrorMessage("Was not able to delete the other SkyblockAddons files found in your mods folder!" + System.lineSeparator() + "Please make sure that your minecraft is currently closed and try again, or feel" + System.lineSeparator() + "free to open your mods folder and delete those files manually.");
                        return true;
                    }
                }
                jarFile.close();
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        return false;
    }

    public void onOpenFolder() {
        try {
            Desktop.getDesktop().open(this.getModsFolder());
        }
        catch (Exception e) {
            SkyblockAddonsInstallerFrame.showErrorPopup(e);
        }
    }

    public File getModsFolder() {
        String userHome = System.getProperty("user.home", ".");
        File modsFolder = this.getFile(userHome, "minecraft/mods/1.8.9");
        if (!modsFolder.exists()) {
            modsFolder = this.getFile(userHome, "minecraft/mods");
        }
        if (!modsFolder.exists() && !modsFolder.mkdirs()) {
            throw new RuntimeException("The working directory could not be created: " + modsFolder);
        }
        return modsFolder;
    }

    public File getFile(String userHome, String minecraftPath) {
        File workingDirectory;
        switch (this.getOperatingSystem()) {
            case LINUX: 
            case SOLARIS: {
                workingDirectory = new File(userHome, '.' + minecraftPath + '/');
                break;
            }
            case WINDOWS: {
                String applicationData = System.getenv("APPDATA");
                if (applicationData != null) {
                    workingDirectory = new File(applicationData, "." + minecraftPath + '/');
                    break;
                }
                workingDirectory = new File(userHome, '.' + minecraftPath + '/');
                break;
            }
            case MACOS: {
                workingDirectory = new File(userHome, "Library/Application Support/" + minecraftPath);
                break;
            }
            default: {
                workingDirectory = new File(userHome, minecraftPath + '/');
            }
        }
        return workingDirectory;
    }

    public OperatingSystem getOperatingSystem() {
        String osName = System.getProperty("os.name").toLowerCase(Locale.US);
        if (osName.contains("win")) {
            return OperatingSystem.WINDOWS;
        }
        if (osName.contains("mac")) {
            return OperatingSystem.MACOS;
        }
        if (osName.contains("solaris") || osName.contains("sunos")) {
            return OperatingSystem.SOLARIS;
        }
        if (osName.contains("linux") || osName.contains("unix")) {
            return OperatingSystem.LINUX;
        }
        return OperatingSystem.UNKNOWN;
    }

    public void centerFrame(JFrame frame) {
        Rectangle rectangle = frame.getBounds();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Rectangle screenRectangle = new Rectangle(0, 0, screenSize.width, screenSize.height);
        int newX = screenRectangle.x + (screenRectangle.width - rectangle.width) / 2;
        int newY = screenRectangle.y + (screenRectangle.height - rectangle.height) / 2;
        if (newX < 0) {
            newX = 0;
        }
        if (newY < 0) {
            newY = 0;
        }
        frame.setBounds(newX, newY, rectangle.width, rectangle.height);
    }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(null, message, "SkyblockAddons", 1);
    }

    public void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(null, message, "SkyblockAddons - Error", 0);
    }

    private static String getStacktraceText(Throwable ex) {
        StringWriter stringWriter = new StringWriter();
        ex.printStackTrace(new PrintWriter(stringWriter));
        return stringWriter.toString().replace("\t", "  ");
    }

    private static void showErrorPopup(Throwable ex) {
        ex.printStackTrace();
        JTextArea textArea = new JTextArea(SkyblockAddonsInstallerFrame.getStacktraceText(ex));
        textArea.setEditable(false);
        Font currentFont = textArea.getFont();
        Font newFont = new Font("Monospaced", currentFont.getStyle(), currentFont.getSize());
        textArea.setFont(newFont);
        JScrollPane errorScrollPane = new JScrollPane(textArea);
        errorScrollPane.setPreferredSize(new Dimension(600, 400));
        JOptionPane.showMessageDialog(null, errorScrollPane, "Error", 0);
    }

    private String getVersionFromMcmodInfo() {
        String version = "";
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(this.getClass().getClassLoader().getResourceAsStream("mcmod.info"), "mcmod.info not found.")));
            while ((version = bufferedReader.readLine()) != null) {
                if (!version.contains("\"version\": \"")) continue;
                version = version.split(Pattern.quote("\"version\": \""))[1];
                version = version.substring(0, version.length() - 2);
                break;
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        return version;
    }

    private String getModIDFromInputStream(InputStream inputStream) {
        String version = "";
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            while ((version = bufferedReader.readLine()) != null) {
                if (!version.contains("\"modid\": \"")) continue;
                version = version.split(Pattern.quote("\"modid\": \""))[1];
                version = version.substring(0, version.length() - 2);
                break;
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        return version;
    }

    private File getThisFile() {
        try {
            return new File(SkyblockAddonsInstallerFrame.class.getProtectionDomain().getCodeSource().getLocation().toURI());
        }
        catch (URISyntaxException ex) {
            SkyblockAddonsInstallerFrame.showErrorPopup(ex);
            return null;
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    public static enum OperatingSystem {
        LINUX,
        SOLARIS,
        WINDOWS,
        MACOS,
        UNKNOWN;

    }
}


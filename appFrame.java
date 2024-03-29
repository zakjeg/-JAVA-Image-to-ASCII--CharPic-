
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class appFrame extends JFrame implements ActionListener {

    int xResolution = 720;
    int yResolution = 360;
    int maxWidth=0;
    int minWidth = 25;
    double razmerje;
    BufferedImage slika;
    String outputDestionatoin;
    int sliderXValue;
    int sliderYValue;
    boolean selectedImageOk = false;
    boolean selectedDestination = false;
    //String statusText = "Waiting for user Input (select src. Image)";




    private static final long serialVersionUID = 1L;
    private JTextField txtCharpic;
    private final JLabel lblNewLabel_3 = new JLabel(" " + xResolution + " x " + yResolution + " ");

    JButton btnNewButton;
    JButton btnNewButton_1;
    JButton btnNewButton_2;
    JLabel lblNewLabel_5;
    JSlider slider;

    public appFrame() {
        ImageIcon image = new ImageIcon(getClass().getResource("CharPic.png"));
        //String path = "C:\\Users\\zak\\IdeaProjects\\CharPic\\src\\charPic.java";
        this.setIconImage(image.getImage());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        getContentPane().setLayout(new GridLayout(6, 1, 10, 10));

        JPanel panel_1 = new JPanel();
        getContentPane().add(panel_1);

        JLabel lblNewLabel_6 = new JLabel("CharPic");
        lblNewLabel_6.setFont(new Font("Lucida Console", Font.BOLD, 26));
        panel_1.add(lblNewLabel_6);

        JPanel panel_2 = new JPanel();
        getContentPane().add(panel_2);
        panel_2.setLayout(new GridLayout(1, 2, 5, 5));

        JLabel lblNewLabel = new JLabel("Chose Input File:");
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel_2.add(lblNewLabel);

        btnNewButton = new JButton("Browse");
        btnNewButton.addActionListener(this);
        panel_2.add(btnNewButton);

        JPanel panel_3 = new JPanel();
        getContentPane().add(panel_3);
        panel_3.setLayout(new GridLayout(1, 2, 5, 5));

        JLabel lblNewLabel_1 = new JLabel("Choose Output Directory:");
        lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
        panel_3.add(lblNewLabel_1);

        btnNewButton_1 = new JButton("Browse");
        btnNewButton_1.addActionListener(this);
        panel_3.add(btnNewButton_1);

        JPanel panel_4 = new JPanel();
        getContentPane().add(panel_4);
        panel_4.setLayout(new BorderLayout(0, 0));

        slider = new JSlider();                                         //SLIDER
        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if(selectedImageOk){
                    sliderXValue = slider.getValue();
                    sliderYValue = (int) (sliderXValue*razmerje/1.2); //(sliderXValue*razmerje); //popravek širine
                    lblNewLabel_3.setText(" " + sliderXValue + " x " + sliderYValue);
                }
                else{
                    lblNewLabel_3.setText(" No image selected.");
                    lblNewLabel_5.setText("Cannot set resolution while no Image is selected.");
                }

            }
        });
        panel_4.add(slider, BorderLayout.CENTER);

        JLabel lblNewLabel_2 = new JLabel("Output resolution: ");
        lblNewLabel_2.setHorizontalAlignment(SwingConstants.RIGHT);
        panel_4.add(lblNewLabel_2, BorderLayout.WEST);
        lblNewLabel_3.setHorizontalAlignment(SwingConstants.LEFT);
        panel_4.add(lblNewLabel_3, BorderLayout.EAST);

        JPanel panel_5 = new JPanel();
        getContentPane().add(panel_5);

        btnNewButton_2 = new JButton("Run Program");
        btnNewButton_2.addActionListener(this);
        panel_5.add(btnNewButton_2);

        JPanel panel_6 = new JPanel();
        getContentPane().add(panel_6);
        panel_6.setLayout(new BorderLayout(5,5));

        JLabel lblNewLabel_4 = new JLabel("Status : ");
        lblNewLabel_4.setHorizontalAlignment(SwingConstants.RIGHT);
        panel_6.add(lblNewLabel_4, BorderLayout.WEST);

        lblNewLabel_5 = new JLabel( "Waiting for user Input (select Image).");
        panel_6.add(lblNewLabel_5, BorderLayout.CENTER);

        JPanel contentPaneWithPadding = new JPanel(new BorderLayout());
        contentPaneWithPadding.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        //contentPaneWithPadding.setPreferredSize(new Dimension(1280,720));
        contentPaneWithPadding.add(getContentPane(), BorderLayout.CENTER);
        setContentPane(contentPaneWithPadding);

        this.setVisible(true);
    }
    private String chechPathFormat(String path) {
        if (path.endsWith(".txt")) {
            return path;
        } else {
            int dotIndex = path.lastIndexOf(".");
            if (dotIndex >= 0) {
                return path.substring(0, dotIndex) + ".txt";
            } else {
                return path + ".txt";
            }
        }
    }
    private boolean isValidImageFile(File file) {
        String extension = getFileExtension(file);
        String[] supportedFormats = ImageIO.getReaderFileSuffixes();
        if(!extension.isEmpty()){
            for (String format : supportedFormats) {
                if (format.equalsIgnoreCase(extension)) {
                    return true;
                }
            }
        }
        return false;
    }

    private String getFileExtension(File file) {
        String name = file.getName();
        int lastIndexOfDot = name.lastIndexOf('.');
        if (lastIndexOfDot == -1) {
            return "";
        }
        return name.substring(lastIndexOfDot + 1).toLowerCase();
    }

    public void actionPerformed(ActionEvent e){
        if(e.getSource()==btnNewButton){
            JFileChooser fileChooser = new JFileChooser();

            FileNameExtensionFilter imageFilter = new FileNameExtensionFilter("image files", ImageIO.getReaderFileSuffixes());
            fileChooser.setFileFilter(imageFilter);
            int openResponse = fileChooser.showOpenDialog(null);

            if(openResponse==JFileChooser.APPROVE_OPTION){
                File srcFile = new File(fileChooser.getSelectedFile().getAbsolutePath());
                if (isValidImageFile(srcFile)) {
                    selectedImageOk=true;
                    btnNewButton.setText(getLastTwoDirectories(srcFile.getAbsolutePath()));
                    lblNewLabel_5.setText("Select output Image destination folder.");
                    System.out.println("SelectedImageFile: " + srcFile.getAbsolutePath());
                    try {
                        getMaxMinRes(srcFile);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }else{
                    selectedImageOk=false;
                    lblNewLabel_5.setText("Selected file is not a supported image format.");
                }}

        }
        if(e.getSource()==btnNewButton_1){
            JFileChooser fileDirectoryChooser = new JFileChooser();
            int saveResponse = fileDirectoryChooser.showSaveDialog(null);
            if(saveResponse==JFileChooser.APPROVE_OPTION){
                File saveFile = new File(fileDirectoryChooser.getSelectedFile().getAbsolutePath());
                outputDestionatoin = saveFile.getAbsolutePath();
                outputDestionatoin=chechPathFormat(outputDestionatoin);
                btnNewButton_1.setText(getLastTwoDirectories(outputDestionatoin));
                selectedDestination=true;
                lblNewLabel_5.setText("Choose Image resolution and run the program.");
            }
        }
        if(e.getSource()==btnNewButton_2){
            if(selectedDestination&&selectedImageOk){
                lblNewLabel_5.setText("Converting image....");
                try {
                    charPic.convertImage(slika,sliderXValue, sliderYValue, outputDestionatoin); // actualy converting the Image!
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                lblNewLabel_5.setText("Image saved to selected destination.");
            }
            else{
                lblNewLabel_5.setText("You must first set the input and output file.");
            }
        }
    }
    private void setSliderLimits(int sliderMin, int sliderMax){
        slider.setMaximum(sliderMax);
        slider.setMinimum(sliderMin);
        slider.setValue((sliderMax/2+sliderMin/2));
        lblNewLabel_5.setText("SliderMaxResoluiton is " + sliderMax + "Slider min resolution = " + sliderMin);
    }
    private void getMaxMinRes(File imageFile) throws IOException{
        slika = ImageIO.read(imageFile);
        maxWidth = slika.getWidth();
        if(maxWidth>500)maxWidth=500;
        razmerje = 1.0*slika.getHeight()/slika.getWidth();
        if(maxWidth>=25){
            minWidth = 25;
        }else {int minWidth = maxWidth;}
        setSliderLimits(minWidth,maxWidth);
    }
    private String getLastTwoDirectories(String path) {
        String[] directories = path.split("\\\\|/");
        int length = directories.length;

        if (length < 2) {
            return path; // Return the full path if it has less than two directories
        } else {
            return directories[length - 2] + File.separator + directories[length - 1];
        }
    }
}

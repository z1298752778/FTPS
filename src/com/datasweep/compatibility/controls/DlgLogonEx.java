package com.datasweep.compatibility.controls;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.datasweep.core.eventlog.EventLog;
import com.datasweep.plantops.swing.JBaseDialog;
import com.datasweep.plantops.swing.UIConstants;
import com.datasweep.plantops.swing.util.UIUtilities;
import com.rockwell.external.AuthenticationEvent;
import com.rockwell.external.AuthenticationEventListener;
import com.rockwell.external.CustomAuthentication;

public class DlgLogonEx extends JBaseDialog implements AuthenticationEventListener, IDlgLogon {
    //配置项
    String logoImage;
    String indexImage;
    String rollText;
    String titleText;
    String panelStartColor;
    String panelEndColor;
    String rollTextColor;
    String textFont;

    boolean rc;
    ExternalAuthentication extAuthenticator;
    ImagePanel icon;
    JTextArea label;
    JLabel label1;
    JLabel label2;
    JTextField edUserName;
    JPasswordField edPassword;
    JButton btnOK;
    JButton btnCancel;
    JLabel textArea = new JLabel();



    static Color green = new Color(255, 255, 255);

    /**
     * @wbp.parser.constructor
     */
    public DlgLogonEx(final Component locationRelativeTo) {
        this((Frame) ((locationRelativeTo instanceof Frame) ? locationRelativeTo
                : SwingUtilities.getAncestorOfClass(Frame.class, locationRelativeTo)), true);
        this.setLocationRelativeTo(locationRelativeTo);
    }

    private DlgLogonEx(final Frame frame, final boolean b) {
        super(frame, b);
        this.rc = false;
        this.extAuthenticator = null;
        this.icon = new ImagePanel("images/loginKey.gif");
        this.label = new JTextArea();
        this.label.setOpaque(false);
        this.label1 = new JLabel();
        this.label1.setOpaque(false);
        this.label2 = new JLabel();
        this.label2.setOpaque(false);
        this.edUserName = new JTextField();
        this.edPassword = new JPasswordField();
        this.btnOK = new JButton();
        this.btnOK.setBackground(green);
        this.btnCancel = new JButton();
        this.btnCancel.setBackground(green);
        this.initForm();
        UIUtilities.setPreferredWidthToMaximumPreferredWidth(new JComponent[]{this.label1, this.label2});
        UIUtilities.setPreferredWidthToMaximumPreferredWidth(new JComponent[]{this.btnOK, this.btnCancel});
    }

    public String[] getResult() {
        return new String[]{this.edUserName.getText(), new String(this.edPassword.getPassword())};
    }

    public boolean showDialog() {
        try {
            this.extAuthenticator = new ExternalAuthentication((JDialog) this,
                    System.getProperty("CustomAuthentication", "com.rockwell.external.BiometricAuthentication"));
            if (this.extAuthenticator != null) {
                this.extAuthenticator.addEventListener((AuthenticationEventListener) this);
                new Thread(this.extAuthenticator).start();
            }
        } catch (Throwable t) {
        }
        this.show();
        return this.rc;
    }

    public void onAuthenticationEvent(final AuthenticationEvent authenticationEvent) {
        if (SwingUtilities.isEventDispatchThread()) {
            this.edUserName.setText(authenticationEvent.getUserName());
            this.edPassword.setText(authenticationEvent.getPassword());
        } else {
            try {
                SwingUtilities.invokeAndWait(new Runnable() {
                    @Override
                    public void run() {
                        DlgLogonEx.this.edUserName.setText(authenticationEvent.getUserName());
                        DlgLogonEx.this.edPassword.setText(authenticationEvent.getPassword());
                    }
                });
            } catch (InvocationTargetException ex) {
                EventLog.logException((Throwable) ex, (Object) this,
                        "onAuthenticationEvent(), setUserName or setPassword in Event Dispatch Thread throw InvocationTargetException: "
                                + ex.getStackTrace());
            } catch (InterruptedException ex2) {
                EventLog.logException((Throwable) ex2, (Object) this,
                        "onAuthenticationEvent(), setUserName or setPassword in Event Dispatch Thread throw InterruptedException: "
                                + ex2.getStackTrace());
            }
        }
        if (authenticationEvent.isAutoOK()) {
            final int n = (this.extAuthenticator != null) ? this.extAuthenticator.getAutoOKDelay() : 0;
            if (n > 0) {
                try {
                    Thread.sleep(n);
                } catch (Throwable t) {
                }
            }
            if (this.btnOK.isEnabled()) {
                if (SwingUtilities.isEventDispatchThread()) {
                    this.btnOK.doClick();
                } else {
                    try {
                        SwingUtilities.invokeAndWait(new Runnable() {
                            @Override
                            public void run() {
                                DlgLogonEx.this.btnOK.doClick();
                            }
                        });
                    } catch (InvocationTargetException ex3) {
                        EventLog.logException((Throwable) ex3, (Object) this,
                                "onAuthenticationEvent(), btnOK.doClick() in Event Dispatch Thread throw InvocationTargetException: "
                                        + ex3.getStackTrace());
                    } catch (InterruptedException ex4) {
                        EventLog.logException((Throwable) ex4, (Object) this,
                                "onAuthenticationEvent(), btnOK.doClick() in Event Dispatch Thread throw InterruptedException: "
                                        + ex4.getStackTrace());
                    }
                }
            }
        }
    }

    private void doConfirm() {
        final String s = new String(this.edPassword.getPassword());
        this.btnOK.setEnabled(this.edUserName.getText().length() != 0 && s.length() != 0);
    }

    private void close(final boolean rc) {
        if (this.extAuthenticator != null) {
            this.extAuthenticator.removeEventListener((AuthenticationEventListener) this);
            this.extAuthenticator = null;
        }
        this.rc = rc;
        this.DlgLogon_windowClosing();
    }

    private void DlgLogon_windowClosing() {
        this.setVisible(false);
    }

    protected void initForm() {

        Properties properties = new Properties();
        try {
            InputStream inputStream = Objects.requireNonNull(this.getClass().getResource("config.properties")).openStream();
            Reader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            BufferedReader bufferedReader = new BufferedReader(reader);
            properties.load(bufferedReader);
            inputStream.close();
            logoImage = properties.getProperty("image.logo");
            indexImage = properties.getProperty("image.index");
            rollText = properties.getProperty("text.roll");
            titleText = properties.getProperty("text.title");
            panelStartColor = properties.getProperty("color.start.panel");
            panelEndColor = properties.getProperty("color.end.panel");
            rollTextColor = properties.getProperty("color.text.roll");
            textFont = properties.getProperty("font.text");
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.icon.setMinimumSize(new Dimension(32, 32));
        this.icon.setPreferredSize(new Dimension(32, 32));
        this.icon.setMaximumSize(new Dimension(32, 32));
        String s = System.getProperty("user.language.format");
        if (s == null) {
            s = System.getProperty("user.language");
        }
        String s2 = System.getProperty("user.country.format");
        if (s2 == null) {
            s2 = System.getProperty("user.country");
        }
        UIConstants.switchLocale(new Locale(s, s2));
        this.label.setText(UIConstants.logonText);
        this.label.setEditable(false);
        this.label.setLineWrap(true);
        this.label.setWrapStyleWord(true);
        this.label.setFocusable(false);
        this.label.setBackground(this.getBackground());
        this.label1.setText("用户名:");
        this.label2.setText("密码:");

        label1.setFont(new Font(textFont, Font.BOLD, 20));
        label2.setFont(new Font(textFont, Font.BOLD, 20));

        this.edUserName.setText("");
        this.edUserName.getDocument().addDocumentListener(new DocumentListenerAdapter());
        this.edPassword.setText("");
        this.edPassword.getDocument().addDocumentListener(new DocumentListenerAdapter());
        this.btnOK.setEnabled(false);
        this.btnOK.setText("登录");
        this.btnOK.setPreferredSize(new Dimension(340, 45));
        this.btnOK.setMnemonic(UIConstants.okButtonMnemonic);
        this.btnOK.setFont(new Font(textFont, Font.PLAIN,14));
        this.btnOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent actionEvent) {
                DlgLogonEx.this.close(true);
            }
        });
        this.btnCancel.setText(UIConstants.cancelButtonText);
        this.btnCancel.setMnemonic(UIConstants.cancelButtonMnemonic);
        this.btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent actionEvent) {
                DlgLogonEx.this.close(false);
            }
        });
        final JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BorderLayout());
        panel.add(this.label, "Center");
        final JPanel panel2 = new JPanel();
        panel2.setOpaque(false);
        panel2.setLayout(new BoxLayout(panel2, 0));
        panel2.add(this.label1);
        panel2.add(Box.createRigidArea(new Dimension(8, 0)));
        panel2.add(this.edUserName);
        final JPanel panel3 = new JPanel();
        panel3.setOpaque(false);
        panel3.setLayout(new BoxLayout(panel3, 0));
        panel3.add(this.label2);
        panel3.add(Box.createRigidArea(new Dimension(8, 0)));
        panel3.add(this.edPassword);
        final JPanel panel4 = new JPanel();
        panel4.setOpaque(false);
        panel4.setLayout(new BoxLayout(panel4, 1));
        panel4.add(panel2);
        panel4.add(Box.createRigidArea(new Dimension(0, 50)));
        panel4.add(panel3);
        final JPanel panel5 = new JPanel();
        panel5.setOpaque(false);
        panel5.setBounds(93, 250, 421, 70);
        panel5.setLayout(new FlowLayout(1, 4, 0));
        panel5.add(this.btnOK);
        final JPanel panel6 = new JPanel();
        panel6.setOpaque(false);
        panel6.setLayout(new BoxLayout(panel6, 0));
        panel6.add(panel4);
        final JPanel panel7 = new JPanel();
        panel7.setOpaque(false);
        panel7.setBounds(51, 51, 421, 150);
        panel7.setLayout(new BoxLayout(panel7, 1));
        panel7.add(Box.createRigidArea(new Dimension(0, 11)));
        panel7.add(panel6);



        final JPanel panel8 = new JPanel() {

            private static final long serialVersionUID = 1L;

            public void paintComponent(Graphics g) {
                super.paintComponent(g);
            }
        };
        BoxLayout layout = new BoxLayout(panel8, BoxLayout.Y_AXIS);
        panel8.setLayout(layout);

        final JPanel panel9 = new JPanel();

        panel9.setLayout(null);
        panel9.add(panel7);
        panel9.add(panel5);
        panel9.setBorder(UIConstants.dialogBorder);
        panel9.setOpaque(false);
        JPanel panel1 = new JPanel();
        FlowLayout flowLayout = new FlowLayout();
        flowLayout.setAlignment(FlowLayout.RIGHT);
        textArea.setBackground(new Color(220, 34, 34, 0));
        panel1.setLayout(flowLayout);
        panel1.add(textArea);
        panel.setSize(300, 50);

        ImageIcon logo = new ImageIcon(Objects.requireNonNull(this.getClass().getResource(logoImage)));
        System.out.println(this.getClass().getResource(""));


        JPanel jPanel = new JPanel() {
            private static final long serialVersionUID = 1L;

            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                setSize(logo.getIconWidth(), logo.getIconHeight());
                g.drawImage(logo.getImage(), 0, 0, logo.getIconWidth(), logo.getIconHeight(), this);
            }
        };
        jPanel.setOpaque(true);
        jPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));

        panel9.setOpaque(true);
        panel9.setBackground(new Color(218, 103, 103));


        panel8.setBorder(BorderFactory.createEmptyBorder(50, 50, 0, 50));


        JPanel jPanelTop = new JPanel();
        BoxLayout boxLayout = new BoxLayout(jPanelTop, BoxLayout.X_AXIS);
        FlowLayout flowLayout1 = new FlowLayout();
        flowLayout1.setAlignment(FlowLayout.LEFT);
        jPanelTop.setLayout(boxLayout);
        jPanelTop.add(jPanel);
        jPanelTop.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel jPanelCL = new JPanel() {

            @Override
            protected void paintComponent(Graphics g) {
                int width = getWidth();
                int height = getHeight();
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(0, 0, getConfigColor(panelStartColor), width, height, getConfigColor(panelEndColor));
                g2d.setPaint(gp);
                g2d.fillRoundRect(0, 0, width, height, 70, 70);
                g2d.dispose();

            }


        };
        jPanelCL.setOpaque(false);
        jPanelCL.setPreferredSize(new Dimension(50, 700));
        BoxLayout boxLayout1 = new BoxLayout(jPanelCL, BoxLayout.X_AXIS);
        jPanelCL.setLayout(boxLayout1);
        //jPanelCL.setBackground(new Color(224, 7, 81));

        JPanel jPanel1 = new JPanel();
        GridLayout gridLayout = new GridLayout();
        jPanel1.setPreferredSize(new Dimension(600, 100));
        jPanel1.setLayout(gridLayout);
        jPanel1.setBorder(new EmptyBorder(50, 50, 50, 50));

        JPanel jPanel2 = new JPanel() {
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                int width = getWidth();
                int height = getHeight();
                int radius = 50;
                BufferedImage roundImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
                Graphics2D g2d = roundImage.createGraphics();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setClip(new RoundRectangle2D.Double(0, 0, width, height, radius, radius));
                BufferedImage ic = null;
                try {
                    ic = ImageIO.read(Objects.requireNonNull(this.getClass().getResource(indexImage)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                g2d.drawImage(ic, 0, 0, width, height, this);
                g2d.dispose();
                g.drawImage(roundImage, 0, 0, this);

            }

        };
        jPanel2.setOpaque(false);
        jPanel1.add(jPanel2);
        jPanel2.setBackground(new Color(120, 122, 161));


        jPanel1.setOpaque(false);
        jPanelCL.add(jPanel1);

        JPanel jPanel3 = new JPanel();
        GridLayout gridLayou1 = new GridLayout();
        jPanel3.setLayout(gridLayou1);
        jPanel3.setBorder(new EmptyBorder(50, 0, 50, 50));

        JPanel jPanel5 = new JPanel();
        GridLayout gridLayou2 = new GridLayout();
        jPanel5.setLayout(gridLayou2);
        jPanel5.setBorder(new EmptyBorder(50, 50, 50, 50));

        JPanel jPanel4 = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                int width = getWidth();
                int height = getHeight();
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(0, 0, new Color(135, 215, 212, 171), width, height, new Color(43, 150, 158, 168));
                g2d.setPaint(gp);
                g2d.fillRoundRect(0, 0, width, height, 70, 70);
                g2d.dispose();

            }

        };
        BoxLayout boxLayout2 = new BoxLayout(jPanel4, BoxLayout.Y_AXIS);
        jPanel4.setLayout(boxLayout2);

        JPanel jPanel6 = new JPanel();
        jPanel6.setBorder(new EmptyBorder(50, 50, 0, 50));
        jPanel6.setLayout(new GridLayout());
        jPanel6.setBackground(new Color(122, 145, 199));

        jPanel6.setPreferredSize(new Dimension(0, -200));

        JPanel jPanel7 = new JPanel() {

            @Override
            protected void paintComponent(Graphics g) {
                int width = getWidth();
                int height = getHeight();
                Graphics2D graphics = (Graphics2D) g;
                graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                graphics.setColor(getBackground());
                graphics.fillRoundRect(0, 0, width, height, 20, 20);
                super.paintComponent(g);

            }


        };
        jPanel7.setOpaque(false);
        jPanel7.setBackground(Color.white);
        jPanel7.setPreferredSize(new Dimension(200, 200));
        jPanel7.setBorder(new EmptyBorder(50, 0, 0, 0));
        JLabel jLabel = new JLabel();
        jLabel.setText(titleText);
        jLabel.setFont(new Font(textFont, Font.PLAIN, 50));
        jLabel.setBounds(0, 100, 200, 200);
        jPanel7.add(jLabel);
        jPanel6.add(jPanel7);

        jPanel4.add(jPanel6);

        jPanel4.add(panel9);

        //jPanel5.add(jPanel4);

        jPanel6.setOpaque(false);
        panel9.setOpaque(false);


        jPanel3.add(jPanel4);


        jPanel3.setOpaque(false);
        jPanelCL.add(jPanel3);

        panel9.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        this.getContentPane().setLayout(new BorderLayout());
        panel8.add(jPanelTop);
        panel8.add(Box.createRigidArea(new Dimension(1, 40)));
        this.getContentPane().add(panel8, "Center");
        this.getRootPane().setDefaultButton(this.btnOK);
        panel8.add(jPanelCL);

        //panel1.setPreferredSize(new Dimension(1,-30));
        panel8.add(panel1);

        MovingMessagePanel messagePanel = new MovingMessagePanel(rollText);
        messagePanel.setOpaque(false);
        messagePanel.setPreferredSize(new Dimension(900, 52));

        Font ROLL_FONT = new Font(textFont, Font.PLAIN, 50);

        messagePanel.setFont(ROLL_FONT);
        messagePanel.setBackground(new Color(243, 252, 251));

        jPanelTop.add(messagePanel);

        Timer timer = new Timer(100, new TimerListener());

        timer.start();

        this.setTitle(UIConstants.logonInfo);
        this.setVisible(false);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) screenSize.getWidth();
        int height = (int) screenSize.getHeight() - 40;
        this.setSize(width, height);
        this.setLocation(width, height);
        this.addWindowListener((WindowListener) new WindowAdapter() {
            @Override
            public void windowClosing(final WindowEvent windowEvent) {
                DlgLogonEx.this.close(false);
            }
        });
    }

    /**
     * 获取配置的颜色
     *
     * @param configColor
     * @return
     */
    private static Color getConfigColor(String configColor) {
        String[] split = configColor.split(",");
        Color color = new Color(255, 255, 255);
        try {
            if (split.length >= 3) {
                color = new Color(Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2]));
            }
        } catch (Exception e) {
            e.getStackTrace();
        }
        return color;
    }

    protected JTextField getEdUserName() {
        return this.edUserName;
    }

    protected JTextField getEdPassword() {
        return this.edPassword;
    }

    protected JButton getBtnOK() {
        return this.btnOK;
    }

    protected JButton getBtnCancel() {
        return this.btnCancel;
    }

    protected void setIconVisible(final boolean visible) {
        this.icon.setVisible(visible);
    }

    protected void closeDialog() {
        this.close(false);
    }

    class ExternalAuthentication implements Runnable {
        JDialog container;
        CustomAuthentication authenticator;

        ExternalAuthentication(final JDialog container, final String s)
                throws ClassNotFoundException, InstantiationException, IllegalAccessException {
            this.container = null;
            this.authenticator = null;
            this.container = container;
            final Class<?> forName = Class.forName(s);
            if (forName != null) {
                this.authenticator = (CustomAuthentication) forName.newInstance();
            }
        }

        void addEventListener(final AuthenticationEventListener authenticationEventListener) {
            if (this.authenticator != null) {
                this.authenticator.addEventListener(authenticationEventListener);
            }
        }

        void removeEventListener(final AuthenticationEventListener authenticationEventListener) {
            if (this.authenticator != null) {
                this.authenticator.removeEventListener(authenticationEventListener);
            }
        }

        int getAutoOKDelay() {
            if (this.authenticator != null) {
                return this.authenticator.getAutoOKDelay();
            }
            return 0;
        }

        @Override
        public void run() {
            if (this.container != null) {
                while (!this.container.isShowing()) {
                    try {
                        Thread.sleep(100L);
                    } catch (Throwable t) {
                    }
                }
            }
            this.authenticator.readyToAuthenticate();
        }
    }

    class MovingMessagePanel extends JPanel {
        private String message = "";

        private int x = 600;

        private int y = 60;

        public MovingMessagePanel(String message) {
            //super();
            this.message = message;
        }

        @Override
        public void paintComponent(Graphics g) {
            int width = getWidth();
            int height = getHeight();
            Graphics2D graphics = (Graphics2D) g;
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            graphics.setColor(getBackground());
            graphics.fillRoundRect(0, 0, width, height, 30, 30);
            super.paintComponent(g);
            Font ROLL_FONT = new Font(textFont, Font.PLAIN, 50);
            FontMetrics metrics = g.getFontMetrics(ROLL_FONT);
            int textWidth = metrics.stringWidth(message);
            if (x <= -50 - textWidth) {
                x = 600 + textWidth / 2;
            }
            x -= 3;
            g.setColor(getConfigColor(rollTextColor));
            g.drawString(message, x, y);
        }
    }

    class TimerListener implements ActionListener {
        String date = new Date().toString();

        @Override
        public void actionPerformed(ActionEvent e) {
            repaint();
            //System.out.println(new Date().toString());
            try {
                textArea.setText(DateToString(new Date()));
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
        }

        /**
         * ??date?????yyyy-MM-dd HH:mm:ss
         *
         * @param date
         * @return
         * @throws ParseException
         */
        public String DateToString(Date date) throws ParseException {

            //"yyyy-MM-dd HH:mm:ss"
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            String format = simpleDateFormat.format(date);
            return format;
        }
    }

    class DocumentListenerAdapter implements DocumentListener {
        @Override
        public void insertUpdate(final DocumentEvent documentEvent) {
            DlgLogonEx.this.doConfirm();
        }

        @Override
        public void removeUpdate(final DocumentEvent documentEvent) {
            DlgLogonEx.this.doConfirm();
        }

        @Override
        public void changedUpdate(final DocumentEvent documentEvent) {
            DlgLogonEx.this.doConfirm();
        }
    }

    class ImagePanel extends JPanel {
        Image image;

        ImagePanel(final String s) {
            this.image = Toolkit.getDefaultToolkit().getImage(DlgLogonEx.class.getResource(s));
        }

        public void paintComponent(final Graphics graphics) {
            super.paintComponent(graphics);
            graphics.drawImage(this.image, 0, 0, this);
        }
    }
}

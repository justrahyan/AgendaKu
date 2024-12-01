package Custom;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicPasswordFieldUI;

/**
 *
 * @author LOQ
 */
public class JPasswordFieldRounded extends JPasswordField {
    private PasswordFieldUI textUI;
    
    public JPasswordFieldRounded() {
        textUI = new PasswordFieldUI(this);
        setUI(textUI);
    }
    
    private class PasswordFieldUI extends BasicPasswordFieldUI{
        private JPasswordField passwordField;
        private Border border;
        private int round = 15;
        private List<String> items = new ArrayList<>();

        public Border getBorder() {
            return border;
        }

        public void setBorder(Border border) {
            this.border = border;
        }

        public int getRound() {
            return round;
        }

        public void setRound(int round) {
            this.round = round;
        }

        public List<String> getItems() {
            return items;
        }

        public void setItems(List<String> items) {
            this.items = items;
        }
        
        public PasswordFieldUI(JPasswordField passwordField) {
            this.passwordField = passwordField;
            border = new Border(10);
            border.setRound(round);
            passwordField.setBorder(border);
            passwordField.setOpaque(false);
            passwordField.setSelectionColor(new Color(153,204,255));
            passwordField.setSelectedTextColor(Color.BLACK);
            passwordField.addFocusListener(new FocusAdapter(){
                @Override
                public void focusGained(FocusEvent e) {
                    border.setColor(new Color(180,180,180));
                    passwordField.repaint();
                }

                @Override
                public void focusLost(FocusEvent e) {
                    border.setColor(new Color(180,180,180));
                    passwordField.repaint();
                }
            });
        }

        @Override
        protected void paintBackground(Graphics g) {
            if(passwordField.isOpaque()){
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(passwordField.getBackground());
                g2.drawRoundRect(0, 0, passwordField.getWidth(), passwordField.getHeight(), round, round);
                g2.dispose();
            }
        }
        
        
        private class Border extends EmptyBorder{

            public Color getFocusColor() {
                return focusColor;
            }

            public void setFocusColor(Color focusColor) {
                this.focusColor = focusColor;
            }

            public Color getColor() {
                return color;
            }

            public void setColor(Color color) {
                this.color = color;
            }

            public int getRound() {
                return round;
            }

            public void setRound(int round) {
                this.round = round;
            }
            
            private Color focusColor = new Color(0,104,153);
            private Color color = new Color(180,180,180);
            private int round;
            
            public Border(int border) {
                super(border, border, border, border);
            }
            
            public Border(){
                this(5);
            }

            @Override
            public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if(c.isFocusOwner()){
                    g2.setColor(focusColor);
                }else{
                    g2.setColor(color);
                }
                g2.drawRoundRect(x, y, width - 1, height - 1, round, round);
                g2.dispose();
            }
            
            
            
        }
    }
}

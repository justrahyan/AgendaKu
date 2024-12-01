package Custom;

import com.toedter.calendar.JDateChooser;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.metal.MetalTextFieldUI;
import javax.swing.SpinnerDateModel;

public class JTextFieldRoundedDate extends JTextField {
    
    private TextFieldUI textUI;
    private JDateChooser dateChooser; // Komponen JCalendar
    private JSpinner timeSpinner; // Komponen JSpinner untuk memilih waktu
    private SimpleDateFormat dateTimeFormat; // Format tanggal dan waktu yang digunakan

    public JTextFieldRoundedDate() {
        textUI = new TextFieldUI(this);
        setUI(textUI);

        // Inisialisasi JCalendar untuk memilih tanggal
        dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("yyyy-MM-dd");
        
        // Inisialisasi JSpinner untuk memilih waktu (jam, menit, detik)
        timeSpinner = new JSpinner(new SpinnerDateModel());
        timeSpinner.setEditor(new JSpinner.DateEditor(timeSpinner, "HH:mm:ss"));
        
        // Format untuk tanggal dan waktu
        dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        // Tambahkan mouse listener untuk membuka dialog kalender
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                openCalendarDialog();
            }
        });
    }

    private void openCalendarDialog() {
        // Panel untuk menampung komponen tanggal dan waktu
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Tambahkan JDateChooser (tanggal)
        panel.add(dateChooser, BorderLayout.NORTH);

        // Tambahkan JSpinner (waktu)
        panel.add(timeSpinner, BorderLayout.CENTER);

        // Tampilkan dialog untuk memilih tanggal dan waktu
        int result = JOptionPane.showConfirmDialog(
                null,
                panel,
                "Select Date and Time",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        // Set tanggal dan waktu yang dipilih ke JTextField jika OK ditekan
        if (result == JOptionPane.OK_OPTION) {
            Date selectedDate = dateChooser.getDate();
            Date selectedTime = (Date) timeSpinner.getValue();
            if (selectedDate != null && selectedTime != null) {
                // Gabungkan tanggal dan waktu
                selectedDate.setHours(selectedTime.getHours());
                selectedDate.setMinutes(selectedTime.getMinutes());
                selectedDate.setSeconds(selectedTime.getSeconds());

                // Set ke JTextField dengan format yang sesuai
                setText(dateTimeFormat.format(selectedDate));
            }
        }
    }

    private class TextFieldUI extends MetalTextFieldUI {
        private JTextField textfield;
        private Border border;
        private int round = 15;

        public TextFieldUI(JTextField textfield) {
            this.textfield = textfield;
            border = new Border(10);
            border.setRound(round);
            textfield.setBorder(border);
            textfield.setOpaque(false);
            textfield.setSelectionColor(new Color(153, 204, 255));
            textfield.setSelectedTextColor(Color.BLACK);
        }

        @Override
        protected void paintBackground(Graphics g) {
            if (textfield.isOpaque()) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(textfield.getBackground());
                g2.drawRoundRect(0, 0, textfield.getWidth(), textfield.getHeight(), round, round);
                g2.dispose();
            }
        }

        private class Border extends EmptyBorder {

            private Color focusColor = new Color(0, 104, 153);
            private Color color = new Color(180, 180, 180);
            private int round;

            public Border(int border) {
                super(border, border, border, border);
            }

            public int getRound() {
                return round;
            }

            public void setRound(int round) {
                this.round = round;
            }

            @Override
            public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(c.isFocusOwner() ? focusColor : color);
                g2.drawRoundRect(x, y, width - 1, height - 1, round, round);
                g2.dispose();
            }
        }
    }
}

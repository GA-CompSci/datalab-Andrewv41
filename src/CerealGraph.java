import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class CerealGraph extends JPanel {

    private ArrayList<Cereal> cereals;

    public CerealGraph(ArrayList<Cereal> cereals) {
        this.cereals = cereals;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (cereals == null || cereals.size() == 0) return;

        int width = getWidth();
        int height = getHeight();
        int margin = 50;
        int barWidth = (height - 2 * margin) / cereals.size();
        int maxBarHeight = width - 2 * margin;

        // Find max net carbs
        double maxNet = Double.MIN_VALUE;
        for (Cereal c : cereals) {
            double net = Math.max(0, CerealRunner3.findNetCarbs(c));
            if (net > maxNet) maxNet = net;
        }

        for (int i = 0; i < cereals.size() && i < 10; i++) { // first 10 cereals
            Cereal c = cereals.get(i);
            double net = Math.max(0, CerealRunner3.findNetCarbs(c));
            int barHeight = (int) ((net / maxNet) * maxBarHeight);
            int x = margin + i * barWidth;
            int y = height - margin - barHeight;

            g.setColor(Color.BLUE);
            g.fillRect(x, y, barWidth - 10, barHeight);

            g.setColor(Color.BLACK);
            g.drawString(c.getName(), x, height - margin + 15);
            g.drawString(String.valueOf((int) net), x, y - 5);
        }

        g.drawLine(margin, height - margin, width - margin, height - margin); // x-axis
        g.drawLine(margin, height - margin, margin, margin); // y-axis
    }

    public static void showGraph(ArrayList<Cereal> cereals) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Cereal Net Carbs Graph");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);
            frame.add(new CerealGraph(cereals));
            frame.setVisible(true);
        });
    }
}

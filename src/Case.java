import java.awt.Color;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Case extends JPanel {

		private int x;
		private int y;
		private Color color;
		private boolean alive;
		
		
		public Case(int n, int m, boolean alive) {
			this.x = n;
			this.y = m;
			setAlive(alive);
			setEnabled(true);
		}
		
		public void setAlive(boolean a) {
			this.alive = a;
			this.color = a ? Color.WHITE : Color.BLACK;
			setBackground(color);
		}
		
		public boolean isAlive() {
			return this.alive;
		}
		
		public Color getColor() {
			return this.color;
		}
		
		public int getx() {
			return this.x;
		}
		
		public int gety() {
			return this.y;
		}
}

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Grid extends JPanel {
	
	private Case grid[][];
	private int size;
	
	public Grid() {
		int ds = 50;
		this.size = 50;
		this.grid = new Case[ds][ds];
		setOpaque(false);
		setLayout(new GridLayout(ds,ds));
		
		for (int i = 0; i < ds; i++) {
			for (int j = 0; j < ds; j++) {
				this.grid[i][j] = new Case(i, j, false);
				this.grid[i][j].setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
				this.grid[i][j].addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
						Case clickedCase = (Case)e.getSource();
						clickedCase.setAlive(clickedCase.isAlive()?false:true);
					}
				});
				add(this.grid[i][j]);
			}
		}
	}
	
	public Grid(int size) {
		this.grid = new Case[size][size];
		this.size = size;
		setOpaque(false);
		setLayout(new GridLayout(size,size));
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				this.grid[i][j] = new Case(i, j, false);
				this.grid[i][j].setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
				this.grid[i][j].addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
						Case clickedCase = (Case)e.getSource();
						clickedCase.setAlive(clickedCase.isAlive()?false:true);
					}
				});
				add(this.grid[i][j]);
			}
		}
	}
	
	public void setSize(int s) {
		this.size = s;
	}
	
	public int getS() {
		return this.size;
	}
	
	public Case getCase(int i, int j) {
		return this.grid[i][j];
	}
	
	public int aliveNeighbor(Case c) {
		int r = 0;
		int a = c.getx();
		int b = c.gety();
		if (a != 0 && a != this.getS()-1 && b != 0 && b != this.getS()-1) {
			for (int k = -1; k < 2; k++) {
				r = r + (grid[a-1][b+k].isAlive() ? 1 : 0);
			}
			for (int k = -1; k < 2; k++) {
				r = r + (grid[a+1][b+k].isAlive() ? 1 : 0);
			}
			r = r + (grid[a][b-1].isAlive() ? 1 : 0);
			r = r + (grid[a][b+1].isAlive() ? 1 : 0);
		}
		else if (a == 0 && b == 0) {
			r = r + (grid[a][b+1].isAlive() ? 1 : 0);
			r = r + (grid[a+1][b+1].isAlive() ? 1 : 0);
			r = r + (grid[a+1][b].isAlive() ? 1 : 0);
			
		}
		
		else if (a == 0 && b == this.getS()-1) {
			r = r + (grid[a][b-1].isAlive() ? 1 : 0);
			r = r + (grid[a+1][b-1].isAlive() ? 1 : 0);
			r = r + (grid[a+1][b].isAlive() ? 1 : 0);
		}
		
		else if (a == this.getS()-1 && b == this.getS()-1) {
			r = r + (grid[a-1][b].isAlive() ? 1 : 0);
			r = r + (grid[a-1][b-1].isAlive() ? 1 : 0);
			r = r + (grid[a][b-1].isAlive() ? 1 : 0);
		}
		
		else if (a == this.getS()-1 && b == 0) {
			r = r + (grid[a-1][b].isAlive() ? 1 : 0);
			r = r + (grid[a-1][b+1].isAlive() ? 1 : 0);
			r = r + (grid[a][b+1].isAlive() ? 1 : 0);
			
		}
		
		else if (a == 0) {
			for (int k = -1; k < 2; k++) {
				r = r + (grid[a+1][k+c.gety()].isAlive() ? 1 : 0);
			}
			r = r + (grid[a][b-1].isAlive() ? 1 : 0);
			r = r + (grid[a][b+1].isAlive() ? 1 : 0);
		}
		
		else if (a == this.getS()-1) {
			for (int k = -1; k < 2; k++) {
				r = r + (grid[a-1][b+k].isAlive() ? 1 : 0);
			}
			r = r + (grid[a][b-1].isAlive() ? 1 : 0);
			r = r + (grid[a][b+1].isAlive() ? 1 : 0);
		}
		
		else if (b == 0) {
			for (int k = -1; k < 2; k++) {
				r = r + (grid[a+k][b+1].isAlive() ? 1 : 0);
			}
			r = r + (grid[a-1][b].isAlive() ? 1 : 0);
			r = r + (grid[a+1][b].isAlive() ? 1 : 0);
		}
		
		else if (b == this.getS()-1) {
			for (int k = -1; k < 2; k++) {
				r = r + (grid[a+k][b-1].isAlive() ? 1 : 0);
			}
			r = r + (grid[a-1][b].isAlive() ? 1 : 0);
			r = r + (grid[a+1][b].isAlive() ? 1 : 0);
		}
		//System.out.println(r);
		return r;
	}
}
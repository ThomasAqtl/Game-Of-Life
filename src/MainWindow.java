import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SwingUtilities;

@SuppressWarnings("serial")
public class MainWindow extends JFrame {
	
	private boolean running = true;
	
	public Grid grid = new Grid();
	private JPanel buttons = new JPanel();
	
	private JButton generateB = new JButton("Generate");
	private JButton runB = new JButton("Run !");
	public JButton stopGo = new JButton("Stop/Go");
	
	private JSpinner js = new JSpinner();
	
	private JLabel jl = new JLabel("Grid size :");
	
	public MainWindow() {
		super("Game of life");
		this.getContentPane();
		this.setLayout(new BorderLayout());
		
		buttons.setLayout(new FlowLayout());
		buttons.add(jl);
		buttons.add(js);
		generateB.addActionListener(new ButtonActionListener(this));
		buttons.add(generateB);
		runB.addActionListener(new ButtonActionListener(this));
		buttons.add(runB);
		stopGo.setEnabled(false);
		buttons.add(stopGo);
		add(buttons, BorderLayout.NORTH);
		
		add(grid, BorderLayout.CENTER);
		
		setVisible(true);
		pack();
	}
	
	public JSpinner getjs() {
		return this.js;
	}
	
	public void Generate(int a) {
		this.remove(grid);
		SwingUtilities.updateComponentTreeUI(this);
		this.grid = new Grid(a);
		this.add(grid, BorderLayout.CENTER);
		SwingUtilities.updateComponentTreeUI(this);
	}
	
	public void updateGrid() throws InterruptedException {
		Grid grid2 = new Grid(grid.getS());
		for (int i = 0; i < grid.getS()-1; i++) {
			for (int j = 0; j < grid.getS()-1; j++) {
				if (grid.getCase(i, j).isAlive()) {
					if (grid.aliveNeighbor(grid.getCase(i, j)) < 1 || grid.aliveNeighbor(grid.getCase(i, j)) > 3) grid2.getCase(i, j).setAlive(false);
					else if (grid.aliveNeighbor(grid.getCase(i, j)) == 3) grid2.getCase(i, j).setAlive(true);
					else if (grid.aliveNeighbor(grid.getCase(i, j)) == 2) grid2.getCase(i, j).setAlive(grid.getCase(i, j).isAlive());
				}
				else {
					if (grid.aliveNeighbor(grid.getCase(i, j)) == 3) grid2.getCase(i, j).setAlive(true);
					else if (grid.aliveNeighbor(grid.getCase(i, j)) == 2) grid2.getCase(i, j).setAlive(grid.getCase(i, j).isAlive());
				}
			}
		}
		displayNewGrid(grid2);
		}
	
	public void displayNewGrid(Grid g) {
		this.remove(grid);
		SwingUtilities.updateComponentTreeUI(this);
		this.grid = g;
		this.add(grid, BorderLayout.CENTER);
		SwingUtilities.updateComponentTreeUI(this);
	}
	
	public void setRunning(boolean b) {
		this.running = b;
	}
	
	public boolean isRunning() {
		return this.running;
	}
	
	
	public static void main(String[] args) {
		@SuppressWarnings("unused")
		MainWindow mWindow = new MainWindow();
	}
}

class ButtonActionListener implements ActionListener {
	
	MainWindow mw;
	public static volatile boolean running = true;
	
	public ButtonActionListener(MainWindow m) {
		this.mw = m;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Thread r = new Thread(new Runnable() {
			@Override
			public void run() {
				while(mw.isRunning()){
					try {
						Grid grid2 = new Grid(mw.grid.getS());
						for (int i = 0; i < mw.grid.getS()-1; i++) {
							for (int j = 0; j < mw.grid.getS()-1; j++) {
								if (mw.grid.getCase(i, j).isAlive()) {
									if (mw.grid.aliveNeighbor(mw.grid.getCase(i, j)) < 1 || mw.grid.aliveNeighbor(mw.grid.getCase(i, j)) > 3) grid2.getCase(i, j).setAlive(false);
									else if (mw.grid.aliveNeighbor(mw.grid.getCase(i, j)) == 3) grid2.getCase(i, j).setAlive(true);
									else if (mw.grid.aliveNeighbor(mw.grid.getCase(i, j)) == 2) grid2.getCase(i, j).setAlive(mw.grid.getCase(i, j).isAlive());
								}
								else {
									if (mw.grid.aliveNeighbor(mw.grid.getCase(i, j)) == 3) grid2.getCase(i, j).setAlive(true);
									else if (mw.grid.aliveNeighbor(mw.grid.getCase(i, j)) == 2) grid2.getCase(i, j).setAlive(mw.grid.getCase(i, j).isAlive());
								}
							}
						}
						mw.remove(mw.grid);
						mw.revalidate();
						mw.grid = grid2;
						mw.add(mw.grid,BorderLayout.CENTER);
						mw.revalidate();
						Thread.sleep(50);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		
		if (e.getActionCommand().equals("Generate")) {
			int s = (int)mw.getjs().getValue();
			mw.Generate(s);
		}
		
		else if (e.getActionCommand().equals("Run !")) {
			mw.stopGo.setEnabled(true);
			r.start();
		}
		else if (e.getActionCommand().equals("Stop/Go")) {
			r.interrupt();
		}
	}
}
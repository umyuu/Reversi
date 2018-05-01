package widget;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JToggleButton;

import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.LinkedHashMap;
import java.util.StringJoiner;
import java.util.Map.Entry;
import java.awt.event.ActionEvent;
import java.awt.Dimension;
import java.awt.Point;

import core.EventName;
import core.Game;
import core.Stone;
import java.awt.Font;
import javax.swing.SwingConstants;

public class Nav extends JPanel {
	private final PropertyChangeSupport changes = new PropertyChangeSupport(this);

	/**
	 * Create the panel.
	 * @param game 
	 */
	public Nav(Game game) {
		setMinimumSize(new Dimension(100, 10));
		setLayout(null);

		JToggleButton tglbtnNewToggleButton = new JToggleButton("AI");
		tglbtnNewToggleButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PropertyChangeEvent evt = new PropertyChangeEvent(e.getSource(), e.getActionCommand(), null, "1");
				changes.firePropertyChange(evt);
			}
		});
		tglbtnNewToggleButton.setBounds(20, 151, 115, 67);
		add(tglbtnNewToggleButton);

		JLabel lblTrun = new JLabel("");
		lblTrun.setBounds(0, 0, 155, 16);
		add(lblTrun);

		JButton btnNewButton = new JButton("PASS");
		btnNewButton.setBounds(0, 256, 115, 44);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PropertyChangeEvent evt = new PropertyChangeEvent(e.getSource(), e.getActionCommand(), null, "1");
				changes.firePropertyChange(evt);
			}
		});
		add(btnNewButton);
		
		JLabel lblScore = new JLabel("2 / 2");
		lblScore.setHorizontalAlignment(SwingConstants.CENTER);
		lblScore.setFont(new Font("游ゴシック", Font.PLAIN, 16));
		lblScore.setBounds(20, 93, 77, 34);
		add(lblScore);
		game.addPropertyChangeListener((evt)->{
			if (!EventName.SET_PIECE.name().equals(evt.getPropertyName())) {
				return;
			}
			StringJoiner joiner = new StringJoiner(" / ");
			for(Entry<Stone, Integer> entry:game.getScore().entrySet()) {
				joiner.add(entry.getValue().toString());
			}
			lblScore.setText(joiner.toString());
			return;
		});
	}
	
	public void addBeanPropertyChangeListener(PropertyChangeListener listener) {
		changes.addPropertyChangeListener(listener);
	}

	public void removeBeanPropertyChangeListener(PropertyChangeListener listener) {
		changes.removePropertyChangeListener(listener);
	}
}

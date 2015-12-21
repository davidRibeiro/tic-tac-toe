//package ia;

import java.util.LinkedList;

public class Game {
	public int _state[][];
	public int filled = 0;
	int depth;
	Game parent;
	int utility;
	public LinkedList<Game> nodes = new LinkedList<Game>();

	Game(int[][] table, Game dad) {
		_state = new int[3][3];
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				_state[i][j] = table[i][j];
				if (table[i][j] != 0) {
					filled++;
				}
			}
		}
		if (dad != null) {
			parent = dad;
			depth = dad.depth + 1;
		} else {
			parent = null;
			depth = 0;
		}
	}

	boolean isEmpty(int[] aux) {
		// TODO Auto-generated method stub
		return (_state[aux[0]][aux[1]] == 0);
	}

	public void stateprint(char cpuChar, char userChar) {
		int index = 0;
		for (int i = 0; i < 3; i++) {
			System.out.println("    |     |    ");
			for (int j = 0; j < 3; j++) {
				index++;
				if (j > 0) {
					System.out.print(" | ");
				}
				if (_state[i][j] == 1) {
					System.out.print(" " + cpuChar + " ");
				} else if (_state[i][j] == 2) {
					System.out.print(" " + userChar + " ");
				} else {
					System.out.print("[" + index + "]");
				}
			}

			System.out.println();
			System.out.println("    |     |    ");
			if (i < 2) {
				System.out.println("----------------");
			}
		}
		System.out.println();
	}

	public boolean isLeaf() {
		// TODO Auto-generated method stub
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (_state[i][j] == 0) {
					return false;
				}
			}
		}
		return true;
	}

	public void createChilds(int player) {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (_state[i][j] == 0) {
					Game kid = new Game(this._state, this);
					kid._state[i][j] = player;
					this.nodes.add(kid);
				}
			}
		}
	}
}
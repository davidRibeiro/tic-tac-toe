//package ia;

public class Algorithm {

	int totalNodes = 0;
	int nodes = 0;

	public Game minimaxDecision(Game gameboard, int player) {
		nodes = 0;
		gameboard.depth = 0;
		int v = maxValue(gameboard);
		for (Game n : gameboard.nodes) {
			if (v == n.utility) {
				System.out.println("Nesta jogada foram criados " + nodes
						+ "nos.");
				totalNodes += nodes;
				return n;
			}
		}
		System.out.println("Nesta jogada foram criados " + nodes + "nos.");
		totalNodes += nodes;
		return gameboard;
	}

	private int maxValue(Game gameboard) {
		if (terminalTest(gameboard)) {
			gameboard.utility = didComputerWin(gameboard);
			return gameboard.utility;
		}
		int bestValue = Integer.MIN_VALUE;
		gameboard.createChilds(1);
		nodes += gameboard.nodes.size();
		for (Game n : gameboard.nodes) {
			bestValue = Math.max(bestValue, minValue(n));
			n.utility = bestValue;
		}
		return bestValue;
	}

	private int minValue(Game gameboard) {
		if (terminalTest(gameboard)) {
			gameboard.utility = didComputerWin(gameboard);
			return gameboard.utility;
		}

		int bestValue = Integer.MAX_VALUE;
		gameboard.createChilds(2);
		nodes += gameboard.nodes.size();
		for (Game n : gameboard.nodes) {
			bestValue = Math.min(bestValue, maxValue(n));
			n.utility = bestValue;
		}
		return bestValue;
	}

	private boolean terminalTest(Game gameboard) {
		if (gameboard.isLeaf()) {
			return true;
		}
		int winner = didComputerWin(gameboard);
		if (winner == 0) {
			return false;
		}
		return true;

	}

	public Game alphaBetaSearch(Game gameboard) {
		nodes = 0;
		int v = maxValueAlphaBeta(gameboard, Integer.MIN_VALUE,
				Integer.MAX_VALUE);
		for (Game n : gameboard.nodes) {
			if (n.utility == v) {
				System.out.println("Nesta jogada foram criados " + nodes
						+ " nos.");
				totalNodes += nodes;
				return n;
			}
		}
		return gameboard;
	}

	private int maxValueAlphaBeta(Game gameboard, int alfa, int beta) {
		if (terminalTest(gameboard)) {
			gameboard.utility = didComputerWin(gameboard);
			return gameboard.utility;
		}
		int bestValue = Integer.MIN_VALUE;
		gameboard.createChilds(1);
		nodes += gameboard.nodes.size();
		for (Game n : gameboard.nodes) {
			bestValue = Math.max(bestValue, minValueAlphaBeta(n, alfa, beta));
			if (bestValue > beta) {
				return bestValue;
			}
			alfa = Math.max(alfa, bestValue);
			n.utility = bestValue;
		}
		return bestValue;
	}

	private int minValueAlphaBeta(Game gameboard, int alfa, int beta) {
		if (terminalTest(gameboard)) {
			gameboard.utility = didComputerWin(gameboard);
			return gameboard.utility;
		}
		int bestValue = Integer.MAX_VALUE;
		gameboard.createChilds(2);
		nodes += gameboard.nodes.size();
		for (Game n : gameboard.nodes) {
			bestValue = Math.min(bestValue, maxValueAlphaBeta(n, alfa, beta));
			if (bestValue < alfa) {
				return bestValue;
			}
			beta = Math.min(beta, bestValue);
			n.utility = bestValue;
		}
		return bestValue;
	}

	public int didComputerWin(Game gameboard) {
		int aux = 10 - gameboard.filled;
		if (getScore(gameboard, 1)) {
			return aux;
		}
		if (getScore(gameboard, 2)) {
			return aux * -1;
		}

		return aux*0;
	}

	// verifica se um jogador ganhou
	boolean getScore(Game gameboard, int player) {
		if (gameboard._state[0][0] == player
				&& gameboard._state[1][1] == player
				&& gameboard._state[2][2] == player) {
			return true;
		}
		if (gameboard._state[0][2] == player
				&& gameboard._state[1][1] == player
				&& gameboard._state[2][0] == player) {
			return true;
		}
		for (int i = 0; i < 3; i++) {
			if (gameboard._state[0][i] == player
					&& gameboard._state[1][i] == player
					&& gameboard._state[2][i] == player) {
				return true;
			}
			if (gameboard._state[i][0] == player
					&& gameboard._state[i][1] == player
					&& gameboard._state[i][2] == player) {
				return true;
			}
		}
		return false;
	}

	public void success(int player, double cpuPlayTime, double gameTime) {
		if (player > 0)
			System.out.println("CPU Ganhou.");
		else if (player < 0)
			System.out.println("Parabens, ganhou um pacote de bolachas!");
		else
			System.out.println("Empatou");
		System.out.println("O jogo demorou um total de " + gameTime/1000000 + " milisegundos.");
		System.out
				.println("O computador passou "+cpuPlayTime / 1000000 + " milisegundos a calcular jogadas.");
		System.out.println("No total foram criados " + totalNodes + " nos.");
		System.exit(0);
	}
}
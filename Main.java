//package ia;

import java.util.Scanner;

public class Main {

	static int[] convertMove(int move) {
		int pos[] = new int[2];
		switch (move) {
		case 1: pos[0] = 0;	pos[1] = 0;	break;
		case 2: pos[0] = 0;	pos[1] = 1;	break;
		case 3:	pos[0] = 0;	pos[1] = 2;	break;
		case 4:	pos[0] = 1;	pos[1] = 0;	break;
		case 5:	pos[0] = 1;	pos[1] = 1;	break;
		case 6:	pos[0] = 1;	pos[1] = 2;	break;
		case 7:	pos[0] = 2;	pos[1] = 0;	break;
		case 8:	pos[0] = 2;	pos[1] = 1;	break;
		case 9:	pos[0] = 2;	pos[1] = 2;	break;
		default:break;
		}
		return pos;
	}

	public static void main(String agrs[]) {
		int op, player;
		Game gameboard;
		Scanner in = new Scanner(System.in);

		System.out.println("Bem vindo ao Jogo do Galo");
		System.out
				.println("Escolha o algoritmo de jogo:\n1 - Min-max\t2 - Alpha-beta");
		op = in.nextInt();
		while (op != 1 && op != 2) {
			System.out
					.println("Nao escolheu uma opcao valida, tente outra vez!");
			System.out.println("1 - Min-max\t2 - Alpha-beta");
			op = in.nextInt();
		}
		System.out
				.println("Quem joga primeiro?\n1 - Computador\t2 - Utilizador");
		player = in.nextInt();
		while (player != 1 && player != 2) {
			System.out
					.println("Nao escolheu uma opcao valida, tente outra vez!");
			System.out.println("1 - Computador\t2 - Utilizador");
			player = in.nextInt();
		}
		System.out.println("Deseja jogar com qual simbolo?\n1 - X\t2 - O");
		int symbol = in.nextInt();
		while (player != 1 && player != 2) {
			System.out
					.println("Nao escolheu uma opcao valida, tente outra vez!");
			System.out.println("1 - X\t2 - O");
			symbol = in.nextInt();
		}
		gameboard = new Game(new int[3][3], null);

		Algorithm algoritmo = new Algorithm();

		char cpuChar, userChar;
		if (symbol == 1) {
			cpuChar = 'O';
			userChar = 'X';
		}

		else {
			cpuChar = 'X';
			userChar = 'O';
		}

		gameboard.stateprint(cpuChar, userChar);
		double cpuPlayTime = 0;
		double gameTime = System.nanoTime();
		// comeca o jogo
		while (true) {

			// joga o user
			if (player % 2 == 0) {
				System.out.println("Chegou a sua vez de jogar!");
				System.out
						.println("Escolha um numero entre o 1 e o 9 para fazer a sua jogada.");
				int move = in.nextInt();
				int[] pos = new int[2];
				pos = convertMove(move);
				boolean moveTest = gameboard.isEmpty(pos);
				moveTest = moveTest && (move > 0 && move < 10);
				// enquanto a jogada nao for valida (celula ja preenchida ou
				// celula escolhida inexistente)
				while (!moveTest) {
					System.out.println("Jogada impossivel");
					System.out
							.println("Escolha um numero entre o 1 e o 9 para fazer a sua jogada.");
					move = in.nextInt();
					pos = convertMove(move);
					moveTest = gameboard.isEmpty(pos);
					moveTest = moveTest && (move > 0 && move < 10);
				}
				gameboard._state[pos[0]][pos[1]] = 2;
			}
			// joga o CPU
			else {
				System.out.println("Chegou a vez do computador jogar!");
				double startTime = System.nanoTime();
				if (op == 1) {
					gameboard = algoritmo.minimaxDecision(gameboard, 2);
				} else {
					gameboard = algoritmo.alphaBetaSearch(gameboard);
				}
				gameboard.nodes.clear();
				startTime = System.nanoTime() - startTime;
				cpuPlayTime += startTime;
				System.out.println("O computador demorou "+ startTime/1000000+" milisegundos a calcular a jogada.");

			}

			System.out.println();
			gameboard.stateprint(cpuChar, userChar);
			System.out.println();

			// verificar se ja existe um vencedor ou se o jogo chegou ao fim
			int winner = algoritmo.didComputerWin(gameboard);
			// verificar se existe um vencedor
			if (winner != 0){
				gameTime = System.nanoTime() - gameTime;
				algoritmo.success(winner, cpuPlayTime, gameTime);
			}
			if(winner == 0 && gameboard.isLeaf()){
				gameTime = System.nanoTime() - gameTime;
				algoritmo.success(winner, cpuPlayTime, gameTime);
			}
			// se nao houver vencedores mas se o tabuleiro estiver cheio temos
			// um empate
			// passa vez: cpu -> user ; user -> cpu
			player++;
			player = player % 2;
		}
	}
}
